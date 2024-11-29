package com.example.examentap.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    @FXML private BorderPane bpPrincipal;

    @FXML
    private void onReturn(ActionEvent event) throws IOException {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/examentap/vw_login.fxml"));  // Ruta absoluta sugerida
            Stage stage = new Stage();
            Scene scene = new Scene(root,350,400);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            //Stage stage1 = (Stage)((Node)ae.getSource()).getScene().getWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showPropiedades(ActionEvent event) {
        genericOpenMetod("Abriendo vista Propiedades","vw_propiedades.fxml");
    }
        @FXML
    private void onUsersView(ActionEvent event) throws IOException {
        genericOpenMetod("Abriendo vista Usuarios","vw_Users.fxml");
    }
    @FXML
    private void onCitasView(ActionEvent event) throws IOException {
        genericOpenMetod("Abriendo vista Citas","adminViews/vw_citasAdmin.fxml");
        /*try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/examentap/adminViews/vw_citasAdmin.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root,900,550);
            stage.setTitle("Citas");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    public void registeredUser(String username){
        System.out.println("Hello " + username);
    }
    private void genericOpenMetod(String msg,String file){
        System.out.println(msg);
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage(file);
        bpPrincipal.setCenter(view);
    }
}
