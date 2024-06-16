package screen;

import controller.SampleTourController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Location;
import model.SampleTour;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class SampleTourDetailScreen implements Initializable {
    private int tourId;

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

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

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

    public void setTour(){
        SampleTourController sampleTourController = new SampleTourController();
        try {
            SampleTour tour = sampleTourController.getById(tourId);
            titleTxtDetail.setText(tour.getTourName());
            locationTxtDetail.setText(tour.getLocations().getFirst().getKey().getName());
            priceTxtDetail.setText(String.valueOf(tour.getTotalCost()));
            description.setText(tour.getDescription());

            for (Pair<Location, Timestamp> pair : tour.getLocations()){
                HBox hBox = createHBox(pair);
                locationList.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
