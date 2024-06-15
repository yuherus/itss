package screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Location;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OptionalBookInfoScreen {

    @FXML
    private VBox copiedVBox;

    private Stage stage;

    // Hàm này để thiết lập stage từ bên ngoài vào
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void copyTripDetailAndShowBackButton(VBox tripDetail) {
        copiedVBox.getChildren().clear();
        copiedVBox.getChildren().addAll(tripDetail.getChildren());
        changeButtonToBack();
    }

    private void changeButtonToBack() {
        Button nextButton = (Button) copiedVBox.lookup("#nextButton");
        if (nextButton != null) {
            nextButton.setText("Back");
            nextButton.setOnAction(event -> navigateBack());
        }
    }

    private void navigateBack() {
        try {
            // Load FXML và controller của optional-book.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book.fxml"));
            Parent root = loader.load();

            // Lấy controller của optional-book.fxml
            OptionalBookScreen controller = loader.getController();

            // Truyền stage từ OptionalBookInfoScreen vào controller của optional-book.fxml
            controller.setStage(stage); // Truyền lại stage từ OptionalBookInfoScreen

            // Đặt scene mới cho stage
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/*
public class OptionalBookInfoScreen {

    private Stage stage;
    private VBox tripDetail;

    @FXML
    private VBox copiedVBox;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void copyTripDetailAndShowBackButton(VBox tripDetail) {
        this.tripDetail = tripDetail;

        copiedVBox.getChildren().clear();
        copiedVBox.getChildren().addAll(tripDetail.getChildren());

        changeButtonToBack();
    }

    private void changeButtonToBack() {
        Button nextButton = (Button) copiedVBox.lookup("#nextButton");
        if (nextButton != null) {
            nextButton.setText("Back");
            nextButton.setOnAction(event -> navigateBack());
        }
    }

    private void navigateBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user/optional-book.fxml"));
            Parent root = loader.load();

            OptionalBookScreen controller = loader.getController();
            controller.setStage(stage);
            controller.copyTripDetailAndShowNextButton(tripDetail); // Chuyển tripDetail về OptionalBookScreen

            // Đặt scene mới cho stage
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */







