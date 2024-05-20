module itss.itss {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens touristech to javafx.fxml;
    exports touristech;
    exports screen;
    opens screen to javafx.fxml;
}