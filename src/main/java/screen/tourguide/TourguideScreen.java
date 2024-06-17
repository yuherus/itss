package screen.tourguide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TourguideScreen implements Initializable {

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button notiBtn;

    @FXML
    private Button tourBtn;

    @FXML
    private BorderPane tourguideView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeBtn.setOnAction(event -> changeScene("/views/tourguide/home.fxml"));
        tourBtn.setOnAction(event -> changeScene("/views/tourguide/tourlist.fxml"));
        notiBtn.setOnAction(event -> changeScene("/views/tourguide/notification.fxml"));
        logoutBtn.setOnAction(event -> {
            try {
                AnchorPane root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                tourguideView.getScene().setRoot(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void changeScene(String scenePath) {
        ScrollPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            view = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tourguideView.setCenter(view);
    }

}
