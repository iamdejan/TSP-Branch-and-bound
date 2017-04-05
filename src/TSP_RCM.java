import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winarto on 03/31/2017.
 */
public class TSP_RCM {
  Matrices mainMatrix;
  Matrices rcmMatrix;
  List<Integer> processedBranch;
  int travelCost;
  int jumlahSimpul;

  TSP_RCM(Matrices matrix) {
    mainMatrix = matrix;
    processedBranch = new ArrayList<>();
    processedBranch.add(0);
    jumlahSimpul = 1;
    countReduceCostMatrix(matrix);
  }

  public void solveTSP() {
    Matrices tempMatrix = new Matrices(rcmMatrix);
    while(!getFinishStatus(tempMatrix)) {
      Matrices[] bufMatrix = new Matrices[tempMatrix.getLength()];
      for (int idx = 0; idx < tempMatrix.getLength(); idx++) {
        if (!processedBranch.contains(idx)) {
          int prevBranch = processedBranch.get(processedBranch.size() - 1);
          bufMatrix[idx] = new Matrices(tempMatrix);
          setInfinite(prevBranch, idx, bufMatrix[idx]);
          bufMatrix[idx] = getReducedMatrix(bufMatrix[idx], false);
          bufMatrix[idx] = getReducedMatrix(bufMatrix[idx], true);
          bufMatrix[idx].setCost(bufMatrix[idx].getCost() + rcmMatrix.getMatrices()[prevBranch][idx]);
          jumlahSimpul++;
        }
      }
      int minIdx = getMinCostIdx(bufMatrix);
      processedBranch.add(minIdx);
      tempMatrix = bufMatrix[minIdx];
    }
    processedBranch.add(0);
    travelCost = tempMatrix.getCost();
  }

  public int getJumlahSimpul() {
    return jumlahSimpul;
  }

  public List<Integer> getRoute() {
    return processedBranch;
  }

  public int getMinCost() {
    return travelCost;
  }

  private void countReduceCostMatrix(Matrices matrix) {
    Matrices tempMatrix = getReducedMatrix(matrix,false);
    rcmMatrix = getReducedMatrix(tempMatrix,true);
  }

  private void setInfinite(int row, int col,Matrices matrix) {
    for(int idx = 0;idx < matrix.getLength();idx++) {
      matrix.setValue(row,idx,-1);
      matrix.setValue(idx,col,-1);
    }
    matrix.setValue(col,0,-1);
  }

  private boolean getFinishStatus(Matrices matrix) {
    boolean found = true;
    int row = 0, col = 0;
    while(row < matrix.getLength() && found) {
      while(col < matrix.getLength() && found) {
        int matrixVal = matrix.getMatrices()[row][col];
        if(matrixVal != -1) {
          found = false;
        }
        col++;
      }
      col = 0;
      row++;
    }
    return found;
  }

  private int getMinCostIdx(Matrices[] matrix) {
    int minIdx = 0;
    int min = 9999;
    for(int idx = 0; idx < mainMatrix.getLength();idx++) {
      if(!processedBranch.contains(idx)) {
        if (matrix[idx].getCost() < min) {
          minIdx = idx;
          min = matrix[idx].getCost();
        }
      }
    }
    return minIdx;
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

        if(!reverse) {
          if(matrix.getMatrices()[row][idx] != -1)
            matrixVal = matrix.getMatrices()[row][idx] - min;
          else
            matrixVal = -1;
          tempMatrix.setValue(row, idx, matrixVal);
        }
        else {
          if(matrix.getMatrices()[idx][row] != -1)
            matrixVal = matrix.getMatrices()[idx][row] - min;
          else
            matrixVal = -1;
          tempMatrix.setValue(idx, row, matrixVal);
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