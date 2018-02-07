package coin;

import org.json.simple.JSONObject;

import util.APIHandler;
import util.APIHandler.CallType;

/**
 * Coin object - stores data on a specific cryptocurrency
 * @author Somar Aani
 *
 */
public class Coin {
	
	private String name; 
	private String code; 
	
	private double totalSupply; 
	
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
	 * Returns market cap of coin relative to USD through an API call
	 * @return current market Cap of coin (relative to USD)
	 */
	public double getMarketCap() {
		return getMarketCap("USD");
	}
	
	/**
	 * Returns market cap of coin relative to a specific coin through an API call
	 * @param relCoinCode 3 character coin code, returns market cap relative to this currency
	 * @return current market Cap of coin (relative to relCoinCode)
	 */
	public double getMarketCap(String relCoinCode) {
		JSONObject mainObj = (JSONObject)APIHandler.request(CallType.PRICE_MULTI_FULL, this.code, relCoinCode);
		JSONObject rawObj = (JSONObject) ((JSONObject) ((JSONObject) mainObj.get("RAW")).get(this.code)).get(relCoinCode);
		return Double.parseDouble(rawObj.get("MKTCAP").toString());
	}
	
	/**  @return name of coin */
	public String getName() {
		return name;
	}
	/**  @return 3 digit code of coin */
	public String getCode() {
		return code;
	}
	
	/**  @return total supply of coin */
	public double getTotalSupply() {
		return totalSupply;
	}
	
}
