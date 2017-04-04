import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Created by Winarto on 03/31/2017.
 */
public class Main {
  public static void main(String args[]) throws Exception {
    ReadFile reader = new ReadFile("matrik.txt");
    Matrices mainMatrix = reader.createMatrix();
 /*   long startTime = System.nanoTime();
    BranchAndBound bbObj = new BranchAndBound(mainMatrix);
    bbObj.solveTSP();
    long endTime = System.nanoTime();
    DrawGraph graph = new DrawGraph("TSP",mainMatrix,bbObj.getRoute(), true);
    System.out.println((double) (endTime - startTime)/1000000 + " ms");
    System.out.print(bbObj.getMinCost());
    graph.DisplayGraph();
*/
    long startTime = System.nanoTime();
    TourLengkap tour = new TourLengkap(mainMatrix);
    tour.solveTSP();
    long endTime = System.nanoTime();
    DrawGraph graph = new DrawGraph("TSP",mainMatrix,tour.getRoute(), false);
    System.out.println((double) (endTime - startTime)/1000000 + " ms");
    System.out.println(tour.getTourCost());
    List<Integer> tes = tour.getRoute();
    for(int i=0;i< tes.size()-1;i++) {
      System.out.print(tes.get(i)+1 + " -> ");
    }
    System.out.print(tes.get(tes.size()-1)+1);
    graph.DisplayGraph();
  }
}
