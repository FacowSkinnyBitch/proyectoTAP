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
    //form
    @FXML
    private TextField tf_user;
    @FXML
    private PasswordField pf_pass;
    private static boolean banderita = false;

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static Navegacion nav = new Navegacion();

    private Stage stage;
    FXMLLoader loader;
    Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void comprobarUser(ActionEvent ae){
        String user = tf_user.getText();
        String pass = pf_pass.getText();

        for(Usuario u: usuarioDAO.findAll()){
            if(u.getUser().equals(user) && u.getContraseya().equals(pass)){
                if(u.getRole().equals("Admin")){
                    adminMenu(ae, u);
                    System.out.println(u.getUser()+" "+u.getRole());
                    banderita = true;
                }else{
                    userMenu(ae,u);
                    System.out.println(u.getUser()+" "+u.getRole());
                    banderita = true;
                }
            }
        }
        if(!banderita){
            mostrarAlerta(Alert.AlertType.ERROR,"Error de inicio de sesión",
                    "Datos incorrectos","Los datos no coiniden con un usuario registrado.");
        }
    }
    @FXML
    //regresar de escena
    private void onUsuarioInvitado(ActionEvent ae) {
        if(mostrarAlerta(Alert.AlertType.INFORMATION,"Modo Invitado","Entrando como invitado","¿Deseas iniciar como invitado?")){
            openWindow(ae,"userViews/vw_UserMenu.fxml","Modo Invitado");
        }
    }
    private void userMenu(ActionEvent ae, Usuario user) {
        //openWindow(ae,"userViews/vw_UserMenu.fxml","Modo Usuario");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userViews/vw_UserMenu.fxml"));
            Parent root = loader.load();
            UserMenu_Controller userController = loader.getController();
            userController.registeredUser(user);

            Stage stage = (Stage)((Node)ae.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Menú Usuario");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private void adminMenu(ActionEvent ae, Usuario user) {
        //nav.openWindow(ae,"adminViews/vw_modoAdmin.fxml","Modo Administrador");
        //openWindow(ae,"adminViews/vw_modoAdmin.fxml","Modo Administrador");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminViews/vw_modoAdmin.fxml"));
            Parent root = loader.load();
            AdminController adminController = loader.getController();
            adminController.registeredUser(user.getUser());

            Stage stage = (Stage)((Node)ae.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu Administrador");
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

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

    @FXML
    private void onSignin(ActionEvent ae) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("vw_signin.fxml"));
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,350,600);
            stage.setTitle("Signin");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
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
    public void terminarApp(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}