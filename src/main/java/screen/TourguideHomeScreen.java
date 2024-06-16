package screen;

import controller.RequestController;
import controller.StyleController;
import controller.TourController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TourguideHomeScreen implements Initializable {
    @FXML
    private TilePane currentTour;

    @FXML
    private TilePane history;
    @FXML
    private VBox optionalBook;

    @FXML
    private VBox quickBook;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TourController tourController = new TourController();
        updateTour();
    }

    public void setCurrentTours(List<Tour> tours) {
        currentTour.getChildren().clear();
        for (Tour tour : tours) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #dddddd; -fx-border-width: 1;");

            ImageView imageView = new ImageView();
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            Image image = new Image(tour.getLocations().getFirst().getKey().getImageUrl());
            imageView.setImage(image);

            Label nameLabel = new Label(tour.getTourName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label statusLabel = new Label(tour.getStatus().toString());
            switch (tour.getStatus()) {
                case PENDING:
                    statusLabel.setTextFill(Color.BLUE);
                    break;
                case CONFIRMED:
                    statusLabel.setTextFill(Color.GREEN);
                    break;
                case COMPLETED:
                    statusLabel.setTextFill(Color.GRAY);
                    break;
                case CANCELLED:
                    statusLabel.setTextFill(Color.RED);
                    break;
            }

            Button viewButton = new Button("View detail");
            viewButton.setOnAction(event -> {
                changeScene("/views/tourguide/tourguidedetail.fxml", event, tour);
            });
            viewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

            vBox.getChildren().addAll(imageView, nameLabel, statusLabel, viewButton);
            currentTour.getChildren().add(vBox);
        }
    }

    public void setHistory(List<Tour> tours) {
        history.getChildren().clear();
        for (Tour tour : tours) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #dddddd; -fx-border-width: 1;");

            ImageView imageView = new ImageView();
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            Image image = new Image(tour.getLocations().getFirst().getKey().getImageUrl());
            imageView.setImage(image);

            Label nameLabel = new Label(tour.getTourName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label statusLabel = new Label(tour.getStatus().toString());
            switch (tour.getStatus()) {
                case PENDING:
                    statusLabel.setTextFill(Color.BLUE);
                    break;
                case CONFIRMED:
                    statusLabel.setTextFill(Color.GREEN);
                    break;
                case COMPLETED:
                    statusLabel.setTextFill(Color.GRAY);
                    break;
                case CANCELLED:
                    statusLabel.setTextFill(Color.RED);
                    break;
            }

            Button viewButton = new Button("View detail");
            viewButton.setOnAction(event -> {
                changeScene("/views/tourguide/tourguidedetail.fxml", event, tour);
            });
            viewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

            vBox.getChildren().addAll(imageView, nameLabel, statusLabel, viewButton);
            history.getChildren().add(vBox);
        }
    }

    private void changeScene(String scenePath, ActionEvent event, Tour tour) {
        BorderPane tourguideView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#tourguideView");
        ScrollPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            view = loader.load();
            TourguideDetailScreen controller = loader.getController();
            controller.setTour(tour);
            tourguideView.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setQuickBooks(List<Tour> tours) throws SQLException {
        quickBook.getChildren().clear();
        for (Tour tour : tours) {
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #dddddd; -fx-border-width: 1;");

            HBox hBox = new HBox();
            Label placeLabel = new Label(tour.getTourName());
            placeLabel.setStyle("-fx-font-size: 16px;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            UserController userController = new UserController();
            User tourist = userController.getById(tour.getTouristId());
            Label touristLabel = new Label(tourist.getName());
            touristLabel.setStyle("-fx-font-size: 14px;");

            hBox.getChildren().addAll(placeLabel, spacer, touristLabel);

            String durationDay = String.valueOf(tour.getEndDate().getDay() - tour.getStartDate().getDay());
            Label dateLabel = new Label(tour.getStartDate() + " ~ " + durationDay + " days");

            Button acceptButton = new Button("Accept");
            acceptButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            acceptButton.setOnAction(event -> {
                try {
                    tour.setStatus(Tour.Status.CONFIRMED);
                    tour.setTourGuideId(Integer.parseInt(System.getProperty("userId")));
                    TourController tourController = new TourController();
                    tourController.update(tour);
                    updateTour();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            vBox.getChildren().addAll(hBox, dateLabel, acceptButton);
            vBox.setOnMouseClicked(event -> {
                BorderPane tourguideView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#tourguideView");
                ScrollPane view = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tourguide/tourguidedetail.fxml"));
                    view = loader.load();
                    TourguideDetailScreen controller = loader.getController();
                    controller.setTour(tour);
                    tourguideView.setCenter(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            quickBook.getChildren().add(vBox);
        }
    }

    public void setOptionalBooks(List<Request> optionalBooks) throws SQLException {
        optionalBook.getChildren().clear();
        for (Request optionalBookData : optionalBooks) {
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #dddddd; -fx-border-width: 1;");
            User tourist = new UserController().getById(optionalBookData.getTouristId());
            Label touristLabel = new Label("Tourist: " + tourist.getName());
            touristLabel.setStyle("-fx-font-size: 16px;");

            Label placeLabel = new Label("Place:");
            placeLabel.setFont(new Font(16));

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefHeight(40);
            scrollPane.setPrefWidth(200);

            HBox hBox = new HBox();
            hBox.setSpacing(20);
            for (Location location : optionalBookData.getLocations()) {
                Label placeItemLabel = new Label(location.getName());
                placeItemLabel.setStyle("-fx-font-size: 16px;");
                hBox.getChildren().add(placeItemLabel);
            }

            scrollPane.setContent(hBox);

            Style requestedStyle = new StyleController().getById(optionalBookData.getStyleId());
            Label styleLabel = new Label("Style: " + requestedStyle.getName());
            styleLabel.setStyle("-fx-font-size: 14px;");

            Button acceptButton = new Button("Accept");
            acceptButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            acceptButton.setOnAction(event -> {
                BorderPane tourguideView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#tourguideView");
                ScrollPane view = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tourguide/design.fxml"));
                    view = loader.load();
                    TourguideDesignScreen controller = loader.getController();
                    controller.setRequest(optionalBookData);
                    tourguideView.setCenter(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            vBox.getChildren().addAll(touristLabel, placeLabel, scrollPane, styleLabel, acceptButton);
            optionalBook.getChildren().add(vBox);
        }
    }

       public void updateTour() {
            TourController tourController = new TourController();
           RequestController requestController = new RequestController();
        try {
            List<Tour> tours = tourController.getAll();
            List<Tour> currentTours = tours.stream()
                    .filter(tour -> tour.getStatus() == Tour.Status.CONFIRMED)
                    .toList();
            List<Tour> historyTours = tours.stream()
                    .filter(tour -> tour.getStatus() == Tour.Status.COMPLETED || tour.getStatus() == Tour.Status.CANCELLED)
                    .toList();
            List<Tour> quickBooks = tours.stream()
                    .filter(tour -> tour.getStatus() == Tour.Status.PENDING)
                    .toList();
            List<Request> optionalBooks = requestController.getAll();
            setCurrentTours(currentTours);
            setHistory(historyTours);
            setQuickBooks(quickBooks);
            setOptionalBooks(optionalBooks);
        } catch (Exception e) {
            e.printStackTrace();
        }
       }
}


