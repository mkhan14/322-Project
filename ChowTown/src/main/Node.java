package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Node {
	
	private String name;
    
    private List<Node> shortestPath = new LinkedList<>();
     
    private int distance = Integer.MAX_VALUE;
     
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
  
    public Node(String name) {
        this.name = name;
    }
     
    // getters and setters
    public void setName(String name) {
		this.name = name;
    }
    public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
    }
    public void setDistance(int distance) {
    	this.distance = distance;
    }
    public void setAdjacentNodes(Map<Node,Integer> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
    }
 
	public String getName(){
		return name;
	}
	public List<Node> getShortestPath(){
		return shortestPath;
	}
	public int getDistance() {
		return distance;
	}
	public Map<Node, Integer> getAdjacentNodes(){
		return adjacentNodes;
	}
    
	//constructor
	public void Node(String name, List<Node> shortestPath, int distance, Map<Node, Integer> adjacentNodes ) {
		setName(name);
		setShortestPath(shortestPath);
		setDistance(distance);
		setAdjacentNodes(adjacentNodes);
	}
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
