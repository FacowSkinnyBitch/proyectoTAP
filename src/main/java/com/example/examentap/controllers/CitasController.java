package com.example.examentap.controllers;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.models.Datos_Cita;
import com.example.examentap.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.sql.Date;

public class CitasController {
    @FXML
    private TableView citasTable = new TableView();
    @FXML
    private TableColumn<Datos_Cita, Integer> col_idCita;
    @FXML
    private TableColumn<Datos_Cita, String> col_nombreCompleto;
    @FXML
    private TableColumn<Datos_Cita, String> col_correo;
    @FXML
    private TableColumn<Datos_Cita, String> col_telefono;
    @FXML
    private TableColumn<Datos_Cita, Date> col_fecha;
    @FXML
    private TableColumn<Datos_Cita, String> col_hora;
    @FXML
    private TableColumn<Datos_Cita, Integer> col_idPropiedad;
    private Usuario usuarioIngresado;

    @FXML
    private void initialize() {
        initTableCitas();
        if (usuarioIngresado != null) cargarCitasPorUsuario();
    }
    public void registeredUser(Usuario user) {
        usuarioIngresado = user;
        if (usuarioIngresado != null && usuarioIngresado.getRole().equals("User"))  {
            cargarCitasPorUsuario();
        }
    }
    private void initTableCitas() {
        col_idCita.setCellValueFactory(new PropertyValueFactory<>("id_cita"));
        col_nombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_cita"));
        col_hora.setCellValueFactory(new PropertyValueFactory<>("hora_cita"));
        col_idPropiedad.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
    }
    private void cargarCitasPorUsuario() {
        CitasDao citasDao = new CitasDao();
        ObservableList<Datos_Cita> datosCitas = FXCollections.observableArrayList(citasDao.findCitaByUser(usuarioIngresado.getId()));
        citasTable.setItems(datosCitas);
    }

    @FXML
    public void onClose(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


}
