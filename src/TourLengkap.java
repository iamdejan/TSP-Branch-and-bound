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

  public TourLengkap(Matrices inMatrix) {
    mainMatrix = inMatrix;
    solutionCost = new ArrayList<>();
    tourCost = countMinCost(mainMatrix);
  }

  public void solveTSP() {
    float minCost = 9999f;
    List<Integer> solutionIdx = new ArrayList<>();
    //Process first level tree
    for(int idx = 1;idx < mainMatrix.getLength();idx++) {
      float branchCost = countMinCost(mainMatrix,0,idx);
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
            float branchCost = countMinCost(mainMatrix,prevBranch,jdx);
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

  private float countMinCost(Matrices matrix, int from, int to) {
    float totalCost = 0f;
    for(int idx = 0;idx<matrix.getLength();idx++) {
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
    totalCost *= 0.5f;
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
    float min = 9999f;
    int minIdx = -1;
    for(int idx = 0;idx<solutionCost.size();idx++) {
      if (solutionCost.get(idx) < min) {
        min = solutionCost.get(idx);
        minIdx = idx;
      }
    }
    return processedBranch.get(minIdx);
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
