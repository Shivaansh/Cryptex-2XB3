package main;

import coin.Coin;
import coin.CoinList;
import util.APINotRespondingException;
import util.Logger;

public class Main {
	public static void main(String args[]) {
		try {
            int count = 1;
			CoinList.init();
			CoinList.loadMarketData("USD");
			for(Coin c : CoinList.getList()){
			    System.out.println(count + " " + c.getName() + " " + c.getPrice() + " " + c.getMarketCap() + " " + c.getDailyChangePercent());
			    count ++;
            }

		} catch (APINotRespondingException e) {
			Logger.error("API Not responding");
			e.printStackTrace();
		}
	}

}
