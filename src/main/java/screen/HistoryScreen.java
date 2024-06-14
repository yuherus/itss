package screen;

import controller.HistoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Location;
import model.Tour;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.web;

public class HistoryScreen implements Initializable {

    @FXML
    private VBox listHistory;;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HistoryController historyController = new HistoryController();
        try {
            List<Tour> tours = historyController.getAll();
            Collections.sort(tours, new Comparator<Tour>() {
                @Override
                public int compare(Tour o1, Tour o2) {
                    return o1.getStartDate().compareTo(o2.getStartDate());
                }
            });

            for (Tour tour : tours) {
                HBox hBox = createTourHBox(tour);
                listHistory.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HBox createTourHBox(Tour tour) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40.0);
        hBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.5, 0, 0); -fx-background-color: #FFFFFF; -fx-padding: 10.0;");

        // ImageView
        ImageView imageView = new ImageView();
        imageView.setFitHeight(80.0);
        imageView.setFitWidth(80.0);
        imageView.setPreserveRatio(true);
        Image image = new Image(getClass().getResourceAsStream("/images/SajekValley.png")); // Update with actual image path
        imageView.setImage(image);

        // VBox for text
        VBox vBoxText = new VBox();
        vBoxText.setAlignment(Pos.CENTER_LEFT);
        vBoxText.setSpacing(2.0);

        // Tour name
        Text tourName = new Text(tour.getTourName());
        tourName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Location
        HBox hBoxLocation = new HBox();
        hBoxLocation.setAlignment(Pos.CENTER_LEFT);
        hBoxLocation.setSpacing(5.0);

        FontIcon locationIcon = new FontIcon("fas-map-marker-alt");
        locationIcon.setIconSize(14);
        locationIcon.setIconColor(web("#818181"));

        Text locationText = new Text(tour.getLocations().getFirst().getKey().getName());
        locationText.setStyle("-fx-fill: #818181; -fx-font-size: 14px;");

        hBoxLocation.getChildren().addAll(locationIcon, locationText);

        // Rating
        HBox hBoxRating = new HBox();
        hBoxRating.setAlignment(Pos.CENTER_LEFT);
        hBoxRating.setSpacing(5.0);

        FontIcon starIcon = new FontIcon("fas-star");
        starIcon.setIconSize(14);
        starIcon.setIconColor(web("#f75d37"));

        Text ratingText = new Text("4.5"); // Replace with actual rating if needed
        ratingText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Text reviewsText = new Text("(150 Reviews)"); // Replace with actual reviews if needed
        reviewsText.setStyle("-fx-fill: #818181; -fx-font-size: 14px;");

        hBoxRating.getChildren().addAll(starIcon, ratingText, reviewsText);

        // Add all text components to vBoxText
        vBoxText.getChildren().addAll(tourName, hBoxLocation, hBoxRating);

        // Spacer Pane
        Pane spacer = new Pane();
        spacer.setPrefSize(340.0, 100.0);

        // Date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        Text dateTimeText = new Text(dateFormat.format(tour.getStartDate()) + "\n" + timeFormat.format(tour.getStartDate()));
        dateTimeText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #484848;");

        // Trash icon
        FontIcon trashIcon = new FontIcon("fas-trash-alt");
        trashIcon.setIconSize(24);
        trashIcon.setIconColor(Color.GREY);

        // Add all components to hBox
        hBox.getChildren().addAll(imageView, vBoxText, spacer, dateTimeText, trashIcon);

        return hBox;
    }
}
