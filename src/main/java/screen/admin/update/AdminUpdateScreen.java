package screen.admin.update;

import javafx.event.ActionEvent;

import java.sql.SQLException;

public interface AdminUpdateScreen {
    public void handleUpdateBtnPressed(ActionEvent event) throws SQLException;
    public void handleBackBtnPressed(ActionEvent event);

}
