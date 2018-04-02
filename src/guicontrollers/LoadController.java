package guicontrollers;

import coin.Coin;
import coin.CoinList;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadController implements Initializable {
    @FXML private JFXProgressBar loadingBar;
    @FXML private Label loadInfo;

    private Task<?> task;

    //CoinMainScreen.fxl path
    private String coinMainPath = "/resources/fxml/CoinMainScreen.fxml";

    //CryptexIcon.png path
    private String iconPath = "/resources/images/CryptexIcon.png";

    private ObservableList<Coin> coinArray = FXCollections.observableArrayList();

    private int count = 0;

    boolean bottom = false;
    
    /**
     * 
     */
    public void Load(){
        loadingBar.setProgress(0.0);
        task = taskCreate();
        loadingBar.progressProperty().unbind();
        loadingBar.progressProperty().bind(task.progressProperty());
        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        doneLoad();
                    }
                });
        new Thread(task).start();
    }

    /**
     * 
     */
    public void doneLoad(){
        CoinMainController.setCoinArray(coinArray);
        Stage window = MainScreenController.getWindow();
        window.hide();
        Parent root3 = null;
        try {
            root3 = FXMLLoader.load(getClass().getResource(coinMainPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene main = new Scene(root3);
        Stage window2 = new Stage();
        window2.getIcons().add(new Image(iconPath));
        window2.setTitle("Cryptex");
        window2.setScene(main);
        window2.show();
        TableView<Coin> table = CoinMainController.getTable2();
        setScroll(main, table);
    }

    /**
     * 
     * @return
     */
    public Task<?> taskCreate(){
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                /*for(int i=count; i<100+count; i++) {
                    int odd = (int) Math.pow(-1, i);
                    coin.add(new Coin(i + 1, new ImageView(new Image("https://www.cryptocompare.com/media/20627/nxt.png", 50, 0, true, false)), "Bitcoin" + i, "$" + (i + 1) * 100, i * 10, odd * (i * 20) + "%"));
                    updateProgress(i+1, 100);
                }*/
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        loadInfo.setText("Attempting to fetch data...");
                    }
                });

                if(count == 0) {
                    CoinList.init();
                    CoinList.loadAllMarketData("USD");
                }
                
                Coin[] list = CoinList.getList();
                for(int i = count; i < count+100; i++){
                    if(i == CoinList.getList().length){
                        count = i;
                        bottom = true;
                        break;
                    }
                    Coin c = list[i];
                    coinArray.add(c);
                    //System.out.println(CoinList.getList()[i].getDailyChangePercent());
                    updateProgress(i+1, 100);
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            loadInfo.setText("Added " + c.getName());
                        }
                    });
                }
                return true;
            }
        };
    }

    private ScrollBar getTableViewScrollBar(TableView<?> table) {
        ScrollBar scrollbar = null;
        for (Node node : table.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) node;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    scrollbar = bar;
                }
            }
        }
        return scrollbar;
    }

    public void setScroll(Scene main, TableView<Coin> table){
        ScrollBar tScroll = getTableViewScrollBar(table);
        tScroll.valueProperty().addListener((observable, oldValue, newValue) -> {
            double position = newValue.doubleValue();
            ScrollBar scrollBar = getTableViewScrollBar(table);
            if (position == scrollBar.getMax()) {
                task = taskCreate();
                if(!bottom){
                    count+=100;
                    main.setCursor(Cursor.WAIT);
                }
                task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                        new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent event) {
                                main.setCursor(Cursor.DEFAULT);
                            }
                        });
                new Thread(task).start();
                CoinMainController.setCoinArray(coinArray);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Load();
    }
}
