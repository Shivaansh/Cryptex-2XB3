package main;

import util.Coin;
import util.CoinList;

public class Main {
	public static void main(String args[]) {
		if(!CoinList.init())
			System.out.println("Couldn't connect to API");
		
		for(Coin c : CoinList.getList()) {
			System.out.printf("%s - %sn", c.getName(),  c.getCode());
		}
	}

}
