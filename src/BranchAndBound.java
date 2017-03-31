/**
 * Created by Winarto on 03/31/2017.
 */
public class BranchAndBound {
  Matrices mainMatrix;
  Matrices rcmMatrix;

  BranchAndBound(Matrices matrix) {
    mainMatrix = matrix;
    countReduceCostMatrix(matrix);
    System.out.println(mainMatrix.toString());
    System.out.print(rcmMatrix.toString());
  }

  public void SolveTSP() {

  }

  private void countReduceCostMatrix(Matrices matrix) {
    Matrices tempMatrix = getReducedMatrix(matrix,false);
    rcmMatrix = getReducedMatrix(tempMatrix,true);
    System.out.println(rcmMatrix.getCost());
  }

  private Matrices getReducedMatrix(Matrices matrix,boolean reverse) {
    Matrices tempMatrix = new Matrices(matrix.getLength());
    int row = 0, col = 0;
    int min = 9999;
    int tempCost = 0;

    while (row < matrix.getLength()) {
      while (col < matrix.getLength()) {
        int matrixVal;

        if(!reverse)
          matrixVal = matrix.getMatrices()[row][col];
        else
          matrixVal = matrix.getMatrices()[col][row];

        if (matrixVal != -1 && matrixVal < min) {
          min = matrixVal;
        }
        col++;
      }
      if(min == 9999) {
        min = 0;
      }
      for (int idx = 0; idx < matrix.getLength(); idx++) {
        int matrixVal;

        if(!reverse)
          matrixVal = matrix.getMatrices()[row][idx] - min;
        else
          matrixVal = matrix.getMatrices()[idx][row] - min;

        if (row != idx) {
          if(!reverse)
            tempMatrix.setValue(row, idx, matrixVal);
          else
            tempMatrix.setValue(idx, row, matrixVal);
        }
        else {
          tempMatrix.setValue(row, idx, -1);
        }
      }
      tempCost += min;
      min = 9999;
      col = 0;
      row++;
    }
    tempMatrix.setCost(tempCost + matrix.getCost());
    return tempMatrix;
  }
}
