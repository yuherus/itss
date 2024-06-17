package screen.user;

import controller.NotificationController;
import controller.TourController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import model.Location;
import model.Notification;
import model.Tour;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationScreen implements Initializable {
    @FXML
    private VBox notList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NotificationController notificationController = new NotificationController();
        try {
            List<Notification> notifications = notificationController.getByUserId(Integer.parseInt(System.getProperty("userId")));
            for(Notification notification : notifications){
                notList.getChildren().add(createHBox(notification));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HBox createHBox(Notification notification) {
        HBox hBox = new HBox();
        hBox.setSpacing(10); // khoảng cách giữa các thành phần
        hBox.setPadding(new Insets(10)); // khoảng cách từ viền của HBox đến các thành phần
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        hBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.2, 0, 0); -fx-background-color: #FFFFFF; -fx-background-radius: 5px");

        // Tạo label cho thông báo
        Label messageLabel = new Label(notification.getMessage());
        messageLabel.setStyle("-fx-font-size: 16px;");
        if (notification.getStatus()) {
            messageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        }

        // Tạo dấu chấm xanh nếu status là "unread"
        if (notification.getStatus()) {
            Circle greenDot = new Circle(5, Color.GREEN);
            hBox.getChildren().add(greenDot);
        }

        hBox.getChildren().add(messageLabel);

        // Thêm các nút nếu tour_type là "optional"
        if (notification.getTourType().toString().equals("OPTIONALBOOK")) {
            Button confirmButton = new Button("Accept");
            confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 16px;");
            confirmButton.setOnAction(event -> {
                TourController tourController = new TourController();
                try {
                    Tour tour = notification.getTour();
                    tour.setStatus(Tour.Status.CONFIRMED);
                    tourController.update(tour);
                    notification.setStatus(true);
                    new NotificationController().update(notification);
                    changeScene("/views/user/booking-now.fxml", event);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 16px;");
            cancelButton.setOnAction(event -> {
                TourController tourController = new TourController();
                try {
                    Tour tour = notification.getTour();
                    tour.setStatus(Tour.Status.CANCELLED);
                    tourController.update(tour);
                    new NotificationController().delete(notification.getNotificationId());
                    changeScene("/views/user/notification.fxml", event);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            hBox.getChildren().addAll(region, confirmButton , cancelButton);
        }

        hBox.setOnMouseClicked(event -> {
            ScrollPane view = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/sampletourdetail.fxml"));
                view = loader.load();
                TourDetailScreen tourDetaiScreen = loader.getController();
                tourDetaiScreen.setTour(notification.getTour());
            } catch (Exception e) {
                e.printStackTrace();
            }
            BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
            userView.setCenter(view);
        });
        return hBox;
    }

    private void changeScene(String scenePath, ActionEvent event) {
        ScrollPane view = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            view = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
        userView.setCenter(view);
    }
}
