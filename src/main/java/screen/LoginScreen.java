package screen;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class LoginScreen {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private void handleLogin() throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Email and Password are required!");
            alert.showAndWait();
            return;
        }

        try {
            User user = UserController.login(username, password);
            if (user != null) {
                System.setProperty("userId", String.valueOf(user.getUserId()));
                if (user.getUserType() == User.UserType.ADMIN) {
                     setScene("/views/admin/admin.fxml");
                } else if (user.getUserType() == User.UserType.TOUR_GUIDE) {
                     setScene("/views/tourguide/tourguide.fxml");
                } else if (user.getUserType() == User.UserType.TOURIST) {
                     setScene("/views/user/user.fxml");
                }
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while accessing the database");
        }

    }

    private void showAlert(AlertType type, String title, String message) {
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
        forgotPasswordLink.setOnAction(event -> {

        });

        signUpLink.setOnAction(event -> {
            Parent signupParent = null;
            try {
                signupParent = FXMLLoader.load(getClass().getResource("/views/signup.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene signupScene = new Scene(signupParent);

            // Get stage thông qua event source (nút "Login")
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            window.setTitle("Signup");
            window.setScene(signupScene);
            window.show();
        });
    }
}
