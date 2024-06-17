package screen.admin;

import controller.LocationController;
import controller.StyleController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Location;
import model.Style;

import java.sql.SQLException;

public class AdminNewLocationScreen {
    private LocationCreatedListenerJava listener;
    public void setLocationCreatedListener(LocationCreatedListenerJava listener) {
        this.listener = listener;
    }
    @FXML
    private Button backBtn;
    @FXML
    private TextField textAddress;

    @FXML
    private TextField textDescription;

    @FXML
    private TextField textName;

    @FXML
    private TextField textStyle;

    @FXML
    private TextField textURL;
    public Button getBackBtn() {
        return backBtn; // Đảm bảo rằng backBtn không trả về null
    }
    @FXML
    private TableView<Location> tableview;
    public AdminNewLocationScreen() {
        tableview = new TableView<Location>();
        // Initialize other properties of tableview here
    }

    public void setTableview(TableView<Location> tableview) {
        this.tableview = tableview;
    }
    //Xử lý sự kiện khi người dùng nhấn vào nút Back
    @FXML
    public void handleBackBtnPressed(ActionEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }
    // Xử lý sự kiện khi người dùng nhấn vào nút Create
    @FXML
    public void createBtn(ActionEvent event) throws SQLException {
        Location newLocation = new Location();

        newLocation.setName(textName.getText());
        newLocation.setDescription(textDescription.getText());
        newLocation.setAddress(textAddress.getText());
        newLocation.setImageUrl(textURL.getText());
        String styleId = textStyle.getText();


        StyleController styleController = new StyleController();
        Style style = styleController.getById(Integer.parseInt(styleId));
        newLocation.setStyle(style);

        new LocationController().add(newLocation);
        int locationId = tableview.getItems().size() + 1;
        System.out.println("locationId: " + locationId);
        newLocation.setLocationId(locationId);

        if (listener != null) {
            listener.onLocationCreated(newLocation);
        }

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Location");
        alert.setHeaderText("Create location successfully");
        alert.showAndWait();

    }
}

