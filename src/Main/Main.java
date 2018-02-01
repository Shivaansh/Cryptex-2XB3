package Main;

import Util.Coin;
import Util.CoinList;

public class Main {
	public static void main(String args[]) {
		if(!CoinList.init())
			System.out.println("Couldn't connect to API");
		
		for(Coin c : CoinList.getList()) {
			System.out.printf("%s - %s - %s\n", c.getName(),  c.getCode(), c.getTotalSupply());
		}
	}

}
