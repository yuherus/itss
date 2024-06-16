package screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminScreen implements Initializable {

    @FXML
    private BorderPane adminView;

    @FXML
    private MenuButton infomenu;

    @FXML
    private MenuItem place;

    @FXML
    private Button logout;

    @FXML
    private Button request;

    @FXML
    private MenuItem sampletour;

    @FXML
    private MenuItem style;

    @FXML
    private MenuItem tour;

    @FXML
    private MenuButton tourmenu;

    @FXML
    private Button transaction;

    @FXML
    private MenuItem customer;

    @FXML
    private MenuItem tourguide;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeScene("/views/admin/sampletour.fxml");
        tour.setOnAction(event -> changeScene("/views/admin/tourlist.fxml"));
        sampletour.setOnAction(event -> changeScene("/views/admin/sampletour.fxml"));
        style.setOnAction(event -> changeScene("/views/admin/style.fxml"));
        transaction.setOnAction(event -> changeScene("/views/admin/transaction.fxml"));
        request.setOnAction(event -> changeScene("/views/admin/request.fxml"));
        place.setOnAction(event -> changeScene("/views/admin/location.fxml"));
        customer.setOnAction(event -> changeScene("/views/admin/customer-information.fxml"));
        tourguide.setOnAction(event -> changeScene("/views/admin/tourguide-information.fxml"));
        logout.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Stage stage = (Stage) logout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void changeScene(String scenePath) {
        AnchorPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            view = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adminView.setCenter(view);
    }
}
