
import java.io.BufferedReader;
import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

import org.junit.Test;

public class TEST {

    @Test
    public void test_1() {


        String graph = "G1.txt";
        String test = "test1.txt";
        String expected = "test1_excpected.txt";
        String happend = "oupput_test1.txt";

        makeOutPut(graph, test, happend);
    
        In exp=new In(expected);
        In hap = new In(happend);

        while ((hap.hasNextLine()) && (exp.hasNextLine())) {
            assertEquals(hap.readLine(), exp.readLine());
        }
    }
    
        @Test
    public void test_2() {
        String graph = "G2.txt";
        String test = "test2.txt";
        String expected = "test2_excpected.txt";
        String happend ="oupput_test2.txt";

        makeOutPut(graph, test, happend);
    
        In exp=new In(expected);
        In hap = new In(happend);

        while ((hap.hasNextLine()) && (exp.hasNextLine())) {
            assertEquals(hap.readLine(), exp.readLine());
        }
    }

        public void test_3() {
        String graph = "G3.txt";
        String test = "test3.txt";
        String expected = "test3_excpected.txt";
        String happend = "oupput_test3.txt";

        makeOutPut(graph, test, happend);
    
        In exp=new In(expected);
        In hap = new In(happend);

        while ((hap.hasNextLine()) && (exp.hasNextLine())) {
            assertEquals(hap.readLine(), exp.readLine());
        }
    }


    public void makeOutPut(String graphName, String testName, String outPutFile) {

        try {
            File outFile = new File(outPutFile);
            outFile.createNewFile();
            FileReader graphFile = new FileReader(graphName);
            FileReader inputFile = new FileReader(testName);
            BufferedReader graphReader = new BufferedReader(graphFile);
            BufferedReader inputReader = new BufferedReader(inputFile);
            PrintWriter outputWriter = new PrintWriter(outFile);
            int vertex = Integer.parseInt(graphReader.readLine());
            int edges = Integer.parseInt(graphReader.readLine());
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
            DijkstraSP DijGraph = null;
            for (int i = 0; i < numOfQ; i++) {
                line = inputReader.readLine();
                String splitArr[] = line.split(",");
                int from = Integer.parseInt(splitArr[0]);
                int to = Integer.parseInt(splitArr[1]);
                int BListLen = Integer.parseInt(splitArr[2]);
                int BList[] = new int[BListLen];
                for (int j = 0; j < BListLen; j++) {
                    BList[j] = Integer.parseInt(splitArr[3 + j]);
                }
                Graph.setBL(BList);
                DijGraph = new DijkstraSP(Graph, from);
                double dist = DijGraph.distTo(to);
                String BlistPrint = "";
                for (int j = 0; j < BListLen; j++) {
                    BlistPrint = BlistPrint + BList[j] + " ";
                }
                outputWriter.print(from + " " + to + " " + BListLen + " " + BlistPrint + dist + "\n");
                Graph.RetBL(BList);
            }
            String tieFlag = "";
            if (!(DijGraph.check(Graph, 0))) {
                tieFlag = "!";
            }
            GraphProperties graphP = new GraphProperties(Graph);
            outputWriter.print("Graph: |V|=" + vertex + "  |E|=" + edges + "  " + tieFlag + "TIE " + "  Diameter=" + graphP.diameter() + "  Radius=" + graphP.radius());
            outputWriter.close();
            inputReader.close();
            graphFile.close();

        } catch (Exception e) {
            System.out.println("The file does not exist");
        }
    }

}
