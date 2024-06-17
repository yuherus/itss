package screen.admin.update;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.sql.SQLException;

public class AdminUpdateUser implements AdminUpdateScreen{
    @FXML
    private TextField textUserName;

    @FXML
    private TextField textPassword;
    @FXML
    private TextField textName;
    @FXML
    private TextField textEmail;
    @FXML
    private CheckBox adminCheckBox;
    @FXML
    private CheckBox tourGuideCheckBox;
    @FXML
    private CheckBox touristCheckBox;
    private User user;
    private TableView<User> tableview;

    public void setUser(User user, TableView<User> tableview) {
        this.user = user;
        this.tableview = tableview;

        // Update the text fields with the current values of the sample tour
        textUserName.setText(user.getUsername());
        textName.setText(user.getName());
        textEmail.setText(user.getEmail());
        textPassword.setText(user.getPassword());
        if (user.getUserType() == User.UserType.ADMIN) {
            adminCheckBox.setSelected(true);
        } else if (user.getUserType() == User.UserType.TOUR_GUIDE) {
            tourGuideCheckBox.setSelected(true);
        } else if (user.getUserType() == User.UserType.TOURIST) {
            touristCheckBox.setSelected(true);
        }

    }

    @Override
    @FXML
    public void handleUpdateBtnPressed(ActionEvent event) throws SQLException {
        // Update the sample tour with the new values from the text fields
        user.setUsername(textUserName.getText());
        user.setPassword(textPassword.getText());
        user.setName(textName.getText());
        user.setEmail(textEmail.getText());

        if (adminCheckBox.isSelected()) {
            user.setUserType(User.UserType.ADMIN);
        } else if (tourGuideCheckBox.isSelected()) {
            user.setUserType(User.UserType.TOUR_GUIDE);
        } else if (touristCheckBox.isSelected()) {
            user.setUserType(User.UserType.TOURIST);
        }

        new UserController().update(user);

        tableview.refresh();

        // Close the window
        Stage stage = (Stage) textName.getScene().getWindow();
        stage.close();
    }
    @Override
    public void handleBackBtnPressed(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the popup
        stage.close();
    }
}