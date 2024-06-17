package screen.user;

import controller.TourController;
import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Location;
import model.Tour;
import model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class TourDetailScreen implements Initializable {

    @FXML
    private ImageView hearderImg;

    @FXML
    private VBox locationList;

    @FXML
    private Button btnBook;

    @FXML
    private Text description;

    @FXML
    private Text locationTxtDetail;

    @FXML
    private Text priceTxtDetail;

    @FXML
    private Text titleTxtDetail;

    @FXML
    private Text statusTxt;

    @FXML
    private HBox titleHbox;

    @FXML
    private Text emailTxt;

    @FXML
    private Text guideNameTxt;

    @FXML
    private Text nameTxt;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public HBox createHBox(Pair<Location, Timestamp> pair) {
        // Create Text nodes
        Text dateTimeText = new Text(String.valueOf(pair.getValue()));
        dateTimeText.setFont(new Font(14));

        Text locationText = new Text(pair.getKey().getName());
        locationText.setFont(new Font(14));

        Text descriptionText = new Text(pair.getKey().getDescription());
        descriptionText.setFont(new Font(14));

        // Create HBox and add Text nodes
        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(dateTimeText, locationText, descriptionText);

        // Set padding for HBox
        hBox.setPadding(new Insets(0, 0, 0, 20));

        return hBox;
    }

    public void setTour(Tour tour){
        TourController tourController = new TourController();
        UserController userController = new UserController();
        try {
            User guide = userController.getById(tour.getTourGuideId());

            hearderImg.setImage(new Image(new FileInputStream(tour.getLocations().getFirst().getKey().getImageUrl())));
            titleTxtDetail.setText(tour.getTourName());

            String statusStr = tour.getStatus().toString();
            statusTxt.setText(Character.toUpperCase(statusStr.charAt(0)) + statusStr.substring(1).toLowerCase());
            priceTxtDetail.setText(String.valueOf(tour.getTotalCost()));
            description.setText(tour.getDescription());

            guideNameTxt.setText(guide.getUsername());
            nameTxt.setText(guide.getName());
            emailTxt.setText(guide.getEmail());

            StringJoiner locationJoiner = new StringJoiner(", ");
            for (Pair<Location, Timestamp> pair : tour.getLocations()){
                HBox hBox = createHBox(pair);
                locationList.getChildren().add(hBox);
                locationJoiner.add(pair.getKey().getName());
            }
            String locationStr = locationJoiner.toString();
            locationTxtDetail.setText(locationStr);

        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
