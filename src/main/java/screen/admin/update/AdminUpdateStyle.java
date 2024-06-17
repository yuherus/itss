package screen.admin.update;

import controller.StyleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Style;

import java.sql.SQLException;

public class AdminUpdateStyle implements AdminUpdateScreen{
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

    @Override
    @FXML
    public void handleUpdateBtnPressed(ActionEvent event) throws SQLException {
        // Update the sample tour with the new values from the text fields
        style.setName(textName.getText());
        style.setDescription(textDescription.getText());
        new StyleController().update(style);
        tableview.refresh();

        // Close the window
        Stage stage = (Stage) textName.getScene().getWindow();
        stage.close();
    }
    @Override
    public void handleBackBtnPressed(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the popup
        stage.close();
    }
}
