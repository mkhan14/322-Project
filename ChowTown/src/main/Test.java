package main;

public class Test {
	
	public static final int CHOWTOWN = 0;
	public static final int A = 1;
	public static final int B = 2;
	public static final int C = 3;
	public static final String[] areas = {"CHOWTOWN", "A", "B", "C"};

	public static void main(String args[]) {
		int a;
		int b;
		int c;
		
		int graph[][] = new int[][] {
			{0,1,2,3},
			{1,0,4,1},
			{2,4,0,3},
			{3,1,3,0}
		};
		
		dijkstra(graph, 0);
	}
	
	public static void dijkstra(int graph[][], int src) {
		int dist[] = new int[4];
		boolean visited[] = new boolean [4];
		for(int i = 0; i < 4; i++) {
			dist[i] = Integer.MAX_VALUE;
			visited[i] = false;
		}
		dist[src] = 0;
		for(int count = 0; count < 4; count++) {
			int d = minDistance(dist, visited);
			System.out.println(areas[d]);
			visited[d] = true;
			for(int i = 0; i < 4; i++) {
				//graph[d][i] != 0 makes sure its not to its own 
				//dist[d] != Integer.MAX_VALUE makes sure its not visited
				if(!visited[i] && graph[d][i] != 0 && dist[d] != Integer.MAX_VALUE && dist[d] + graph[d][i] < dist[i]) {
					dist[i] = dist[d] + graph[d][i];
				}
			}
		}
		
		//jpanel
		System.out.println(dist[1]);
		
	}
	
	public static int minDistance(int dist[], boolean visited[]) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < 4; i++) {
			if(!visited[i] && dist[i] <= min) {
				min = dist[i];
				index = i;
			}
		}
		return index;
	}

}
