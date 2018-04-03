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
<<<<<<< HEAD
	public static int search(String symbol, Coin[] list, Comparator<Coin> c) { //The "Search.java" has the proper search
		int low = 0;
//		int high = list.length;
//		while (low <= high) {
//			int mid = (low+high)/2;
//			Coin midCoin = list[mid];
//			
//			if (c.compare(o1, o2) < 0) {
//				low = mid + 1;
//			}
//			else if(midVal.compareToIgnoreCase(s) > 0) {
//				high = mid -1;
//			}
//			else {
//				return mid; //index of found key
//			}
//		}
		return low; // index of insertion point
	}
	
=======
	public static int search(String symbol, Coin[] list, Comparator<Coin> c) {
		return -1;
	}
>>>>>>> refs/remotes/origin/juwon
	
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
