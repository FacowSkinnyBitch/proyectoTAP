package com.example.examentap;

import com.example.examentap.controllers.AdminController;
import com.example.examentap.controllers.UserMenu_Controller;
import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Navegacion;
import com.example.examentap.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LoginController implements Initializable {
    @FXML
    private TextField tf_user;
    @FXML
    private PasswordField pf_pass;

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    @FXML
    private void comprobarUser(ActionEvent ae){
        boolean encontrado = false;

        String user = tf_user.getText();
        String pass = pf_pass.getText();

        for (Usuario u : usuarioDAO.findAll()) {
            if (u.getUser().equals(user) && u.getContraseya().equals(pass)) {
                encontrado = true;
                if (u.getRole().equals("Admin")) {
                    abrirControlador(ae, "adminViews/vw_modoAdmin.fxml", "Modo Administrador", u);
                } else if (u.getRole().equals("User")){
                    abrirControlador(ae, "userViews/vw_UserMenu.fxml", "Modo Usuario", u);
                }
                break;
            }
        }
        if(!encontrado){
            mostrarAlerta(Alert.AlertType.ERROR,"Error de inicio de sesión",
                    "Datos incorrectos","Los datos no coiniden con un usuario registrado.");
            tf_user.clear();
            pf_pass.clear();
        }
    }
    @FXML
    private void onSignup(ActionEvent ae) {
        abrirVentana(ae, "vw_signup.fxml", "Registrar Usuario");
    }

    private void abrirVentana(ActionEvent ae, String rutaFXML, String titulo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setWidth(500);
            stage.setHeight(710);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void iniciarModoInvitado(ActionEvent ae) {
        if (mostrarAlerta(Alert.AlertType.CONFIRMATION, "Modo Invitado", null, "¿Deseas iniciar como invitado?")) {
            abrirControlador(ae, "userViews/vw_UserMenu.fxml", "Modo Invitado",null);
        }
    }

    // Metodo para abrir una ventana y pasar datos al controlador.
    private void abrirControlador(ActionEvent ae, String rutaFXML, String titulo, Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            if (rutaFXML.contains("adminViews")) {
                AdminController adminController = loader.getController();
                adminController.registeredUser(usuario);
            } else if (rutaFXML.contains("userViews")) {
                UserMenu_Controller userController = loader.getController();
                userController.registeredUser(usuario);
            }

            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    @FXML
    public void terminarApp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}