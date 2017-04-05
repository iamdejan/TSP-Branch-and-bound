import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winarto on 04/01/2017.
 */
public class TourLengkap {
  Matrices mainMatrix;
  float tourCost;
  int simpulDibangkitkan;
  boolean[] dikunjungi;
  float finalCost;
  List<Integer> finalPath;

  public TourLengkap(Matrices inMatrix) {
    simpulDibangkitkan = 0;
    finalPath = new ArrayList<>();
    mainMatrix = inMatrix;
    finalCost = 9999f;
    tourCost = countMinCost(mainMatrix);
    dikunjungi = new boolean[mainMatrix.getLength()];
    for(int idx = 0;idx<mainMatrix.getLength();idx++) {
      dikunjungi[idx] = false;
    }
    int[] tempPath = new int[mainMatrix.getLength()];
    tempPath[0] = 0;
    dikunjungi[0] = true;
    solveTSP2(0,tourCost,1,tempPath);
  }

  public void solveTSP2(float curWeight, float curBound, int level, int[] path) {
    if(level == mainMatrix.getLength()) {
      float tempCost = curWeight + mainMatrix.getMatrices()[path[level-1]][0];
      if(tempCost < finalCost) {
        finalCost = tempCost;
        finalPath.clear();
        for(int idx =0;idx<level;idx++) {
          finalPath.add(path[idx]);
        }
      }
      simpulDibangkitkan++;
      return;
    }
    for(int idx = 0;idx<mainMatrix.getLength();idx++) {
      int matrixVal = mainMatrix.getMatrices()[path[level-1]][idx];
      if(matrixVal != -1 && !dikunjungi[idx]) {
        float tempBound;
        if(level == 1) {
          tempBound = countMinCost(mainMatrix,0,idx,curBound);
        } else {
          tempBound = countMinCost(mainMatrix,path[level-1],idx,curBound);
        }
        if(tempBound+curWeight+matrixVal < finalCost) {
          dikunjungi[idx] = true;
          path[level] = idx;
          solveTSP2(curWeight+(float)matrixVal,tempBound,level+1,path);
        }
        for(int jdx=0;jdx<mainMatrix.getLength();jdx++) {
          dikunjungi[jdx] = false;
        }
        for(int jdx=0;jdx<level;jdx++) {
          dikunjungi[path[jdx]] = true;
        }
        simpulDibangkitkan++;
      }
    }
  }

  private float countMinCost(Matrices matrix) {
    float totalCost = 0f;
    for(int idx = 0;idx<matrix.getLength();idx++) {
      int firstMin = getFirstMin(matrix.getMatrices()[idx]);
      int secondMin = getSecondMin(matrix.getMatrices()[idx]);
      totalCost += (float)(firstMin+secondMin);
    }
    totalCost *= 0.5f;
    return totalCost;
  }

  private float countMinCost(Matrices matrix, int from, int to, float curBound) {
    float totalCost = curBound;
    if(from == 0) {
      totalCost -= ((getFirstMin(matrix.getMatrices()[from])+getFirstMin(matrix.getMatrices()[to]))/2f);
    } else {
      totalCost -= ((getSecondMin(matrix.getMatrices()[from])+getFirstMin(matrix.getMatrices()[to]))/2f);
    }
    return totalCost;
  }

  private int getFirstMin(int[] numbers) {
    int min = 9999;
    for(int idx = 0;idx < numbers.length;idx++) {
      if(numbers[idx] != -1 && numbers[idx] < min) {
        min = numbers[idx];
      }
    }
    return min;
  }

  private int getSecondMin(int[] numbers) {
    int firstMin = 9999, secondMin = 9999;
    for(int idx = 0;idx < numbers.length;idx++) {
      if(numbers[idx] != -1 && numbers[idx] < firstMin) {
        secondMin = firstMin;
        firstMin = numbers[idx];
      }
      else if(numbers[idx] < secondMin && numbers[idx] != -1) {
        secondMin = numbers[idx];
      }
    }
    return secondMin;
  }

  public List<Integer> getRoute() {
    return finalPath;
  }

  public int getSimpulDibangkitkan() {
    return simpulDibangkitkan;
  }

  public float getTourCost(){
    return finalCost;
  }
}
