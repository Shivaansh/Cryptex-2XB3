package util.search;

import java.util.ArrayList;
import java.util.Comparator;

import coin.Coin;
import coin.CoinList;
import coin.SortOrder;
import util.APINotRespondingException;

public class Search {

	/**
	 * Will search the given coin list using a String 
	 * @param String s the substring to search for
	 * @param list the coinList which to search against
	 * @return a list of Coin Objects that contain String s as a substring (used for popup)
	 */
	public static ArrayList<Coin> search(Coin[] list, String symbol) {
		ArrayList<Coin> coins = new ArrayList<Coin>();
		//list = CoinList.getList(); //if not done in main
		
		// sort the list alphabetically
		int i = binSearch(list,0,list.length, symbol);
		//shows up to a max of 6, the next entries containing user's search. (List is pre-sorted alphabetically)
		for (int j = i; j < i+6; j++) {
			if (list[j].getName().toLowerCase().startsWith(symbol.toLowerCase()))
				coins.add(list[i]);
				//System.out.println(coins.get(i).getName());
		}
		return coins;
		
	}
	/**
	 * The second search that returns just 1 Coin Object
	 * @param list
	 * @param symbol
	 * @return
	 */
	public static Coin searchCoin(String symbol) {
		ArrayList<Coin> coin;
		coin = search(CoinList.getList(),symbol);
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
	
	public static void main(String[] args) {
		try {
			CoinList.init();
		} catch (APINotRespondingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CoinList.sort(SortOrder.ALPHABETICAL);
		Search.search(CoinList.getList(),"bit");
			
	}
	
	//add private helped / sorting methods here
}

