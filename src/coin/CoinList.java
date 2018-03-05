package coin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	
	private static Coin[] list; 
	private static SortOrder sortOrder = SortOrder.PRICE;
	
	private static boolean isInit; 
	
	private static final int MAX_MARKET_INPUT = 60;
	
	/**
	 * Initializes the coin list by making an API call
	 * @return if the list was initialized correctly
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public static void init() throws APINotRespondingException {
		Logger.info("Initializing coin list ...");
		
		//gets coin list via API call
		JSONObject mainObj;
		mainObj = APIHandler.request(CallType.COIN_LIST);
				
		Object[] values = ((JSONObject) mainObj.get("Data")).values().toArray();
		
		//initialize state variables
		list = new Coin[values.length];
		
		//adds coins to list
		JSONObject obj;
		for(int i = 0; i < values.length; i++) {
			obj = (JSONObject)values[i];
			list[i] = new Coin(obj);
		}
		
		isInit = true;
		Logger.info("Coin list successfully initialized - " + list.length + " coins");
	}
	
	/**
	 * Loads market data for all coins in the list, relating to {@link relCoinCode}
	 * @param relCoinCode coin symbol 
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public static void loadMarketData(String relCoinCode) throws APINotRespondingException {
		//to-do -> load all market data for coins (do ~50 at a time for speed)
		
		if(!isInit)
			throw new IllegalStateException("CoinList must be initialized!");
		
		String param = "";
		int i = 0;
		for(; i < list.length/MAX_MARKET_INPUT; i++) {
			for(int j = i * MAX_MARKET_INPUT; j < MAX_MARKET_INPUT * (i + 1); j++) {
				param += list[j].getCode() + ",";
			}
			setCoinData(i * MAX_MARKET_INPUT, MAX_MARKET_INPUT * (i + 1), param, relCoinCode);
			param = "";
		}
		
		for(int j = i * MAX_MARKET_INPUT; j < list.length; j++) 
			param += list[j].getCode() + ",";
		
		setCoinData(i * MAX_MARKET_INPUT, list.length, param, relCoinCode);
		
	}
	
	//helper method, assigns all coin data from start to end in the coin list
	private static void setCoinData(int start, int end, String param, String relCoinCode) throws APINotRespondingException {
		JSONObject mainObj, coinObj;
		Map<String, JSONObject> mainSet;
		
		mainObj = (JSONObject)APIHandler.request(CallType.PRICE_MULTI_FULL, param, relCoinCode);
		mainSet = (Map<String, JSONObject>) mainObj.get("RAW");		
		for(int j = start; j < end; j++) {
			coinObj = mainSet.get(list[j].getCode());
			if(coinObj != null) {
				double mkt = Double.parseDouble(((JSONObject) coinObj.get(relCoinCode)).get("MKTCAP").toString());
				list[j].setMarketCap(mkt);
			}else {
				list[j].setMarketCap(Double.NaN);
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			init();
			loadMarketData("USD");
			System.out.println(getCoin("BTC").getMarketCap());
			loadMarketData("CAD");
		
		} catch (APINotRespondingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds a specific coin
	 * @param code 3 character code associated with coin
	 * @return Coin object representing the specific cryptocurrency
	 */
	public static Coin getCoin(String code) {
		if(!isInit)
			throw new IllegalStateException("CoinList must be initialized!");
		
		for(int i = 0; i < list.length; i++) {
			if(list[i].getCode().equals(code))
				return list[i];
		}
		return null;
	}
	
	/** 
	 * Gets reference to the coin list
	 * @return coin list 
	 */
	public static Coin[] getList() {
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
