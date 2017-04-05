package us.rayandrew.algorithm;

import java.io.File;
import java.io.FileNotFoundException;

import org.graphstream.graph.Node;
import us.rayandrew.utility.Utility;

/**
 * Class ReducedCostMatrix
 * <p>
 * Inherited from GraphMatrix
 * </p>
 * Created by rayandrew on 3/30/2017.
 */
public class ReducedCostMatrix extends GraphMatrix {
  private int[] cost;
  private int[][] reduced;

  /**
   * ReduceCostMatrix constructor.
   * @param file file external
   * @throws FileNotFoundException if file not found
   */
  public ReducedCostMatrix(File file) throws FileNotFoundException {
    super(file);
    //nameOfGraph = "ReducedCostMatrix";
    cost = new int[row]; // max allocation for spanning the graph
    reduced = new int[row][col];
    visited = new boolean[row];
    visited[0] = true;
    pathOf = new int[row + 1];
    pathOf[neffOfPath] = 0;
    pathOf[row] = 0;
    neffOfPath++;
    for (int idx = 0; idx < row; idx++) {
      System.arraycopy(weightMatrix[idx], 0, reduced[idx], 0, col);
    }
    cost[0] = Utility.rowReduction(reduced) + Utility.colReduction(reduced);
  }

  private int[][] reducingMatrix(int fromLoc, int toLoc) {
    int[][] matrixTemporary = new int[row][col];
    for (int idx = 0; idx < row; idx++) {
      System.arraycopy(reduced[idx], 0, matrixTemporary[idx], 0, col);
    }
    for (int idx = 0; idx < matrixTemporary.length; idx++) {
      matrixTemporary[fromLoc][idx] = 99999;
    }
    for (int idx = 0; idx < matrixTemporary.length; idx++) {
      matrixTemporary[idx][toLoc] = 99999;
    }
    matrixTemporary[toLoc][fromLoc] = 99999;

    return matrixTemporary;
  }

  private int calculateBound(int fromLoc, int toLoc) {
    int[][] matrixTemporary = reducingMatrix(fromLoc, toLoc);
    return (
        Utility.rowReduction(matrixTemporary)
            + Utility.colReduction(matrixTemporary)
            + cost[fromLoc]
            + reduced[fromLoc][toLoc]);
  }

  private boolean checkVisited() {
    boolean check = true;
    for (int idx = 0; idx < row; idx++) {
      check = check && visited[idx];
    }
    return check;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(row);
    str.append(" X ");
    str.append(col);
    str.append("\n");
    for (int idx = 0; idx < row; idx++) {
      for (int jdx = 0; jdx < col; jdx++) {
        str.append(reduced[idx][jdx]);
        str.append(" ");
      }
      str.append("\n");
    }
    return str.toString();
  }

  @Override
  public void run() {
    for (int jdx = 1; jdx < col; jdx++) {
      System.out.println("Calculate Bound from " + 0 + " to " + jdx);
      cost[jdx] = calculateBound(0, jdx);
      System.out.println("Cost = " + cost[jdx]);
    }
    int minimum = Utility.minOfArr(cost, 1, col - 1);
    System.out.println("Minimum = " + minimum);
    finalCost = cost[minimum];
    pathOf[neffOfPath] = minimum;
    reduced = reducingMatrix(0, minimum);
    visited[minimum] = true;
    neffOfPath++;

    int minimumAfter;
    while (!checkVisited() && neffOfPath < row) {
      for (int idx = 1; idx < row; idx++) {
        if (!visited[idx]) {
          //System.out.println("Calculate Bound from " + minimum + " to " + idx);
          cost[idx] = calculateBound(minimum, idx);
          //System.out.println("Cost = " + cost[idx]);
          nodeGenerated++;
        }
      }

      minimumAfter = Utility.minOfArr(cost, 1, col - 1, minimum);
      System.out.println("MinimumAfter = " + minimumAfter);
      finalCost = cost[minimumAfter];
      pathOf[neffOfPath] = minimumAfter;
      reduced = reducingMatrix(minimum, minimumAfter);
      visited[minimumAfter] = true;
      minimum = minimumAfter;
      for (int idx = 0; idx < row; idx++) {
        if (visited[idx] && idx != minimumAfter) {
          cost[idx] = 99999;
        }
      }
      neffOfPath++;
    }
    bestPath();
  }
}
