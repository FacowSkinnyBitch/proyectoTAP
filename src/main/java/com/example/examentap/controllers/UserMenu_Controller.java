package com.example.examentap.controllers;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.databases.dao.PropiedadesDao;
import com.example.examentap.models.Datos_Cita;
import com.example.examentap.models.Propiedades;
import com.example.examentap.models.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Date;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class UserMenu_Controller implements Initializable {
    //  etiquetas
    @FXML
    private TextField tf_nombre,tf_correo,tf_telefono,tf_hora_cita;
    @FXML
    private DatePicker dp_fecha_cita;
    @FXML
    private Button btn_Ingresar, btn_Cancelar, btn_Cita, btn_MostrarCitas;

    @FXML private GridPane gpFromCita, gpPropiedades;

    @FXML private ComboBox cb_filtro,cb_filtroProp, cb_filtroCiudad;
    //    tabla
    @FXML
    private TableView tv_Propiedades = new TableView();
    @FXML
    private TableColumn<Propiedades, Integer> col_idPropiedad;
    @FXML
    private TableColumn<Propiedades, String> col_descripcion;
    @FXML
    private TableColumn<Propiedades, String> col_tipoPropiedad;
    @FXML
    private TableColumn<Propiedades, Boolean> col_status;
    @FXML
    private TableColumn<Propiedades, String> col_Ciudad;

    private Usuario usuarioIniciado;

    private PropiedadesDao propDao = new PropiedadesDao();
    private List<Propiedades> propiedadesList = new ArrayList<Propiedades>();
    private CitasDao citasDao = new CitasDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_idPropiedad.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_tipoPropiedad.setCellValueFactory(new PropertyValueFactory<>("tipo_propiedad"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_Ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        iniciarTabla();
        desactivarForm(true);
        String[] status = {"Renta","Venta","Todo"};
        String[] tipo_prop = {"Casa","Negocio","Condominio","Todo"};
        String[] ciudadUbicada = {"León", "Guadalajara", "Querétaro", "Morelia", "Todo"};
        cb_filtro.setItems(FXCollections.observableArrayList(status));
        cb_filtroProp.setItems(FXCollections.observableArrayList(tipo_prop));
        cb_filtroCiudad.setItems(FXCollections.observableArrayList(ciudadUbicada));

        cb_filtro.valueProperty().addListener(event -> {
            if(cb_filtro.getSelectionModel().getSelectedItem().equals("Renta")){
                propiedadesList = propDao.filterPropByStatus("renta");
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));

            }else if(cb_filtro.getSelectionModel().getSelectedItem().equals("Venta")){
                propiedadesList = propDao.filterPropByStatus("venta");
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }else{
                propiedadesList = propDao.findAll();
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }
        });
        cb_filtroProp.valueProperty().addListener(event -> {
            if(cb_filtroProp.getSelectionModel().getSelectedItem().toString().equals("Casa")){
                propiedadesList = propDao.filterPropByTipoProp(1);
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }else if(cb_filtroProp.getSelectionModel().getSelectedItem().equals("Negocio")){
                propiedadesList = propDao.filterPropByTipoProp(2);
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }else if(cb_filtroProp.getSelectionModel().getSelectedItem().equals("Condominio")){
                propiedadesList = propDao.filterPropByTipoProp(3);
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }else{
                propiedadesList = propDao.findAll();
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }
        });
        cb_filtroCiudad.valueProperty().addListener(event -> {
            if(cb_filtroCiudad.getSelectionModel().getSelectedItem().toString().equals("León")){
                propiedadesList = propDao.filterPropByCiudad(1);
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }else if(cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Guadalajara")){
                propiedadesList = propDao.filterPropByCiudad(2);
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }else if (cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Querétaro")) {
                propiedadesList = propDao.filterPropByCiudad(3);
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            } else if (cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Morelia")) {
                propiedadesList = propDao.filterPropByCiudad(4);
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }else{
                propiedadesList = propDao.findAll();
                tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));
            }
        });

    }
    public void registeredUser(Usuario u) {
        this.usuarioIniciado = u;
        if (usuarioIniciado == null) { // Modo invitado
            gpFromCita.setVisible(false);
            btn_Cita.setVisible(false);
            btn_MostrarCitas.setVisible(false);
            System.out.println("Bienvenido al modo invitado...");
        } else { // Usuario normal
            System.out.println("Bienvenido: " + usuarioIniciado.getNombre());

        }
    }
    public void iniciarTabla(){
        propiedadesList = propDao.findAll();
        tv_Propiedades.setItems(FXCollections.observableList(propiedadesList));

        tv_Propiedades.setOnMouseClicked(mouseEvent ->{
            Propiedades p = (Propiedades) tv_Propiedades.getSelectionModel().getSelectedItem();
            switch(mouseEvent.getClickCount()) {
                case 1:
                    break;
                case 2:
                    if(tv_Propiedades.getSelectionModel().getSelectedItem() == null) {
                        System.out.println("Nada seleccionado");
                    }else{
                        onMostrarPropiedad(p);
                    }
            }
        });

    }

    private void desactivarForm(boolean activar) {
        tf_nombre.setDisable(activar);
        tf_correo.setDisable(activar);
        tf_telefono.setDisable(activar);
        dp_fecha_cita.setDisable(activar);
        tf_hora_cita.setDisable(activar);
        btn_Ingresar.setDisable(activar);
        btn_Cancelar.setDisable(activar);
    }

    @FXML
    private void onGenerarCita() {
        desactivarForm(false);
        tf_nombre.setText(usuarioIniciado.getNombre());
        tf_correo.setText(usuarioIniciado.getEmail());
        tf_telefono.setText(usuarioIniciado.getTelefono());

    }
    @FXML
    private void onCancelarCita(ActionEvent event) {
        desactivarForm(false);
        limpiarCampos();
    }
    @FXML
    private void onAgregarCita(ActionEvent event) {
        if(validarCampos()){
            System.out.println("Agregando datos...");
            Propiedades p = (Propiedades) tv_Propiedades.getSelectionModel().getSelectedItem();
            if(p == null){
                mostrarAlerta(Alert.AlertType.ERROR,
                        "Error",
                        "Error al cargar la cita.",
                        "Necesita seleccionar una propiedad para hacer una cita.");
            }else{
                agregarCita(p.getId_propiedad());
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Cita realizada",
                        "La cita ha sido registrada con exito",
                        "Nos pondremos en contacto contigo más tarde");
            }
            limpiarCampos();
            desactivarForm(true);
        }
    }
    private boolean validarCampos() {
        boolean validacion = true;
        if (tf_telefono.getText().isEmpty() || dp_fecha_cita.getValue() == null || tf_hora_cita.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos incompletos", null, "Complete todos los campos.");
            validacion = false;
        }
        return validacion;
    }
    public void agregarCita(int id_propiedad){
        int telefono = Integer.parseInt(tf_telefono.getText());
        LocalDate fecha = dp_fecha_cita.getValue();
        String hora = tf_hora_cita.getText();
        LocalTime horaCita = LocalTime.parse(hora);

        Datos_Cita datosCita = new Datos_Cita(usuarioIniciado.getNombre(), usuarioIniciado.getEmail(), telefono, Date.valueOf(fecha), Time.valueOf(horaCita), id_propiedad,usuarioIniciado.getId());
        citasDao.save(datosCita);
    }
    @FXML
    private void onMostrarCita(ActionEvent ae) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/userViews/vw_citas.fxml"));
            Parent root = loader.load();
            CitasController citaController = loader.getController();
            citaController.registeredUser(usuarioIniciado);

            Stage stage = new Stage();
            stage.setTitle("Contactos");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Para bloquear la ventana principal mientras esta está abierta
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
    public void limpiarCampos(){
        tf_nombre.clear();
        tf_correo.clear();
        tf_telefono.clear();
        dp_fecha_cita.setValue(null);
        tf_hora_cita.clear();
    }
    private void openWindow(ActionEvent ae, String file,String title){
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource(file));
            Stage stage1 = (Stage)((Node)ae.getSource()).getScene().getWindow();
            Scene scene1 = new Scene(root1);
            stage1.setTitle(title);
            stage1.setScene(scene1);
            stage1.centerOnScreen();
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean mostrarAlerta(Alert.AlertType alertType, String title, String header, String content) {
        // Crear la alerta de tipo ERROR
        Alert alerta = new Alert(alertType);
        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        Optional<ButtonType> result = alerta.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    public void logout(ActionEvent ae){
        if(mostrarAlerta(Alert.AlertType.INFORMATION,"Logout","Cerrando seseion...", "¿Seguro que desea cerrar sesión?")){
            usuarioIniciado=null;
            openWindow(ae,"/com/example/examentap/vw_login.fxml","Login");
        }
    }
    @FXML
    public void terminarApp(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}