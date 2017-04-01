/**
 * Created by Winarto on 04/01/2017.
 */
public class TourLengkap {
  Matrices mainMatrix;
  float tourCost;

  public TourLengkap(Matrices inMatrix) {
    mainMatrix = inMatrix;
    tourCost = countMinCost(mainMatrix);
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
}
