package coin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import util.APIHandler;
import util.Logger;
import util.APIHandler.CallType;
import util.APINotRespondingException;

/**
 * Coin List that stores all coins avaiable
 * @author Somar Aani
 */

public class CoinList{
	
	private static List<Coin> list; 
	private static SortOrder sortOrder = SortOrder.PRICE;
	
	private static boolean isInit; 
	
	/**
	 * Initializes the coin list by making an API call
	 * @return if the list was initialized correctly
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public static void init() throws APINotRespondingException {
		Logger.info("Initializing coin list ...");
		
		//initialize state variables
		list = new ArrayList<Coin>();
		
		//gets coin list via API call
		JSONObject mainObj;
		mainObj = APIHandler.request(CallType.COIN_LIST);
				
		Object[] values = ((JSONObject) mainObj.get("Data")).values().toArray();
		
		//adds coins to list
		JSONObject obj;
		for(int i = 0; i < values.length; i++) {
			obj = (JSONObject)values[i];
			list.add(new Coin(obj));
		}
		
		isInit = true;
		Logger.info("Coin list successfully initialized - " + list.size() + " coins");
	}
	
	/**
	 * Loads market data for all coins in the list, relating to {@link relCoinCode}
	 * @param relCoinCode coin symbol 
	 */
	public void loadMarketData(String relCoinCode) {
		//to-do -> load all market data for coins (do ~50 at a time for speed)
	}
	
	/**
	 * Finds a specific coin
	 * @param code 3 character code associated with coin
	 * @return Coin object representing the specific cryptocurrency
	 */
	public static Coin getCoin(String code) {
		if(!isInit)
			throw new IllegalStateException("CoinList must be initialized!");
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getCode().equals(code))
				return list.get(i);
		}
		return null;
	}
	
	/** 
	 * Gets reference to the coin list
	 * @return coin list 
	 */
	public static List<Coin> getList() {
		if(!isInit)
			throw new IllegalStateException("CoinList must be initialized!");
		
		return list;
	}
	
	/**
	 * Gets the current sort order
	 * @return SortOrder enum of current sorting order
	 */
	public SortOrder getSortOrder() {
		return this.sortOrder;
	}
	
	/**
	 * Sets the sort order
	 * @param SortOrder enum of wanted sort order
	 */
	public void setSortOrder(SortOrder s) {
		this.sortOrder = s;
	}
	
	/**
	 * Sorts list based on SortOrder {@link s}
	 * @param s SortOrder enum to specify how to sort
	 */
	public static void sort(SortOrder s) {
		//to-do -> sort depending on s
	}
}
