package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CoinList{
	
	private final static String COINLIST_URL = "https://www.cryptocompare.com/api/data/coinlist/";
	private static List<Coin> list; 
	
	public static boolean init() {
		try {
			//opens url into reader
			URL url = new URL(COINLIST_URL);
			InputStream input = url.openConnection().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			//parses JSON to create object array
			JSONParser parser = new JSONParser();
			JSONObject mainObj = (JSONObject) parser.parse(reader);
			
			//check if successful
			String response = mainObj.get("Response").toString();
			if(!response.equals("Success")) {
				return false;
			}
			
			JSONObject dataObj = (JSONObject) mainObj.get("Data");
			Object[] values = dataObj.values().toArray();
			
			//initialize list array
			list = new ArrayList<Coin>();
			
			//adds coins to list
			JSONObject obj;
			for(int i = 0; i < values.length; i++) {
				obj = (JSONObject)values[i];
				list.add(new Coin(obj));
			}
			return true;
		}catch(IOException e) {
			System.out.println(e.getMessage());
			return false;
		}catch(ParseException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static List<Coin> getList() {
		return list;
	}
}
