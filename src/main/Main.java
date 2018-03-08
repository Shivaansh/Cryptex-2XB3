package main;

import coin.Coin;
import coin.CoinList;
import util.APINotRespondingException;
import util.Logger;

public class Main {
	public static void main(String args[]) {
		//Test Comment - Z
		try {
			CoinList.init();
			CoinList.loadMarketData("USD");
			
			for(Coin c : CoinList.getList())
				System.out.printf("%s (%s): Price: %f, Market Cap: %f, 24h: %f %% \n", c.getName(), c.getCode(), c.getPrice(), c.getMarketCap(), c.getDailyChangePercent());
						
		} catch (APINotRespondingException e) {
			Logger.error("API Not responding");
			e.printStackTrace();
		}
	}

}
