package main;

import coin.CoinList;
import util.Logger;

public class Main {
	public static void main(String args[]) {
		if(!CoinList.init()) {
			Logger.error("Couldn't initialize coin list");
			return;
		}
		
		//testing getting coin + market cap
		System.out.println(CoinList.getCoin("BTC").getMarketCap());
		
		//testing coin list
		/*for(Coin c : CoinList.getList()) {
			System.out.printf("%s - %s - %s\n", c.getName(),  c.getCode(), c.getTotalSupply());
		}*/
	}

}
