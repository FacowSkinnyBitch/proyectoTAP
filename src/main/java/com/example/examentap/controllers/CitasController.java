package com.example.examentap.controllers;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.models.Datos_Cita;
import com.example.examentap.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CitasController {
    @FXML
    private TableView<Datos_Cita> contactosTable;

    @FXML
    private TableColumn<Datos_Cita, Integer> idColumn;

    @FXML
    private TableColumn<Datos_Cita, String> nombreColumn;

    @FXML
    private TableColumn<Datos_Cita, String> correoColumn;

    @FXML
    private TableColumn<Datos_Cita, Integer> telefonoColumn;

    @FXML
    private TableColumn<Datos_Cita, String> fechaColumn;
    @FXML
    private TableColumn<Datos_Cita, String> propiedades;
    private Usuario usuarioIngresado;
    @FXML
    private TableColumn<Datos_Cita, String> horaColumn;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_cita"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("correo"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_cita"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("hora_cita"));
        propiedades.setCellValueFactory(new PropertyValueFactory<>("propiedad"));
        if (usuarioIngresado != null) cargarContactos();
    }
    public void registeredUser(Usuario user) {
        usuarioIngresado = user;
        if (usuarioIngresado != null)  cargarContactos();
    }

    private void cargarContactos() {
        CitasDao citasDao = new CitasDao();
        ObservableList<Datos_Cita> datosCitas = FXCollections.observableArrayList(citasDao.findCitaByUser(usuarioIngresado.getId()));
        contactosTable.setItems(datosCitas);
    }
    @FXML
    public void onClose(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
