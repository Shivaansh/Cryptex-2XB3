//<<<<<<< HEAD
package main;

import coin.Coin;
import coin.CoinList;
import util.APINotRespondingException;
import util.Logger;
//Test branch
public class Main {
	public static void main(String args[]) {
		try {
			CoinList.init();
			CoinList.loadMarketData("USD");
			
			for(Coin c : CoinList.getList())
				System.out.printf("%s (%s): Price: %f, Market Cap: %f, 24h: %f %% \n", c.getName(), c.getCode(), c.getPrice(), c.getMarketCap(), c.getDailyChangePercent());
						
		} catch (APINotRespondingException e) {
			Logger.error("API Not responding");
			e.printStackTrace();
		}
	}

}
=======
package main;

import javafx.scene.image.*;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    //Setup MainScreen
    public void start(Stage MainScreen) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MainScreen.fxml"));
        Scene scene = new Scene(root);
        
        //Disable window resizing
        MainScreen.setResizable(false);
        
        //Set the icon for the MainScreen window
        MainScreen.getIcons().add(new Image("/resources/images/CryptexIcon.png"));
        
        //Set the title of the MainScreen window
        MainScreen.setTitle("Cyptex");
        
        //Set the screen and display it
        MainScreen.setScene(scene);
        MainScreen.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
>>>>>>> refs/remotes/origin/juwon
