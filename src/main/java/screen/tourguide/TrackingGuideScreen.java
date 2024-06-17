package screen.tourguide;

import controller.TourController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Location;
import model.Tour;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class TrackingGuideScreen implements Initializable {
    @FXML
    private Text tourName;

    @FXML
    private VBox trackList;

    @FXML
    private ImageView hearderImg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setTour(Tour currentTour){
        List<Pair<Location, Timestamp>> locations = currentTour.getLocations();
        Collections.sort(locations, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        try {
            hearderImg.setImage(new Image(new FileInputStream(currentTour.getLocations().getFirst().getKey().getImageUrl())));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        tourName.setText(currentTour.getTourName());

        LocalDateTime now = LocalDateTime.now();

        boolean isLast = true;
        for (Pair<Location, Timestamp> pair : locations) {
            if (pair.getValue().toLocalDateTime().isBefore(now)) {
                HBox hBox = createHbox(pair, true);
                trackList.getChildren().add(hBox);
                isLast = false;
            } else {
                HBox hBox = createHbox(pair, false);
                trackList.getChildren().add(hBox);
            }
        }

        HBox hBoxBegin = createHboxBegin();
        trackList.getChildren().add(hBoxBegin);
    }

    private HBox createHboxBegin() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(20.0);
        hBox.setPadding(new Insets(0, 0, 0, 90.0));
        hBox.isDisable();

        // VBox with Button
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        Button button = new Button();
        button.setPrefSize(36.0, 36.0);
        button.setStyle("-fx-background-radius: 50%; -fx-background-color: #2E7FDD; "
                + "-fx-border-width: 1px; -fx-border-radius: 50%; -fx-border-color: black; -fx-opacity: 1;");
        FontIcon icon = new FontIcon("fas-clipboard-list");
        icon.setIconSize(18);
        button.setGraphic(icon);

        vBox.getChildren().add(button);

        // Text 2
        Text statusText = new Text("Booked success");
        statusText.setWrappingWidth(150.0);
        statusText.setFont(new Font(14.0));
        statusText.setStyle("-fx-font-weight: bold;");

        hBox.getChildren().addAll(vBox, statusText);

        return hBox;
    }

    private HBox createHbox(Pair<Location, Timestamp> pair, boolean isPast) {
        HBox hBox = new HBox(20);  // Spacing of 20.0
        hBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        if (!isPast) {
            hBox.setStyle("-fx-opacity: 0.3;");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Text dateTimeText = new Text(dateFormat.format(pair.getValue()).toString());
        dateTimeText.setWrappingWidth(72.0);
        dateTimeText.setFont(new Font(14.0));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);

        Button button = new Button();
        button.setPrefSize(36, 36);
        if (isPast) {
            button.setStyle("-fx-background-radius: 50%; -fx-background-color: #2E7FDD; "
                    + "-fx-border-width: 1px; -fx-border-radius: 50%; -fx-border-color: black; -fx-opacity: 1;");
        } else {
            button.setStyle("-fx-background-radius: 50%; -fx-background-color: transparent; "
                    + "-fx-border-width: 1px; -fx-border-radius: 50%; -fx-border-color: black; -fx-opacity: 1;");
        }

        FontIcon icon = new FontIcon();
        icon.setIconLiteral("fas-car-alt");
        icon.setIconSize(18);
        button.setGraphic(icon);

        Line line = new Line();
        line.setEndX(-214.93);
        line.setEndY(84.89);
        line.setStartX(-214.83);
        line.setStartY(201.79);

        vBox.getChildren().addAll(button, line);

        Text locationText = new Text("You have arrived in " + pair.getKey().getName());
        locationText.setWrappingWidth(150.0);
        if (isPast) {
            locationText.setFont(Font.font("System", 14));
            locationText.setStyle("-fx-font-weight: bold;");
        }

        hBox.getChildren().addAll(dateTimeText, vBox, locationText);

        return hBox;
    }
}
