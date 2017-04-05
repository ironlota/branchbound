package us.rayandrew;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Main class inherited from JFrame.
 * Will implements swing to make GUI
 * Created by rayandrew on 3/30/2017.
 */
class MainClass extends JFrame {
  /**
   * Static main procedure.
   * Will initialize the Swing GUI
   *
   * @param argv argument that passed
   */
  public static void main(String[] argv) {
    try {
      UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }
    MainClassForm form = new MainClassForm();
    Thread thread = new Thread(form);
    thread.start();
    try {
      thread.join();
      System.out.println("\nEnd of simulating --");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}