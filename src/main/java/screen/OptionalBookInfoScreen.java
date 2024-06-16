package screen;

import controller.RequestController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Location;
import model.Request;
import model.Style;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class OptionalBookInfoScreen implements Initializable {

    @FXML
    private VBox copiedVBox;

    @FXML
    private Button requestBtn;

    private List<Location> places;

    private Style style;

    public void setPlaces(List<Location> places) {
        this.places = places;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void copyTripDetailAndShowBackButton(VBox tripDetail) {
        copiedVBox.getChildren().clear();
        copiedVBox.getChildren().addAll(tripDetail.getChildren());
        changeButtonToBack();
    }

    private void changeButtonToBack() {
        Button nextButton = (Button) copiedVBox.lookup("#nextButton");
        if (nextButton != null) {
            nextButton.setText("Back");
            nextButton.setOnAction(event -> {
                BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
                ScrollPane view = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book.fxml"));
                    view = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userView.setCenter(view);
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestBtn.setOnAction(event -> {
            Request request = new Request();
            request.setLocations(places);
            request.setTouristId(Integer.parseInt(System.getProperty("userId")));
            request.setStyleId(style.getStyleId());
            try {
                RequestController requestController = new RequestController();
                requestController.add(request);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Request Sent");
                alert.setHeaderText(null);
                alert.setContentText("Your request has been sent successfully!");
                alert.showAndWait();
                BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
                ScrollPane view = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/tour.fxml"));
                    view = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userView.setCenter(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

