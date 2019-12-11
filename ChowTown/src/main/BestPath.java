package main;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;

public class BestPath {
	
	private static Node getLowestDistanceNode(Set < Node > unsettledNodes) { //
	    Node lowestDistanceNode = null;										
	    int lowestDistance = Integer.MAX_VALUE; 
	    for (Node node: unsettledNodes) {
	        int nodeDistance = node.getDistance();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
	}
	
	
	private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
	    Integer sourceDistance = sourceNode.getDistance();
	    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
	        evaluationNode.setDistance(sourceDistance + edgeWeigh);
	        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
	        shortestPath.add(sourceNode);
	        evaluationNode.setShortestPath(shortestPath);
	    }
	}
	
	public static void calculateShortestPathFromSource(CityMap graph, Node source) { //this is the queue
	    source.setDistance(0);
	 
	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();
	 
	    unsettledNodes.add(source);
	 
	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry < Node, Integer> adjacencyPair: 
	          currentNode.getAdjacentNodes().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
	            Integer edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    
	    for (Node temp : settledNodes) {
	    	System.out.print(temp.getName() + " -> ");
	    }
	    
	    
//	    while(settledNodes.hasNext()){
//	        System.out.println(settledNodes.next()+" -> ");
//	     }
	    
//	    while(!settledNodes.last) {
//	    	forEach settledNodes
//	    		System.out.println(settledNodes(i)+" -> ");
//	    }
	}
	
	
//-------------------------------------------------------------------
	public static void main(String[] args) {
		Node chowtown = new Node("ChowTown");
		Node a = new Node("Area A");
		Node b = new Node("Area B");
		Node c = new Node("Area C"); 
		 
		chowtown.addDestination(a, 1);
		chowtown.addDestination(b, 1);
		chowtown.addDestination(c, 3);
		a.addDestination(b, 4);
		a.addDestination(c, 1);
		b.addDestination(c, 3);
		 
		 
		CityMap citymap = new CityMap();
		 
		citymap.addNode(chowtown);
		citymap.addNode(a);
		citymap.addNode(b);
		citymap.addNode(c);
		 
		calculateShortestPathFromSource(citymap, chowtown);
	}

}
