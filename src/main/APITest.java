package main;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import coin.Coin;
import coin.CoinList;
import util.APINotRespondingException;
import util.Logger;

public class Main {
	public static void main(String args[]) {
		try {
			CoinList.init();
			CoinList.loadAllMarketData("USD");
			
			for(Coin c : CoinList.getList()) {
				System.out.printf("%s (%s), %s, %s, %s\n", c.getName(), c.getCode(), c.getDisplayPrice(), c.getDisplayMarketCap(), c.getDisplayDailyChangePercent());
				System.out.printf("%s (%s), %f, %f, %f\n\n", c.getName(), c.getCode(), c.getPrice(), c.getMarketCap(), c.getDailyChangePercent());
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
