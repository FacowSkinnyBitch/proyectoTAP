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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsuarioAdminController implements Initializable {
    @FXML
    private TableView tvUsuarios;
    @FXML
    private TableColumn<Usuario,Integer> tvid;
    @FXML
    private TableColumn<Usuario,String> tvusuario,tvnombre,tvprimer_apellido,tvsegundo_apellido,
        tvemail,tvcontraseya,tvdireccion,tvgenero, tvnacimiento,tvrole;
    @FXML
    private Button btn_crearNuevoUsuario;



    ContextMenu contextMenu = new ContextMenu();
    MenuItem menuItemSelectUser = new MenuItem("Info User");
    MenuItem menuItemDeleteUser = new MenuItem("Delete");
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

            }
        });
        menuItemSelectUser.setOnAction(event -> {
            onReadUser();
            //usuarioDAO.update(tvUsuarios.getSelectionModel().getSelectedItem().getId(), true);
        });
        menuItemUpdate.setOnAction(event -> {
            onUpdateUser();
        });
        btn_crearNuevoUsuario.setOnAction(event -> {
            onCreateUser();
        });

        tvUsuarios.setContextMenu(contextMenu);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTablaUsuario();
        metodoCRUD();
        initContextMenu();
        initMetodosCrud();
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

    private void initMetodosCrud(){
        menuItemDeleteUser.setOnAction(event -> {
            Usuario usuarioSeleccionado = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            if(usuarioSeleccionado != null){
                if(confirmDelete()){
                    System.out.println("id deleted: "+usuarioSeleccionado.getId());
                    onDeleteUser();
                    //reloadAll();
                    //borrar los usuarios que se encuentren en la tabla de datos_cita
                }
            }
        });
    }
    private boolean confirmDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this user?");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }


    @FXML
    private void onCreateUser() {
        // Crear un nuevo objeto Usuario vacío
        Usuario nuevoUsuario = new Usuario();

        // Crear un diálogo de entrada para capturar los datos del usuario
        Dialog<Usuario> dialog = crearDialogoCreacion(nuevoUsuario);
        Optional<Usuario> resultado = dialog.showAndWait();

        resultado.ifPresent(usuarioCreado -> {
            if (usuarioDAO.save(usuarioCreado)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario creado correctamente.", ButtonType.OK);
                alert.showAndWait();
                cargarTablaUsuario();  // Recargar la tabla de usuarios
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo crear el usuario.", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }


    @FXML
    private void onReadUser() {
        Usuario usuarioSeleccionado = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detalles del Usuario");
            alert.setHeaderText("Información detallada del usuario");
            alert.setContentText(
                    "ID: " + usuarioSeleccionado.getId() + "\n" +
                            "Usuario: " + usuarioSeleccionado.getUser() + "\n" +
                            "Nombre: " + usuarioSeleccionado.getNombre() + " " + usuarioSeleccionado.getPrimer_apellido() + " " + usuarioSeleccionado.getSegundo_apellido() + "\n" +
                            "Email: " + usuarioSeleccionado.getEmail() + "\n" +
                            "Contraseña: " + usuarioSeleccionado.getContraseya() + "\n" +
                            "Telefono: " + usuarioSeleccionado.getTelefono() + "\n" +
                            "Genero: " + usuarioSeleccionado.getGenero() + "\n" +
                            "Fecha de Nacimiento: " + usuarioSeleccionado.getNacimiento() + "\n" +
                            "Rol: " + usuarioSeleccionado.getRole() + "\n"
            );
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un usuario.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void onUpdateUser() {
        Usuario usuarioSeleccionado = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            Dialog<Usuario> dialog = crearDialogoEdicion(usuarioSeleccionado);
            Optional<Usuario> resultado = dialog.showAndWait();

            resultado.ifPresent(usuarioEditado -> {
                if (usuarioDAO.update(usuarioEditado)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario actualizado correctamente.", ButtonType.OK);
                    alert.showAndWait();
                    cargarTablaUsuario();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar el usuario.", ButtonType.OK);
                    alert.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un usuario para actualizar.", ButtonType.OK);
            alert.showAndWait();
        }
    }


    @FXML
    private void onDeleteUser() {
        Usuario usuarioSeleccionado = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            if (confirmDelete()) {
                if (usuarioDAO.delete(usuarioSeleccionado.getId())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario eliminado correctamente.", ButtonType.OK);
                    alert.showAndWait();
                    cargarTablaUsuario();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar el usuario.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un usuario para eliminar.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Dialog<Usuario> crearDialogoEdicion(Usuario usuario) {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Editar Usuario");

        // Cargar el FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/adminViews/vw_adminEditUser.fxml") );
        DialogPane dialogPane;
        try {
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retorna null en caso de error
        }

        // Obtener campos del controlador
        TextField txtUser = (TextField) dialogPane.lookup("#txtUser");
        TextField txtNombre = (TextField) dialogPane.lookup("#txtNombre");
        TextField txtPrimerApellido = (TextField) dialogPane.lookup("#txtPrimerApellido");
        TextField txtSegundoApellido = (TextField) dialogPane.lookup("#txtSegundoApellido");
        TextField txtEmail = (TextField) dialogPane.lookup("#txtEmail");
        TextField txtContraseya = (TextField) dialogPane.lookup("#txtContraseya");
        TextField txtTelefono = (TextField) dialogPane.lookup("#txtTelefono");
        TextField txtDireccion = (TextField) dialogPane.lookup("#txtDireccion");
        TextField txtGenero = (TextField) dialogPane.lookup("#txtGenero");
        DatePicker dpNacimiento = (DatePicker) dialogPane.lookup("#dpNacimiento");
        TextField txtRole = (TextField) dialogPane.lookup("#txtRole");

        // Configurar valores iniciales
        txtUser.setText(usuario.getUser());
        txtNombre.setText(usuario.getNombre());
        txtPrimerApellido.setText(usuario.getPrimer_apellido());
        txtSegundoApellido.setText(usuario.getSegundo_apellido());
        txtEmail.setText(usuario.getEmail());
        txtContraseya.setText(usuario.getContraseya());
        txtTelefono.setText(usuario.getTelefono());
        txtDireccion.setText(usuario.getDireccion());
        txtGenero.setText(usuario.getGenero());
        dpNacimiento.setValue(usuario.getNacimiento().toLocalDate());
        txtRole.setText(usuario.getRole());

        dialog.setDialogPane(dialogPane);

        // Configurar botón Guardar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialogPane.getButtonTypes().get(0)) { // Botón Guardar
                usuario.setUser(txtUser.getText());
                usuario.setNombre(txtNombre.getText());
                usuario.setPrimer_apellido(txtPrimerApellido.getText());
                usuario.setSegundo_apellido(txtSegundoApellido.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setContraseya(txtContraseya.getText());
                usuario.setTelefono(txtTelefono.getText());
                usuario.setDireccion(txtDireccion.getText());
                usuario.setGenero(txtGenero.getText());
                usuario.setNacimiento(Date.valueOf(dpNacimiento.getValue()));
                usuario.setRole(txtRole.getText());
                return usuario;
            }
            return null;
        });

        return dialog;
    }


    private Dialog<Usuario> crearDialogoCreacion(Usuario usuario) {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Crear Nuevo Usuario");

        // Cargar el FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/adminViews/vw_adminCreateUser.fxml"));
        DialogPane dialogPane;
        try {
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Obtener campos del controlador
        TextField txtUser = (TextField) dialogPane.lookup("#txtUser");
        TextField txtNombre = (TextField) dialogPane.lookup("#txtNombre");
        TextField txtPrimerApellido = (TextField) dialogPane.lookup("#txtPrimerApellido");
        TextField txtSegundoApellido = (TextField) dialogPane.lookup("#txtSegundoApellido");
        TextField txtEmail = (TextField) dialogPane.lookup("#txtEmail");
        PasswordField txtContraseya = (PasswordField) dialogPane.lookup("#txtContraseya");
        TextField txtTelefono = (TextField) dialogPane.lookup("#txtTelefono");
        TextField txtDireccion = (TextField) dialogPane.lookup("#txtDireccion");
        TextField txtGenero = (TextField) dialogPane.lookup("#txtGenero");
        DatePicker dpNacimiento = (DatePicker) dialogPane.lookup("#dpNacimiento");
        TextField txtRole = (TextField) dialogPane.lookup("#txtRole");

        dialog.setDialogPane(dialogPane);

        // Configurar botón Guardar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialogPane.getButtonTypes().get(0)) { // Botón Guardar
                usuario.setUser(txtUser.getText());
                usuario.setNombre(txtNombre.getText());
                usuario.setPrimer_apellido(txtPrimerApellido.getText());
                usuario.setSegundo_apellido(txtSegundoApellido.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setContraseya(txtContraseya.getText());
                usuario.setTelefono(txtTelefono.getText());
                usuario.setDireccion(txtDireccion.getText());
                usuario.setGenero(txtGenero.getText());
                usuario.setNacimiento(Date.valueOf(dpNacimiento.getValue()));
                usuario.setRole(txtRole.getText());
                return usuario;
            }
            return null;
        });

        return dialog;
    }


}
