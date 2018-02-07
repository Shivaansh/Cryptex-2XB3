package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Includes methods to handle API calls
 * @author Somar Aani
 */
public class APIHandler {
	
	/** Enum containing all possible API calls */
	public static enum CallType {
		COIN_LIST ("all/coinlist"),
		PRICE_MULTI_FULL ("pricemultifull");
		
		/** contains path to data in API */
		private final String path;
		
		CallType(String identifier) {
			this.path = identifier;
		}
	}
	
	private final static String BASE_URL = "https://min-api.cryptocompare.com/data/";
	
	/**
	 * Makes a request to the API that does not require parameters
	 * @param type type of call to make
	 * @return JSON object containing data from API
	 */
	public static JSONObject request(CallType type) {
		return request(type, "", "");
	} 
	
	/**
	 * Makes a request to the API including parameters
	 * @param type type of call to make
	 * @param inParam API input parameters
	 * @param outParam API output parameters
	 * @return JSON object containing data from API
	 */
	public static JSONObject request(CallType type, String inParam, String outParam) {
		try {			
			//open api 
			URL url = new URL(BASE_URL + type.path + "?fsyms=" + inParam + "&tsyms=" + outParam);
			Logger.info("Attempting to fetch " + url.toString());
			
			InputStream input = url.openConnection().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			Logger.info("Connection successful, parsing object ...");
			
			//parses JSON to create object array
			JSONParser parser = new JSONParser();
			JSONObject mainObj = (JSONObject) parser.parse(reader);
						
			//get response (not always available)
			String response = (String) mainObj.get("Response");
			Logger.info("API Response: " + response);
		
			//return object
			return mainObj;
			
		}catch(MalformedURLException e) {
			Logger.error(e.toString());
			e.printStackTrace();
		}catch(IOException e) {
			Logger.error(e.toString());
			e.printStackTrace();
		} catch (ParseException e) {
			Logger.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}

}
