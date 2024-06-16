package screen;
import controller.SampleTourController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SampleTour;

import java.sql.SQLException;

public class AdminSampleTourScreen {
    @FXML
    private TableView<SampleTour> tableview;
    @FXML
    private TableColumn<SampleTour, String> t1;
    @FXML
    private TableColumn<SampleTour, String> t11;
    @FXML
    private TableColumn<SampleTour, String> t2;
    @FXML
    private TableColumn<SampleTour, String> t3;

    @FXML
    public void initialize() throws SQLException {
        SampleTourController sampleTourController = new SampleTourController();
        ObservableList<SampleTour> sampleTourObservableList = FXCollections.observableArrayList(sampleTourController.getAll());


        t1.setCellValueFactory(new PropertyValueFactory<>("sampleTourId"));
        t11.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        t2.setCellValueFactory(new PropertyValueFactory<>("tourName"));
        t3.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableview.setItems(FXCollections.observableArrayList(sampleTourObservableList));
    }
}
