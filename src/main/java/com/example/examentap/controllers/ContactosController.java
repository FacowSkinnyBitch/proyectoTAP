package com.example.examentap.controllers;

import com.example.examentap.databases.dao.ContactoDao;
import com.example.examentap.models.Contacto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ContactosController {
    @FXML
    private TableView<Contacto> contactosTable;

    @FXML
    private TableColumn<Contacto, Integer> idColumn;

    @FXML
    private TableColumn<Contacto, String> nombreColumn;

    @FXML
    private TableColumn<Contacto, String> correoColumn;

    @FXML
    private TableColumn<Contacto, Integer> telefonoColumn;

    @FXML
    private TableColumn<Contacto, String> fechaColumn;
    @FXML
    private TableColumn<Contacto, String> propiedades;

    @FXML
    private TableColumn<Contacto, String> horaColumn;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_contacto"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("correo"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_cita"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("hora_cita"));
        propiedades.setCellValueFactory(new PropertyValueFactory<>("propiedad"));

        cargarContactos();
    }

    private void cargarContactos() {
        ContactoDao contactoDao = new ContactoDao();
        ObservableList<Contacto> contactos = FXCollections.observableArrayList(contactoDao.findAll());
        contactosTable.setItems(contactos);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) contactosTable.getScene().getWindow();
        stage.close();
    }
}
