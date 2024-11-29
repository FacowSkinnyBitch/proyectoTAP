package com.example.examentap.controllers;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.models.Datos_Cita;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CitasController_admin implements Initializable {
    @FXML
    private TableView citasTable = new TableView();
    @FXML
    private TableColumn<Datos_Cita, Integer> idCita;
    @FXML
    private TableColumn<Datos_Cita, String> nombreCompleto;
    @FXML
    private TableColumn<Datos_Cita, String> correo;
    @FXML
    private TableColumn<Datos_Cita, String> telefono;
    @FXML
    private TableColumn<Datos_Cita, Date> fecha;
    @FXML
    private TableColumn<Datos_Cita, String> hora;
    @FXML
    private TableColumn<Datos_Cita, String> propiedad;
    @FXML
    private TableColumn<Datos_Cita, Integer> id_usuario;
    private CitasDao citasDao = new CitasDao();
    private List<Datos_Cita> datosCitaList = new ArrayList<Datos_Cita>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCita.setCellValueFactory(new PropertyValueFactory<>("id_cita"));
        nombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_cita"));
        hora.setCellValueFactory(new PropertyValueFactory<>("hora_cita"));
        propiedad.setCellValueFactory(new PropertyValueFactory<>("propiedad"));
        id_usuario.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        iniciarTabla();

    }
    public void iniciarTabla(){
        datosCitaList = citasDao.findAll();
        citasTable.setItems(FXCollections.observableArrayList(datosCitaList));
    }

    @FXML
    private void onReturn(ActionEvent event) throws IOException {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/examentap/adminViews/vw_modoAdmin.fxml"));  // Ruta absoluta sugerida
            Stage stage = new Stage();
            Scene scene = new Scene(root,350,500);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
