package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Includes methods to handle API calls
 * @author Somar Aani
 */
public class APIHandler {
	
	/** Enum containing all possible API calls */
	public static enum CallType {
		COIN_LIST ("all/coinlist"),
		PRICE_MULTI_FULL ("pricemultifull", "fsyms", "tsyms"),
		PRICE ("price", "fsym", "tsyms");
		
		/** contains path to data in API */
		private final String path;
		private final String[] params;
		
		CallType(String path, String... params) {
			this.path = path;
			this.params = params;
		}
	}
	
	private final static String BASE_URL = "https://min-api.cryptocompare.com/data/";
	
	/**
	 * Makes a request to the API using parameters
	 * @param type type of call to make
	 * @param params list of input parameters
	 * @return JSON object containing data from API
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public static JsonObject request(CallType type, String... params) throws APINotRespondingException {
		
		//create url from input parameters
		String urlString = BASE_URL + type.path + "?";
		for(int i = 0; i < type.params.length; i++) {
			urlString += type.params[i] + "=" + params[i] + "&";
		}
		
		//Logger.info("Attempting to fetch " + urlString);
		
		InputStream input;
		URL url;
		
		//open stream to api
		try {
			url = new URL(urlString);
			input = url.openConnection().getInputStream();
		}
		catch(IOException e) {
			throw new APINotRespondingException(e);
		}
				
		//create rootObject from API JSON
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JsonObject rootObject = new Gson().fromJson(reader, JsonObject.class);
		
		if(rootObject == null)
			throw new APINotRespondingException();
					
		//get response (not always available), throw Exception if error
		if(rootObject.has("Response") && rootObject.get("Response").equals("Error"))
			throw new APINotRespondingException(rootObject.get("Message").getAsString());
			
		//Logger.info("API Response: " + rootObject.get("Response"));
	
		return rootObject;
	}
	
}
