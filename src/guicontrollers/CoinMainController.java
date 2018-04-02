package guicontrollers;

import coin.Coin;
import coin.CoinList;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CoinMainController implements Initializable{
    @FXML private AnchorPane top;
    @FXML private JFXHamburger hamburger;
    @FXML private AnchorPane drawer;
    @FXML private Label name;
    @FXML private AnchorPane menuOpen;
    @FXML private TableColumn<Coin, Integer> numCol;
    @FXML private TableColumn<Coin, ImageView> imageCol;
    @FXML private TableColumn<Coin, String> nameCol;
    @FXML private TableColumn<Coin, String> priceCol;
    @FXML private TableColumn<Coin, String> capCol;
    @FXML private TableColumn changeCol;
    @FXML private TableView<Coin> tableView;
    @FXML private AnchorPane infoPane;
    @FXML private Pagination coinPage;
    private static TableView<Coin> table2;

    private HamburgerBasicCloseTransition transition;
    private int start = 0;
    private int pageNum = 1;
    private static ObservableList<Coin> coin = FXCollections.observableArrayList();

    @FXML
    public void showHand(){
        top.setCursor(Cursor.HAND);
    }

    @FXML
    public void showDefault(){
        top.setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void hamClick() {
        Timeline openNav = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(drawer.layoutXProperty(), drawer.getLayoutX())),
                new KeyFrame(new Duration(350), new KeyValue(drawer.layoutXProperty(), 0)));
        Timeline closeNav = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(drawer.layoutXProperty(), drawer.getLayoutX())),
                new KeyFrame(new Duration(350), new KeyValue(drawer.layoutXProperty(), -200)));
        Timeline open = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(menuOpen.prefWidthProperty(), menuOpen.getWidth())),
                new KeyFrame(new Duration(350), new KeyValue(menuOpen.prefWidthProperty(), 200)));
        Timeline close = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(menuOpen.prefWidthProperty(), menuOpen.getWidth())),
                new KeyFrame(new Duration(350), new KeyValue(menuOpen.prefWidthProperty(), 0)));
        if(start == 0){
            start = 1;
            openNav.play();
            open.play();
        }
        else if(start == 1){
            start = 0;
            closeNav.play();
            close.play();
        }
        hamburger.setDisable(true);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(350),
                ae -> hamburger.setDisable(false)));
        timeline.play();
        transition.setRate(transition.getRate()*-1);
        transition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coinPage.setPageCount((int)Math.ceil((double)CoinList.getList().length/CoinList.MAX_MARKET_INPUT));
        coin = getCoin();
        transition = new HamburgerBasicCloseTransition(hamburger);
        transition.setRate(-1);
        name.setText(getName());
        priceCol.prefWidthProperty().bind(tableView.widthProperty().subtract(165).divide(4));
        nameCol.prefWidthProperty().bind(tableView.widthProperty().subtract(165).divide(4));
        capCol.prefWidthProperty().bind(tableView.widthProperty().subtract(165).divide(4));
        changeCol.prefWidthProperty().bind(tableView.widthProperty().subtract(165).divide(4));
        
        nameCol.setReorderable(false);
        priceCol.setReorderable(false);
        imageCol.setReorderable(false);
        numCol.setReorderable(false);
        capCol.setReorderable(false);
        
        //numCol.setCellValueFactory(new PropertyValueFactory<Coin, Integer>("number"));
        //imageCol.setCellValueFactory(new PropertyValueFactory<Coin, ImageView>("logo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Coin, String>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Coin, String>("displayPrice"));
        capCol.setCellValueFactory(new PropertyValueFactory<Coin, String>("displayMarketCap"));
        changeCol.setCellValueFactory(new PropertyValueFactory<Coin, String>("displayDailyChangePercent"));
        
        //https://stackoverflow.com/questions/6998551/setting-font-color-of-javafx-tableview-cells
       changeCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<Coin, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        double temp;
                        super.updateItem(item, empty);
                        if (item != null) {
                            if(item.contains("-")){
                                this.setTextFill(Color.RED);
                            }
                            else {
                                this.setTextFill(Color.GREEN);
                            }
                            setText(item);
                        }
                    }
                };
            }
        });
        
        //tableView.scrollTo(100);
       
        //Add to table
        //tableView.setItems(coin);
        table2 = tableView;
        doubleClickedRow();
    }

    private String getName(){
        Scanner x = null;
        String[] nameFull;
        try {
            x = new Scanner(new File("info.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(Objects.requireNonNull(x).hasNext()){
            nameFull = x.nextLine().split(",");
            if(nameFull[0].equals(MainScreenController.getTemp())){
                return nameFull[2];
            }
        }
        return null;
    }

    public static void setCoinArray(ObservableList<Coin> coin) {
        CoinMainController.coin = coin;
    }

    private ObservableList<Coin> getCoin() {
        return coin;
    }

    public static TableView<Coin> getTable2() {
        return table2;
    }

    private void doubleClickedRow(){
        tableView.setRowFactory( tv -> {
            TableRow<Coin> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty()) ) {
                    infoPane.toFront();
                }
            });
            return row;
        });
    }

    @FXML public void closeInfo(){
        infoPane.toBack();
    }

    @FXML public void searchClicked(){
        infoPane.toFront();
    }

    @FXML public void searchEntered(ActionEvent e){
        infoPane.toFront();
    }
}
