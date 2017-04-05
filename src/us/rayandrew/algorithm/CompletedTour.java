package us.rayandrew.algorithm;

import java.io.File;
import java.io.FileNotFoundException;

import us.rayandrew.utility.Utility;

/**
 * Completed Tour class inherited from GraphMatrix.
 * Will implement the "Completed Tour Algorithm"
 * Created by rufus on 4/1/2017.
 */
public class CompletedTour extends GraphMatrix {
  private boolean[] visited;
  private int neffOfPath = 0;
  int temp;
  int currentCost = 0;

  /**
   * Completed Tour Constructor.
   *
   * @param file path of external file
   * @throws FileNotFoundException if the file is not found at the path
   */
  public CompletedTour(File file) throws FileNotFoundException {
    super(file);
    //nameOfGraph = "CompletedTour";
    finalCost = 99999;
    visited = new boolean[row];
    visited[0] = true;
    for (int idx = 1; idx < row; idx++) {
      visited[idx] = false;
    }
    pathOf[neffOfPath] = 0;
    neffOfPath++;
  }

  /**
   * Recursive of Completed Tour Algorithm.
   * @param currentBound of last node visited
   * @param currentWeight of last node visited
   * @param level basis of recursive
   * <p>
   *  will back to initial node, that's why level maximum is
   *  equal to node amount + 1
   * </p>
   */
  private void calculateBound(int currentBound, int currentWeight, int level) {
    // basis of recursive
    if (level == row) {
      if (weightMatrix[pathOf[level - 1]][pathOf[0]] != 99999) {
        currentCost = currentWeight + weightMatrix[pathOf[level - 1]][pathOf[0]];
        if (currentCost < finalCost && currentCost > 0 && finalCost > 0) {
          bestPath();
          finalCost = currentCost;
        }
      }
      return;
    } else {
      nodeGenerated++;
      // recursive
      for (int idx = 0; idx < row; idx++) {
        if (weightMatrix[pathOf[level - 1]][idx] != 99999 && !visited[idx]) {
          currentWeight += weightMatrix[pathOf[level - 1]][idx];
          temp = currentBound;
          if (level == idx) {
            currentBound = (
                weightMatrix[idx][pathOf[level - 1]]
                    + Utility.minOfArr(weightMatrix[idx]) == pathOf[level - 1]
                    ? weightMatrix[idx][Utility.secondMinOfArr(weightMatrix[idx])]
                    : weightMatrix[idx][Utility.minOfArr(weightMatrix[idx])]
              ) / 2;
          } else {
            currentBound = (
                weightMatrix[idx][Utility.secondMinOfArr(weightMatrix[idx])]
                    + weightMatrix[idx][Utility.minOfArr(weightMatrix[idx])]
              ) / 2;
          }
          if (currentBound + currentWeight < finalCost) {
            pathOf[level] = idx;
            visited[idx] = true;
            calculateBound(currentBound, currentWeight, level + 1);
          } else {
            return;
          }

          currentWeight -= weightMatrix[pathOf[level - 1]][idx];
          currentBound = temp;

          for (int jdx = 0; jdx < row; jdx++) {
            visited[jdx] = false;
          }

          for (int jdx = 0; jdx < level; jdx++) {
            visited[pathOf[jdx]] = true;
          }
        }
      }
    }
  }

  /**
   * Implement run method from runnable interface.
   */
  @Override
  public void run() {
    int currentBound = 0;
    for (int idx = 0; idx < row; idx++) {
      currentBound += weightMatrix[idx][Utility.minOfArr(weightMatrix[idx])]
          + weightMatrix[idx][Utility.secondMinOfArr(weightMatrix[idx])];
    }
    currentBound = (currentBound % 2) == 1 ? currentBound / 2 + 1 : currentBound / 2;
    System.out.println("currentBound node 0 = " + currentBound);
    nodeGenerated++;
    calculateBound(currentBound, 0, 1);
  }
}
