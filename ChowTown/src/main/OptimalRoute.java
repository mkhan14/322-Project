package main;

//change distance to traffic
//i think x is current node or something
//the print shit is weird

public class OptimalRoute {
	
	public void printOptimalRoute(int distance[], int x) {
		System.out.println("Your Optimal Route is: ");
		for(int i = 0; i < 4; i++) {
			System.out.println(i+"------"+distance[i]);
		}
	}
	
	public void djkistra(int citymap[][], int chowtown) {
		int distance[] = new int[4];
		Boolean visited[] = new Boolean[4];
		for(int i=0; i<4; i++) {
			distance[i] = Integer.MAX_VALUE;
			visited[i]  = false;//b
		}
		distance[0] = 0;
		for(int count = 0; count<4; count++) {
			int s = shortestDistance(distance, visited);//u
			visited[s]=true;
			for(int x = 0; x<4; x++) {
				if(!visited[x] && citymap[s][x]!=0 && distance[s]!=Integer.MAX_VALUE && distance[s]+citymap[s][x]<distance[x]) {
					distance[x] = distance[s]+citymap[s][x];
				}
			}
			printOptimalRoute(distance, 4); //ajfn.kfbhfj//
		}
	}
	
	public int shortestDistance(int distance[], Boolean visited[]) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int v = 0; v < 4; v++) {
			if(visited[v]==false && distance[v]<=min) {//x
				min = distance[v];
				index = v;
			}
		}
		return index;
	}
//-------------
	public static void main(String[] args) {
		int citymap[][]=new int[][] {{0,1,2,3}, //dist from chow to chow,A,B,C
									 {1,0,4,1}, //dist from A to chow,A,B,C
									 {2,4,0,3}, //dist from B to chow,A,B,C
									 {3,1,3,0}};//dist from C to chow,A,B,C
	
	OptimalRoute path = new OptimalRoute();
	path.djkistra(citymap,0);
	
	}
}

