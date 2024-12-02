package com.example.examentap.controllers;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.models.Datos_Cita;
import com.example.examentap.reports.CitasExcelReport;
import com.example.examentap.reports.CitasPDFReport;
import com.example.examentap.reports.UsersExcelReports;
import com.example.examentap.reports.UsersPDFReport;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CitasController_admin implements Initializable {
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
    @FXML
    private TableColumn<Datos_Cita, Integer> col_idUsuario;
    private final CitasDao citasDao = new CitasDao();
    private List<Datos_Cita> datosCitaList = new ArrayList<Datos_Cita>();


    public static final String DEST1 = "results/pdf/Citas.pdf";
    public static final String DEST2 = "results/excel/Citas.xlsx";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_idCita.setCellValueFactory(new PropertyValueFactory<>("id_cita"));
        col_nombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_cita"));
        col_hora.setCellValueFactory(new PropertyValueFactory<>("hora_cita"));
        col_idPropiedad.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        col_idUsuario.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        iniciarTabla();

    }
    public void iniciarTabla(){
        datosCitaList = citasDao.findAll();
        citasTable.setItems(FXCollections.observableArrayList(datosCitaList));
    }

    @FXML
    private void generarPDF() throws IOException {
        File file = new File(DEST1);
        file.getParentFile().mkdirs();
        new CitasPDFReport().createPdf(DEST1);

        showMessage("Report generated!");
        if(showMessage("PDF Citas")){
            openFile(DEST1);
        }
    }

    // la alerta te regresa un true si le das en aceptar
    private boolean showMessage(String message){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("PDF generated...");
        a.setContentText(message);
        Optional<ButtonType> result = a.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    @FXML
    private void generarExcel(){
        CitasExcelReport document = new CitasExcelReport();
        document.createExcel(DEST2,0);
        openFile(DEST2);
    }

    //metodo para abrir reportes pdf o excel
    private void openFile(String filename) {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    //metodo para mostrar mensajes
    /*
    private void showMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PDF generated...");
        alert.setContentText(message);
        alert.show();
    }

     */
}
