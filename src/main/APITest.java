package main;

import coin.Coin;
import coin.CoinList;
import util.APINotRespondingException;
import util.Logger;

public class APITest {
	public static void main(String args[]) {
		try {
			CoinList.init();
			CoinList.loadNextMarketData(CoinList.MAX_MARKET_INPUT, "USD");
			
			for(Coin c : CoinList.getList()) {
				System.out.println(c.getName() + ", " + c.getMarketCap());
			}
			
			
		/*	double[] d = CoinList.getCoin("BTC").getDailyHistorical("USD", "10/02/2017", "09/03/2017");
			for(double a : d)
				System.out.println(a);*/
			
		} catch (APINotRespondingException e) {
			Logger.error("API Not responding");
			e.printStackTrace();
		}
	}

}
