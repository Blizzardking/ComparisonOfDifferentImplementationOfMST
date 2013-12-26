import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class performanceComparison {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] edges = null;
		int numberOfVertices = 0, numberOfEdges =0;
		try {
			//make a 'file' object
			File file = new File("in.100k");  
			//Get data from this file using a file reader.
			//To store the contents read via File Reader
			BufferedReader input = new BufferedReader(new FileReader(file));
			String line0 = input.readLine();
			String[] items= line0.split("\\s+");
			numberOfVertices = Integer.parseInt(items[0]);
			numberOfEdges = Integer.parseInt(items[1]);
			edges = new int[2 * numberOfEdges][3];
			readEdges( input, edges);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		WeightedGraph2<Integer> graph = new WeightedGraph2<Integer>(edges,  numberOfVertices);
		//graph.printWeightedEdges();
		long time1 = System.currentTimeMillis();
		WeightedGraph2.MST tree1 = graph.getMinimumSpanningTreeByPrim1();
		long time2 = System.currentTimeMillis();
		System.out.printf("Prim's algorithm Implementation 1 \n" +
				"Sample output:%d\n" +
				"Elapsed time(ms):%d\n\n", tree1.getTotalWeight(), time2-time1);
		long time3 = System.currentTimeMillis();
		WeightedGraph2.MST tree2 = graph.getMinimumSpanningTreeByPrim2();
		long time4 = System.currentTimeMillis();
		System.out.printf("Prim's algorithm Implementation 2(Using PairingHeap) \n" +
				"Sample output:%d\n" +
				"Elapsed time(ms):%d\n\n", tree2.getTotalWeight(), time4-time3);
		
		long time5 = System.currentTimeMillis();
		WeightedGraph2.MST tree3 = graph.getMinimumSpanningTreeByPrim3();
		long time6 = System.currentTimeMillis();
		System.out.printf("Prim's algorithm Implementation 2(Using BinaryHeap) \n" +
				"Sample output:%d\n" +
				"Elapsed time(ms):%d\n\n", tree3.getTotalWeight(), time6-time5);
		
		long time7 = System.currentTimeMillis();
		int weight = graph.getMinimumSpanningTreeByKruskal();
		long time8 = System.currentTimeMillis();
		System.out.printf("Kruskal's algorithm \n" +
				"Sample output:%d\n" +
				"Elapsed time(ms):%d\n\n", weight, time8-time7);
				
	}
	public static void readEdges( BufferedReader in, int[][] edges) throws IOException
    {
            String line;
            int i = 0;
            while( ( line = in.readLine( ) ) != null ) {
            		String[] items= line.trim().split("\\s+");
            		edges[i][0] = Integer.parseInt(items[0])-1;
            		edges[i][1] = Integer.parseInt(items[1])-1;
            		edges[i][2] = Integer.parseInt(items[2]);
            		i++;
            		edges[i][0] = Integer.parseInt(items[1])-1;
            		edges[i][1] = Integer.parseInt(items[0])-1;
            		edges[i][2] = Integer.parseInt(items[2]);
            		i++;
            }

    } 
}
