package screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SampleTour;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class sampletour {
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
    public void initialize() {
        ObservableList<SampleTour> data = FXCollections.observableArrayList();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select sampletour_id, tour_name, description, total_cost from sample_tour";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                data.add(new SampleTour(resultSet.getString("sampletour_id"), resultSet.getString("tour_name"), resultSet.getString("description"), resultSet.getString("total_cost")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        t1.setCellValueFactory(new PropertyValueFactory<>("sampletourId"));
        t11.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        t2.setCellValueFactory(new PropertyValueFactory<>("tourName"));
        t3.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableview.setItems(FXCollections.observableArrayList(data));
        System.out.println(data.size());
    }
}
