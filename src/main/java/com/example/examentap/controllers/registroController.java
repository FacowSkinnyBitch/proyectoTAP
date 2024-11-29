package com.example.examentap.controllers;

import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class registroController {
    @FXML
    private TextField tf_user,tf_nombre,tf_primerA,tf_segundoA,tf_correo,tf_pass,tf_confirmPass,tf_telefono,tf_direccion;
    @FXML
    private RadioButton rb_women,rb_man,rb_other;
    @FXML
    private DatePicker fechaN;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    @FXML
    private void onRegistrarse(ActionEvent ae) throws IOException {
        Usuario newUser = new Usuario();

        String user = tf_user.getText();
        String nombre = tf_nombre.getText();
        String primerA = tf_primerA.getText();
        String segundoA = tf_segundoA.getText();
        String correo = tf_correo.getText();
        String pass = tf_pass.getText();
        String confirmPass = tf_confirmPass.getText();
        String telefono = tf_telefono.getText();
        String direccion = tf_direccion.getText();
        LocalDate fechaNac = fechaN.getValue();
        // newTask.setDueDate(Date.valueOf(dueDate));
        newUser.setUser(user);
        newUser.setNombre(nombre);
        newUser.setPrimer_apellido(primerA);
        newUser.setSegundo_apellido(segundoA);
        newUser.setEmail(correo);
        newUser.setContraseya(pass);
        newUser.setTelefono(telefono);
        newUser.setDireccion(direccion);
        newUser.setGenero(rb_man.getText());
        newUser.setNacimiento(Date.valueOf(fechaNac));
        newUser.setRole("User");
        usuarioDAO.save(newUser);
        if(mostrarAlerta(Alert.AlertType.INFORMATION,"Fabuloso","Usuario registrado con exito!","Registrando...")){
            returnLogin(ae);
        }
    }
    @FXML
    private void returnLogin(ActionEvent ae) throws IOException {
        try {
            Stage currentStage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/examentap/vw_login.fxml"));  // Ruta absoluta sugerida
            Stage stage = new Stage();
            Scene scene = new Scene(root,400,400);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            //Stage stage1 = (Stage)((Node)ae.getSource()).getScene().getWindow();
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

    private void clearFields(ActionEvent ae){
        tf_user.clear();
        tf_nombre.clear();
        tf_primerA.clear();
        tf_segundoA.clear();
        tf_correo.clear();
        tf_pass.clear();
        tf_confirmPass.clear();
        tf_telefono.clear();
        tf_direccion.clear();
        rb_women.setSelected(false);
        rb_man.setSelected(false);
        rb_other.setSelected(false);
        fechaN.setValue(null);
    }
}
