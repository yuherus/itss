package screen.tourguide;

import controller.LocationController;
import controller.StyleController;
import controller.TourController;
import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import model.Location;
import model.Notification;
import model.Request;
import model.Tour;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TourguideDesignScreen implements Initializable {
    private Request request;

    private List<Pair<Location, Timestamp>> designLocations = new ArrayList<>();

    @FXML
    private Button addLocationButton;

    @FXML
    private TextField costInput;

    @FXML
    private DatePicker dateInput;

    @FXML
    private GridPane itinerary;

    @FXML
    private ChoiceBox<Location> locationInput;

    @FXML
    private Label requestStyle;

    @FXML
    private Label requestUser;

    @FXML
    private Button submitButton;

    @FXML
    private TextField timeInput;

    @FXML
    private TextArea descriptionInput;

    @FXML
    private TextField nameInput;

    @FXML
    private HBox requestLocation;

    @FXML
    private DatePicker startDateInput;

    @FXML
    private DatePicker endDateInput;

    public void setRequest(Request request) throws SQLException {
        this.request = request;
        requestStyle.setText(new StyleController().getById(request.getStyleId()).getName());
        requestUser.setText(new UserController().getById(request.getTouristId()).getUsername());
        List<Location> locations = request.getLocations();
        for (Location location : locations) {
            requestLocation.getChildren().add(createRequestLocation(location));
        }
        List<Location> styleLocations = new LocationController().getByStyle(request.getStyleId());
        styleLocations.addAll(locations);
        locationInput.getItems().addAll(styleLocations);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addLocationButton.setOnAction(event -> addLocation());
        submitButton.setOnAction(event -> {
            Tour tour = new Tour();
            tour.setTourName(nameInput.getText());
            tour.setTotalCost(Double.parseDouble(costInput.getText()));
            tour.setTouristId(request.getTouristId());
            tour.setStatus(Tour.Status.CREATED);
            tour.setDescription(descriptionInput.getText());
            tour.setLocations(designLocations);
            tour.setTourGuideId(Integer.parseInt(System.getProperty("userId")));
            tour.setStartDate(Date.valueOf(startDateInput.getValue()));
            tour.setEndDate(Date.valueOf(endDateInput.getValue()));
            try {
                TourController tourController = new TourController();
                tourController.add(tour);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Notification notification = new Notification();
            notification.setMessage("Here is the tour you requested.");
            notification.setTourType(Notification.TourType.OPTIONALBOOK);
            try {
                notification.setUser(new UserController().getById(request.getTouristId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            notification.setTour(tour);
            try {
                new controller.NotificationController().add(notification);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Tour Created");
            alert.setContentText("The tour has been successfully created.");
            alert.showAndWait();

            BorderPane tourguideView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#tourguideView");
            ScrollPane view = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tourguide/home.fxml"));
                view = loader.load();
                tourguideView.setCenter(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void addLocation() {
        if (locationInput.getValue() == null || dateInput.getValue() == null || timeInput.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText("Required Field Missing");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }
        Location location = locationInput.getValue();
        String dateTime = dateInput.getValue().toString()+ " " + timeInput.getText();
        designLocations.add(new Pair<>(location, Timestamp.valueOf(dateTime)));
        itinerary.addRow(itinerary.getRowCount(),new Label(dateTime) ,new Label(location.getName()), new Label(location.getDescription()));
    }

    private Label createRequestLocation(Location location) {
        Label label = new Label(location.getName());
        label.setPrefHeight(29.0);
        label.setPrefWidth(95.0);
        label.setStyle("-fx-border-color: #dedede; -fx-border-radius: 3px;");
        label.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        return label;
    }
}
