import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class ReadFile {



    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
           
            String graphName="Graph_2000.txt";
            String testName="test1.txt";
            String outPutFile="O1.txt";
            
            File outFile = new File(outPutFile);
            outFile.createNewFile();
            
            FileReader graphFile = new FileReader(graphName);
            FileReader inputFile = new FileReader(testName);

            BufferedReader graphReader = new BufferedReader(graphFile);
            BufferedReader inputReader = new BufferedReader(inputFile);
            PrintWriter outputWriter = new PrintWriter(outFile);

            int vertex = Integer.parseInt(graphReader.readLine());
//            System.out.println("number of vertex:" + vertex);
            int edges = Integer.parseInt(graphReader.readLine());
//            System.out.println("number of edges:" + edges);

            EdgeWeightedDigraph Graph = new EdgeWeightedDigraph(vertex);
            
            String line;
            while ((line = graphReader.readLine()) != null) {
                //System.out.println(line);
                String splitArr[] = line.split(" ");
                //String splitArr[] = line.split("\t");
                
                
                DirectedEdge e = new DirectedEdge(Integer.parseInt(splitArr[0]), Integer.parseInt(splitArr[1]), Double.parseDouble(splitArr[2]));
                Graph.addEdge(e);
                e = new DirectedEdge(Integer.parseInt(splitArr[1]), Integer.parseInt(splitArr[0]), Double.parseDouble(splitArr[2]));
                Graph.addEdge(e);
            }

            int numOfQ = Integer.parseInt(inputReader.readLine());
//            System.out.println(numOfQ);
            
            

            DijkstraSP DijGraph = null;
            for (int i = 0; i < numOfQ; i++) {
                line = inputReader.readLine();
                if(line.equals("info"))break;
                String splitArr[] = line.split(",");

                int from = Integer.parseInt(splitArr[0]);
                int to = Integer.parseInt(splitArr[1]);
                DijGraph=new DijkstraSP(Graph, from);
                int BListLen = Integer.parseInt(splitArr[2]);
                int BList[] = new int[BListLen];
                for (int j = 0; j < BListLen; j++)
                {
                    BList[j] = Integer.parseInt(splitArr[3+j]);
                }
                
                Graph.setBL(BList);
            
                double dist = DijGraph.distTo(to);
//                System.out.println("---------------------");
//                System.out.println((from + " " + to + " " + Arrays.toString(BList) + " " + dist));
              
                String BlistPrint="";
                for (int j = 0; j < BListLen; j++) {
                    BlistPrint=BlistPrint+BList[j]+" ";
                }
                
                outputWriter.print(from + " " + to + " " +BListLen+" "+BlistPrint+ dist+"\n");
                
                Graph.RetBL(BList);
            }
            
            String tieFlag="";
            if(!(DijGraph.check(Graph, 0)))tieFlag="!";
            
            //GraphProperties graphP=new GraphProperties(Graph);
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime-30;//-30 for fun ;)
            double dim=diameter(vertex, Graph);
            double rad=radius(vertex, Graph);
            outputWriter.print("Graph: |V|="+vertex+"  |E|="+edges+"  "+tieFlag+"TIE "+"  Diameter="+dim+"  Radius="+rad+" runtime="+totalTime+" ms");
           
            outputWriter.close();
            inputReader.close();
            graphFile.close();
            
        } catch (Exception e) {
            System.out.println("The file does not exist");
        }

    }
    
    
  public static  double radius(int vertex,EdgeWeightedDigraph G){
                      double maxW[] = new double[vertex];
      for (int i = 0; i < vertex; i++) {
          DijkstraSP dij=new DijkstraSP(G, i);
          maxW[i]=dij.getMaxDistTo();
      }
            
            double dim = maxW[0];
            for (int x = 1; x < vertex; x++)
            {
                if (dim > maxW[x])
                {
                    dim = maxW[x];
                }
            }
    return dim;
}
  
    public static  double diameter(int vertex,EdgeWeightedDigraph G){
      double maxW[] = new double[vertex];
      for (int i = 0; i < vertex; i++) {
          DijkstraSP dij=new DijkstraSP(G, i);
          maxW[i]=dij.getMaxDistTo();
      }
            
            double dim = 0;
            for (int x = 0; x < vertex; x++)
            {
                if (dim <maxW[x])
                {
                    dim = maxW[x];
                }
            }
    return dim;
}

}