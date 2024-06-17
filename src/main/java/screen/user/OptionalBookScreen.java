package screen.user;

import controller.LocationController;
import controller.StyleController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Location;
import model.Style;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class OptionalBookScreen implements Initializable {

    @FXML
    private HBox place1;

    @FXML
    private HBox place2;

    @FXML
    private HBox place3;

    @FXML
    private VBox nameDisplayBox;

    @FXML
    private VBox tripDetail;

    @FXML
    private Button nextButton;

    @FXML
    private ChoiceBox<Style> selectStyle;

    @FXML
    private Button searchBtn;

    private Map<Location, Button> addedPlaces = new HashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Style> styles = new StyleController().getAll();
            selectStyle.getItems().addAll(styles);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LocationController locationController = new LocationController();
        searchBtn.setOnAction(event -> {
            Style style = selectStyle.getValue();
            try {
                List<Location> places = locationController.getByStyle(style.getStyleId());
                place1.getChildren().clear();
                place2.getChildren().clear();
                place3.getChildren().clear();
                for (int i = 0; i < places.size(); i++) {
                    VBox destinationBox = createDestinationBox(places.get(i));
                    if (i < 3) {
                        place1.getChildren().add(destinationBox);
                    } else if (i < 6) {
                        place2.getChildren().add(destinationBox);
                    } else if (i < 9){
                        place3.getChildren().add(destinationBox);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        nextButton.setOnAction(event -> {
            BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
            ScrollPane view = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book-info.fxml"));
                view = loader.load();
                OptionalBookInfoScreen controller = loader.getController();
                List<Location> places = new ArrayList<>(addedPlaces.keySet());
                controller.setPlaces(places);
                controller.setStyle(selectStyle.getValue());
                controller.copyTripDetailAndShowBackButton(tripDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userView.setCenter(view);
        });
    }

    private VBox createDestinationBox(Location place) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getStyleClass().add("destination-box");

        ImageView imageView = new ImageView(new Image(place.getImageUrl()));
        imageView.setFitHeight(150);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(false);
        imageView.setPickOnBounds(true);
        imageView.getStyleClass().add("destination-image");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Label nameLabel = new Label(place.getName());
        nameLabel.setMaxWidth(180);
        nameLabel.getStyleClass().add("destination-name");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox.setMargin(nameLabel, new Insets(0, 0, 0, 10));
        hBox.getChildren().addAll(nameLabel, region);

        Text descriptionText = new Text(place.getDescription());
        descriptionText.setWrappingWidth(195);
        descriptionText.getStyleClass().add("destination-description");

        Button bookButton = new Button("Add Place");
        bookButton.setOnAction(event -> addPlaceToDisplay(place, bookButton));
        bookButton.getStyleClass().add("destination-button");
        VBox.setMargin(bookButton, new Insets(0, 0, 10, 0));
        vBox.getChildren().addAll(imageView, hBox, descriptionText, bookButton);

        return vBox;
    }

    private void addPlaceToDisplay(Location place, Button bookButton) {
        if (addedPlaces.containsKey(place)) {
            removePlaceFromDisplay(place, bookButton);
        } else {
            HBox newPlaceBox = new HBox();
            newPlaceBox.setAlignment(Pos.CENTER);
            newPlaceBox.setSpacing(10);

            Label placeNameLabel = new Label(place.getName());
            placeNameLabel.getStyleClass().add("place-name-label");
            HBox.setMargin(placeNameLabel, new Insets(0, 0, 0, 15));
            Button removeButton = new Button("x");
            removeButton.setOnAction(event -> removePlaceFromDisplay(place, bookButton));
            removeButton.getStyleClass().add("remove-button");
            HBox.setMargin(removeButton, new Insets(0, 15, 0, 0));
            newPlaceBox.getChildren().addAll(placeNameLabel, removeButton);

            // Add the new HBox to the VBox
            nameDisplayBox.getChildren().add(newPlaceBox);
            addedPlaces.put(place, bookButton);

            // Update the button text and style
            bookButton.setText("Added");
            bookButton.getStyleClass().add("added-button");
        }
    }

    private void removePlaceFromDisplay(Location place, Button bookButton) {
        // Find and remove the HBox associated with the place
        for (int i = 0; i < nameDisplayBox.getChildren().size(); i++) {
            HBox hbox = (HBox) nameDisplayBox.getChildren().get(i);
            Label label = (Label) hbox.getChildren().get(0);
            if (label.getText().equals(place.getName())) {
                nameDisplayBox.getChildren().remove(i);
                break;
            }
        }
        // Remove the place from the map and update the button text and style
        addedPlaces.remove(place);
        bookButton.setText("Add Place");
        bookButton.getStyleClass().remove("added-button");
    }

    @FXML
    private Stage stage;

    // Hàm này để thiết lập stage từ bên ngoài vào
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}


