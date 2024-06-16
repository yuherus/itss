package screen;
import controller.SampleTourController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.SampleTour;
import org.w3c.dom.events.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class TourScreen implements Initializable {

    @FXML
    private HBox popular1;

    @FXML
    private HBox popular2;

    @FXML
    private HBox sale1;

    @FXML
    private HBox sale2;

    @FXML
    private Button more1;

    @FXML
    private Button more2;

    @FXML
    private Button customBook;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SampleTourController sampleTourController = new SampleTourController();
        try {
            List<SampleTour> tour = sampleTourController.getAll();
            List<SampleTour> newTours = new ArrayList<>();
            for (int i = tour.size() - 1; i >= tour.size() - 6; i--) {
                newTours.add(tour.get(i));
            }
            Collections.sort(tour, new Comparator<SampleTour>() {
                @Override
                public int compare(SampleTour o1, SampleTour o2) {
                    return Double.compare(o1.getTotalCost(), o2.getTotalCost());
                }
            });
            List<SampleTour> cheapestTour = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                cheapestTour.add(tour.get(i));
            }

            for (int i = 0; i < 3; i++) {
                popular1.getChildren().add(createDestinationBox(newTours.get(i)));
            }
            for (int i = 3; i < 6; i++) {
                popular2.getChildren().add(createDestinationBox(newTours.get(i)));
            }
            for (int i = 0; i < 3; i++) {
                sale1.getChildren().add(createDestinationBox(cheapestTour.get(i)));
            }
            for (int i = 3; i < 6; i++) {
                sale2.getChildren().add(createDestinationBox(cheapestTour.get(i)));
            }

            more1.setOnAction(event -> changeTourList(event));
            more2.setOnAction(event -> changeTourList(event));
        } catch (SQLException | FileNotFoundException e) {

            throw new RuntimeException(e);
        }

        customBook.setOnAction(event -> {
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

    private VBox createDestinationBox(SampleTour tour) throws FileNotFoundException {
        VBox vBox = new VBox();
        vBox.setPrefWidth(270);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getStyleClass().add("destination-box");

//        kiểm tra có là đường dẫn file
        InputStream inputStream = new FileInputStream(tour.getLocations().getFirst().getKey().getImageUrl());
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(false);
        imageView.setPickOnBounds(true);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Label nameLabel = new Label(tour.getTourName());
        nameLabel.setMaxWidth(160);
        Label priceLabel = new Label(tour.getTotalCost() + " $");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        hBox.getChildren().addAll(nameLabel, region, priceLabel);

        Text descriptionText = new Text(tour.getDescription());
        descriptionText.setWrappingWidth(195);

        Button bookButton = new Button("Booking now");
        bookButton.setOnAction(event -> {
            ScrollPane view = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/sampletourdetail.fxml"));
                view = loader.load();
                SampleTourDetailScreen sampleTourDetaiScreen = loader.getController();
                sampleTourDetaiScreen.setTour(tour);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BorderPane userView = (BorderPane) popular1.getScene().lookup("#userView");
            userView.setCenter(view);
        });

        vBox.getChildren().addAll(imageView, hBox, descriptionText, bookButton);

        return vBox;
    }

    public void changeTourList(ActionEvent event){
        ScrollPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/tourlist.fxml"));
            view = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
        userView.setCenter(view);
    }
}