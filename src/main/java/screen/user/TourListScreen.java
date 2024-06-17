package screen.user;

import controller.SampleTourController;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class TourListScreen implements Initializable {
    @FXML
    private VBox tourList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SampleTourController sampleTourController = new SampleTourController();
        try {
            List<SampleTour> tours = sampleTourController.getAll();
            Collections.sort(tours, new Comparator<SampleTour>() {
                @Override
                public int compare(SampleTour o1, SampleTour o2) {
                    return Double.compare(o1.getTotalCost(), o2.getTotalCost());
                }
            });

            for(int i = 0; i < tours.size(); i=i+3){
                HBox hBox = new HBox();
                hBox.setSpacing(20);
                hBox.setAlignment(Pos.BASELINE_LEFT);
                for(int j =0; j<3 && i+j<tours.size(); j++){
                    hBox.getChildren().add(createDestinationBox(tours.get(i+j)));
                }
                tourList.getChildren().add(hBox);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private VBox createDestinationBox(SampleTour tour) {
        VBox vBox = new VBox();
        vBox.setPrefWidth(270);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-spacing: 16;\n" +
                "    -fx-padding: 10;\n" +
                "    -fx-border-width: 1;\n" +
                "    -fx-border-color: lightgray;\n" +
                "    -fx-border-radius: 10px;\n" +
                "    -fx-background-color: #fff;\n" +
                "    -fx-background-radius: 10px;");

        ImageView imageView = new ImageView(new Image(tour.getLocations().getFirst().getKey().getImageUrl()));
        imageView.setFitHeight(150);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(false);
        imageView.setPickOnBounds(true);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Label nameLabel = new Label(tour.getTourName());
        nameLabel.setMaxWidth(160);
        nameLabel.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-family: Arial;\n" +
                "    -fx-font-weight: bold;");
        Label priceLabel = new Label(tour.getTotalCost() + " $");
        priceLabel.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-family: Arial;\n" +
                "    -fx-font-weight: bold;");

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
            BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
            userView.setCenter(view);
        });
        bookButton.setStyle("-fx-background-color: #fd6951;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-background-radius: 16px;\n" +
                "    -fx-font-weight: bold;");

        vBox.getChildren().addAll(imageView, hBox, descriptionText, bookButton);

        return vBox;
    }
}
