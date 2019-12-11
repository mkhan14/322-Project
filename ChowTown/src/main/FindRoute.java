package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 

public class FindRoute {

	private static String [] areas = {"CHOWTOWN", "A", "B", "C"};
	
	public static int[] dijkstra(int src) {
		//generate random "traffic":
		Random rand = new Random();
		int A = rand.nextInt(10); 
		int B = rand.nextInt(10); 
		int C = rand.nextInt(10);
		int D = rand.nextInt(10);
		int E = rand.nextInt(10);
		int F = rand.nextInt(10);
		
		//traffic from the corresponding node (0) to other nodes 
		int graph[][] = new int[][] {
			{0,A,B,C},				//traffic from ChowTown to :   Chowtown = 0; A = a; B = b; C = c
			{A,0,D,E},				//traffic from A to:           Chowtown = d; A = 0; B = e; C = f
			{B,D,0,F},				//traffic from B to:		   Chowtown = g; A = h; B = 0; C = i
			{C,E,F,0}				//traffic from C to:		   Chowtown = j; A = k; B = l; C = 0
		};

		JFrame routes = new JFrame();
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		String s = "";
		for(int row = 0; row < graph.length; row++) {
			for(int col = 0; col < graph.length; col++) {
				if(row != col) {
					s = "From " + areas[row] + " to " + areas[col] + " is " + graph[row][col];
					JLabel label = new JLabel(s);
					c.gridy += 1;
					panel.add(label, c);	
				}
			}
		}
		routes.add(panel);
		routes.setVisible(true);
		routes.setSize(300,500);
		
		int traffic[] = new int[4]; //traffic from chowtown to other areas
		boolean visited[] = new boolean [4];
		for(int i = 0; i < 4; i++) {		//initialization
			traffic[i] = Integer.MAX_VALUE;
			visited[i] = false;
		}
		traffic[src] = 0; //traffic from source to ChowTown is 0;
		for(int count = 0; count < 4; count++) {
			int d = minTraffic(traffic, visited);
			visited[d] = true;
			for(int i = 0; i < 4; i++) {
				//graph[d][i] != 0 makes sure its not to its own 
				//traffic[d] != Integer.MAX_VALUE makes sure there is a connection (path)
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