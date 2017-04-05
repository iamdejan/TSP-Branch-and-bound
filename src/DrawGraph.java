import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.List;

/**
 * Created by Winarto on 04/01/2017.
 */
public class DrawGraph {
  private Matrices matrix;
  private int matrixLength;
  private Graph mainGraph;
  private Node[] allNode;
  private Edge[][] allEdge;
  private List<Integer> solutionPath;
  private boolean isDirected;

  public DrawGraph(String graphName, Matrices inMatrix, List<Integer> path, boolean directed) {
    matrix = inMatrix;
    matrixLength = inMatrix.getLength();
    allNode = new Node[matrixLength];
    allEdge = new Edge[matrixLength][matrixLength];
    solutionPath = path;
    mainGraph = new MultiGraph(graphName);
    isDirected = directed;
    if(directed) {
      createDirectedGraph();
    }else {
      createNonDirectedGraph();
    }
  }

  public void DisplayGraph() {
    if(isDirected)
      System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    //allNode[0].addAttribute("ui.style" , ("size: 20px, 20px; fill-color: red; shape: rounded-box; stroke-mode: plain; stroke-color: yellow;"));
    /*allNode[0].setAttribute("label", "tes");
    allEdge[0][1].setAttribute("label","1.0");
    allEdge[0][1].addAttribute("ui.style" , ("fill-color: red;"));*/
    mainGraph.addAttribute("ui.quality");
    mainGraph.addAttribute("ui.antialias");
    mainGraph.display();
  }

  private void createDirectedGraph() {
    for(int idx = 0;idx < matrixLength;idx++) {
      allNode[idx] = mainGraph.addNode(Integer.toString(idx));
      allNode[idx].setAttribute("label", Integer.toString(idx+1));
      if(solutionPath.contains(idx)) {
        allNode[idx].addAttribute("ui.style",("size:20px,20px;fill-color: red;"));
      }
    }
    for(int row = 0;row < matrixLength;row++) {
      for(int col = 0;col < matrixLength;col++) {
        String edgeName = Integer.toString(row)+Integer.toString(col);
        if(row != col) {
          allEdge[row][col] = mainGraph.addEdge(edgeName, allNode[row], allNode[col], true);
          allEdge[row][col].setAttribute("label",Integer.toString(matrix.getMatrices()[row][col]));
        }
      }
    }
    for(int idx = 1;idx < solutionPath.size();idx++){
      int prev = solutionPath.get(idx-1);
      int next = solutionPath.get(idx);
      allEdge[prev][next].addAttribute("ui.style",("fill-color: red;"));
    }
  }

  private void createNonDirectedGraph() {
    for(int idx = 0;idx < matrixLength;idx++) {
      allNode[idx] = mainGraph.addNode(Integer.toString(idx));
      allNode[idx].setAttribute("label", Integer.toString(idx+1));
      if(solutionPath.contains(idx)) {
        allNode[idx].addAttribute("ui.style",("size:20px,20px;fill-color: red;"));
      }
    }
    for(int row = 0;row < matrixLength;row++) {
      for(int col = 0;col < row;col++) {
        String edgeName = Integer.toString(row)+Integer.toString(col);
        if(row != col) {
          allEdge[row][col] = mainGraph.addEdge(edgeName, allNode[row], allNode[col], false);
          allEdge[row][col].setAttribute("label",Integer.toString(matrix.getMatrices()[row][col]));
        }
      }
    }
    for(int idx = 1;idx < solutionPath.size();idx++){
      int prev = solutionPath.get(idx-1);
      int next = solutionPath.get(idx);
      if(prev > next)
        allEdge[prev][next].addAttribute("ui.style",("fill-color: red;stroke-mode:plain;stroke-color: yellow;"));
      else
        allEdge[next][prev].addAttribute("ui.style",("fill-color: red;stroke-mode:plain;stroke-color: yellow;"));
    }
  }
}
