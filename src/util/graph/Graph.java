package util.graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Iterator;

import coin.Coin;
import coin.CoinList;
import util.APIHandler;
import util.APINotRespondingException;
import util.Logger;
import util.APIHandler.CallType;

public class Graph {

    private final int V;
    private int E;
    private HashMap<Coin, HashSet<Coin>> adj;
    
    /**
     * Take in CoinList
     *
     * @param  
     * @throws 
     */
    public Graph() {
        this.V = 0;
        this.E = 0;
        adj = new HashMap<Coin, HashSet<Coin>>();
        
        try {
			//inialize coinlist
			CoinList.init();

			//this code here prints all of the pairs 
			JsonObject mainObj = APIHandler.request(CallType.TRADING_PAIRS);
			JsonObject pairs = mainObj.getAsJsonObject("Cryptsy");
			for(Entry<String, JsonElement> e : pairs.entrySet()) {
				addEdges(e);
			}
		} catch (APINotRespondingException e) {
			Logger.error("API Not responding");
			e.printStackTrace();
		}
        
    }


    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return this.adj.size();
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the undirected edge v-w to this graph.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdges(Entry<String, JsonElement> e) {
        this.E++;
        Coin coinFrom;
        coinFrom = CoinList.getByCode(e.getKey());
        if ( !this.adj.containsKey(coinFrom) ) {
            this.adj.put( coinFrom, new HashSet<Coin>());
        }
		JsonArray coins = e.getValue().getAsJsonArray();
		for(JsonElement k : coins)
			if (k != null) {
				this.adj.get(coinFrom).add( CoinList.getByCode(k.getAsString()));
			}
		System.out.println(coinFrom + "--> " + coins.toString());
		
    }


    /**
     * Returns the Coins adjacent to vertex at Coin v.
     *
     * @param  Coin v to find tradeable coins
     * @return the vertices adjacent to vertex at Coin v
     */
    public HashSet<Coin> adj(Coin v) {
        return this.adj.get(v);
    }

    /**
     * Returns the degree of Coin
     *
     * @param  v the the Coin
     * @return the degree of the Coin v
     */
    public int degree(Coin v) {
        return this.adj.get(v).size();
    }


    /**
     * Returns a string representation of this graph.
     *
     * @return String of Coins with tradeable partner Coins
     */
    public String toString() {
    	String s = "";
    	// Getting a Set of Key-value pairs
        Set entrySet = this.adj.entrySet();
     
        // Obtaining an iterator for the entry set
        Iterator it = entrySet.iterator();
     
        while(it.hasNext()){
           Map.Entry me = (Map.Entry)it.next();
           s = (s + "CoinFrom is: "+me.getKey().toString() + "\n");
       }
        return s;
    }


    /**
     * Unit tests the {@code Graph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Graph tradeables = new Graph();
        
    }

}