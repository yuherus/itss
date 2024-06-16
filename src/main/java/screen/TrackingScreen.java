package screen;

import controller.HistoryController;
import controller.TrackingController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Tour;
import model.Tracking;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class TrackingScreen implements Initializable {
    @FXML
    private Text tourName;

    @FXML
    private VBox trackList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TrackingController trackingController = new TrackingController();
        try {
            List<Tracking> trackings = trackingController.getAll();
            Collections.sort(trackings, new Comparator<Tracking>() {
                @Override
                public int compare(Tracking o1, Tracking o2) {
                    return o1.getVisitTime().compareTo(o2.getVisitTime());
                }
            });

            tourName.setText(trackingController.getTourName());

            LocalDateTime now = LocalDateTime.now();
            for (Tracking tracking : trackings) {
//                if (now.isAfter(tracking.getStartTime())) {
//                    HBox hBox = createHbox(tracking);
//                    trackList.getChildren().add(hBox);
//                }
                HBox hBox = createHbox(tracking);
                trackList.getChildren().add(hBox);
            }
            HBox hBoxBegin = createHboxBeggin();
            trackList.getChildren().add(hBoxBegin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HBox createHboxBeggin() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(20.0);
        hBox.setStyle("-fx-opacity: 0.3;");

        // Text 1
        Text dateText = new Text("20/5/2024\n5:33 PM");
        dateText.setWrappingWidth(72.0);
        dateText.setFont(new Font(14.0));

        // VBox with Button
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        Button button = new Button();
        button.setPrefSize(36.0, 36.0);
        button.setStyle("-fx-background-radius: 50%; -fx-background-color: transparent; "
                + "-fx-border-width: 1px; -fx-border-radius: 50%; -fx-border-color: black; -fx-opacity: 1;");

        FontIcon icon = new FontIcon("fas-clipboard-list");
        icon.setIconSize(18);
        button.setGraphic(icon);

        vBox.getChildren().add(button);

        // Text 2
        Text statusText = new Text("Booked success");
        statusText.setWrappingWidth(150.0);
        statusText.setFont(new Font(14.0));

        hBox.getChildren().addAll(dateText, vBox, statusText);

        return hBox;
    }

    private HBox createHbox(Tracking tracking) {
        HBox hBox = new HBox(20);  // Spacing of 20.0
        hBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        hBox.setAlignment(Pos.BASELINE_CENTER);

        Text dateTimeText = new Text(tracking.getVisitTime().toString());
        dateTimeText.setStyle("-fx-font-weight: bold;");
        dateTimeText.setWrappingWidth(72.0);

        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Button button = new Button();
        button.setPrefSize(36, 36);
        button.setStyle("-fx-background-radius: 50%; -fx-background-color: #2E7FDD; -fx-border-width: 1px; -fx-border-radius: 50%; -fx-border-color: black; -fx-opacity: 1;");
        FontIcon icon = new FontIcon();
        icon.setIconLiteral("far-check-circle");
        icon.setIconSize(18);
        button.setGraphic(icon);

        Line line = new Line();
        line.setEndX(-214.93);
        line.setEndY(84.89);
        line.setStartX(-214.83);
        line.setStartY(201.79);

        vBox.getChildren().addAll(button, line);

        Text locationText = new Text("You have arrived in " + tracking.getLocation().getName());
        locationText.setStyle("-fx-font-weight: bold;");
        locationText.setWrappingWidth(150.0);

        hBox.getChildren().addAll(dateTimeText, vBox, locationText);

        return hBox;
    }
}
