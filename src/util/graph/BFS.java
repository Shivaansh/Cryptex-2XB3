package util.graph;

import coin.Coin;
import coin.CoinList;
import sun.misc.Queue;

import java.util.HashMap;
import java.util.Stack;

/**
 * Class to create the Graph Object
 * Used Help from Algorithms 4th edition - SedgeWick
 * @author Zuhair Makda
 *
 */
public class BFS {

	private static final int INFINITY = Integer.MAX_VALUE;
    private HashMap<Coin, Boolean> marked;  // marked
    private HashMap<Coin, Coin> edgeTo;      // edgeTo = previous edge on shortest s-v path
    private HashMap<Coin, Integer> distTo;      // distTo= number of edges shortest s-v path

    /**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param
     */
    public BFS(Graph G, Coin s) {
    	this.marked = new HashMap<Coin, Boolean>();
    	this.edgeTo = new HashMap<Coin, Coin>();
    	this.distTo = new HashMap<Coin, Integer>();
    	
    	for (Coin x : CoinList.getAlphabeticalList()) {
    		this.marked.put(x, false);
    		this.distTo.put(x, INFINITY);
    	}
    	
    	bfs(G, s);
    }



    // breadth-first search from a single source
    private void bfs(Graph G, Coin s) {
        Queue<Coin> q = new Queue<Coin>();
        /*for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        */
        this.distTo.put(s, 0);
        this.marked.put(s, true);
        q.enqueue(s);

        while (!q.isEmpty()) {
            Coin v = null;
			try {
				v = q.dequeue();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            for (Coin w : G.adj(v)) {
                if (!this.marked.get(w)) {
                	this.edgeTo.put(w, v);
                	this.distTo.put(w, this.distTo.get(v)+1);
                	this.marked.put(w, true);
                    q.enqueue(w);
                }
            	
            }
        }
    }


    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     */
    public boolean hasPathTo(Coin v) {
        return this.marked.get(v);
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        return this.distTo.get(v);
    }

    
    public Iterable<Coin> pathTo(Coin v) {
        if (!hasPathTo(v)) return null;
        Stack<Coin> path = new Stack<Coin>();
        Coin x = null;
        for (x = v; this.distTo.get(x) != 0; x = this.edgeTo.get(x))
            path.push(x);
        path.push(x);
        return path;
    }
    
    
    public String pathToString(Iterable<Coin> path) {
    	
    	String s = "";
    	Stack<Coin> q = new Stack<Coin>();
    	
    	for (Coin x : path) {
    		q.push(x);
    	}
    	
    	while (!q.isEmpty()) {
				s = s + q.pop().toString() + "->";
    	}
    	
    	return s;
    	
    }
    
    /**
     * Unit tests the {@code BreadthFirstPaths} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Graph G = new Graph();
        System.out.println(G.toString());

        BFS bfs = new BFS(G, CoinList.getByCode("ZCC"));

        /*
         
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }

        }
        */
        System.out.println(bfs.hasPathTo(CoinList.getByCode("FLO")));
        System.out.println(bfs.pathToString(bfs.pathTo(CoinList.getByCode("FLO"))));
    }

}
