/**
 * Created by Winarto on 03/31/2017.
 */
public class Main {
  public static void main(String args[]) throws Exception {
    ReadFile reader = new ReadFile("bmatriks.txt");
    Matrices mainMatrix = reader.createMatrix();
/*    BranchAndBound bbObj = new BranchAndBound(mainMatrix);
    bbObj.SolveTSP();
    DrawGraph graph = new DrawGraph("TSP",mainMatrix,bbObj.getRoute());
    System.out.print(bbObj.getMinCost());
    graph.DisplayGraph();*/
    TourLengkap tour = new TourLengkap(mainMatrix);
  }
}
