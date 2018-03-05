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
		COIN_LIST ("all/coinlist", "", ""),
		PRICE_MULTI_FULL ("pricemultifull", "fsyms", "tsyms"),
		PRICE ("price", "fsym", "tsyms");
		
		/** contains path to data in API */
		private final String path;
		private final String inputParam;
		private final String outputParam;
		
		CallType(String path, String inputParam, String outputParam) {
			this.path = path;
			this.inputParam = inputParam;
			this.outputParam = outputParam;
		}
	}
	
	private final static String BASE_URL = "https://min-api.cryptocompare.com/data/";
	
	/**
	 * Makes a request to the API that does not require parameters
	 * @param type type of call to make
	 * @return JSON object containing data from API
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public static JSONObject request(CallType type) throws APINotRespondingException {
		return request(type, "", "");
	} 
	
	/**
	 * Makes a request to the API using parameters
	 * @param type type of call to make
	 * @param inParam API input parameters
	 * @param outParam API output parameters
	 * @return JSON object containing data from API
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public static JSONObject request(CallType type, String inParam, String outParam) throws APINotRespondingException {
				
		//open api 
		InputStream input;
		URL url;
		
		try {
			url = new URL(BASE_URL + type.path + "?" + type.inputParam + "=" + inParam + "&" + type.outputParam +"=" + outParam);
			input = url.openConnection().getInputStream();
		}
		catch(IOException e) {
			throw new APINotRespondingException(e);
		}
		
		Logger.info("Attempting to fetch " + url.toString());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		//Logger.info("Connection successful, parsing object ...");
		
		//parses JSON to create object array
		JSONParser parser = new JSONParser();
		JSONObject mainObj;
		try {
			mainObj = (JSONObject) parser.parse(reader);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		if(mainObj == null)
			throw new APINotRespondingException();
					
		//get response (not always available)
		String response = (String) mainObj.get("Response");
		if(response != null && response.equals("Error"))
			throw new APINotRespondingException(mainObj.get("Message").toString());
			
		//Logger.info("API Response: " + response);
	
		//return object
		return mainObj;
	}

}
