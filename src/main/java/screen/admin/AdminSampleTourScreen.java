package screen.admin;
import controller.SampleTourController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.SampleTour;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import screen.admin.insert.AdminNewSampleTourController;
import screen.admin.update.AdminUpdateSampleTour;

public class AdminSampleTourScreen {
    private List<SampleTour> sampleTourData = new ArrayList<>();
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<SampleTour> tableview;
    @FXML
    private TableColumn<SampleTour, String> t1;
    @FXML
    private TableColumn<SampleTour, String> t11;
    @FXML
    private TableColumn<SampleTour, String> t2;
    @FXML
    private TableColumn<SampleTour, String> t3;
    @FXML
    private TableColumn<SampleTour, String> t4;
    @FXML
    private TableColumn<SampleTour, String> t5;


    @FXML
    private Button btnSearch;
    @FXML
    private Button delete;

    @FXML
    public void initialize() throws SQLException {
        System.out.println("AdminSampleTourScreen initialize");

        SampleTourController sampleTourController = new SampleTourController();
        sampleTourData = sampleTourController.getAll();
        ObservableList<SampleTour> sampleTourObservableList = FXCollections.observableArrayList(sampleTourController.getAll());

        t1.setCellValueFactory(new PropertyValueFactory<>("sampleTourId"));
        t11.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        t2.setCellValueFactory(new PropertyValueFactory<>("tourName"));
        t3.setCellValueFactory(new PropertyValueFactory<>("description"));
        t4.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        t5.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tableview.setItems(FXCollections.observableArrayList(sampleTourObservableList));
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("Enter");
            searchData();
        }
    }

    @FXML
    void handleSearchBtnPressed(ActionEvent event) {
        System.out.println("Search Button Pressed");
        searchData();
    }

    private void searchData() {
        String textSearch = tfSearch.getText();
        System.out.println("Search: " + textSearch);
        List<SampleTour> sampleTourList = new ArrayList<>();
        for (SampleTour sampleTour : sampleTourData) {
            if (sampleTour.getTourName().contains(textSearch)) {
                sampleTourList.add(sampleTour);
            }
        }
        tableview.setItems(FXCollections.observableArrayList(sampleTourList));
    }

    @FXML
    void handleDeleteBtnPressed(ActionEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Delete Sample Tour");
        alertConfirm.setHeaderText("Are you sure you want to delete this sample tour?");
        alertConfirm.setContentText("This action cannot be undone.");
        alertConfirm.showAndWait();
        if (alertConfirm.getResult() == ButtonType.OK) {
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Delete Sample Tour");
            alertSuccess.setHeaderText("Delete sample tour successfully");
            alertSuccess.showAndWait();
            deleteSampleTour();
        }
    }

    private void deleteSampleTour() {
        SampleTour sampleTour = tableview.getSelectionModel().getSelectedItem();
        if (sampleTour != null) {
            sampleTourData.remove(sampleTour);
            tableview.getItems().remove(sampleTour);
        }
    }

    @FXML
    void handleAddBtnPressed(ActionEvent event) {
        openPopup();
    }

    private void openPopup() {
        try {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Sample Tour");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/NewSampleTour.fxml"));
            Parent root = loader.load(); // Use Parent instead of VBox

            // Get the controller of the FXML file
            AdminNewSampleTourController controller = loader.getController();
            controller.setTableview(tableview);

            // Set the action of the Back button to close the popup
            controller.getBackBtn().setOnAction(e -> popupStage.close());

            // Set this class as a SampleTourCreatedListener
            controller.setSampleTourCreatedListener(newSampleTour -> {
                // Add the new sample tour to the table view
                tableview.getItems().add(newSampleTour);
            });

            Scene popupScene = new Scene(root, 420, 470);
            popupStage.setScene(popupScene);

            // Assign an ID to the popup stage for CSS styling
            popupScene.getRoot().setId("add");

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleUpdateBtnPressed(ActionEvent event) {
        SampleTour selectedSampleTour = tableview.getSelectionModel().getSelectedItem();
        if (selectedSampleTour != null) {
            openUpdatePopup(selectedSampleTour);
        } else {
            // Show an error message or do nothing
            System.out.println("Please select a sample tour to update");
        }
    }

    private void openUpdatePopup(SampleTour sampleTour) {
        try {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Update Sample Tour");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/UpdateSampleTour.fxml"));
            Parent root = loader.load();

            // Get the controller of the FXML file
            AdminUpdateSampleTour controller = loader.getController();
            controller.setSampleTour(sampleTour, tableview);

            Scene popupScene = new Scene(root, 420, 450);
            popupStage.setScene(popupScene);

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}








