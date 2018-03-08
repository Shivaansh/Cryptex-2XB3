package coin;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
		
		//gets coin list as JsonObject via API call
		JsonObject mainObj = APIHandler.request(CallType.COIN_LIST, "", "").get("Data").getAsJsonObject();
	
		Set<Entry<String, JsonElement>> dataMap = mainObj.entrySet();
		list = new Coin[dataMap.size()];
		
		//initializes all coins in list using entry set
		int i = 0;
		for(Entry<String, JsonElement> e : dataMap) {
			list[i++] = new Coin(e.getValue().getAsJsonObject());
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
		
		//loads market data MAX_MARKET_INPUT coins at a time, to speed up the process
		
		if(!isInit)
			throw new IllegalStateException("CoinList must be initialized!");
		
		Logger.info("Loading market data relative to " + relCoinCode);
		
		String param = "";
		int i = 0;
		
		//divide into MAX_MARKET_INPUT length blocks
		for(; i < list.length/MAX_MARKET_INPUT; i++) {
			
			//create input parameter for block
			for(int j = i * MAX_MARKET_INPUT; j < MAX_MARKET_INPUT * (i + 1); j++) {
				param += list[j].getCode() + ",";
			}
			
			//set coin data for the block 
			setCoinData(i * MAX_MARKET_INPUT, MAX_MARKET_INPUT * (i + 1), param, relCoinCode);
			param = "";
		}
		
		for(int j = i * MAX_MARKET_INPUT; j < list.length; j++) 
			param += list[j].getCode() + ",";
		
		setCoinData(i * MAX_MARKET_INPUT, list.length, param, relCoinCode);
		Logger.info("Market data successfully loaded");
	}
	
	//helper method, assigns all coin data from start to end in the coin list
	private static void setCoinData(int start, int end, String param, String relCoinCode) throws APINotRespondingException {
		JsonObject rawObj = APIHandler.request(CallType.PRICE_MULTI_FULL, param, relCoinCode).get("RAW").getAsJsonObject();
		Set<Entry<String, JsonElement>> dataMap = rawObj.entrySet();
		
		JsonObject currCoinObj;
		Iterator<Entry<String, JsonElement>> iterator = dataMap.iterator();
		Entry<String, JsonElement> currEntry = iterator.next();
				
		for(int i = start; i < end - 1; i++) {
			if(list[i].getCode().equals(currEntry.getKey())) {
				currCoinObj = currEntry.getValue().getAsJsonObject().getAsJsonObject(relCoinCode);
				
				list[i].setMarketCap(currCoinObj.getAsJsonPrimitive("MKTCAP").getAsDouble());
				list[i].setPrice(currCoinObj.getAsJsonPrimitive("PRICE").getAsDouble());
				
				if(currCoinObj.get("CHANGEPCT24HOUR").isJsonNull())
					list[i].setDailyChangePercent(Double.NaN);
				else
					list[i].setDailyChangePercent(currCoinObj.getAsJsonPrimitive("CHANGEPCT24HOUR").getAsDouble());

				if(iterator.hasNext())
					currEntry = iterator.next();
			} else {
				list[i].setMarketCap(Double.NaN);
				list[i].setPrice(Double.NaN);
			}
		}
	}
	
	/**
	 * Finds a specific coin
	 * @param code 3 character code associated with coin
	 * @return Coin object representing the specific cryptocurrency
	 */
	public static Coin getCoin(String code) {
		//to-do -> make faster by using binary search
		
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
		return CoinList.sortOrder;
	}
	
	/**
	 * Sets the sort order
	 * @param SortOrder enum of wanted sort order
	 */
	public void setSortOrder(SortOrder s) {
		CoinList.sortOrder = s;
	}
	
	/**
	 * Sorts list based on SortOrder {@link s}
	 * @param s SortOrder enum to specify how to sort
	 */
	public static void sort(SortOrder s) {
		Comparator<Coin> comp;
		
		switch(s) {
		case ALPHABETICAL: comp = new NameComparator(); break;
		case PRICE: comp = new PriceComparator(); break;
		case MKTCAP: comp = new MarketCapComparator(); break;
		case CHANGE: comp = new DailyChangeComparator(); break;
		}

	}
}
