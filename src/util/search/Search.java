package util.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import coin.Coin;
import coin.CoinList;
import coin.SortOrder;
import coin.comparator.NameComparator;
import util.APINotRespondingException;

public class Search {

	/**
	 * Will search the given coin list using a String 
	 * @param String s the substring to search for
	 * @param list the coinList which to search against
	 * @return a list of Coin Objects that contain String s as a substring (used for popup)
	 */
	public static ArrayList<Coin> search(Coin[] list, String symbol) {
		ArrayList<Coin> coins = new ArrayList<Coin>(); //Search results from Name
		ArrayList<Coin> coins2 = new ArrayList<Coin>(); // Search results from Code
		
		int i = binSearch(list,0,list.length, symbol);
		int k = binSearch2(list, 0, list.length, symbol);
		
		//shows up to a max of 6, the next entries containing user's search. (List is pre-sorted alphabetically)
		for (int j = i; j < i+6; j++) {
			if (list[j].getName().toLowerCase().startsWith(symbol.toLowerCase()))
				coins.add(list[j]);
				//System.out.println(coins.get(i).getName());
		}
		
		for (int j = k; j < k+6; j++) {
			if (list[j].getCode().toLowerCase().startsWith(symbol.toLowerCase()))
				coins2.add(list[j]);
				//System.out.println(coins2.get(j).getCode());
		}
		//So now i have a list of COINS from searching by name, and another list of COINS searching by Code
		// Going to Remove all duplicates coins, join them, and sort them.
		coins.removeAll(coins2);
		coins.addAll(coins2);
		Collections.sort(coins,new NameComparator());
		return coins;
		
	}
	/**
	 * The second search that returns just 1 Coin Object
	 * @param list
	 * @param symbol
	 * @return a List containing Coins that are similar to the Name/Code searched
	 */
	public static Coin searchCoin(String symbol) {
		ArrayList<Coin> coin;
		coin = search(CoinList.getAlphabeticalList(),symbol);
		return coin.get(0);
		
	}
	
	
	private static int binSearch(Coin[] list ,int start, int end, String s) {
		s = s.toLowerCase();
		int low = start;
		int high = end;
		while (low <= high) {
			int mid = (low+high)/2;
			String midVal = list[mid].getName();
			
			if (midVal.compareToIgnoreCase(s) < 0) {
				low = mid + 1;
			}
			else if(midVal.compareToIgnoreCase(s) > 0) {
				high = mid -1;
			}
			else {
				return mid; //index of found key
			}
		}
		return low; // index of insertion point
		
	}
	
	//This one is for the Coin Code
	private static int binSearch2(Coin[] list ,int start, int end, String s) {
		int low = start;
		int high = end;
		while (low <= high) {
			int mid = (low+high)/2;
			String midVal = list[mid].getCode();
			
			if (midVal.compareToIgnoreCase(s) < 0) {
				low = mid + 1;
			}
			else if(midVal.compareToIgnoreCase(s) > 0) {
				high = mid -1;
			}
			else {
				return mid; //index of found key
			}
		}
		return low; // index of insertion point
		
	}
	
	public static void main(String[] args) {
		try {
			CoinList.init();
		} catch (APINotRespondingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CoinList.sort(SortOrder.ALPHABETICAL);
		Search.search(CoinList.getAlphabeticalList(),"bit");
			
	}
	
	//add private helped / sorting methods here
}

