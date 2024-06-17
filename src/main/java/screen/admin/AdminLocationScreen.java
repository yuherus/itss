package screen.admin;

import controller.LocationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Location;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminLocationScreen {
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Location> tableview;
    @FXML
    private TableColumn<Location, String> t1;
    @FXML
    private TableColumn<Location, String> t2;
    @FXML
    private TableColumn<Location, String> t3;
    @FXML
    private TableColumn<Location, String> t4;
    @FXML
    private TableColumn<Location, String> t5;
    @FXML
    private TableColumn<Location, String> t6; // Define t6
    @FXML
    private Button btnSearch;
    @FXML
    private Button delete;
    private List<Location> locationData = new ArrayList<>();

    @FXML
    public void initialize() throws SQLException {
        System.out.println("AdminLocationScreen initialize");

        LocationController locationController= new LocationController();
        locationData = locationController.getAll(); // Call getAll() from an instance
        ObservableList<Location> locationsObservableList = FXCollections.observableArrayList(locationData);

        t1.setCellValueFactory(new PropertyValueFactory<>("locationId"));
        t2.setCellValueFactory(new PropertyValueFactory<>("name"));
        t3.setCellValueFactory(new PropertyValueFactory<>("address"));
        t4.setCellValueFactory(new PropertyValueFactory<>("description"));
        t5.setCellValueFactory(new PropertyValueFactory<>("style"));
        t6.setCellValueFactory(new PropertyValueFactory<>("imageUrl")); // Use t6
        tableview.setItems(locationsObservableList);
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
        List<Location> locationList = new ArrayList<>();
        for (Location location : locationData) {
            if (location.getName().contains(textSearch)) {
                locationList.add(location);
            }
        }
        tableview.setItems(FXCollections.observableArrayList(locationList));
    }
    @FXML
    void handleDeleteBtnPressed(ActionEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Delete Location");
        alertConfirm.setHeaderText("Are you sure you want to delete this location?");
        alertConfirm.setContentText("This action cannot be undone.");
        alertConfirm.showAndWait();
        if (alertConfirm.getResult() == ButtonType.OK) {
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Delete Location");
            alertSuccess.setHeaderText("Delete location successfully");
            alertSuccess.showAndWait();
            deleteLocation();
        }
    }

    private void deleteLocation() {
        Location location = tableview.getSelectionModel().getSelectedItem();
        if (location != null) {
            locationData.remove(location);
            tableview.getItems().remove(location);
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
            popupStage.setTitle("New Location");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/NewLocation.fxml"));
            Parent root = loader.load(); // Use Parent instead of VBox

            // Get the controller of the FXML file
            AdminNewLocationScreen controller = loader.getController();
            controller.setTableview(tableview);

            // Set the action of the Back button to close the popup
            controller.getBackBtn().setOnAction(e -> popupStage.close());

            controller.setLocationCreatedListener(newLocation -> {
                // Add the new location to the table view
                tableview.getItems().add(newLocation);
            });
            Scene popupScene = new Scene(root, 400, 400);
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
        Location selectedLocation = tableview.getSelectionModel().getSelectedItem();
        if (selectedLocation != null) {
            openUpdatePopup(selectedLocation);
        } else {
            // Show an error message or do nothing
            System.out.println("Please select a location tour to update");
        }
    }

    private void openUpdatePopup(Location location) {
        try {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Update Location");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/UpdateLocation.fxml"));
            Parent root = loader.load();

            // Get the controller of the FXML file
            AdminUpdateLocation controller = loader.getController();
            controller.setLocation(location, tableview);

            Scene popupScene = new Scene(root, 400, 400);
            popupStage.setScene(popupScene);

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}






