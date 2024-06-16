package screen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SampleTour;

public class AdminUpdateSampleTour {
    @FXML
    private TextField textTourName;
    @FXML
    private TextField textDescription;
    @FXML
    private TextField textTotalCost;
    private SampleTour sampleTour;
    private TableView<SampleTour> tableview;

    public void setSampleTour(SampleTour sampleTour, TableView<SampleTour> tableview) {
        this.sampleTour = sampleTour;
        this.tableview = tableview;

        // Update the text fields with the current values of the sample tour
        textTourName.setText(sampleTour.getTourName());
        textDescription.setText(sampleTour.getDescription());
        textTotalCost.setText(String.valueOf(sampleTour.getTotalCost()));
    }

    @FXML
    public void handleUpdateBtnPressed(ActionEvent event) {
        // Update the sample tour with the new values from the text fields
        sampleTour.setTourName(textTourName.getText());
        sampleTour.setDescription(textDescription.getText());
        sampleTour.setTotalCost(Double.parseDouble(textTotalCost.getText()));
        tableview.refresh();

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

