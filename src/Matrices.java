/**
 * Created by user on 03/31/2017.
 */
public class Matrices {
  private int[][] matrix;
  private int length;
  private int cost;

  public Matrices(int matrixLength) {
    length = matrixLength;
    matrix = new int[length][length];
    cost = 0;
  }

  public String toString() {
    String strTemp = "";
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        strTemp += Integer.toString(matrix[i][j]) + " ";
      }
      strTemp += "\n";
    }
    return strTemp;
  }

  public void setValue(int row, int column, int value) {
    matrix[row][column] = value;
  }

  public void setCost(int value) {
    cost = value;
  }

  public int getCost() {
    return cost;
  }

  public int getLength() {
    return length;
  }

  public int[][] getMatrices() {
    return matrix;
  }
}
