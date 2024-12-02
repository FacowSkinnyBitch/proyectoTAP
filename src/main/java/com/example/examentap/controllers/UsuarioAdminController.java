package com.example.examentap.controllers;

import com.example.examentap.databases.dao.UsuarioDAO;
import com.example.examentap.models.Usuario;
import com.example.examentap.reports.UsersExcelReports;
import com.example.examentap.reports.PropiedadesPDFReports;
import com.example.examentap.reports.UsersPDFReport;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.File;
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
    @FXML
    private Button btn_generarExcel;

    public static final String DEST2 = "results/pdf/Usuarios.pdf";
    public static final String DEST3 = "results/excel/Usuarios.xlsx";

    ContextMenu contextMenu = new ContextMenu();
    //MenuItem menuItemSelectUser = new MenuItem("Info User");
    MenuItem menuItemDeleteUser = new MenuItem("Delete");
    MenuItem menuItemUpdate = new MenuItem("Update");

    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTablaUsuario();
        initContextMenu();
        initMetodosCrud();

        tvUsuarios.setOnMouseClicked(mouseEvent ->{
            Usuario selectedUser = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            if (mouseEvent.getClickCount() == 2) {
                onReadUser(selectedUser);
            }
        });

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

    private void initContextMenu(){
        /*FontIcon iconDelete = new FontIcon();
        iconDelete.setIconLiteral("anto-close-circle");
        iconDelete.setIconSize(20);
        iconDelete.setIconColor(Color.RED);
        menuItemSelectUser.setGraphic(iconDelete);*/


        FontIcon iconComplete = new FontIcon();
        iconComplete.setIconLiteral("anto-file-done");
        iconComplete.setIconSize(20);
        iconComplete.setIconColor(Color.GREEN);
        menuItemUpdate.setGraphic(iconComplete);

        FontIcon iconIncomplete = new FontIcon();
        iconIncomplete.setIconLiteral("antf-frown");
        iconIncomplete.setIconSize(20);
        iconIncomplete.setIconColor(Color.DARKMAGENTA);
        contextMenu.getItems().addAll(menuItemUpdate,menuItemDeleteUser);
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
        Usuario selectedUser = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            if(selectedUser != null){
                if(confirmDelete()){
                    System.out.println("id deleted: "+selectedUser.getId());
                    onDeleteUser(selectedUser);
                    //reloadAll();
                    //borrar los usuarios que se encuentren en la tabla de datos_cita
                }
            }
        });

        menuItemUpdate.setOnAction(event -> {
            Usuario selectedUser = (Usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            onUpdateUser(selectedUser);
        });
        btn_crearNuevoUsuario.setOnAction(event -> {
            onCreateUser();
        });

        tvUsuarios.setContextMenu(contextMenu);
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
    private void onGenerarPdf() throws IOException {
        File file = new File(DEST2);
        file.getParentFile().mkdirs();
        new UsersPDFReport().createPdf(DEST2);

        showMessage("Report generated!");
        if(showMessage("PDF Usuarios")){
            openFile(DEST2);
        }
    }

    @FXML
    private void onGenerarExcel(){
        UsersExcelReports document = new UsersExcelReports();
        document.createExcel(DEST3,0);
        //showMessage("Report generated!");
        if(showMessage("Excel Usuarios")){
            openFile(DEST3);
        }
    }

    //metodo para abrir reportes pdf o excel
    private void openFile(String filename) {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    //metodo para mostrar mensajes
    private boolean showMessage(String message){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("PDF generated...");
        a.setContentText(message);
        Optional<ButtonType> result = a.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    @FXML
    private void onReadUser(Usuario selectedUser) {
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detalles del Usuario");
            alert.setHeaderText("Información detallada del usuario");
            alert.setContentText(
                    "ID: " + selectedUser.getId() + "\n" +
                            "Usuario: " + selectedUser.getUser() + "\n" +
                            "Nombre: " + selectedUser.getNombre() + " " + selectedUser.getPrimer_apellido() + " " + selectedUser.getSegundo_apellido() + "\n" +
                            "Email: " + selectedUser.getEmail() + "\n" +
                            "Contraseña: " + selectedUser.getContraseya() + "\n" +
                            "Telefono: " + selectedUser.getTelefono() + "\n" +
                            "Genero: " + selectedUser.getGenero() + "\n" +
                            "Fecha de Nacimiento: " + selectedUser.getNacimiento() + "\n" +
                            "Rol: " + selectedUser.getRole() + "\n"
            );
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un usuario.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void onUpdateUser(Usuario selectedUser) {

        if (selectedUser != null) {
            Dialog<Usuario> dialog = crearDialogoEdicion(selectedUser);
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
    private void onDeleteUser(Usuario selectedUser) {

        if (selectedUser != null) {

            if (usuarioDAO.delete(selectedUser.getId())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario eliminado correctamente.", ButtonType.OK);
                alert.showAndWait();
                cargarTablaUsuario();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar el usuario.", ButtonType.OK);
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un usuario para eliminar.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Dialog<Usuario> crearDialogoEdicion(Usuario usuario) {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Editar Usuario");
        dialog.setHeaderText("Actualiza los datos del usuario");

        // Botones de confirmación
        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        // Campos de edición
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtUser = new TextField(usuario.getUser());
        TextField txtNombre = new TextField(usuario.getNombre());
        TextField txtPrimerApellido = new TextField(usuario.getPrimer_apellido());
        TextField txtSegundoApellido = new TextField(usuario.getSegundo_apellido());
        TextField txtEmail = new TextField(usuario.getEmail());
        TextField txtContraseya = new TextField(usuario.getContraseya());
        TextField txtTelefono = new TextField(usuario.getTelefono());
        TextField txtDireccion = new TextField(usuario.getDireccion());
        TextField txtGenero = new TextField(usuario.getGenero());
        DatePicker dpNacimiento = new DatePicker(usuario.getNacimiento().toLocalDate());
        TextField txtRole = new TextField(usuario.getRole());

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(txtUser, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Primer Apellido:"), 0, 2);
        grid.add(txtPrimerApellido, 1, 2);
        grid.add(new Label("Segundo Apellido:"), 0, 3);
        grid.add(txtSegundoApellido, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(txtEmail, 1, 4);
        grid.add(new Label("Contraseña:"), 0, 5);
        grid.add(txtContraseya, 1, 5);
        grid.add(new Label("Teléfono:"), 0, 6);
        grid.add(txtTelefono, 1, 6);
        grid.add(new Label("Dirección:"), 0, 7);
        grid.add(txtDireccion, 1, 7);
        grid.add(new Label("Género:"), 0, 8);
        grid.add(txtGenero, 1, 8);
        grid.add(new Label("Nacimiento:"), 0, 9);
        grid.add(dpNacimiento, 1, 9);
        grid.add(new Label("Role:"), 0, 10);
        grid.add(txtRole, 1, 10);

        dialog.getDialogPane().setContent(grid);

        // Al hacer clic en Guardar, actualizar el objeto Usuario
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
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
        dialog.setHeaderText("Ingresa los datos del nuevo usuario");

        // Botones de confirmación
        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        // Campos de entrada
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtUser = new TextField();
        TextField txtNombre = new TextField();
        TextField txtPrimerApellido = new TextField();
        TextField txtSegundoApellido = new TextField();
        TextField txtEmail = new TextField();
        PasswordField txtContraseya = new PasswordField();
        TextField txtTelefono = new TextField();
        TextField txtDireccion = new TextField();
        TextField txtGenero = new TextField();
        DatePicker dpNacimiento = new DatePicker();
        TextField txtRole = new TextField();

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(txtUser, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Primer Apellido:"), 0, 2);
        grid.add(txtPrimerApellido, 1, 2);
        grid.add(new Label("Segundo Apellido:"), 0, 3);
        grid.add(txtSegundoApellido, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(txtEmail, 1, 4);
        grid.add(new Label("Contraseña:"), 0, 5);
        grid.add(txtContraseya, 1, 5);
        grid.add(new Label("Teléfono:"), 0, 6);
        grid.add(txtTelefono, 1, 6);
        grid.add(new Label("Dirección:"), 0, 7);
        grid.add(txtDireccion, 1, 7);
        grid.add(new Label("Género:"), 0, 8);
        grid.add(txtGenero, 1, 8);
        grid.add(new Label("Nacimiento:"), 0, 9);
        grid.add(dpNacimiento, 1, 9);
        grid.add(new Label("Role:"), 0, 10);
        grid.add(txtRole, 1, 10);

        dialog.getDialogPane().setContent(grid);

        // Al hacer clic en Guardar, actualizar el objeto Usuario
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
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
