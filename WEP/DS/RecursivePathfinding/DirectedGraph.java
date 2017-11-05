package lab2;

import java.util.LinkedList;
import java.util.List;

public class DirectedGraph {

	public class Node<T> {
		public List<Edge> edges;
		public T t;
		
		public Node(T t) {
			this.t = t;
		}
	}
	
	public class Edge {
		public Node<Integer> from;
		public Node<Integer> to;
		
		public Edge(Node<Integer> n1, Node<Integer> n2) {
			this.from = n1;
			this.to = n2;
		}
	}
	
	private List<Node<Integer>> nodes;
	
	public DirectedGraph(Matrix m) {
		for (int i=1; i<=m.getSideLen(); i++) {
			nodes.add(i-1, new Node<Integer>(i));
		}
		
		for (int j=0; j<m.getSideLen(); j++) {
			nodes.get(j).edges = edgesForNode(m, j);
		}
	}
	
	/**
	 * Generates a LinkedList of nodes adjacent to the current node by examining the edges leading from the current node
	 * @param current Node to be examined for adjacent nodes
	 * @return LinkedList of adjacent Nodes
	 */
	public LinkedList<Node<Integer>> adjacentNodes(Node<Integer> current) {
		LinkedList<Node<Integer>> adj = new LinkedList<Node<Integer>>();
		for (int i=0; i<current.edges.size(); i++) {
			adj.add(current.edges.get(i).to);
		}
		return adj;
	}
	
	/**
	 * Generates edges for a given node in an adjaceny matrix by reading the passed in index of that row
	 * @param m Matrix in which the graph data is stored
	 * @param i Index of the row corresponding to the node in question
	 * @return List of the edges leading from the node in question
	 */
	private List<Edge> edgesForNode(Matrix m, int i) {
		List<Edge> edges = nodes.get(i).edges;
		for (int j=0; j<m.getSideLen(); j++) {
			if (m.getMatrix()[i][j] == 1) {
				edges.add(new Edge(nodes.get(i), nodes.get(j+1)));
			}
		}
		return edges;
	}
}
