package us.rayandrew.utility;

/**
 * Created by rufus on 4/1/2017.
 */
public class Utility {
  /**
   * minOfRow will return the minimum value of table on specific row.
   *
   * @param matrixTable matrix of integer
   * @param rowBound    specific row
   * @return minRow
   */
  private static int minOfRow(int[][] matrixTable, int rowBound) {
    int min = matrixTable[rowBound][0];
    for (int idx = 1; idx < matrixTable.length; idx++) {
      if (matrixTable[rowBound][idx] != 99999) {
        min = matrixTable[rowBound][idx] < min ? matrixTable[rowBound][idx] : min;
      }
    }
    return min;
  }

  /**
   * minOfCol will return the minimum value of table based on specific column.
   *
   * @param matrixTable matrix of integer
   * @param colBound    specific row
   * @return minCol
   */
  private static int minOfCol(int[][] matrixTable, int colBound) {
    int min = matrixTable[0][colBound];
    for (int idx = 1; idx < matrixTable.length; idx++) {
      if (matrixTable[idx][colBound] != 99999) {
        min = matrixTable[idx][colBound] < min ? matrixTable[idx][colBound] : min;
      }
    }
    return min;
  }

  /**
   * Procedure to reduce the cost of all row.
   * <p>
   * Formula is = matrixTable[idx][jdx] - minOfRow
   * </p>
   *
   * @param matrixTable matrix of integer
   * @return minTotal
   */
  public static int rowReduction(int[][] matrixTable) {
    int minTotal = 0;
    for (int idx = 0; idx < matrixTable.length; idx++) {
      int rowMin = minOfRow(matrixTable, idx);
      if (rowMin != 99999) {
        for (int jdx = 0; jdx < matrixTable[idx].length; jdx++) {
          if (matrixTable[idx][jdx] != 99999) {
            matrixTable[idx][jdx] = matrixTable[idx][jdx] - rowMin;
          }
        }
        minTotal += rowMin;
      }
    }
    return minTotal;
  }

  /**
   * Procedure to reduce the cost of all column.
   * <p>
   * Formula is = matrixTable[idx][jdx] - minOfCol
   * </p>
   *
   * @param matrixTable matrix of integer
   * @return minTotal
   */
  public static int colReduction(int[][] matrixTable) {
    int minTotal = 0;
    for (int jdx = 0; jdx < matrixTable[0].length; jdx++) {
      int colMin = minOfCol(matrixTable, jdx);
      //System.out.println("Colmin = " + colMin);
      if (colMin != 99999) {
        for (int idx = 0; idx < matrixTable.length; idx++) {
          if (matrixTable[idx][jdx] != 99999) {
            matrixTable[idx][jdx] = matrixTable[idx][jdx] - colMin;
          }
        }
        minTotal += colMin;
      }
    }
    return minTotal;
  }

  /**
   * minOfArr will return the minimum value in array.
   *
   * @param arrInput array input
   * @return minimum value
   */
  public static int minOfArr(int[] arrInput) {
    int min = 0;
    for (int idx = 1; idx < arrInput.length; idx++) {
      if (arrInput[idx] != 99999) {
        min = arrInput[idx] < arrInput[min] ? idx : min;
      }
    }
    return min;
  }

  /**
   * minOfArr will return the minimum value in array with range.
   *
   * @param arrInput  array input
   * @param fromIndex starting index
   * @param toIndex   last index
   * @return minimum value within range
   */
  public static int minOfArr(int[] arrInput, int fromIndex, int toIndex) {
    int min = fromIndex;
    int bound = toIndex > arrInput.length ? arrInput.length - 1 : toIndex;
    for (int idx = fromIndex + 1; idx <= bound; idx++) {
      if (arrInput[idx] != 99999) {
        min = arrInput[idx] < arrInput[min] ? idx : min;
      }
    }
    return min;
  }

  /**
   * minOfArr will return the minimum value in array with range and exception.
   *
   * @param arrInput   array input
   * @param fromIndex  starting index
   * @param toIndex    last index
   * @param excludeInt integer excluded
   * @return minimum value within range
   */
  public static int minOfArr(int[] arrInput, int fromIndex, int toIndex, int excludeInt) {
    if (fromIndex == toIndex) {
      return fromIndex;
    } else {
      int min = fromIndex == excludeInt ? fromIndex + 1 : fromIndex;
      int bound = toIndex > arrInput.length ? arrInput.length - 1 : toIndex;
      for (int idx = fromIndex + 1; idx <= bound; idx++) {
        if (arrInput[idx] != 99999 && idx != excludeInt) {
          min = arrInput[idx] < arrInput[min] ? idx : min;
        }
      }
      return min;
    }
  }

  /**
   * Finding the second minimum of array.
   *
   * @param arrInput array input
   * @return second index that has minimum value
   */
  public static int secondMinOfArr(int[] arrInput) {
    int min = 0;
    int secondMin = 1;
    for (int idx = 1; idx < arrInput.length; idx++) {
      if (arrInput[idx] != 99999) {
        if (arrInput[idx] <= arrInput[min]) {
          secondMin = min;
          min = idx;
        } else if (arrInput[idx] <= arrInput[secondMin] && arrInput[idx] > arrInput[min]) {
          secondMin = idx;
        }
      }
    }
    return secondMin;
  }

  /**
   * Sleep for holding operation for 1 second.
   */
  public static void sleep() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
