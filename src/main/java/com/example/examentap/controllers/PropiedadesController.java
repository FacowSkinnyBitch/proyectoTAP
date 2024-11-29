package com.example.examentap.controllers;

import com.example.examentap.databases.dao.PropiedadesDao;
import com.example.examentap.models.Propiedades;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PropiedadesController implements Initializable {
    @FXML private TableView<Propiedades> propiedadesTable;
    @FXML private TableColumn<Propiedades, Integer> tv_id;
    @FXML private TableColumn<Propiedades, String> tv_direccion;
    @FXML private TableColumn<Propiedades, Double> tv_precio;
    @FXML private TableColumn<Propiedades, Integer> tv_tipoProp;
    @FXML private TableColumn<Propiedades, Boolean> tv_status;

    private PropiedadesDao propDao = new PropiedadesDao();
    private List<Propiedades> propiedadesList = new ArrayList<Propiedades>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
    }
    private void initTable() {
        tv_id.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        tv_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tv_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tv_tipoProp.setCellValueFactory(new PropertyValueFactory<>("tipo_propiedad"));
        tv_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        propiedadesList = propDao.findAll();
        propiedadesTable.setItems(FXCollections.observableList(propiedadesList));

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

}
