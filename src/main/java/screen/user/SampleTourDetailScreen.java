package screen.user;

import controller.SampleTourController;
import controller.TourController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Location;
import model.SampleTour;
import model.Tour;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class SampleTourDetailScreen implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public HBox createHBox(Pair<Location, Timestamp> pair) {
        Text dateTimeText = new Text(String.valueOf(pair.getValue()));
        dateTimeText.setFont(new Font(14));

        Text locationText = new Text(pair.getKey().getName());
        locationText.setFont(new Font(14));

        Text descriptionText = new Text(pair.getKey().getDescription());
        descriptionText.setFont(new Font(14));

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(dateTimeText, locationText, descriptionText);

        // Set padding for HBox
        hBox.setPadding(new Insets(0, 0, 0, 20));

        return hBox;
    }

    public void setTour(SampleTour tour){
        try {
            hearderImg.setImage(new Image(new FileInputStream(tour.getLocations().getFirst().getKey().getImageUrl())));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        titleTxtDetail.setText(tour.getTourName());
            priceTxtDetail.setText(String.valueOf(tour.getTotalCost()));
            description.setText(tour.getDescription());

            StringJoiner locationJoiner = new StringJoiner(", ");
            for (Pair<Location, Timestamp> pair : tour.getLocations()){
                HBox hBox = createHBox(pair);
                locationList.getChildren().add(hBox);
                locationJoiner.add(pair.getKey().getName());
            }
            String locationStr = locationJoiner.toString();
            locationTxtDetail.setText(locationStr);

            btnBook.setOnAction(event -> {
                Tour bookedTour = new Tour();
                bookedTour.setTourName(tour.getTourName());
                bookedTour.setTotalCost(tour.getTotalCost());
                bookedTour.setLocations(tour.getLocations());
                bookedTour.setDescription(tour.getDescription());
                bookedTour.setTouristId(Integer.parseInt(System.getProperty("userId")));
                bookedTour.setStartDate(tour.getStartDate());
                bookedTour.setEndDate(tour.getEndDate());
                bookedTour.setStatus(Tour.Status.PENDING);
                bookedTour.setTourGuideId(4); //default tour guide id

                ScrollPane view = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/booking-now.fxml"));
                    view = loader.load();
                    BookingNowScreen bookingNowScreen = loader.getController();
                    bookingNowScreen.infoTour(bookedTour);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
                userView.setCenter(view);
            });
    }
}
