package screen.admin;

import controller.StyleController;
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
import model.Style;
import screen.admin.insert.AdminNewStyleController;
import screen.admin.update.AdminUpdateStyle;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminStyleTravelScreen {
    private List<Style> StyleData = new ArrayList<>();
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Style> tableview;
    @FXML
    private TableColumn<Style, String> t1;
    @FXML
    private TableColumn<Style, String> t2;
    @FXML
    private TableColumn<Style, String> t3;
    @FXML
    private Button btnSearch;
    @FXML
    private Button delete;

    @FXML
    public void initialize() throws SQLException {
        System.out.println("AdminStyleTravelScreen initialize");


        StyleController styleController= new StyleController();
        StyleData = styleController.getAll(); // Call getAll() from an instance
        ObservableList<Style> styleObservableList = FXCollections.observableArrayList(StyleData);

        t1.setCellValueFactory(new PropertyValueFactory<>("styleId"));
        t2.setCellValueFactory(new PropertyValueFactory<>("name"));
        t3.setCellValueFactory(new PropertyValueFactory<>("description")); // Add closing parenthesis
        tableview.setItems(styleObservableList);
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
        List<Style> styleList = new ArrayList<>();
        for (Style style : StyleData) {
            if (style.getName().contains(textSearch)) {
                styleList.add(style);
            }
        }
        tableview.setItems(FXCollections.observableArrayList(styleList));
    }
    @FXML
    void handleDeleteBtnPressed(ActionEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Delete Travel Style");
        alertConfirm.setHeaderText("Are you sure you want to delete this style?");
        alertConfirm.setContentText("This action cannot be undone.");
        alertConfirm.showAndWait();
        if (alertConfirm.getResult() == ButtonType.OK) {
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Delete Style successfully");
            alertSuccess.setHeaderText("Delete style successfully");
            alertSuccess.showAndWait();
            deleteStyle();
        }
    }

    private void deleteStyle() {
        Style style = tableview.getSelectionModel().getSelectedItem();
        if (style != null) {
           StyleData.remove(style);
            tableview.getItems().remove(style);
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
            popupStage.setTitle("New Travel Style");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/NewTravelStyle.fxml"));
            Parent root = loader.load(); // Use Parent instead of VBox

            // Get the controller of the FXML file
            AdminNewStyleController controller = loader.getController();
            controller.setTableview(tableview);

            // Set the action of the Back button to close the popup
            controller.getBackBtn().setOnAction(e -> popupStage.close());

            // Set this class as a SampleTourCreatedListener
            controller.setStyleCreatedListener(newStyle -> {
                // Add the new sample tour to the table view
                tableview.getItems().add(newStyle);
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
        Style selectedStyle = tableview.getSelectionModel().getSelectedItem();
        if (selectedStyle != null) {
            openUpdatePopup(selectedStyle);
        } else {
            // Show an error message or do nothing
            System.out.println("Please select a style to update");
        }
    }

    private void openUpdatePopup(Style style) {
        try {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Update Style");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/UpdateTravelStyle.fxml"));
            Parent root = loader.load();

            // Get the controller of the FXML file
            AdminUpdateStyle controller = loader.getController();
            controller.setStyle(style, tableview);

            Scene popupScene = new Scene(root, 400, 400);
            popupStage.setScene(popupScene);

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }




