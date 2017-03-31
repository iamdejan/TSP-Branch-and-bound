import java.io.File;
import java.util.Scanner;

/**
 * Created by user on 03/31/2017.
 */
public class ReadFile {
  private String fileName;

  public ReadFile(String inFileName) {
    fileName = "src/" + inFileName;
  }

  public Matrices createMatrix() throws Exception {
    Matrices matrix;

    File inFile = new File(fileName);
    Scanner in = new Scanner(inFile);

    int intLength = 0;
    String[] length = in.nextLine().trim().split("\\s+");
    for (int i = 0; i < length.length; i++) {
      intLength++;
    }

    in.close();

    matrix = new Matrices(intLength);
    in = new Scanner(inFile);

    int lineCount = 0;
    while (in.hasNextLine()) {
      String[] currentLine = in.nextLine().trim().split("\\s+");
      for (int i = 0; i < currentLine.length; i++) {
        matrix.setValue(lineCount, i, Integer.parseInt(currentLine[i]));
      }
      lineCount++;
    }
    return matrix;
  }
}
