package screen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.Request;

import java.net.URL;
import java.util.ResourceBundle;

public class TourguideDesignScreen implements Initializable {

    @FXML
    private DatePicker dateInput;

    @FXML
    private TextArea descriptionInput;

    @FXML
    private GridPane itinerary;

    @FXML
    private TextField locationInput;

    @FXML
    private TextField timeInput;

    @FXML
    private TextField costInput;

    @FXML
    private Button addLocationButton;

    @FXML
    private Button submitButton;

    public void setRequest(Request request) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addLocationButton.setOnAction(event -> addLocation());
    }

    public void addLocation() {
        String location = locationInput.getText();
        String description = descriptionInput.getText();
        String dateTime = dateInput.getValue().toString()+ " " + timeInput.getText();
        itinerary.addRow(itinerary.getRowCount(),new Label(dateTime) ,new Label(location), new Label(description));
    }
}
