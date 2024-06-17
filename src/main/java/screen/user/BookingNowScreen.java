package screen.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.SampleTour;
import model.Tour;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingNowScreen implements Initializable {
    @FXML
    private VBox book_tour;

    @FXML
    private DatePicker startdate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text totalCost;

    @FXML
    private Text tourCost;

    @FXML
    private ImageView tourImage;

    @FXML
    private Text tourName;

    @FXML
    private MenuItem item1;

    @FXML
    private MenuItem item2;

    @FXML
    private MenuButton menuBtn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void infoTour(Tour tour){
        try {
            tourImage.setImage(new Image(new FileInputStream(tour.getLocations().getFirst().getKey().getImageUrl())));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        tourName.setText(tour.getTourName());
        tourCost.setText(String.valueOf(tour.getTotalCost()) + "$ / Person");
        totalCost.setText(String.valueOf(tour.getTotalCost()) +"$");
        startdate.setValue(tour.getStartDate().toLocalDate());
        endDate.setValue(tour.getEndDate().toLocalDate());
        item1.setOnAction(event -> handleMenuItem(menuBtn, item1));
        item2.setOnAction(event -> handleMenuItem(menuBtn, item2));
    }

    private void handleMenuItem(MenuButton menuBtn, MenuItem item) {
        menuBtn.setText(item.getText());
    }
}
