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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarComboBox();
        cbIdUser.valueProperty().addListener((event) -> {
            Usuario us = (Usuario) cbIdUser.getValue();
            for(Usuario u: usuarioList){
                if(us.getId() == u.getId()) {
                    //si se presiona el combo box de los usuarios
                    tfNombre.setText(dtc.getNombre_completo());
                    tfCorreo.setText(dtc.getCorreo());
                    tfTelefono.setText(dtc.getTelefono() + "");
                    //dpFecha.setValue(dtc.getFecha_cita().toLocalDate());
                    tfHora.setText(dtc.getHora_cita() + "");
                }
            }
            //System.out.println(cb_categorias.getSelectionModel().getSelectedItem());
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
            cbIdProp.setDisable(true);
            cargarDatos();

        }else if(modo == 3){
            //modo 3 es para crear una cita nueva
            lbTitle.setText("Nueva Cita");
            btn_aceptar.setText("Guardar");
            btn_close.setText("Cancelar");
        }
    }

    private void cargarDatos(){
        tfID.setText(dtc.getId_cita()+"");
        tfNombre.setText(dtc.getNombre_completo()+"");
        //cbIdUser.setText(dtc.getId_cita()+"");
        tfCorreo.setText(dtc.getCorreo()+"");
        tfTelefono.setText(dtc.getTelefono()+"");
        //LocalDate date = dtc.getFecha_cita().toLocalDate();
        //dpFecha.setValue(date) ;
        tfHora.setText(dtc.getHora_cita()+"");
        //cbIdProp.setText(dtc.getId_propiedad()+"");
        tfStatus.setText(dtc.getStatus()+"");


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
    private void onAceptar(){

    }

    @FXML
    public void onCloseForm(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
