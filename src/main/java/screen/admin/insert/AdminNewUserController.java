package screen.admin.insert;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.User;
import model.User.UserType;
import screen.admin.UserCreatedListener;

import java.sql.SQLException;

public class AdminNewUserController implements AdminInsertScreen<User> {
    private UserCreatedListener listener;

    public void setUserCreatedListener(UserCreatedListener listener) {
        this.listener = listener;
    }

    @FXML
    private Button backBtn;

    @FXML
    private Button createBtn;

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

    public Button getBackBtn() {
        return backBtn;
    }

    @FXML
    private TableView<User> tableview;

    public AdminNewUserController() {
        tableview = new TableView<>();
    }

    @Override
    public void setTableview(TableView<User> tableview) {
        this.tableview = tableview;
    }

    @FXML
    public void handleBackBtnPressed(ActionEvent event) {
        if (backBtn != null) {
            backBtn.setOnAction(e -> {
                Stage stage = (Stage) backBtn.getScene().getWindow();
                stage.close();
            });
        }
    }
    @Override
    @FXML
    public void createBtn(ActionEvent event) throws SQLException {
        User newUser = new User();
        newUser.setUsername(textUserName.getText());
        newUser.setPassword(textPassword.getText());
        newUser.setName(textName.getText());
        newUser.setEmail(textEmail.getText());

        if (adminCheckBox.isSelected()) {
            newUser.setUserType(UserType.ADMIN);
        } else if (tourGuideCheckBox.isSelected()) {
            newUser.setUserType(UserType.TOUR_GUIDE);
        } else if (touristCheckBox.isSelected()) {
            newUser.setUserType(UserType.TOURIST);
        }

        UserController userController = new UserController();
        userController.add(newUser);
        int userID = userController.getAll().size();
        System.out.println("userId: " + userID);
        newUser.setUserId(userID);

        if (listener != null) {
            listener.onUserCreated(newUser);
        }

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create New Style");
        alert.setHeaderText("Create style successfully");
        alert.showAndWait();
    }
}
