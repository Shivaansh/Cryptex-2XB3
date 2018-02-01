package util;

import org.json.simple.JSONObject;

public class Coin {
	
	private String name; 
	private String code; 
	private double totalSupply; 
	
	private final String NAME_TAG = "CoinName";
	private final String CODE_TAG = "Name";
	private final String SUPLLY_TAG = "TotalCoinSupply";
	
	public Coin(JSONObject o) {
		name = (String) o.get(NAME_TAG);
		code = o.get(CODE_TAG).toString();
				
		String totalSupplyStr = o.get(SUPLLY_TAG).toString().replace(",","").replace(" ", "");
		try {
			totalSupply = Double.parseDouble(totalSupplyStr);
		}catch(NumberFormatException e){
			totalSupply = Double.NaN;
		}
	}
	
	public Coin(String name, String code, double totalSupply) {
		this.name = name;
		this.code = code; 
		this.totalSupply = totalSupply;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public double getTotalSupply() {
		return totalSupply;
	}
	
}
