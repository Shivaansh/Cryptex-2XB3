package main;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import coin.Coin;
import coin.CoinList;
import util.APINotRespondingException;
import util.Logger;

public class Main {
	public static void main(String args[]) {
		try {
			CoinList.init();
			for(Coin c : CoinList.getList())
				//System.out.println("Name: " + c.getName() + "(" + c.getCode() + "), Price: " + c.getPrice("USD") + " USD, Supply: " + c.getTotalSupply());
				System.out.print("," + c.getCode());
		} catch (APINotRespondingException e) {
			e.printStackTrace();
		}
		
		//testing coin list
		/*for(Coin c : CoinList.getList()) {
			System.out.printf("%s - %s - %s\n", c.getName(),  c.getCode(), c.getTotalSupply());
		}*/
	}

}
