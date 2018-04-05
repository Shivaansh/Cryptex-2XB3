package user;

import java.util.ArrayList;

import coin.Coin;

/**
 * ADT for a user
 * @author Juwon
 *
 */
public class User {
	private String username;
	private String password;
	private ArrayList<Coin> favorites;
	
	public User (String name, String pass) {
		this.username = name;
		this.password = pass;
		this.favorites = new ArrayList<Coin>();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Coin> getFavorites() {
		return favorites;
	}
	public void setFavorites(ArrayList<Coin> favorites) {
		this.favorites = favorites;
	}
	public void addFavorite(Coin c) {
		this.favorites.add(c);
	}

}
