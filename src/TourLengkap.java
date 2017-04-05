import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winarto on 04/01/2017.
 */
public class TourLengkap {
  Matrices mainMatrix;
  float tourCost;
  List<List<Integer>> processedBranch;
  List<Float> solutionCost;
  int simpulDibangkitkan;
  boolean[] dikunjungi;
  float finalCost;
  List<Integer> finalPath;

  public TourLengkap(Matrices inMatrix) {
    simpulDibangkitkan = 0;
    finalPath = new ArrayList<>();
    mainMatrix = inMatrix;
    solutionCost = new ArrayList<>();
    finalCost = 9999f;
    tourCost = countMinCost(mainMatrix);
    float tempCost = 0f, tempBound = tourCost;
    dikunjungi = new boolean[mainMatrix.getLength()];
    for(int idx = 0;idx<mainMatrix.getLength();idx++) {
      dikunjungi[idx] = false;
    }
    int[] tempPath = new int[mainMatrix.getLength()];
    tempPath[0] = 0;
    dikunjungi[0] = true;
    solveTSP2(tempCost,tourCost,1,tempPath);
    System.out.println(simpulDibangkitkan);
  }

  public void solveTSP2(float curWeight, float curBound, int level, int[] path) {
    //System.out.println(curWeight);
    if(level == mainMatrix.getLength()) {
      float tempCost = curWeight + mainMatrix.getMatrices()[path[level-1]][0];
      if(tempCost < finalCost) {
        finalCost = tempCost;
        finalPath.clear();
        for(int idx =0;idx<level;idx++) {
          finalPath.add(path[idx]);
          //System.out.println(level + " " + dikunjungi[idx] + " " + path[idx]);
        }
      }
      //simpulDibangkitkan++;
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
          //System.out.println(idx);
          dikunjungi[idx] = true;
          path[level] = idx;
          solveTSP2(curWeight+(float)matrixVal,tempBound,level+1,path);
        }
        for(int jdx=0;jdx<mainMatrix.getLength();jdx++) {
          dikunjungi[jdx] = false;
        }
        for(int jdx=0;jdx<=level-1;jdx++) {
          dikunjungi[path[jdx]] = true;
        }
        simpulDibangkitkan++;
      }
    }
  }

  public void solveTSP() {
    float minCost = 9999f;
    List<Integer> solutionIdx = new ArrayList<>();
    //Process first level tree
    for(int idx = 1;idx < mainMatrix.getLength();idx++) {
      float branchCost = countMinCost(mainMatrix,0,idx,tourCost);
      if(branchCost < minCost) {
        minCost = branchCost;
        solutionIdx.clear();
        solutionCost.clear();
        solutionIdx.add(idx);
        solutionCost.add(branchCost);
      }
      else if(branchCost == minCost) {
        solutionIdx.add(idx);
        solutionCost.add(branchCost);
      }
    }

    processedBranch = new ArrayList<>(solutionIdx.size());
    for(int idx = 0;idx < solutionIdx.size();idx++) {
      processedBranch.add(new ArrayList<>());
      processedBranch.get(idx).add(0);
      processedBranch.get(idx).add(solutionIdx.get(idx));
      while(!getFinishStatus(processedBranch.get(idx))) {
        minCost = 9999;
        int minIdx = -1;
        for(int jdx = 0;jdx < mainMatrix.getLength();jdx++) {
          if(!processedBranch.get(idx).contains(jdx)) {
            int prevBranch = processedBranch.get(idx).get(processedBranch.get(idx).size()-1);
            float branchCost = countMinCost(mainMatrix,prevBranch,jdx,tourCost);
            if(branchCost < minCost) {
             // System.out.println("cost: " + branchCost + " prev: " + prevBranch + " next: " + jdx);
              minCost = branchCost;
              minIdx = jdx;
            }
          }
        }
        processedBranch.get(idx).add(minIdx);
        solutionCost.set(idx, minCost);
      }
      processedBranch.get(idx).add(0);
      solutionCost.set(idx, countFinalCost(processedBranch.get(idx)));
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
/*    for(int idx = 0;idx<matrix.getLength();idx++) {
      int firstMin = getFirstMin(matrix.getMatrices()[idx]);
      int secondMin = getSecondMin(matrix.getMatrices()[idx]);
      if(idx == from || idx == to) {
        int weight = matrix.getMatrices()[from][to];
        if(weight > firstMin && weight > secondMin) {
          secondMin = weight;
        }
      }
      totalCost += (float) (firstMin + secondMin);
    }
    totalCost *= 0.5f;*/
    return totalCost;
  }

  private float countFinalCost(List<Integer> solutionList) {
    float cost = 0f;
    for(int idx = 0;idx < solutionList.size()-1;idx++) {
      cost += mainMatrix.getMatrices()[solutionList.get(idx)][solutionList.get(idx+1)];
    }
    return cost;
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

  private boolean getFinishStatus(List<Integer> path) {
    boolean found = true;
    int idx = 0;
    while(idx < mainMatrix.getLength() && found) {
      if(!path.contains(idx)) {
        found = false;
      } else {
        idx++;
      }
    }
    return found;
  }

  public List<Integer> getRoute() {
/*    float min = 9999f;
    int minIdx = -1;
    for(int idx = 0;idx<solutionCost.size();idx++) {
      if (solutionCost.get(idx) < min) {
        min = solutionCost.get(idx);
        minIdx = idx;
      }
    }
    return processedBranch.get(minIdx);*/
    return finalPath;
  }

  public float getTourCost(){
    float min = 9999f;
    for(int idx = 0;idx<solutionCost.size();idx++) {
      if(solutionCost.get(idx) < min) {
        min = solutionCost.get(idx);
      }
    }
    return min;
  }
}
