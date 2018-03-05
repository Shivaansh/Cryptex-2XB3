package coin;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import util.APIHandler;
import util.APIHandler.CallType;
import util.APINotRespondingException;

/**
 * Coin object - stores data on a specific cryptocurrency
 * @author Somar Aani
 *
 */
public class Coin implements Comparable<Coin> {
	
	private String name; 
	private String code; 
	
	private double totalSupply; 
	private double mktCap;
	private double price;
	
	private final String NAME_TAG = "CoinName";
	private final String CODE_TAG = "Name";
	private final String SUPLLY_TAG = "TotalCoinSupply";
	
	/**
	 * Constructor - creates Coin from JSON Object
	 * @param o JSONObject which populates the fields in the coin object
	 */
	public Coin(JSONObject o) {
		name = (String) o.get(NAME_TAG);
		code = o.get(CODE_TAG).toString();
		
		//totalSupply not always consistently formatted in API
		String totalSupplyStr = o.get(SUPLLY_TAG).toString().replace(",","").replace(" ", "");
		try {
			totalSupply = Double.parseDouble(totalSupplyStr);
		}catch(NumberFormatException e){
			totalSupply = Double.NaN;
		}
	}
	
	/**
	 * Returns market cap of coin relative to a specific coin through an API call
	 * @param relCoinCode currency code, returns market cap relative to this currency
	 * @return current market Cap of coin (relative to relCoinCode)
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public double getMarketCap(String relCoinCode) throws APINotRespondingException {
		JSONObject mainObj = (JSONObject)APIHandler.request(CallType.PRICE_MULTI_FULL, this.code, relCoinCode);
		JSONObject rawObj = (JSONObject) ((JSONObject) ((JSONObject) mainObj.get("RAW")).get(this.code)).get(relCoinCode);
		return Double.parseDouble(rawObj.get("MKTCAP").toString());
	}
	
	/**
	 * Returns price of coin relative to a currency through an API call
	 * @param relCoinCod currency code, returns price relative to this currency
	 * @return current price of coin (relative to relCoinCode)
	 * @throws APINotRespondingException if API does not respond or responds with an error
	 */
	public double getPrice(String relCoinCode) throws APINotRespondingException{
		try {
			JSONObject mainObj = (JSONObject)APIHandler.request(CallType.PRICE, this.code, relCoinCode);
			return Double.parseDouble(mainObj.get(relCoinCode).toString());
		} catch(APINotRespondingException e) {
			if(e.getMessage().contains("There is no data"))
				return Double.NaN;
			else
				throw new APINotRespondingException(e);
		}
	}
	
	/**  @return name of coin */
	public String getName() {
		return name;
	}
	/**  @return code of coin */
	public String getCode() {
		return code;
	}
	
	/**  @return total supply of coin */
	public double getTotalSupply() {
		return totalSupply;
	}

	@Override
	public int compareTo(Coin c) {
		return 0;
	}
	
}
