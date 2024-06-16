package screen;
import controller.TourController;
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
import model.Tour;
import model.Tour;

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
public class AdminTourListScreen {
    private List<Tour> tourData = new ArrayList<>();
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Tour> tableview;
    @FXML
    private TableColumn<Tour, String> t1;
    @FXML
    private TableColumn<Tour, String> t2;
    @FXML
    private TableColumn<Tour, String> t3;
    @FXML
    private TableColumn<Tour, String> t4;
    @FXML
    private TableColumn<Tour, String> t5;
    @FXML
    private TableColumn<Tour, String> t6;
    @FXML
    private TableColumn<Tour, String> t7;
    @FXML
    private TableColumn<Tour, String> t8;
    @FXML
    private TableColumn<Tour, String> t9;

    @FXML
    private Button btnSearch;
    @FXML
    private Button delete;

    @FXML
    public void initialize() throws SQLException {
        System.out.println("AdminTourScreen initialize");

        TourController tourController = new TourController();
        tourData = tourController.getAll();
//        for (Tour tour : tourData) {
//            System.out.println(tour.getDescription());
//        }
        ObservableList<Tour> tourObservableList = FXCollections.observableArrayList(tourController.getAll());

        t1.setCellValueFactory(new PropertyValueFactory<>("tourId"));
        t2.setCellValueFactory(new PropertyValueFactory<>("tourGuideId"));
        t3.setCellValueFactory(new PropertyValueFactory<>("touristId"));
        t4.setCellValueFactory(new PropertyValueFactory<>("tourName"));
        t5.setCellValueFactory(new PropertyValueFactory<>("description"));
        t6.setCellValueFactory(new PropertyValueFactory<>("status"));
        t7.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        t8.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        t9.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        tableview.setItems(FXCollections.observableArrayList(tourObservableList));
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
        List<Tour> tourList = new ArrayList<>();
        for (Tour tour : tourData) {
            if (tour.getTourName().contains(textSearch)) {
                tourList.add(tour);
            }
        }
        tableview.setItems(FXCollections.observableArrayList(tourList));
    }

}