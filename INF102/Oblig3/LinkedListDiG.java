import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.LinkedList; // supports efficient add(0,_) and toString()

public class LinkedListDiG {

	private int V;

	public int V() {
		return V;
	} // number of vertices

	private int E;

	public int E() {
		return E;
	} // number of edges

	private LinkedList<Integer>[] adj; // adjacency lists

	public LinkedListDiG(In in) {
		V = in.readInt();
		E = in.readInt();
		adj = (LinkedList<Integer>[]) new LinkedList[V];
		for (int v = 0; v < V; v++)
			adj[v] = new LinkedList<Integer>();
		for (int e = 0; e < E; e++) {
			int v = in.readInt();
			int w = in.readInt();
			addEdge(v, w);
		}
	}

	public void addEdge(Integer v, Integer w) {
		adj[v].add(0, w);
	}

	public void preDfs(int v, boolean[] marked, LinkedList_Queue<Integer> q) {
		marked[v] = true;
		q.enqueue(v);
		// StdOut.print(v + " ");
		for (int w : adj[v])
			if (!marked[w])
				preDfs(w, marked, q);
	}

	public void testPre() {
		boolean[] marked = new boolean[V];
		LinkedList_Queue<Integer> q = new LinkedList_Queue<Integer>();
		for (int v = 0; v < V; v++)
			if (!marked[v])
				preDfs(v, marked, q);
		for (Integer v : q)
			StdOut.print(v + " ");
		StdOut.println("is the preorder");
	}

	public void postDfs(int v, boolean[] marked, LinkedList_Queue<Integer> q, LinkedList_Stack<Integer> s) {
		marked[v] = true;
		for (int w : adj[v])
			if (!marked[w])
				postDfs(w, marked, q, s);
		q.enqueue(v);
		s.push(v);

	}

	public void testPost() {
		boolean[] marked = new boolean[V];
		LinkedList_Queue<Integer> q = new LinkedList_Queue<Integer>();
		LinkedList_Stack<Integer> s = new LinkedList_Stack<Integer>();
		for (int v = 0; v < V; v++)
			if (!marked[v])
				postDfs(v, marked, q, s);
		for (Integer v : q)
			StdOut.print(v + " ");
		StdOut.println("is the postorder (topological if digraph acyclic)");
		for (Integer w : s)
			StdOut.print(w + " ");
		StdOut.println("is the postorder stack");
	}

	public static void main(String[] args) {
		LinkedListDiG dg = new LinkedListDiG(new In("tinyDAG.txt"));

		dg.testPre();
		dg.testPost();

	} // End of main

} // End of LinkedListDiG, based on Algorithms, 4th Edition, Sec. 4.2
