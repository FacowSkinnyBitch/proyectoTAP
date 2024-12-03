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
import javafx.geometry.Insets;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    @FXML
    private ComboBox<String> cb_filtro, cb_filtroProp, cb_filtroCiudad;

    @FXML
    private VBox vboxPropiedades;

    private Usuario usuarioIniciado;

    private PropiedadesDao propDao = new PropiedadesDao();
    private List<Propiedades> propiedadesList = new ArrayList<Propiedades>();
    private CitasDao citasDao = new CitasDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Inicializar los filtros con opciones
        String[] status = {"Renta", "Venta", "Todo"};
        String[] tipoProp = {"Casa", "Negocio", "Condominio", "Todo"};
        String[] ciudadUbicada = {"León", "Guadalajara", "Querétaro", "Morelia", "Todo"};

        cb_filtro.setItems(FXCollections.observableArrayList(status));
        cb_filtroProp.setItems(FXCollections.observableArrayList(tipoProp));
        cb_filtroCiudad.setItems(FXCollections.observableArrayList(ciudadUbicada));

        // Cargar todas las propiedades al inicio
        propiedadesList = propDao.findAll();
        cargarPropiedades(propiedadesList);

        // Desactivar el formulario hasta que se presione el botón "Generar Cita"
        desactivarForm(true);
        limpiarCampos();

        // Configurar listeners para los filtros
        cb_filtro.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        cb_filtroProp.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        cb_filtroCiudad.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        // Configuración para usuarios invitados o registrados
        if (usuarioIniciado == null) { // Modo invitado
            gpFromCita.setVisible(false); // Ocultar formulario de citas
            btn_Cita.setVisible(false);   // Ocultar botón de generar cita
            btn_MostrarCitas.setVisible(false); // Ocultar botón de mostrar citas
            System.out.println("Bienvenido al modo invitado...");
        } else {
            gpFromCita.setVisible(true);
            btn_Cita.setVisible(true);
            btn_MostrarCitas.setVisible(true);// Usuario registrado
            System.out.println("Bienvenido: " + usuarioIniciado.getNombre());
        }
    }

    public void registeredUser(Usuario u) {
        this.usuarioIniciado = u;

        if (usuarioIniciado == null) { // Modo invitado
            gpFromCita.setVisible(false);         // Oculta formulario de citas
            btn_Cita.setVisible(false);           // Oculta botón de generar cita
            btn_MostrarCitas.setVisible(false);   // Oculta botón de mostrar citas
            System.out.println("Bienvenido al modo invitado...");
        } else { // Usuario registrado
            gpFromCita.setVisible(true);          // Muestra formulario de citas
            btn_Cita.setVisible(true);            // Muestra botón de generar cita
            btn_MostrarCitas.setVisible(true);    // Muestra botón de mostrar citas
            System.out.println("Bienvenido: " + usuarioIniciado.getNombre());
        }

        // Asegúrate de limpiar los campos al inicio
        limpiarCampos();
        desactivarForm(true); // Desactiva el formulario de citas por defecto
    }




    private void desactivarForm(boolean activar) {
        tf_nombre.setDisable(activar);
        tf_correo.setDisable(activar);
        tf_telefono.setDisable(activar);
        dp_fecha_cita.setDisable(activar);
        tf_hora_cita.setDisable(activar);
        btn_Ingresar.setDisable(activar);
        btn_Cancelar.setDisable(activar);

        if (activar) {
            limpiarCampos(); // Limpia los campos si el formulario está desactivado
        }
    }

    @FXML
    private void onGenerarCita() {
        // Habilitar formulario
        desactivarForm(false);

        // Rellenar automáticamente los campos con los datos del usuario (si está registrado)
        if (usuarioIniciado != null) {
            tf_nombre.setText(usuarioIniciado.getNombre());
            tf_correo.setText(usuarioIniciado.getEmail());
            tf_telefono.setText(usuarioIniciado.getTelefono());
        }

        // Cambiar el estilo de las tarjetas para que se activen visualmente
        for (Node tarjeta : vboxPropiedades.getChildren()) {
            tarjeta.getStyleClass().remove("inactive-card");
            tarjeta.getStyleClass().add("active-card");
        }
    }

    @FXML
    private void onCancelarCita(ActionEvent event) {
        desactivarForm(false);
        limpiarCampos();
    }
    @FXML
    private void onAgregarCita(ActionEvent event) {
        if (propiedadSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "Error al cargar la cita.",
                    "Necesita seleccionar una propiedad para hacer una cita.");
            return;
        }

        if (validarCampos()) {
            System.out.println("Agregando datos...");
            agregarCita(propiedadSeleccionada.getId_propiedad());
            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Cita realizada",
                    "La cita ha sido registrada con éxito",
                    "Nos pondremos en contacto contigo más tarde");
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

    private void onMostrarPropiedad(Propiedades p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/userViews/vw_propSelected.fxml"));
            Parent root = loader.load();
            propiedadSeleccionada ps = loader.getController();
            ps.mostrarDatos(p);

            Stage stage = new Stage();
            stage.setTitle("Propiedad Seleccionada");
            Scene scene = new Scene(root);
            stage.setWidth(760);
            stage.setHeight(610);
            scene.getStylesheets().add(getClass().getResource("/com/example/examentap/cssFiles/propselected.css").toExternalForm());
            stage.setScene(scene);
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
        Alert alerta = new Alert(alertType);
        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(content);

        // Aplicar estilo CSS al DialogPane
        try {
            alerta.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/example/examentap/cssFiles/alerta.css").toExternalForm()
            );
            alerta.getDialogPane().getStyleClass().add("dialog-pane");
        } catch (NullPointerException e) {
            System.err.println("Error: No se encontró el archivo CSS en /cssFiles/alerta.css");
        }

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    public void logout(ActionEvent ae){
        if(mostrarAlerta(Alert.AlertType.INFORMATION,"Logout","Cerrando sesion...", "¿Seguro que desea cerrar sesión?")){
            usuarioIniciado=null;
            openWindow(ae,"/com/example/examentap/vw_login.fxml","Login");
        }
    }
    @FXML
    public void terminarApp(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void aplicarFiltros() {
        String filtroStatus = cb_filtro.getValue();
        String filtroTipo = cb_filtroProp.getValue();
        String filtroCiudad = cb_filtroCiudad.getValue();

        propiedadesList = propDao.findAll(); // Obtener todas las propiedades como base inicial

        // Aplicar filtro por estado (Renta/Venta)
        if (filtroStatus != null && !filtroStatus.equals("Todo")) {
            propiedadesList = propDao.filterPropByStatus(filtroStatus.toLowerCase());
        }

        // Aplicar filtro por tipo de propiedad
        if (filtroTipo != null && !filtroTipo.equals("Todo")) {
            int tipoPropiedad = filtroTipo.equals("Casa") ? 1 : filtroTipo.equals("Negocio") ? 2 : 3;
            propiedadesList = propDao.filterPropByTipoProp(tipoPropiedad);
        }

        // Aplicar filtro por ciudad
        if (filtroCiudad != null && !filtroCiudad.equals("Todo")) {
            int ciudad = filtroCiudad.equals("León") ? 1 : filtroCiudad.equals("Guadalajara") ? 2 : filtroCiudad.equals("Querétaro") ? 3 : 4;
            propiedadesList = propDao.filterPropByCiudad(ciudad);
        }

        // Cargar las propiedades filtradas
        cargarPropiedades(propiedadesList);
    }

    private Propiedades propiedadSeleccionada; // Propiedad seleccionada

    public void cargarPropiedades(List<Propiedades> propiedades) {
        vboxPropiedades.getChildren().clear(); // Limpia las tarjetas existentes

        for (Propiedades propiedad : propiedades) {
            VBox tarjeta = new VBox();
            tarjeta.setSpacing(10);
            tarjeta.setPadding(new Insets(10));
            tarjeta.setStyle("-fx-background-color: #2c2c2c; -fx-border-radius: 10px;"); // Estilo base
            tarjeta.getStyleClass().add("inactive-card"); // Clase CSS inactiva por defecto

            Label lblIdTitulo = new Label("ID: " + propiedad.getId_propiedad());
            lblIdTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            Label lblDescripcion = new Label("Descripción: " + propiedad.getDescripcion());
            lblDescripcion.setStyle("-fx-text-fill: #cccccc;");

            Label lblCiudad = new Label("Ciudad: " + propiedad.getCiudad());
            lblCiudad.setStyle("-fx-text-fill: #cccccc;");

            Label lblTipo = new Label("Tipo: " + propiedad.getTipo_propiedad());
            lblTipo.setStyle("-fx-text-fill: #cccccc;");

            Label lblStatus = new Label("Estatus: " + propiedad.getStatus());
            lblStatus.setStyle("-fx-text-fill: #cccccc;");

            // Imagen de la propiedad
            ImageView ivImagen = new ImageView(new Image(getClass().getResource("/com/example/examentap/images/" + propiedad.getImagen()).toExternalForm()));
            ivImagen.setFitHeight(200);
            ivImagen.setFitWidth(300);
            ivImagen.setPreserveRatio(true);

            // Agregar los elementos a la tarjeta
            tarjeta.getChildren().addAll(ivImagen, lblIdTitulo, lblDescripcion, lblCiudad, lblTipo, lblStatus);

            // Evento al hacer clic en la tarjeta
            // Evento al hacer clic en la tarjeta
            tarjeta.setOnMouseClicked(event -> {
                propiedadSeleccionada = propiedad; // Actualiza la propiedad seleccionada
                System.out.println("Propiedad seleccionada: " + propiedadSeleccionada.getId_propiedad());

                // Cambiar el estilo para mostrar que está seleccionada
                for (Node node : vboxPropiedades.getChildren()) {
                    node.getStyleClass().remove("selected-card"); // Remueve estilo seleccionado
                    node.getStyleClass().add("inactive-card");   // Agrega estilo inactivo
                }
                tarjeta.getStyleClass().remove("inactive-card");
                tarjeta.getStyleClass().add("selected-card"); // Marca la tarjeta seleccionada

                // Mostrar la vista de la propiedad seleccionada
                onMostrarPropiedad(propiedad); // Llama al método para abrir la vista
            });


            // Agregar la tarjeta al contenedor
            vboxPropiedades.getChildren().add(tarjeta);
        }
    }





}