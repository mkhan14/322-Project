package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CityMap {

	 private Set<Node> nodes = new HashSet<>();
     
	 public void addNode(Node nodeA) {
		 nodes.add(nodeA);
	 }
	 // getters and setters 
	 public void setNodes(Set<Node> nodes) {
			this.nodes = nodes;
	 }
	 
	 public Set<Node> getNodes(){
		 return nodes;
	 }
	 //constructor
	 public void CityMap(Set<Node> nodes) {
		 setNodes(nodes);
	 }
		//how do we deal with methods again?
}
