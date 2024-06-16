package screen;

import controller.NotificationController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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

            Button cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 16px;");

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            hBox.getChildren().addAll(region, confirmButton , cancelButton);
        }
        return hBox;
    }
}
