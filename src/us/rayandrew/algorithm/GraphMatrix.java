package us.rayandrew.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import us.rayandrew.utility.Utility;

/**
 * Created by rufus on 3/31/2017.
 */
public abstract class GraphMatrix implements Runnable {
  protected String nameOfGraph;
  final int[][] weightMatrix;
  Graph graph = new SingleGraph("Branch and Bound");
  Node[] nodes = null;
  Edge[][] edges = null;
  int[] pathOf;
  int col;
  int row;
  protected int nodeGenerated = 0;
  protected int finalCost;
  protected int[] finalPathOf;
  protected int neffOfPath = 0;
  protected boolean[] visited;

  GraphMatrix() {
    weightMatrix = new int[1][1];
  }

  GraphMatrix(File file) throws FileNotFoundException {
    Scanner scanner = new Scanner(file);
    nameOfGraph = scanner.next();
    System.out.println(nameOfGraph);
    row = scanner.nextInt();
    String not = scanner.findInLine("X");
    col = scanner.nextInt();
    nodes = new Node[row];
    edges = new Edge[row][col];
    weightMatrix = new int[row][col];
    pathOf = new int[row + 1];
    finalPathOf = new int[row + 1];
    for (int idx = 0; idx < row; idx++) {
      for (int jdx = 0; jdx < col; jdx++) {
        weightMatrix[idx][jdx] = scanner.nextInt();
      }
    }

    char alp = 'A';
    for (Integer idx = 0; idx < row; idx++) {
      nodes[idx] = graph.addNode(String.valueOf(alp));
      alp++;
    }

    for (int idx = 0; idx < row; idx++) {
      for (int jdx = 0; jdx < col; jdx++) {
        if (idx != jdx && !graph.getNode(nodes[idx].getId()).hasEdgeBetween(nodes[jdx].getId())) {
          if (weightMatrix[idx][jdx] != weightMatrix[jdx][idx]) {
            graph.addEdge(
                nodes[idx].getId() + nodes[jdx].getId(),
                nodes[idx].getId(),
                nodes[jdx].getId(),
                true
            ).addAttribute("weight", weightMatrix[idx][jdx]);
            graph.addEdge(
                nodes[jdx].getId() + nodes[idx].getId(),
                nodes[jdx].getId(),
                nodes[idx].getId(),
                true
            ).addAttribute("weight", weightMatrix[jdx][idx]);
          } else {
            graph.addEdge(
                nodes[idx].getId() + nodes[jdx].getId(),
                nodes[idx].getId(),
                nodes[jdx].getId(),
                false
            ).addAttribute("weight", weightMatrix[jdx][idx]);
          }
        }
      }
    }
    graph.addAttribute("ui.quality");
    graph.addAttribute("ui.antialias");
    graph.addAttribute("ui.stylesheet", "url('./data/style/Style.css')");
  }

  /**
   * getGraph() method.
   * @return GraphStream graph.
   */
  public Graph getGraph() {
    return graph;
  }

  /**
   * getWeightMatrix() method.
   * @return weightMatrix from external file
   */
  public int[][] getWeightMatrix() {
    return weightMatrix;
  }

  /**
   * getRow() method.
   * @return return the value of row
   */
  public int getRow() {
    return row;
  }

  /**
   * getCol() method.
   * @return return the value of column
   */
  public int getCol() {
    return col;
  }

  /**
   * printWeightMatrix() method.
   * Will print weight adjacency matrix
   */
  public void printWeightMatrix() {
    System.out.print(row);
    System.out.print(" X ");
    System.out.println(col);
    for (int idx = 0; idx < row; idx++) {
      for (int jdx = 0; jdx < col; jdx++) {
        System.out.print(weightMatrix[idx][jdx]);
        System.out.print(" ");
      }
      System.out.println();
    }
  }

  /**
   * getPathOf() method.
   * @return current pathOf
   */
  public int[] getPathOf() {
    return pathOf;
  }

  /**
   * Simulate the way of finalPathOf.
   * will colouring the edge
   */
  public void simulate() {
    char alp = 'A';

    for (int idx = 0; idx <= row; idx++) {
      if (idx < row) {
        if (graph.getEdge(String.valueOf((char) (alp + finalPathOf[idx]))
            + String.valueOf((char) (alp + finalPathOf[idx + 1]))) != null) {
          graph.getEdge(
              String.valueOf((char) (alp + finalPathOf[idx]))
              + String.valueOf((char) (alp + finalPathOf[idx + 1]))
          ).setAttribute("ui.class", "marked");
          System.out.print(
              String.valueOf((char) (alp + finalPathOf[idx]))
                  + String.valueOf((char) (alp + finalPathOf[idx + 1]))
                  + " "
          );
        } else if (graph.getEdge(String.valueOf((char) (alp + finalPathOf[idx + 1]))
            + String.valueOf((char) (alp + finalPathOf[idx]))) != null) {
          graph.getEdge(
              String.valueOf((char) (alp + finalPathOf[idx + 1]))
              + String.valueOf((char) (alp + finalPathOf[idx]))
          ).setAttribute("ui.class", "marked");
          System.out.print(
              String.valueOf((char) (alp + finalPathOf[idx]))
                  + String.valueOf((char) (alp + finalPathOf[idx + 1]))
                  + " "
          );
        }
        Utility.sleep();
      }
    }
  }


  /**
   * Method getFinalPathOf().
   *
   * @return finalPathOf that contain best path of completed tour
   */
  public int[] getFinalPathOf() {
    return finalPathOf;
  }

  /**
   * Method getFinalCost().
   *
   * @return finalCost that holds the best total cost of the tour
   */
  public int getFinalCost() {
    return finalCost;
  }

  /**
   * private method bestPath.
   * Will copy the last and best path from array pathOf to array finalPathOf
   */
  public void bestPath() {
    System.arraycopy(pathOf, 1, finalPathOf, 1, row);
    finalPathOf[0] = 0;
    finalPathOf[row] = 0;
  }

  /**
   * getNodeGenerated() method.
   * @return node generated each algorithm
   */
  public int getNodeGenerated() {
    return nodeGenerated;
  }

  /**
   * getNameOfGraph() method.
   * @return nameOfGraph
   */
  public String getNameOfGraph() {
    return  nameOfGraph;
  }
}