import java.util.List;
import java.util.Scanner;

/**
 * Created by Winarto on 03/31/2017.
 */
public class Main {
  public static void main(String args[]) throws Exception {
    long startTime, endTime;
    ReadFile reader = new ReadFile("bmatriks.txt");
    Matrices mainMatrix = reader.createMatrix();
    System.out.print("1. TSP Branch and Bound RCM\n2. TSP Branch and Bound Bobot Tur Lengkap\n3. Exit\nInput: ");
    Scanner scan = new Scanner(System.in);
    int in = scan.nextInt();
    switch (in) {
      case 1:
        startTime = System.nanoTime();
        TSP_RCM bbObj = new TSP_RCM(mainMatrix);
        bbObj.solveTSP();
        endTime = System.nanoTime();
        DrawGraph graph = new DrawGraph("TSP", mainMatrix, bbObj.getRoute(), true);
        System.out.println("Bobot Tur: " + bbObj.getMinCost());
        System.out.println("Waktu eksekusi: " + (float) (endTime - startTime) / 1000000000 + " s");
        System.out.println("Jumlah simpul dibangkitkan: " + bbObj.getJumlahSimpul());
        System.out.print("Path yang diambil: ");
        List<Integer> tesbb = bbObj.getRoute();
        for (int i = 0; i < tesbb.size() - 1; i++) {
          System.out.print(tesbb.get(i) + 1 + " -> ");
        }
        System.out.print(tesbb.get(tesbb.size() - 1) + 1);
        graph.DisplayGraph();
        break;
      case 2:
        startTime = System.nanoTime();
        TourLengkap tour = new TourLengkap(mainMatrix);
        endTime = System.nanoTime();
        DrawGraph dgraph = new DrawGraph("TSP", mainMatrix, tour.getRoute(), false);
        System.out.println("Bobot Tur: " + tour.getTourCost());
        System.out.println("Waktu eksekusi: " + (float) (endTime - startTime) / 1000000000 + " s");
        System.out.println("Jumlah simpul dibangkitkan: " + tour.getSimpulDibangkitkan());
        System.out.print("Path yang diambil: ");
        List<Integer> tes = tour.getRoute();
        for (int i = 0; i < tes.size() - 1; i++) {
          System.out.print(tes.get(i) + 1 + " -> ");
        }
        System.out.print(tes.get(tes.size() - 1) + 1);
        dgraph.DisplayGraph();
        break;
      case 3:
        break;
      default:
        System.out.println("Input salah!");
        break;
    }
  }
}
