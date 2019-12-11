package main;

import java.util.Random; 

public class FindRoute {
	public static final int CHOWTOWN = 0;
	public static final int A = 1;
	public static final int B = 2;
	public static final int C = 3;
	public static final String[] areas = {"CHOWTOWN", "A", "B", "C"};
	
	public static void main(String args[]) {
		
		//generate random "traffic":
		Random rand = new Random();
		int a = rand.nextInt(10); 
		int b = rand.nextInt(10); 
		int c = rand.nextInt(10);
		int d = rand.nextInt(10);
		int e = rand.nextInt(10);
		int f = rand.nextInt(10);
		int g = rand.nextInt(10);
		int h = rand.nextInt(10);
		int i = rand.nextInt(10);
		int j = rand.nextInt(10);
		int k = rand.nextInt(10);
		int l = rand.nextInt(10);
		
		//traffic from the corresponding node (0) to other nodes 
		int graph[][] = new int[][] {
			{0,a,b,c},				//traffic from ChowTown to :   Chowtown = 0; A = a; B = b; C = c
			{d,0,e,f},				//traffic from A to:           Chowtown = d; A = 0; B = e; C = f
			{g,h,0,i},				//traffic from B to:		   Chowtown = g; A = h; B = 0; C = i
			{j,k,l,0}				//traffic from C to:		   Chowtown = j; A = k; B = l; C = 0
		};
		
		dijkstra(graph, 0);
	}
	
	public static int[] dijkstra(int graph[][], int src) {
		int traffic[] = new int[4]; //traffic from chowtown to other areas
		boolean visited[] = new boolean [4];
		for(int i = 0; i < 4; i++) {		//initialization
			traffic[i] = Integer.MAX_VALUE;
			visited[i] = false;
		}
		traffic[src] = 0; //traffic from source to ChowTown is 0;
		for(int count = 0; count < 4; count++) {
			int d = minTraffic(traffic, visited);
			System.out.println(areas[d]);
			visited[d] = true;
			for(int i = 0; i < 4; i++) {
				//graph[d][i] != 0 makes sure its not to its own 
				//traffic[d] != Integer.MAX_VALUE makes sure its not visited
				if(!visited[i] && graph[d][i] != 0 && traffic[d] != Integer.MAX_VALUE && traffic[d] + graph[d][i] < traffic[i]) {
					traffic[i] = traffic[d] + graph[d][i];
				}
			}
		}
		return traffic;
	}
	
	public static int minTraffic(int traffic[], boolean visited[]) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < 4; i++) {
			if(!visited[i] && traffic[i] <= min) {
				min = traffic[i];
				index = i;
			}
		}
		return index;
	}

}