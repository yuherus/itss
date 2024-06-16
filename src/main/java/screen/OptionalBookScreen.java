package screen;

import controller.LocationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Location;
import touristech.TouristechApplication;

import java.io.IOException;
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

    private Map<Location, Button> addedPlaces = new HashMap<>();
    public class Main extends TouristechApplication {

        @Override
        public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book.fxml"));
            Parent root = loader.load();
            OptionalBookScreen controller = loader.getController();
            controller.setStage(primaryStage); // Truyền stage vào OptionalBookScreen
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocationController locationController = new LocationController();
        try {
            List<Location> places = locationController.getAll();
            List<Location> newPlaces = new ArrayList<>(places.subList(Math.max(0, places.size() - 9), places.size()));
            Collections.reverse(newPlaces);

            for (int i = 0; i < newPlaces.size(); i++) {
                VBox destinationBox = createDestinationBox(newPlaces.get(i));
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
            // Remove the place if it's already added
            removePlaceFromDisplay(place, bookButton);
        } else {
            // Create a new HBox to display the place name and a remove icon
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

    @FXML
    private void navigateToOptionalBookInfo() {
        try {
            // Load FXML và controller của optional-book-info.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book-info.fxml"));
            Parent root = loader.load();

            // Lấy controller của optional-book-info.fxml
            OptionalBookInfoScreen controller = loader.getController();

            // Truyền stage từ OptionalBookScreen vào controller của optional-book-info.fxml
            controller.setStage((Stage) tripDetail.getScene().getWindow()); // Truyền stage hiện tại của OptionalBookScreen

            // Gọi phương thức để sao chép nội dung và thay đổi giao diện
            controller.copyTripDetailAndShowBackButton(tripDetail);

            // Đặt scene mới cho stage
            ((Stage) tripDetail.getScene().getWindow()).setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


/* Cach 2:
public class OptionalBookScreen implements Initializable {

    private List<Location> originalPlaces = new ArrayList<>();

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

    private Map<Location, Button> addedPlaces = new HashMap<>();
    public class Main extends TouristechApplication {

        @Override
        public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book.fxml"));
            Parent root = loader.load();
            OptionalBookScreen controller = loader.getController();
            controller.setStage(primaryStage); // Truyền stage vào OptionalBookScreen
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocationController locationController = new LocationController();
        try {
            List<Location> places = locationController.getAll();
            List<Location> newPlaces = new ArrayList<>(places.subList(Math.max(0, places.size() - 9), places.size()));
            Collections.reverse(newPlaces);

            for (int i = 0; i < newPlaces.size(); i++) {
                VBox destinationBox = createDestinationBox(newPlaces.get(i));
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

    public void addPlaceToDisplay(Location place, Button bookButton) {
        // Tạo một HBox mới để hiển thị tên địa điểm và nút remove
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

        // Thêm HBox mới vào VBox
        nameDisplayBox.getChildren().add(newPlaceBox);
        addedPlaces.put(place, bookButton);

        // Cập nhật văn bản và kiểu nút
        bookButton.setText("Added");
        bookButton.getStyleClass().add("added-button");
    }

    // Phương thức để xóa một địa điểm khỏi giao diện
    private void removePlaceFromDisplay(Location place, Button bookButton) {
        for (int i = 0; i < nameDisplayBox.getChildren().size(); i++) {
            HBox hbox = (HBox) nameDisplayBox.getChildren().get(i);
            Label label = (Label) hbox.getChildren().get(0);
            if (label.getText().equals(place.getName())) {
                nameDisplayBox.getChildren().remove(i);
                break;
            }
        }
        addedPlaces.remove(place);
        bookButton.setText("Add Place");
        bookButton.getStyleClass().remove("added-button");
    }


    @FXML
    private Stage stage;

    private void saveCurrentState() {
        originalPlaces = new ArrayList<>();
        originalPlaces.addAll(addedPlaces.keySet());
    }

    @FXML
    private void navigateToOptionalBookInfo() {
        try {
            // Lưu trạng thái hiện tại trước khi điều hướng
            saveCurrentState();

            // Load FXML và controller của optional-book-info.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book-info.fxml"));
            Parent root = loader.load();

            // Lấy controller của optional-book-info.fxml
            OptionalBookInfoScreen controller = loader.getController();
            controller.setStage((Stage) tripDetail.getScene().getWindow());
            controller.copyTripDetailAndShowBackButton(tripDetail);

            // Đặt scene mới cho stage
            ((Stage) tripDetail.getScene().getWindow()).setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void copyTripDetailAndShowNextButton(VBox tripDetail) {
        this.tripDetail = tripDetail;

        // Hiển thị tripDetail sao chép trên giao diện của OptionalBookScreen
        nameDisplayBox.getChildren().clear();
        nameDisplayBox.getChildren().addAll(tripDetail.getChildren());

        changeButtonToNext();
    }

    private void changeButtonToNext() {
        Button backButton = (Button) nameDisplayBox.lookup("#nextButton");
        if (backButton != null) {
            backButton.setText("Next");
            backButton.setOnAction(event -> navigateNext());
        }
    }

    private void navigateNext() {
        // Thực hiện điều hướng sang OptionalBookInfoScreen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book-info.fxml"));
            Parent root = loader.load();

            OptionalBookInfoScreen controller = loader.getController();
            controller.setStage(stage);
            controller.copyTripDetailAndShowBackButton(tripDetail); // Chuyển tripDetail về OptionalBookInfoScreen

            // Đặt scene mới cho stage
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

 */
