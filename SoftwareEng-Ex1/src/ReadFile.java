import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class ReadFile {



    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
           
            String graphName="C:\\Users\\Zafrir\\Downloads\\test_Ex1.txt";
            String testName="C:\\Users\\Zafrir\\Downloads\\test1_Ex1_run.txt";
            String outPutFile="C:\\Users\\Zafrir\\Downloads\\output.txt";
            
            File outFile = new File(outPutFile);
            outFile.createNewFile();
            
            FileReader graphFile = new FileReader(graphName);
            FileReader inputFile = new FileReader(testName);

            BufferedReader graphReader = new BufferedReader(graphFile);
            BufferedReader inputReader = new BufferedReader(inputFile);
            PrintWriter outputWriter = new PrintWriter(outFile);

            int vertex = Integer.parseInt(graphReader.readLine());
            System.out.println("number of vertex:" + vertex);
            int edges = Integer.parseInt(graphReader.readLine());
            System.out.println("number of edges:" + edges);

            EdgeWeightedDigraph Graph = new EdgeWeightedDigraph(vertex);
            
            String line;
            while ((line = graphReader.readLine()) != null) {
                //System.out.println(line);
                String splitArr[] = line.split("\t");
                DirectedEdge e = new DirectedEdge(Integer.parseInt(splitArr[0]), Integer.parseInt(splitArr[1]), Double.parseDouble(splitArr[2]));
                Graph.addEdge(e);
                e = new DirectedEdge(Integer.parseInt(splitArr[1]), Integer.parseInt(splitArr[0]), Double.parseDouble(splitArr[2]));
                Graph.addEdge(e);
            }

            int numOfQ = Integer.parseInt(inputReader.readLine());
            System.out.println(numOfQ);
            
            DijkstraSP DijGraph = null;
            for (int i = 0; i < numOfQ; i++) {
                line = inputReader.readLine();
                String splitArr[] = line.split(",");

                int from = Integer.parseInt(splitArr[0]);
                int to = Integer.parseInt(splitArr[1]);
                
                int BListLen = Integer.parseInt(splitArr[2]);
                int BList[] = new int[BListLen];
                for (int j = 0; j < BListLen; j++)
                {
                    BList[j] = Integer.parseInt(splitArr[3+j]);
                }
                
                Graph.setBL(BList);
                
                DijGraph = new DijkstraSP(Graph, from);
                double dist = DijGraph.distTo(to);
                System.out.println("---------------------");
                System.out.println((from + " " + to + " " + Arrays.toString(BList) + " " + dist));
              
                String BlistPrint="";
                for (int j = 0; j < BListLen; j++) {
                    BlistPrint=BlistPrint+BList[j]+" ";
                }
                
                outputWriter.print(from + " " + to + " " +BListLen+" "+BlistPrint+ dist+"\n");
                
                Graph.RetBL(BList);
            }
            String tieFlag="";
            if(!(DijGraph.check(Graph, 0)))tieFlag="!";
            
            GraphProperties graphP=new GraphProperties(Graph);
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            outputWriter.print("Graph: |V|="+vertex+"  |E|="+edges+"  "+tieFlag+"TIE "+"  Diameter="+graphP.diameter()+"  Radius="+graphP.radius()+" runtime="+totalTime+" ms");
            System.out.println("Graph: |V|="+vertex+"  |E|="+edges+"  "+tieFlag+"TIE  "+"  Diameter="+graphP.diameter()+"  Radius="+graphP.radius()+" runtime="+totalTime+" ms");
           
            outputWriter.close();
            inputReader.close();
            graphFile.close();
            
        } catch (Exception e) {
            System.out.println("The file does not exist");
        }

    }

}
