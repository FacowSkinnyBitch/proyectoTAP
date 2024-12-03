package com.example.examentap.controllers;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.databases.dao.PropiedadesDao;
import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Datos_Cita;
import com.example.examentap.models.Propiedades;
import com.example.examentap.models.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CrudCitasController implements Initializable {
    Datos_Cita dtc = new Datos_Cita();
    UsuarioDAO userDao = new UsuarioDAO();
    PropiedadesDao propDao = new PropiedadesDao();
    private List<Usuario> usuarioList = new ArrayList<>();
    private List<Propiedades> propiedadesList = new ArrayList<>();

    private int modo;

    @FXML private Label lbTitle;
    @FXML private TextField tfID,tfNombre,tfCorreo,tfTelefono,tfHora,tfStatus;
    @FXML private ComboBox cbIdUser,cbIdProp;
    @FXML private DatePicker dpFecha;
    @FXML private Button btn_aceptar, btn_close;
    @FXML private RadioButton rbProgramada, rbConfirmada;
    private int idUserSeleccionado;
    private int idPropSeleccionada;
    private String statusCita;
    CitasDao citasDao = new CitasDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarComboBox();
        cbIdUser.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedUserId = Integer.parseInt(newValue.toString());
                for (Usuario u : usuarioList) {
                    if (u.getId() == selectedUserId) {
                        tfNombre.setText(u.getNombre() + " " + u.getPrimer_apellido() + " " + u.getSegundo_apellido());
                        tfCorreo.setText(u.getEmail());
                        tfTelefono.setText(u.getTelefono());
                        idUserSeleccionado = selectedUserId;
                        break;
                    }
                }
            }
        });
        cbIdProp.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedProp = Integer.parseInt(newValue.toString());
                for (Propiedades p : propiedadesList) {
                    if (p.getId_propiedad() ==selectedProp) {
                        idPropSeleccionada = selectedProp;
                        break;
                    }
                }
            }
        });

    }
    public void citaSeleccionada(Datos_Cita dtc, int modo){
        this.dtc = dtc;
        this.modo = modo;
        if (modo == 1){
            lbTitle.setText("Datos de la Cita seleccionada");
            btn_aceptar.setVisible(false);
            btn_close.setText("Regresar");
            cbIdUser.setDisable(true);
            cbIdProp.setDisable(true);
            cargarDatos();
        }else if(modo == 2){
            lbTitle.setText("Modo edición");
            btn_aceptar.setText("Guardar Cambios");
            btn_close.setText("Cancelar");
            cbIdUser.setDisable(true);
            cargarDatos();

        }else if(modo == 3){
            //modo 3 es para crear una cita nueva
            tfID.setDisable(true);
            lbTitle.setText("Nueva Cita");
            btn_aceptar.setText("Guardar");
            btn_close.setText("Cancelar");
        }
    }

    private void cargarDatos() {
        tfID.setText(String.valueOf(dtc.getId_cita()));
        tfNombre.setText(dtc.getNombre_completo());
        tfCorreo.setText(dtc.getCorreo());
        tfTelefono.setText(String.valueOf(dtc.getTelefono()));
        dpFecha.setValue(dtc.getFecha_cita().toLocalDate());
        tfHora.setText(dtc.getHora_cita().toString());

        for (Usuario u : usuarioList) {
            if (u.getId() == dtc.getId_usuario()) {
                cbIdUser.setValue(String.valueOf(u.getId()));
                break;
            }
        }

        for (Propiedades p : propiedadesList) {
            if (p.getId_propiedad() == dtc.getId_propiedad()) {
                cbIdProp.setValue(String.valueOf(p.getId_propiedad()));
                break;
            }
        }
        if ("programada".equals(dtc.getStatus())) {
            rbProgramada.setSelected(true);
        } else {
            rbConfirmada.setSelected(true);
        }
    }


    private void cargarComboBox(){
        usuarioList = userDao.findAll();
        List<String> usuario = new ArrayList<>();
        for(Usuario u: usuarioList){
            usuario.add(u.getId()+"");
        }
        cbIdUser.setItems(FXCollections.observableArrayList(usuario));

        propiedadesList = propDao.findAll();
        List<String> prop = new ArrayList<>();
        for(Propiedades p: propiedadesList){
            prop.add(p.getId_propiedad()+"");
        }
        cbIdProp.setItems(FXCollections.observableArrayList(prop));
    }
    @FXML
    private void selectStatus(){
        rbConfirmada.setOnAction( event -> {
            if(rbConfirmada.isSelected()){
                rbProgramada.setSelected(false);
                statusCita = "confirmada";
            }
        });
        rbProgramada.setOnAction( event ->  {
            if(rbProgramada.isSelected()){
                rbConfirmada.setSelected(false);
                statusCita = "programada";
            }
        });
    }


    @FXML
    private void onAceptar(ActionEvent event) {
        try {
            Date fecha = Date.valueOf(dpFecha.getValue());
            String horaString = tfHora.getText();
            Time hora = Time.valueOf(horaString);

            Datos_Cita dc = new Datos_Cita();
            dc.setNombre_completo(tfNombre.getText());
            dc.setId_usuario(idUserSeleccionado);
            dc.setCorreo(tfCorreo.getText());
            dc.setTelefono(Integer.parseInt(tfTelefono.getText()));
            dc.setFecha_cita(fecha);
            dc.setHora_cita(hora);
            dc.setId_propiedad(idPropSeleccionada);
            dc.setStatus(statusCita);

            if (modo == 2) { // Modo edición
                dc.setId_cita(Integer.parseInt(tfID.getText()));
                if (mostrarAlerta(Alert.AlertType.CONFIRMATION, "Confirmar", "Guardar Cambios", "¿Desea actualizar esta cita?")) {
                    try {
                        citasDao.update(dc);
                        System.out.println("Cita actualizada con éxito");
                        onCloseForm(event);
                    } catch (Exception e) {
                        System.out.println("Ocurrió un error al actualizar la cita");
                        e.printStackTrace();
                    }
                }
            } else if (modo == 3) { // Modo creación
                if (mostrarAlerta(Alert.AlertType.CONFIRMATION, "Confirmar", "Guardar Cita", "¿Desea guardar esta cita?")) {
                    try {
                        citasDao.save(dc);
                        System.out.println("Cita creada con éxito");
                        onCloseForm(event);
                    } catch (Exception e) {
                        System.out.println("Ocurrió un error al crear la cita");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al procesar la fecha u hora");
            e.printStackTrace();
        }
    }

    private boolean mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        // Crear la alerta
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);

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
    @FXML
    public void onCloseForm(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
