package coin;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import util.APIHandler;
import util.Logger;
import util.APIHandler.CallType;

/**
 * Coin List that stores all coins avaiable
 * @author Somar Aani
 */

public class CoinList{
			
	private static List<Coin> list; 
	
	/**
	 * Initializes the coin list by making an API call
	 * @return if the list was initialized correctly
	 */
	public static boolean init() {
		Logger.info("Initializing coin list ...");
		
		JSONObject mainObj = APIHandler.request(CallType.COIN_LIST);
		if(mainObj == null) return false;
		
		Object[] values = ((JSONObject) mainObj.get("Data")).values().toArray();
		
		//initialize list array
		list = new ArrayList<Coin>();
		
		//adds coins to list
		JSONObject obj;
		for(int i = 0; i < values.length; i++) {
			obj = (JSONObject)values[i];
			list.add(new Coin(obj));
		}
		
		Logger.info("Coin list successfully initialized - " + list.size() + " coins");
		return true;
	}
	
	/**
	 * Finds a specific coin
	 * @param code 3 character code associated with coin
	 * @return Coin object representing the specific cryptocurrency
	 */
	public static Coin getCoin(String code) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getCode().equals(code))
				return list.get(i);
		}
		return null;
	}
	
	/** @return coin list */
	public static List<Coin> getList() {
		return list;
	}
}
