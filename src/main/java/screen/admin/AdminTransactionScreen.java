package screen.admin;
import controller.PaymentController;
import javafx.beans.property.SimpleStringProperty;
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
import model.Payment;

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
import model.Tour;
import model.Payment;

public class AdminTransactionScreen {
    private List<Payment> paymentData = new ArrayList<>();
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Payment> tableview;
    @FXML
    private TableColumn<Payment, String> t1;
    @FXML
    private TableColumn<Payment, String> t2;
    @FXML
    private TableColumn<Payment, String> t3;
    @FXML
    private TableColumn<Payment, String> t4;
    @FXML
    private TableColumn<Payment, String> t5;
    @FXML
    private TableColumn<Payment, String> t6;


    @FXML
    private Button btnSearch;


    @FXML
    public void initialize() throws SQLException {
        System.out.println("AdminPaymentScreen initialize");

        PaymentController paymentController = new PaymentController();
        paymentData = paymentController.getAll();
        ObservableList<Payment> paymentObservableList = FXCollections.observableArrayList(paymentController.getAll());

        t1.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        t2.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTour().getTourId())));
        t3.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTourist().getUserId())));
        t4.setCellValueFactory(new PropertyValueFactory<>("amount"));
        t5.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        t6.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        tableview.setItems(FXCollections.observableArrayList(paymentObservableList));
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
        List<Payment> paymentList = new ArrayList<>();
        for (Payment payment : paymentData) {
            if (String.valueOf(payment.getTourist().getUserId()).equals(textSearch)) {
                paymentList.add(payment);
            }
        }
        tableview.setItems(FXCollections.observableArrayList(paymentList));
    }}