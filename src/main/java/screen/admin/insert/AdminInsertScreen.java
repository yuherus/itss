package screen.admin.insert;

import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public interface AdminInsertScreen<T> {
    public void setTableview(TableView<T> tableView);

    public void createBtn(ActionEvent event) throws SQLException;
}
