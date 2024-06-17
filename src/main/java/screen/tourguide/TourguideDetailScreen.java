package screen.tourguide;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Tour;

import java.net.URL;
import java.util.ResourceBundle;

public class TourguideDetailScreen implements Initializable {

    private Tour tour;
    @FXML
    private Label tourName;
    public void setTour(Tour tour) {
        this.tour = tour;
        tourName.setText(tour.getTourName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
