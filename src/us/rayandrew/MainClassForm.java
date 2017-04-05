package us.rayandrew;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import us.rayandrew.algorithm.CompletedTour;
import us.rayandrew.algorithm.GraphMatrix;
import us.rayandrew.algorithm.ReducedCostMatrix;

/**
 * Created by rufus on 4/3/2017.
 */
public class MainClassForm extends JFrame implements Runnable {
  private static Graph graph;
  private static Layout layout = new SpringBox(true);
  private JTabbedPane tabbedPane1;
  private JPanel graphStream;
  private JTable weightMatrix;
  private JScrollPane graphPanel;
  private JPanel parentRoot;
  private JScrollPane weightMatrixContainer;
  private JButton generateGraph;
  private JButton openFileButton;
  private JComboBox typeOfGraph;
  private JLabel durationLabel;
  private JLabel pathLabel;
  private JLabel nodeGeneratedLabel;
  private JLabel finalCostLabel;
  private JLabel pathName;
  private GraphMatrix graphParser;
  private Thread thread;
  private Viewer viewer;
  private View view;
  private File selectedFile;

  /**
   * MainClassForm() constructor.
   * Initialize GUI Swing
   */
  public MainClassForm() {
    super("Branch and Bound");
    setContentPane(parentRoot);
    pack();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(320, 240);
    setLocationRelativeTo(null);
    setVisible(true);
    System.setProperty(
        "org.graphstream.ui.renderer",
        "org.graphstream.ui.j2dviewer.J2DGraphRenderer"
    );
  }

  @Override
  public void run() {
    String[] typeOfGraphString = {"Reduced Cost Matrix", "Completed Tour"};
    DefaultComboBoxModel dcm = new DefaultComboBoxModel(typeOfGraphString);
    typeOfGraph.setModel(dcm);
    typeOfGraph.setSelectedIndex(1);

    openFileButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog((Component) e.getSource());
      if (result == JFileChooser.APPROVE_OPTION) {
        selectedFile = fileChooser.getSelectedFile();
        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        pathName.setText(selectedFile.getAbsolutePath());
      }
    });

    try {
      if (typeOfGraph.getSelectedItem().equals("Reduced Cost Matrix")) {
        selectedFile = new File("./data/testcase/rcm_tc3.txt");
        graphParser = new ReducedCostMatrix(selectedFile);
      } else {
        selectedFile = new File("./data/testcase/ct_tc4.txt");
        graphParser = new CompletedTour(selectedFile);
      }
      thread = new Thread(graphParser);
      graph = graphParser.getGraph();
      viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
      viewer.enableAutoLayout();
      view = viewer.addDefaultView(false);
      graph.addSink(layout);
      graph.setStrict(false);
      graph.setAutoCreate(true);

      graphPanel.getViewport().add((Component) view);

      String[] column;
      column = new String[graphParser.getCol()];

      for (int idx = 0; idx < graphParser.getRow(); idx++) {
        column[idx] = graphParser.getGraph().getNode(idx).getId();
      }

      String[][] data;
      data = new String[graphParser.getRow()][graphParser.getCol()];
      for (int idx = 0; idx < graphParser.getRow(); idx++) {
        for (int jdx = 0; jdx < graphParser.getCol(); jdx++) {
          data[idx][jdx] = String.valueOf(graphParser.getWeightMatrix()[idx][jdx]);
        }
      }

      DefaultTableModel tableModel = new DefaultTableModel(column, 0) {
        public boolean isCellEditable(int row, int column) {
          return false;//This causes all cells to be not editable
        }
      };
      for (int idx = 0; idx < graphParser.getRow(); idx++) {
        tableModel.addRow(data[idx]);
      }
      weightMatrix.setModel(tableModel);
      for (Node node : graph) {
        node.addAttribute("ui.label", node.getId());
      }

      for (Edge edge : graph.getEachEdge()) {
        edge.addAttribute("ui.label", edge.getAttribute("weight").toString());
      }
    } catch (FileNotFoundException g) {
      g.printStackTrace();
    }

    generateGraph.addActionListener(e -> {
      FileSinkImages pic = new FileSinkImages(
          FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.VGA
      );
      int index = selectedFile.getName().indexOf(".txt");
      System.out.println();
      pic.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
      try {
        pic.writeAll(
            graphParser.getGraph(),
            "./screenshot/"
            + selectedFile.getName().substring(0, index)
            + ".png"
        );
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });

    long startTime = System.nanoTime();
    thread.start();
    try {
      thread.join();
      long endTime = System.nanoTime();
      Float duration = Float.valueOf((endTime - startTime) / 1000000);
      System.out.println("Duration = " + duration + " ms");
      nodeGeneratedLabel.setText(String.valueOf(graphParser.getNodeGenerated()));
      finalCostLabel.setText(String.valueOf(graphParser.getFinalCost()));
      StringBuilder str = new StringBuilder();
      char alp = 'A';
      for (int idx = 0; idx <= graphParser.getRow(); idx++) {
        str.append(String.valueOf((char)(alp + graphParser.getFinalPathOf()[idx])) + " ");
        pathLabel.setText(str.toString());
      }
      durationLabel.setText(duration + " ms");
      System.out.println("\nEdge visited :");
      graphParser.simulate();
    } catch (InterruptedException a) {
      a.printStackTrace();
    }
    while (layout.getStabilization() < 0.9) {
      layout.compute();
    }
  }
}
