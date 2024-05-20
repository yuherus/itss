package screen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreen{
    @FXML
    Button login;
    @FXML
    Button signup;
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene loginScene = new Scene(loginParent);

        // Get stage thông qua event source (nút "Login")
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene(loginScene);
        window.show();

    }
}