module itss.itss {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    opens model to javafx.base;
    opens touristech to javafx.fxml;
    exports touristech;
    exports controller;
    exports model;
    exports screen;
    opens screen to javafx.fxml;
    exports screen.admin;
    opens screen.admin to javafx.fxml;
    exports screen.tourguide;
    opens screen.tourguide to javafx.fxml;
    exports screen.user;
    opens screen.user to javafx.fxml;
    exports screen.admin.insert;
    opens screen.admin.insert to javafx.fxml;
    exports screen.admin.update;
    opens screen.admin.update to javafx.fxml;
}