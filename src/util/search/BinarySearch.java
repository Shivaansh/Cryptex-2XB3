package util.search;

import java.util.Comparator;

import coin.Coin;
import coin.CoinList;
import coin.SortOrder;
import util.APINotRespondingException;

/**
 * Class to handle binary search
 * @author Juwon
 *
 */
public class BinarySearch {
	
	/**
	 * 
	 * @param list
	 * @param c
	 * @return
	 */
	public static int search(String symbol, Coin[] list, Comparator<Coin> c) {
		return -1;
	}
	
	public static void main(String[] args) {
		try {
			CoinList.init();
		} catch (APINotRespondingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CoinList.sort(SortOrder.ALPHABETICAL);
		
		
	}
}
