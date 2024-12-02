package com.example.examentap.controllers;

import com.example.examentap.models.Propiedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class propiedadSeleccionada {

    // Etiquetas para mostrar los detalles de la propiedad
    @FXML
    private Label labelIdPropiedad, labelDireccion, labelPrecio, labelDescripcion,
            labelNumCuartos, labelNumBanos, labelMetrosCuadrados, labelTipoPropiedad,
            labelStatus, labelAyoConstruccion, labelCiudad;

    // Imagen para mostrar la foto de la propiedad
    @FXML
    private ImageView iv_imagen2;

    @FXML
    public void mostrarDatos(Propiedades p) {
        // Establecer la imagen
        String imagePath = getClass().getResource("/com/example/examentap/images/" + p.getImagen()).toExternalForm();
        iv_imagen2.setImage(new Image(imagePath));

        // Mostrar los datos en las etiquetas
        labelIdPropiedad.setText(String.valueOf(p.getId_propiedad()));
        labelDireccion.setText(p.getDireccion());
        labelPrecio.setText(String.valueOf(p.getPrecio()));
        labelDescripcion.setText(p.getDescripcion());
        labelNumCuartos.setText(String.valueOf(p.getNum_cuartos()));
        labelNumBanos.setText(String.valueOf(p.getNum_bayos()));
        labelMetrosCuadrados.setText(String.valueOf(p.getMetros_cuadrados()));
        labelTipoPropiedad.setText(p.getTipo_propiedad());
        labelStatus.setText(p.getStatus());
        labelAyoConstruccion.setText(String.valueOf(p.getAyo_construccion()));
        labelCiudad.setText(p.getCiudad());
    }

    @FXML
    public void onClosePropiedadView(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
