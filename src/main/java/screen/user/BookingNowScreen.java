package screen.user;

import controller.PaymentController;
import controller.TourController;
import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Payment;
import model.SampleTour;
import model.Tour;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BookingNowScreen implements Initializable {
    @FXML
    private VBox book_tour;

    @FXML
    private DatePicker startdate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text totalCost;

    @FXML
    private Text tourCost;

    @FXML
    private ImageView tourImage;

    @FXML
    private Text tourName;

    @FXML
    private MenuItem item1;

    @FXML
    private MenuItem item2;

    @FXML
    private MenuItem item3;

    @FXML
    private MenuButton menuBtn;

    @FXML
    private Button paymentBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void infoTour(Tour tour){
        try {
            tourImage.setImage(new Image(new FileInputStream(tour.getLocations().getFirst().getKey().getImageUrl())));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        tourName.setText(tour.getTourName());
        tourCost.setText(String.valueOf(tour.getTotalCost()) + "$ / Person");
        totalCost.setText(String.valueOf(tour.getTotalCost()) +"$");
        startdate.setValue(tour.getStartDate().toLocalDate());
        endDate.setValue(tour.getEndDate().toLocalDate());
        paymentBtn.setDisable(true);
        item1.setOnAction(event -> handleMenuItem(menuBtn, item1));
        item2.setOnAction(event -> handleMenuItem(menuBtn, item2));
        item3.setOnAction(event -> handleMenuItem(menuBtn, item3));

        paymentBtn.setOnAction(event -> {
            Payment payment = new Payment();
            switch (menuBtn.getText()){
                case "Cash" :
                    payment.setPaymentMethod(Payment.PaymentMethod.CASH);
                    break;
                case "Credit Card" :
                    payment.setPaymentMethod(Payment.PaymentMethod.CASH);
                    break;
                case "E Wallet" :
                    payment.setPaymentMethod(Payment.PaymentMethod.CASH);
                    break;
            }
            payment.setTour(tour);
            UserController userController = new UserController();
            try {
                payment.setTourist(userController.getById(Integer.parseInt(System.getProperty("userId"))));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            payment.setAmount(tour.getTotalCost());

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            payment.setPaymentDate(Timestamp.valueOf(formattedDateTime));

            PaymentController paymentController = new PaymentController();
            TourController tourController = new TourController();
            try {
                tourController.add(tour);
                paymentController.add(payment);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Successful");
                alert.setHeaderText(null);
                alert.setContentText("Your payment was successful!");
                alert.showAndWait();

                ScrollPane view = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/history.fxml"));
                    view = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BorderPane userView = (BorderPane) ((Node) event.getSource()).getScene().lookup("#userView");
                userView.setCenter(view);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleMenuItem(MenuButton menuBtn, MenuItem item) {
        menuBtn.setText(item.getText());
        paymentBtn.setDisable(false);
    }
}
