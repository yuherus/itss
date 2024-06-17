package screen.user;

import controller.TourController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Location;
import model.Tour;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static javafx.scene.paint.Color.web;

public class HistoryScreen implements Initializable {
    @FXML
    private VBox listHistory;

    @FXML
    private VBox historyBook;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TourController tourController = new TourController();
        try {
            List<Tour> tours = tourController.getByUserId();
            Collections.sort(tours, new Comparator<Tour>() {
                @Override
                public int compare(Tour o1, Tour o2) {
                    return o1.getStartDate().compareTo(o2.getStartDate());
                }
            });
            tours.reversed();

            for (Tour tour : tours) {
                HBox hBox = createTourHBox(tour);
                listHistory.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HBox createTourHBox(Tour tour) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40.0);
        hBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.5, 0, 0); -fx-background-color: #FFFFFF; -fx-padding: 10.0;");

        // ImageView
        ImageView imageView = new ImageView();
        imageView.setFitHeight(80.0);
        imageView.setFitWidth(80.0);
        imageView.setPreserveRatio(true);
        Image image = new Image(tour.getLocations().getFirst().getKey().getImageUrl()); // Cập nhật đường dẫn ảnh thực tế
        imageView.setImage(image);

        // VBox cho văn bản
        VBox vBoxText = new VBox();
        vBoxText.setAlignment(Pos.CENTER_LEFT);
        vBoxText.setSpacing(2.0);

        // Tên tour
        Text tourName = new Text(tour.getTourName());
        tourName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Địa điểm
        HBox hBoxLocation = new HBox();
        hBoxLocation.setAlignment(Pos.CENTER_LEFT);
        hBoxLocation.setSpacing(5.0);

        FontIcon locationIcon = new FontIcon("fas-map-marker-alt");
        locationIcon.setIconSize(14);
        locationIcon.setIconColor(web("#818181"));

        StringJoiner locationJoiner = new StringJoiner(", ");
        for (Pair<Location, Timestamp> pair : tour.getLocations()) {
            locationJoiner.add(pair.getKey().getName());
        }
        String locationStr = locationJoiner.toString();
        Text locationText = new Text(locationStr);
        locationText.setStyle("-fx-fill: #818181; -fx-font-size: 14px;");

        hBoxLocation.getChildren().addAll(locationIcon, locationText);

        // Thêm tất cả các thành phần văn bản vào vBoxText
        vBoxText.getChildren().addAll(tourName, hBoxLocation);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        // Ngày và giờ
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Text dateTimeText = new Text(dateFormat.format(tour.getStartDate()));
        dateTimeText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #484848;");

        // Biểu tượng thùng rác
        FontIcon trashIcon = new FontIcon("fas-trash-alt");
        trashIcon.setIconSize(24);
        trashIcon.setIconColor(Color.GREY);
        trashIcon.setOnMouseClicked(event -> {
            TourController tourController = new TourController();
            try {
                tourController.delete(tour.getTourId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // Thêm tất cả các thành phần vào hBox
        hBox.getChildren().addAll(imageView, vBoxText, region, dateTimeText, trashIcon);

        // Thiết lập bộ xử lý sự kiện cho hBox
        hBox.setOnMouseClicked(event -> changeTracking(tour, event));

        return hBox;
    }


    private void changeTracking(Tour tour, MouseEvent event) {
        ScrollPane view = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/tourdetail.fxml"));
            view = loader.load();
            TourDetailScreen tourDetailScreen = loader.getController();
            tourDetailScreen.setTour(tour);

        } catch (Exception e) {
            e.printStackTrace();
        }

        BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
        userView.setCenter(view);
    }
}
