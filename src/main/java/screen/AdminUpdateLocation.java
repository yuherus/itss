package screen;

import controller.StyleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Location;
import model.Style;

import java.sql.SQLException;

public class AdminUpdateLocation {
    @FXML
    private TextField textName;
    @FXML
    private TextField textDescription;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textStyle;
    @FXML
    private TextField textURL;

    private Location location;
    private TableView<Location> tableview;

    public void setLocation(Location location, TableView<Location> tableview) throws SQLException {
        this.location = location;
        this.tableview = tableview;

        // Update the text fields with the current values of the sample tour
        textName.setText(location.getName());
        textDescription.setText(location.getDescription());
        textAddress.setText(String.valueOf(location.getAddress()));
        textStyle.setText(String.valueOf(location.getStyle().getStyleId()));
        String styleId = textStyle.getText();
        StyleController styleController = new StyleController();
        Style style = styleController.getById(Integer.parseInt(styleId));
        location.setStyle(style); // Set the style of the location
        textURL.setText(location.getImageUrl());    }

    @FXML
    public void handleUpdateBtnPressed(ActionEvent event) throws SQLException {
        // Update the sample tour with the new values from the text fields
        location.setName(textName.getText());
        location.setDescription(textDescription.getText());
        location.setAddress(textAddress.getText());
        location.setImageUrl(textURL.getText());

        String styleId = textStyle.getText();
        StyleController styleController = new StyleController();
        Style style = styleController.getById(Integer.parseInt(styleId));

        location.setStyle(style);
        tableview.refresh();

        // Close the window
        Stage stage = (Stage) textName.getScene().getWindow();
        stage.close();
    }
    public void handleBackBtnPressed(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the popup
        stage.close();
    }
}

