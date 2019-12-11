package main;

import java.util.Random; 

public class FindRoute {
	public static final int CHOWTOWN = 0;
	public static final int A = 1;
	public static final int B = 2;
	public static final int C = 3;
	public static final String[] areas = {"CHOWTOWN", "A", "B", "C"};
	
	
	public static int[] dijkstra(int src) {
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
				
		int traffic[] = new int[4]; 
		boolean visited[] = new boolean [4];
		for(int n = 0; n < 4; i++) {		//initialization
			traffic[n] = Integer.MAX_VALUE;
			visited[n] = false;
		}
		traffic[src] = 0; //traffic from source to ChowTown is 0;
		for(int count = 0; count < 4; count++) {
			int p = minTraffic(traffic, visited);
			System.out.println(areas[p]);
			visited[p] = true;
			for(int q = 0; q < 4; q++) {
				//graph[d][i] != 0 makes sure its not to its own 
				//traffic[d] != Integer.MAX_VALUE makes sure there is a connection (path)
				if(!visited[q] && graph[p][q] != 0 && traffic[p] != Integer.MAX_VALUE && traffic[q] + graph[p][q] < traffic[q]) {
					traffic[q] = traffic[p] + graph[p][q];
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