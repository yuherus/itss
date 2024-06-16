package screen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Style;

public class AdminUpdateStyle {
    @FXML
    private TextField textName;
    @FXML
    private TextField textDescription;
    private Style style;
    private TableView<Style> tableview;

    public void setStyle(Style style, TableView<Style> tableview) {
        this.style = style;
        this.tableview = tableview;

        // Update the text fields with the current values of the sample tour
        textName.setText(style.getName());
        textDescription.setText(style.getDescription());
    }

    @FXML
    public void handleUpdateBtnPressed(ActionEvent event) {
        // Update the sample tour with the new values from the text fields
        style.setName(textName.getText());
        style.setDescription(textDescription.getText());
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
