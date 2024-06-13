package screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderScreen implements Initializable{
    @FXML
    private BorderPane userView;
    @FXML
    private Button historyBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button tourBtn;
    @FXML
    private Button trackingBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeBtn.setOnAction(event -> changeScene("/views/user/home.fxml"));
        historyBtn.setOnAction(event -> changeScene("/views/user/history.fxml"));
        tourBtn.setOnAction(event -> changeScene("/views/user/tour.fxml"));
        trackingBtn.setOnAction(event -> changeScene("/views/user/tracking.fxml"));
        logoutBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Stage stage = (Stage) logoutBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
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
        userView.setCenter(view);
    }


}

