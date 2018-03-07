package main;

import coin.Coin;
import coin.CoinList;
import util.APINotRespondingException;
import util.Logger;

public class Main {
	public static void main(String args[]) {
		try {
			CoinList.init();
			CoinList.loadMarketData("USD");
			
			Coin btc = CoinList.getCoin("BTC");
			Coin dank = CoinList.getCoin("DANK");
			
			System.out.println("\nIn USD:");
			System.out.printf("%s (%s): price: %f USD, mktcap: %f USD \n", btc.getName(), btc.getCode(), btc.getPrice(), btc.getMarketCap());
			System.out.printf("%s (%s): price: %f USD, mktcap: %f USD \n\n", dank.getName(), dank.getCode(), dank.getPrice(), dank.getMarketCap());
			
			CoinList.loadMarketData("CAD");
			
			System.out.println("\nIn CAD:");
			System.out.printf("%s (%s): price: %f CAD, mktcap: %f CAD \n", btc.getName(), btc.getCode(), btc.getPrice(), btc.getMarketCap());
			System.out.printf("%s (%s): price: %f CAD, mktcap: %f CAD \n", dank.getName(), dank.getCode(), dank.getPrice(), dank.getMarketCap());
			
		} catch (APINotRespondingException e) {
			Logger.error("API Not responding");
			e.printStackTrace();
		}
	}

}
