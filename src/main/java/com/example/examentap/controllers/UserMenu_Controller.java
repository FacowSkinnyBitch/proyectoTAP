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
    private TextArea ta_All_Propiedades;
    @FXML
    private TextArea ta_All_Propiedades2;
    @FXML
    private TextField tf_nombre,tf_correo,tf_telefono,tf_hora_cita;
    @FXML
    private DatePicker dp_fecha_cita;
    @FXML
    private Button btn_Ingresar, btn_Cancelar, btn_Cita, btn_MostrarCitas;
    @FXML
    private ImageView iv_imagen;
    @FXML
    private ImageView iv_imagen2;
    @FXML private GridPane gpFromCita;

    @FXML private ComboBox cb_filtro,cb_filtroProp;
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

    private boolean activador = false;

    private Usuario usuarioIniciado;

    private PropiedadesDao propDao = new PropiedadesDao();
    private List<Propiedades> propiedadesList = new ArrayList<Propiedades>();

    private CitasDao citasDao = new CitasDao();
    private List<Datos_Cita> datosCitaList = new ArrayList<Datos_Cita>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_idPropiedad.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_tipoPropiedad.setCellValueFactory(new PropertyValueFactory<>("tipo_propiedad"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        iniciarTabla();
        desactivarForm();
        String[] status = {"Renta","Venta","Todo"};
        String[] tipo_prop = {"Casa","Negocio","Condominio","Todo"};
        cb_filtro.setItems(FXCollections.observableArrayList(status));
        cb_filtroProp.setItems(FXCollections.observableArrayList(tipo_prop));

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

    }
    public void registeredUser(Usuario u){
        usuarioIniciado = u;
        if(usuarioIniciado == null){
            //desactivar boton y formulario
            gpFromCita.setVisible(false);
            btn_Cita.setVisible(false);
            btn_Cita.setVisible(false);
            btn_MostrarCitas.setVisible(false);
        }
        System.out.println("Hola " + u.getNombre() + "bienvenido :)");
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
                        // Cargar la imagen asociada a la propiedad
                        /*String imagePath = getClass().getResource("/com/example/examentap/images/" + p.getImagen()).toExternalForm();
                        iv_imagen2.setImage(new Image(imagePath));
                        ta_All_Propiedades2.setText(p.toString());*/
                        onMostrarPropiedad(p);
                    }
            }
        });

    }


    private void desactivarForm(){
        tf_nombre.setDisable(!activador);
        tf_correo.setDisable(!activador);
        tf_telefono.setDisable(!activador);
        dp_fecha_cita.setDisable(!activador);
        tf_hora_cita.setDisable(!activador);
        btn_Ingresar.setDisable(!activador);
        btn_Cancelar.setDisable(!activador);
    }

    @FXML
    private void onGenerarCita(ActionEvent event) {
        activador = true;
        desactivarForm();
        tf_nombre.setText(usuarioIniciado.getNombre());
        tf_nombre.setEditable(false);
        tf_correo.setText(usuarioIniciado.getEmail());
        tf_correo.setEditable(false);
        tf_telefono.setText(usuarioIniciado.getTelefono());
    }
    @FXML
    private void onCancelarCita(ActionEvent event) {
        activador = false;
        desactivarForm();
        limpiarCampos();
    }
    @FXML
    private void onAgregarCita(ActionEvent event) {
        activador = false;
        if(tf_telefono.getText().isEmpty() || dp_fecha_cita.getValue()==null || tf_hora_cita.getText().isEmpty()) {
            System.out.println("Faltan campos por llenar");
            mostrarAlerta();
        }else{
            System.out.println("Agregando datos...");
            if(agregarCita()){
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Cita realizada",
                        "La cita ha sido registrada con exito",
                        "Nos pondremos en contacto contigo más tarde");
            }
            else{
                mostrarAlerta(Alert.AlertType.ERROR,
                        "Cita fallida",
                        "La cita ha podido ser registrada con exito.",
                        "Lo sentimos, la cita no se pudo realizar.");
            }
            limpiarCampos();
            desactivarForm();
        }
    }
    private void mostrarAlerta() {
        // Crear la alerta de tipo ERROR
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error al agregar el contacto");
        alerta.setHeaderText("Datos incompletos");
        alerta.setContentText("Por favor, complete todos los campos antes de agregar la cita.");

        alerta.showAndWait(); // Mostrar la alerta y esperar a que el usuario la cierre
    }
    public boolean agregarCita(){
        String nombre = usuarioIniciado.getNombre();
        String correo = usuarioIniciado.getEmail();
        int telefono = Integer.parseInt(tf_telefono.getText());
        LocalDate fecha = dp_fecha_cita.getValue();
        String hora = tf_hora_cita.getText();
        LocalTime horaCita = LocalTime.parse(hora);
        Propiedades p = (Propiedades) tv_Propiedades.getSelectionModel().getSelectedItem();
        if(p == null){
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "Error al cargar la cita.",
                    "Necesita seleccionar una propiedad para hacer una cita.");
        }else{
            Datos_Cita datosCita = new Datos_Cita(nombre, correo, telefono, Date.valueOf(fecha), Time.valueOf(horaCita), p.getId_propiedad(),usuarioIniciado.getId());
            citasDao.save(datosCita);
        }
        return true;
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
    /*@FXML
    private void returnMenu(ActionEvent event) {
        try {
             Stage stage;
             FXMLLoader loader;
             Parent root;

            loader = new FXMLLoader(getClass().getResource("vw_login.fxml"));
            root = loader.load();

            stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Para bloquear la ventana principal mientras esta está abierta
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void limpiarCampos(){
        tf_nombre.setText("");
        tf_correo.setText("");
        tf_telefono.setText("");
        dp_fecha_cita.setValue(null);
        tf_hora_cita.setText(null);
    }
    private void openWindow(ActionEvent ae, String file,String title){
        try {
            /*loader = new FXMLLoader(getClass().getResource(file));
            root = loader.load();
            stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            //stage.initModality(Modality.APPLICATION_MODAL); // Para bloquear la ventana principal mientras esta está abierta
            stage.close(); // Para bloquear la ventana principal mientras esta está abierta
            stage.show();*/
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
        alerta.showAndWait(); // Mostrar la alerta y esperar a que el usuario la cierre
        Optional<ButtonType> result = alerta.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    public void logout(ActionEvent ae){
        if(mostrarAlerta(Alert.AlertType.INFORMATION,"Logout","Cerrando seseion...", "¿Seguro que desea cerrar sesión?")){
            usuarioIniciado=null;
            openWindow(ae,"/com/example/examentap/vw_login.fxml","Login");
        }
    }
}