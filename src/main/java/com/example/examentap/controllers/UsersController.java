package com.example.examentap.controllers;

import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    @FXML
    private TableView tvUsuarios;
    @FXML
    private TableColumn<Usuario,Integer> tvid;
    @FXML
    private TableColumn<Usuario,String> tvusuario,tvnombre,tvprimer_apellido,tvsegundo_apellido,
        tvemail,tvcontraseya,tvdireccion,tvgenero, tvnacimiento,tvrole;


    ContextMenu contextMenu = new ContextMenu();
    MenuItem menuItemDeleteUser = new MenuItem("Delete");
    MenuItem menuItemSelectUser = new MenuItem("Select User");
    MenuItem menuItemUpdate = new MenuItem("Update");

    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
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

    private void metodoCRUD(){
        menuItemDeleteUser.setOnAction(event -> {
            if(confirmDelete()){
                //usuarioDAO.delete(tvUsuarios.getSelectionModel().getSelectedItem());
            }
        });
        menuItemSelectUser.setOnAction(event -> {
            //usuarioDAO.update(tvUsuarios.getSelectionModel().getSelectedItem().getId(), true);
        });

        tvUsuarios.setContextMenu(contextMenu);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTablaUsuario();
        metodoCRUD();
        initContextMenu();
    }
    private void initContextMenu(){
        FontIcon iconDelete = new FontIcon();
        iconDelete.setIconLiteral("anto-close-circle");
        iconDelete.setIconSize(20);
        iconDelete.setIconColor(Color.RED);
        menuItemSelectUser.setGraphic(iconDelete);


        FontIcon iconComplete = new FontIcon();
        iconComplete.setIconLiteral("anto-file-done");
        iconComplete.setIconSize(20);
        iconComplete.setIconColor(Color.GREEN);
        menuItemUpdate.setGraphic(iconComplete);

        FontIcon iconIncomplete = new FontIcon();
        iconIncomplete.setIconLiteral("antf-frown");
        iconIncomplete.setIconSize(20);
        iconIncomplete.setIconColor(Color.DARKMAGENTA);
        contextMenu.getItems().addAll(menuItemSelectUser,menuItemUpdate,menuItemDeleteUser);
        menuItemDeleteUser.setGraphic(iconIncomplete);
    }
    private void cargarTablaUsuario() {
        usuarioDAO.findAll();
        tvid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvusuario.setCellValueFactory(new PropertyValueFactory<>("user"));
        tvnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tvprimer_apellido.setCellValueFactory(new PropertyValueFactory<>("primer_apellido"));
        tvsegundo_apellido.setCellValueFactory(new PropertyValueFactory<>("segundo_apellido"));
        tvemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tvcontraseya.setCellValueFactory(new PropertyValueFactory<>("contraseya"));
        tvdireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tvgenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tvnacimiento.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        tvrole.setCellValueFactory(new PropertyValueFactory<>("role"));

        usuarioList = usuarioDAO.findAll();
        tvUsuarios.setItems(FXCollections.observableArrayList(usuarioList));

    }
    private boolean confirmDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this task?");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }
    private void onCreateUser(ActionEvent event) {
        //crear nuevo usuario
    }
    private void onReadUser(ActionEvent event) {
        //Mostrar en una nueva ventana la informacion detallada del usuario
    }
    private void onUpdateUser(ActionEvent event) {
        //modificar la informacion del uuario
    }
    private void onDeleteUser(ActionEvent event) {
        //eliminar usuario seleccionado
    }
}
