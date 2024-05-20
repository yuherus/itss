package touristech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TouristechApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TouristechApplication.class.getResource("/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 576);
        stage.setTitle("Welcome to Touristech");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}