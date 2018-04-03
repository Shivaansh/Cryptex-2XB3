package main;

import java.util.Arrays;

import coin.Coin;
import coin.CoinList;
import coin.comparator.InternalOrderComparator;
import util.APINotRespondingException;
import util.Logger;
import util.sort.QuickSort;

public class APITest {
	public static void main(String args[]) {
		try {
			CoinList.init();
			
			for(Coin c : CoinList.getAlphabeticalList()) {
				System.out.print(c.getName() + ", ");
			}
			
			System.out.println();
			
			for(Coin c : CoinList.getList()) {
				System.out.print(c.getName() + ", ");
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
