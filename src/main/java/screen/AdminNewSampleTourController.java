package screen;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.SampleTour;
import views.admin.SampleTourCreatedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

public class AdminNewSampleTourController {
    private SampleTourCreatedListener listener;
    public void setSampleTourCreatedListener(SampleTourCreatedListener listener) {
        this.listener = listener;
    }
    @FXML
    private Button backBtn;
    @FXML
    private TextField textTourName;
    @FXML
    private TextField textDescription;
    @FXML
    private TextField textStartDate;
    @FXML
    private TextField textEndDate;
    @FXML
    private TextField textTotalCost;
    public Button getBackBtn() {
        return backBtn; // Đảm bảo rằng backBtn không trả về null
    }
    @FXML
    private TableView<SampleTour> tableview;
    public AdminNewSampleTourController() {
        tableview = new TableView<>();
        // Initialize other properties of tableview here
    }

    public void setTableview(TableView<SampleTour> tableview) {
        this.tableview = tableview;
    }
    //Xử lý sự kiện khi người dùng nhấn vào nút Back
    @FXML
    public void backBtn(ActionEvent event) {
        if (backBtn != null) { // Kiểm tra xem backBtn có khác null không trước khi thiết lập hành động
            backBtn.setOnAction(e -> {
                Stage stage = (Stage) backBtn.getScene().getWindow();
                stage.close();
            });
        }
    }
    // Xử lý sự kiện khi người dùng nhấn vào nút Create
    public void createBtn(ActionEvent event) throws ParseException {
        SampleTour newSampleTour = new SampleTour();

        newSampleTour.setTourName(textTourName.getText());
        newSampleTour.setDescription(textDescription.getText());

        int sampleTourId = tableview.getItems().size() + 1;
        System.out.println("sampleTourId: " + sampleTourId);
        newSampleTour.setSampleTourId(sampleTourId);

        String totalCostText = textTotalCost.getText();
        try {
            double totalCost = Double.parseDouble(totalCostText);
            newSampleTour.setTotalCost(totalCost);
        } catch (NumberFormatException e) {
            // Handle the case where totalCostText cannot be parsed to a double
            // For example, show an error message to the user
            System.out.println("Invalid input for total cost: " + totalCostText);
            return;
        }

        String startDateText = textStartDate.getText();
        String endDateText = textEndDate.getText();
        // Parse startDateText and endDateText to Date objects
        // Set the startDate and endDate of newSampleTour
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilStartDate = sdf.parse(startDateText);
        java.util.Date utilEndDate = sdf.parse(endDateText);
        Date startDate = new Date(utilStartDate.getTime());
        Date endDate = new Date(utilEndDate.getTime());
        newSampleTour.setStartDate(startDate);
        newSampleTour.setEndDate(endDate);

//        tableview.getItems().add(newSampleTour);
        if (listener != null) {
            listener.onSampleTourCreated(newSampleTour);
        }

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Sample Tour");
        alert.setHeaderText("Create sample tour successfully");
        alert.showAndWait();

    }

}
