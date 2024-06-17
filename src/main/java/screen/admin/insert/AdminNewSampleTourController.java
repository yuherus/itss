package screen.admin.insert;

import controller.SampleTourController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.SampleTour;
import screen.admin.SampleTourCreatedListener;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class AdminNewSampleTourController implements AdminInsertScreen<SampleTour>{
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
    @Override
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
    @Override
    public void createBtn(ActionEvent event) throws SQLException {
        SampleTour newSampleTour = new SampleTour();

        newSampleTour.setTourName(textTourName.getText());
        newSampleTour.setDescription(textDescription.getText());

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
        java.util.Date utilStartDate = null;
        java.util.Date utilEndDate = null;
        try {
            utilStartDate = sdf.parse(startDateText);
            utilEndDate = sdf.parse(endDateText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date startDate = new Date(utilStartDate.getTime());
        Date endDate = new Date(utilEndDate.getTime());
        newSampleTour.setStartDate(startDate);
        newSampleTour.setEndDate(endDate);

        new SampleTourController().add(newSampleTour);

        int sampleTourId = tableview.getItems().size() + 1;
        System.out.println("sampleTourId: " + sampleTourId);
        newSampleTour.setSampleTourId(sampleTourId);

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
