package screen.admin.insert;

import controller.StyleController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Style;
import screen.admin.StyleCreatedListener;

import java.sql.SQLException;

public class AdminNewStyleController implements AdminInsertScreen<Style> {
    private StyleCreatedListener listener;

    public void setStyleCreatedListener(StyleCreatedListener listener) {
        this.listener = listener;
    }

    @FXML
    private Button backBtn;

    @FXML
    private Button createBtn;

    @FXML
    private TextField textName;

    @FXML
    private TextField textDescription;

    public Button getBackBtn() {
        return backBtn;
    }

    @FXML
    private TableView<Style> tableview;

    public AdminNewStyleController() {
        tableview = new TableView<>();
    }
    @Override
    public void setTableview(TableView<Style> tableview) {
        this.tableview = tableview;
    }

    @FXML
    public void backBtn(ActionEvent event) {
        if (backBtn != null) {
            backBtn.setOnAction(e -> {
                Stage stage = (Stage) backBtn.getScene().getWindow();
                stage.close();
            });
        }
    }
    @Override
    @FXML
    public void createBtn(ActionEvent event) throws SQLException {
        Style newStyle = new Style();

        newStyle.setName(textName.getText());
        newStyle.setDescription(textDescription.getText());
        new StyleController().add(newStyle);

        int styleId = tableview.getItems().size() + 1;
        newStyle.setStyleId(styleId);

        if (listener != null) {
            listener.onStyleCreated(newStyle);
        }

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create New Style");
        alert.setHeaderText("Create style successfully");
        alert.showAndWait();
    }
}
