module com.example.examentap {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.examentap to javafx.fxml;
    exports com.example.examentap;

    opens com.example.examentap.databases to javafx.fxml;
    exports com.example.examentap.databases;

    opens com.example.examentap.controllers to javafx.fxml;
    exports com.example.examentap.controllers;

    opens com.example.examentap.databases.dao to javafx.fxml;
    exports com.example.examentap.databases.dao;

    opens com.example.examentap.models to javafx.fxml;
    exports com.example.examentap.models;
}