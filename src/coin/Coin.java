package coin;

import com.google.gson.JsonObject;

/**
 * Coin object - stores data on a specific cryptocurrency
 * @author Somar Aani
 *
 */
public class Coin{
	
	private String name; 
	private String code; 
	
	private double totalSupply; 
	private double mktCap;
	private double price;
	private double dailyChangePercent; 
	
	private final String NAME_TAG = "CoinName";
	private final String CODE_TAG = "Name";
	private final String SUPLLY_TAG = "TotalCoinSupply";
	
	/**
	 * Constructor - creates Coin from JSON Object
	 * @param jsonObject JSONObject which populates the fields in the coin object
	 */
	public Coin(JsonObject jsonObject) {
		name = jsonObject.get(NAME_TAG).getAsString();
		code = jsonObject.get(CODE_TAG).getAsString();
		
		//totalSupply not always consistently formatted in API
		String totalSupplyStr = jsonObject.get(SUPLLY_TAG).toString().replace(",","").replace(" ", "");
		try {
			totalSupply = Double.parseDouble(totalSupplyStr);
		}catch(NumberFormatException e){
			totalSupply = Double.NaN;
		}
	}
	
	/**
	 * Returns market cap of coin currently stored
	 * @param relCoinCode currency code, returns market cap relative to this currency
	 * @return current market Cap of coin
	 */
	public double getMarketCap() {
		return mktCap;
	}
	
	/**
	 * Sets marketCap of coin
	 * @param d marketCap to set
	 */
	public void setMarketCap(double d){
		mktCap = d;
	}
	
	/**
	 * Returns price of coin 
	 * @return current price of coin 
	 */
	public double getPrice(){
		return price;
	}
	
	/**
	 * Sets coin price
	 * @param p price to set
	 */
	public void setPrice(Double p) {
		price = p;
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
	
	/**
	 * Sets the daily (24h) price change for the coin
	 * @param c 24h change for coin
	 */
	public void setDailyChangePercent(double c) {
		dailyChangePercent = c;
	}
	
	/**
	 * Gets the coins daily (24h change) in price
	 * @return the coins 24h change in price
	 */
	public double getDailyChangePercent() {
		return dailyChangePercent;
	}

	@Override
	public String toString() {
		return name + " (" + code + ")";
	}
	
}
