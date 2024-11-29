package com.example.examentap.controllers;

import com.example.examentap.models.Propiedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class propiedadSeleccionada {
    @FXML
    private TextArea ta_All_Propiedades2;
    @FXML
    private ImageView iv_imagen2;

    @FXML
    public void mostrarDatos(Propiedades p){
        String imagePath = getClass().getResource("/com/example/examentap/images/" + p.getImagen()).toExternalForm();
        iv_imagen2.setImage(new Image(imagePath));
        ta_All_Propiedades2.setText(p.toString());
    }
    @FXML
    public void onClosePropiedadView(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
