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
						
		} catch (APINotRespondingException e) {
			Logger.error("API Not responding");
			e.printStackTrace();
		}
	}

}
