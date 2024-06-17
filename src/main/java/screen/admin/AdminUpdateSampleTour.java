package screen.admin;

import controller.SampleTourController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SampleTour;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminUpdateSampleTour {
    @FXML
    private TextField textTourName;
    @FXML
    private TextField textDescription;
    @FXML
    private TextField textTotalCost;
    @FXML
    private TextField textStartDate;
    @FXML
    private TextField textEndDate;
    private SampleTour sampleTour;
    private TableView<SampleTour> tableview;

    public void setSampleTour(SampleTour sampleTour, TableView<SampleTour> tableview) {
        this.sampleTour = sampleTour;
        this.tableview = tableview;

        // Update the text fields with the current values of the sample tour
        textTourName.setText(sampleTour.getTourName());
        textDescription.setText(sampleTour.getDescription());
        textTotalCost.setText(String.valueOf(sampleTour.getTotalCost()));
        textStartDate.setText(sampleTour.getStartDate().toString());
        textEndDate.setText(sampleTour.getEndDate().toString());
    }

    @FXML
    public void handleUpdateBtnPressed(ActionEvent event) throws ParseException, SQLException {
        // Update the sample tour with the new values from the text fields
        sampleTour.setTourName(textTourName.getText());
        sampleTour.setDescription(textDescription.getText());
        sampleTour.setTotalCost(Double.parseDouble(textTotalCost.getText()));
        String startDateText = textStartDate.getText();
        String endDateText = textEndDate.getText();
        // Parse startDateText and endDateText to Date objects
        // Set the startDate and endDate of newSampleTour
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilStartDate = sdf.parse(startDateText);
        java.util.Date utilEndDate = sdf.parse(endDateText);
        Date startDate = new Date(utilStartDate.getTime());
        Date endDate = new Date(utilEndDate.getTime());
        sampleTour.setStartDate(startDate);
        sampleTour.setEndDate(endDate);

        new SampleTourController().update(sampleTour);
        tableview.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Sample Tour");
        alert.setHeaderText("Update sample tour successfully");
        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) textTourName.getScene().getWindow();
        stage.close();
    }
    public void handleBackBtnPressed(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the popup
        stage.close();
    }
}

