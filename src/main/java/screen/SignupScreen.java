package screen;


import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class SignupScreen {
    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink signInLink;

    @FXML
    private TextField usernameField;

    @FXML
    private void handleSignup() throws SQLException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Signup Error", "Username, Name, Email, and Password are required!");
            return;
        }

        try {
            User user = UserController.signup(username, password, name, email);
            if (user != null) {
                showAlert(Alert.AlertType.INFORMATION, "Signup Successful", "User registered successfully!");
                setScene("/views/login.fxml");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Username already exists")) {
                showAlert(Alert.AlertType.ERROR, "Signup Error", "Username already exists. Please choose another one.");
            } else {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while accessing the database");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the login screen");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setScene(String fxmlPath) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage) usernameField.getScene().getWindow();
        window.setTitle("Touristech");
        window.setScene(loginScene);
        window.show();
    }

    @FXML
    private void initialize() {
        signInLink.setOnAction(event -> {
            try {
                Parent loginParent = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Scene loginScene = new Scene(loginParent);

                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                window.setTitle("Login");
                window.setScene(loginScene);
                window.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
