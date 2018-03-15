package coin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import util.APIHandler;
import util.APIHandler.CallType;
import util.APINotRespondingException;

/**
 * Coin object - stores data on a specific cryptocurrency
 * @author Somar Aani
 *
 */
public class Coin{
	
	private String name; 
	private String code; 
	private double totalSupply; 
	
	//raw data
	private double mktCap;
	private double price;
	private double dailyChangePercent; 
	
	//display data 
	private String mktCapDisp; 
	private String priceDisp;
	private String dailyChangePercentDisp;
	
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
	
	/**
	 * Sets the formatted Daily Change Percent (formatted with % symbol)
	 * @param c formatted daily change percent
	 */
	public void setDisplayDailyChangePercent(String c) {
		dailyChangePercentDisp = c;
	}
	
	/**
	 * Gets the formatted daily change percent (formatted with % symbol)
	 * @return the formated daily change percent as a string
	 */
	public String getDisplayDailyChangePercent() {
		return dailyChangePercentDisp;
	}
	
	/**
	 * Sets the formatted market cap (formatted with coin symbol)
	 * @param c daily change percent string to set
	 */
	public void setDisplayMarketCap(String c) {
		mktCapDisp = c;
	}
	
	/**
	 * Gets the formatted market cap (formatted with coin symbol)
	 * @return the formatted market cap string
	 */
	public String getDisplayMarketCap() {
		return mktCapDisp;
	}
	
	/**
	 * Gets the formatted price (formatted with coin symbol)
	 * @return formatted price string
	 */
	public String getDisplayPrice() {
		return priceDisp;
	}
	
	/**
	 * Set the formatted price (formatted with coin symbol)
	 * @param c display price string 
	 */
	public void setDisplayPrice(String c) {
		priceDisp = c;
	}
	
	/**
	 * Gets the coins daily historical data in the date range specified. Dates must be less than 2000 days apart
	 * @param relCoinCode base currency to use for coin value 
	 * @param fromDate date from, format is "dd/MM/YYYY"
	 * @param toDate date to, format is "dd/MM/YYY"
	 * @return array including all coin prices from fromDate to toDate
	 * @throws APINotRespondingException if API does not responds or responds with an error
	 * @throws IllegalArgumentException if DateTo is before DateFrom or more than 2000 days apart
	 */
	public double[] getDailyHistorical(String relCoinCode, String fromDate, String toDate) throws APINotRespondingException {
	    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	   
	    Date from;
	    Date to;
	    
		try {
			from = dateFormat.parse(fromDate);
			to = dateFormat.parse(toDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date must be of the format dd/MM/yyyy");
		}
	
	    long toTs = to.getTime()/1000;
		long diff = to.getTime() - from.getTime();
	    long limit = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	    
	    System.out.println(limit);
	    
	    if(limit <= 0)
	    	throw new IllegalArgumentException("DateTo must come after DateFrom");
	    	
	    if(limit > 2000)
	    	throw new IllegalArgumentException("Dates cannot be more than 2000 dates apart");
	    

		JsonArray dataArray = APIHandler.request(CallType.HISTO_DAY, "fsym", this.code, "tsym", relCoinCode, "toT", String.valueOf(toTs), "limit", String.valueOf(limit)).getAsJsonArray("Data");
		
		double[] data = new double[dataArray.size()];
		int i = 0;
		for(JsonElement curr : dataArray) {
			data[i++] = curr.getAsJsonObject().getAsJsonPrimitive("close").getAsDouble();
		}
		
		return data;
	}

	@Override
	public String toString() {
		return name + " (" + code + ")";
	}
	
}
