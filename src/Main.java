/**
 * Created by user on 03/31/2017.
 */
public class Main {
  public static void main(String args[]) throws Exception {
    ReadFile reader = new ReadFile("matriks.txt");
    Matrices matrix = reader.createMatrix();
    System.out.print(matrix.toString());
  }
}
