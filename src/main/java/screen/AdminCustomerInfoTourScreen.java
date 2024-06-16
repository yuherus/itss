package screen;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import model.User;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class AdminCustomerInfoTourScreen {
    private List<User> userData = new ArrayList<>();
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<User> tableview;
    @FXML
    private TableColumn<User, String> t1;
    @FXML
    private TableColumn<User, String> t2;
    @FXML
    private TableColumn<User, String> t3;
    @FXML
    private TableColumn<User, String> t4;
    @FXML
    private TableColumn<User, String> t5;
    @FXML
    private TableColumn<User, String> t6;
    @FXML
    private Button searchButton;
    @FXML
    private Button delete;

    @FXML
    public void initialize() throws SQLException {
        System.out.println("AdminCustomerInfoTourScreen initialize");

        UserController userController = new UserController();
        userData = userController.getAll();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userController.getAll());

        t1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        t2.setCellValueFactory(new PropertyValueFactory<>("username"));
        t3.setCellValueFactory(new PropertyValueFactory<>("password"));
        t4.setCellValueFactory(new PropertyValueFactory<>("name"));
        t5.setCellValueFactory(new PropertyValueFactory<>("email"));
        t6.setCellValueFactory(new PropertyValueFactory<>("userType"));

        tableview.setItems(FXCollections.observableArrayList(userObservableList));
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
        List<User> userList = new ArrayList<>();
        for (User user : userData) {
            if (user.getName().contains(textSearch)) {
                userList.add(user);
            }
        }
        tableview.setItems(FXCollections.observableArrayList(userList));}
        @FXML
        void handleDeleteBtnPressed(ActionEvent event) {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Delete User");
            alertConfirm.setHeaderText("Are you sure you want to delete user?");
            alertConfirm.setContentText("This action cannot be undone.");
            alertConfirm.showAndWait();
            if (alertConfirm.getResult() == ButtonType.OK) {
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Delete User");
                alertSuccess.setHeaderText("Delete user successfully");
                alertSuccess.showAndWait();
                deleteUser();
            }
        }

        private void deleteUser() {
            User user = tableview.getSelectionModel().getSelectedItem();
            if (user != null) {
                userData.remove(user);
                tableview.getItems().remove(user);
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
            popupStage.setTitle("New User");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/NewUser.fxml"));
            Parent root = loader.load(); // Use Parent instead of VBox

            // Get the controller of the FXML file
            AdminNewUserController controller = loader.getController();
            controller.setTableview(tableview);

            // Set the action of the Back button to close the popup
            controller.getBackBtn().setOnAction(e -> popupStage.close());

            // Set this class as a UserCreatedListener
            controller.setUserCreatedListener(newUser -> {
                // Add the new sample tour to the table view
                tableview.getItems().add(newUser);
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
        User selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            openUpdatePopup(selectedUser);
        } else {
            // Show an error message or do nothing
            System.out.println("Please select a user to update");
        }
    }

    private void openUpdatePopup(User user) {
        try {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Update User");

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/UpdateUser.fxml"));
            Parent root = loader.load();

            // Get the controller of the FXML file
            AdminUpdateUser controller = loader.getController();
            controller.setUser(user, tableview);

            Scene popupScene = new Scene(root, 400, 400);
            popupStage.setScene(popupScene);

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





