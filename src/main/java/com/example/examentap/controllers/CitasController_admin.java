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
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

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
    @FXML
    private TableColumn<Datos_Cita, String> col_status;
    private final CitasDao citasDao = new CitasDao();
    private List<Datos_Cita> datosCitaList = new ArrayList<Datos_Cita>();


    public static final String DEST1 = "results/pdf/Citas.pdf";
    public static final String DEST2 = "results/excel/Citas.xlsx";

    ContextMenu contextMenu = new ContextMenu();
    javafx.scene.control.MenuItem menuItemDeleteCita = new javafx.scene.control.MenuItem("Delete");
    javafx.scene.control.MenuItem menuItemUpdateCita = new javafx.scene.control.MenuItem("Update");
    javafx.scene.control.MenuItem menuItemStatus = new MenuItem("Status");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarTabla();
        initContextMenu();
        metodosCRUD();

        citasTable.setOnMouseClicked(mouseEvent ->{
            if (mouseEvent.getClickCount() == 2) {
                Datos_Cita datCitas =(Datos_Cita) citasTable.getSelectionModel().getSelectedItem();
                //onReadUser(selectedUser);
                onMostrarCita(datCitas,1);
                System.out.println("Cita seleccionada: "+ datCitas.toString() );
            }
        });

    }
    public void iniciarTabla(){
        citasTable.setContextMenu(contextMenu);
        col_idCita.setCellValueFactory(new PropertyValueFactory<>("id_cita"));
        col_nombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_cita"));
        col_hora.setCellValueFactory(new PropertyValueFactory<>("hora_cita"));
        col_idPropiedad.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        col_idUsuario.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));

        datosCitaList = citasDao.findAll();
        citasTable.setItems(FXCollections.observableArrayList(datosCitaList));


    }
    private void initContextMenu(){
        FontIcon iconDelete = new FontIcon();
        iconDelete.setIconLiteral("anto-delete");
        iconDelete.setIconSize(20);
        iconDelete.setIconColor(Color.BLACK);
        menuItemDeleteCita.setGraphic(iconDelete);

        FontIcon iconUpdate = new FontIcon();
        iconUpdate.setIconLiteral("anto-up-square");
        iconUpdate.setIconSize(20);
        iconUpdate.setIconColor(Color.PURPLE);
        menuItemUpdateCita.setGraphic(iconUpdate);


        FontIcon iconStatus = new FontIcon();
        iconStatus.setIconLiteral("anto-info-circle");
        iconStatus.setIconSize(20);
        iconStatus.setIconColor(Color.BLUE);
        menuItemStatus.setGraphic(iconStatus);

        contextMenu.getItems().addAll(menuItemUpdateCita,menuItemDeleteCita,menuItemStatus);

    }
    @FXML
    public void onMostrarCita(ActionEvent actionEvent) {
        onMostrarCita(null,3);
    }
    private void metodosCRUD(){
        menuItemUpdateCita.setOnAction(event -> {
            Datos_Cita dCita = (Datos_Cita) citasTable.getSelectionModel().getSelectedItem();
            onMostrarCita(dCita,2);

        });
        menuItemDeleteCita.setOnAction(event -> {
            Datos_Cita dCita = (Datos_Cita) citasTable.getSelectionModel().getSelectedItem();
            if(confirmDelete()){
                System.out.println("Cita eliminada: "+ dCita.getId_cita());
                citasDao.deleteCita(dCita.getId_cita(),dCita.getId_propiedad(),dCita.getId_usuario());
                iniciarTabla();
            }
        });
        menuItemStatus.setOnAction(event -> {
            Datos_Cita dCita = (Datos_Cita) citasTable.getSelectionModel().getSelectedItem();

            //changeStatus(dCita);
        });
    }


    private boolean confirmDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Borrado");
        alert.setContentText("¿Esta seguro que quiere borrar la cita seleccionada?");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    private void onMostrarCita(Datos_Cita dtc, int modo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/adminViews/vw_crudCitas.fxml"));
            Parent root = loader.load();
            String title = "";
            CrudCitasController citaController = loader.getController();
            if(modo==1){
                //modo ver info
                citaController.citaSeleccionada(dtc, modo);
                title = "Informacion Cita";
            }else if(modo == 2){
                //modo editar
                citaController.citaSeleccionada(dtc, modo);
                title = "Editar Cita";
            }else if(modo == 3){
                citaController.citaSeleccionada(dtc, modo);
                title = "Nueva Cita";
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            Scene scene = new Scene(root);
            stage.setWidth(720);
            stage.setHeight(510);
            scene.getStylesheets().add(getClass().getResource("/com/example/examentap/cssFiles/citasUser.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Para bloquear la ventana principal mientras esta está abierta
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if(showMessage("Excel Citas")){
            openFile(DEST2);
        }
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

    @FXML
    public void onCloseForm(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


}
