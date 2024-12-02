package com.example.examentap.controllers;

import com.example.examentap.databases.dao.PropiedadesDao;
import com.example.examentap.models.Propiedades;
import com.example.examentap.models.Tipo_Propiedad;
import com.example.examentap.reports.PDFEspecificReport;
import com.example.examentap.reports.PropiedadesPDFReports;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PropiedadesController implements Initializable {
    @FXML private TableView<Propiedades> propiedadesTable;
    @FXML private TableColumn<Propiedades, Integer> tv_id;
    @FXML private TableColumn<Propiedades, String> tv_direccion;
    @FXML private TableColumn<Propiedades, Double> tv_precio;
    @FXML private TableColumn<Propiedades, Integer> tv_tipoProp;
    @FXML private TableColumn<Propiedades, Boolean> tv_status;
    @FXML private TableColumn<Propiedades, String> tv_Ciudad;

    @FXML private ComboBox cb_filtroStatusProp,cb_filtroTipoProp, cb_filtroCiudad;
    //    tabla



    private PropiedadesDao propDao = new PropiedadesDao();
    private List<Propiedades> propiedadesList = new ArrayList<Propiedades>();

    public static final String DEST1 = "results/pdf/Propiedades.pdf";
    public static final String DEST2 = "results/pdf/TipoPropiedades.pdf";
    PropiedadesDao dao = new PropiedadesDao();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_id.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        tv_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tv_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tv_tipoProp.setCellValueFactory(new PropertyValueFactory<>("tipo_propiedad"));
        tv_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tv_Ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        initTable();
        String[] status = {"Renta","Venta","Todo"};
        String[] tipo_prop = {"Casa","Negocio","Condominio","Todo"};
        String[] ciudadUbicada = {"León", "Guadalajara", "Querétaro", "Morelia", "Todo"};
        cb_filtroStatusProp.setItems(FXCollections.observableArrayList(status));
        cb_filtroTipoProp.setItems(FXCollections.observableArrayList(tipo_prop));
        cb_filtroCiudad.setItems(FXCollections.observableArrayList(ciudadUbicada));



        cb_filtroStatusProp.valueProperty().addListener(event -> {
            if(cb_filtroStatusProp.getSelectionModel().getSelectedItem().equals("Renta")){
                propiedadesList = propDao.filterPropByStatus("renta");
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));

            }else if(cb_filtroStatusProp.getSelectionModel().getSelectedItem().equals("Venta")){
                propiedadesList = propDao.filterPropByStatus("venta");
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }else{
                propiedadesList = propDao.findAll();
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }
        });
        cb_filtroTipoProp.valueProperty().addListener(event -> {
            if(cb_filtroTipoProp.getSelectionModel().getSelectedItem().toString().equals("Casa")){
                propiedadesList = propDao.filterPropByTipoProp(1);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }else if(cb_filtroTipoProp.getSelectionModel().getSelectedItem().equals("Negocio")){
                propiedadesList = propDao.filterPropByTipoProp(2);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }else if(cb_filtroTipoProp.getSelectionModel().getSelectedItem().equals("Condominio")){
                propiedadesList = propDao.filterPropByTipoProp(3);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }else{
                propiedadesList = propDao.findAll();
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }
        });
        cb_filtroCiudad.valueProperty().addListener(event -> {
           if(cb_filtroCiudad.getSelectionModel().getSelectedItem().toString().equals("León")){
               propiedadesList = propDao.filterPropByCiudad(1);
               propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
           }else if(cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Guadalajara")){
               propiedadesList = propDao.filterPropByCiudad(2);
               propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
           }else if (cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Querétaro")) {
               propiedadesList = propDao.filterPropByCiudad(3);
               propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
           } else if (cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Morelia")) {
               propiedadesList = propDao.filterPropByCiudad(4);
               propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
           }else{
               propiedadesList = propDao.findAll();
               propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
           }
        });


    }
    private void initTable() {

        propiedadesList = propDao.findAll();
        propiedadesTable.setItems(FXCollections.observableList(propiedadesList));


        propiedadesTable.setOnMouseClicked(mouseEvent ->{
            Propiedades p = (Propiedades) propiedadesTable.getSelectionModel().getSelectedItem();
            switch(mouseEvent.getClickCount()) {
                case 1:
                    break;
                case 2:
                    if(propiedadesTable.getSelectionModel().getSelectedItem() == null) {
                        System.out.println("Nada seleccionado");
                    }else{
                        onMostrarPropiedad(p);
                    }
            }
        });

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

    private void onMostrarPropiedad(Propiedades p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/userViews/vw_propSelected.fxml"));
            Parent root = loader.load();
            propiedadSeleccionada ps = loader.getController();
            ps.mostrarDatos(p);

            Stage stage = new Stage();
            stage.setTitle("Propiedad Seleccionada");
            stage.setScene(new Scene(root));
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
        new PropiedadesPDFReports().createPdf(DEST1);


        if(showMessage("PDF Usuarios")){
            openFile(DEST1);
        }
    }

    @FXML
    private void generarExcel(){

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
    private void generarPdfFiltrado() throws IOException {
        Tipo_Propiedad tpPropiedad = new Tipo_Propiedad();
        if(tpPropiedad == null){
            showMessage("Select a category");
        } else  {
            List<Propiedades> tipoPropiedades =(tpPropiedad.getId_tipo_propiedad() == 0)? dao.findAll() : dao.filterPropByTipoProp(tpPropiedad.getId_tipo_propiedad());
            File file = new File(DEST2);
            file.getParentFile().mkdirs();
            new PDFEspecificReport().createPdf(DEST2, tipoPropiedades);
            showMessage("The products report with esoecific id was generated");
            openFile(DEST2);
        }
    }

    //metodo para mostrar mensajes
    private boolean showMessage(String message){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("PDF generated...");
        a.setContentText(message);
        Optional<ButtonType> result = a.showAndWait();
        return (result.get() == ButtonType.OK);
    }

}
