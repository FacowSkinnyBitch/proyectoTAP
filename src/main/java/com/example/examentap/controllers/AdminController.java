package com.example.examentap.controllers;

import com.example.examentap.models.Usuario;
import com.example.examentap.reports.ExcelReports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private BorderPane bpPrincipal;
    @FXML private VBox vb_left;
    @FXML
    private ImageView iv_imagen;

    public static final String DEST7 = "results/excel/Usuarios.xlsx";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = getClass().getResource("/com/example/examentap/images/admin_wallpaper.jpg").toExternalForm();
        iv_imagen.setImage(new Image(imagePath));

    }


    @FXML
    private void onReturn(ActionEvent event) throws IOException {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/examentap/vw_login.fxml"));  // Ruta absoluta sugerida
            Stage stage = new Stage();
            Scene scene = new Scene(root, 350, 400);
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
        genericOpenMetod("Abriendo vista Propiedades", "vw_propiedades.fxml");
    }

    @FXML
    private void onUsersView(ActionEvent event) throws IOException {
        genericOpenMetod("Abriendo vista Usuarios", "vw_Users.fxml");
    }

    @FXML
    private void onCitasView(ActionEvent event) throws IOException {
        genericOpenMetod("Abriendo vista Citas","vw_citasAdmin.fxml");
    }

    public void registeredUser(Usuario u) {
        System.out.println("Hello " + u.getNombre());
    }

    private void genericOpenMetod(String msg, String file) {
        System.out.println(msg);
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage(file);
        bpPrincipal.setCenter(view);
    }

    @FXML
    private void onPropiedadesVendidas(ActionEvent event) {
        abrirVistaGraficaSeleccionada("Graficas de Propiedades",0);
    }

    @FXML
    private void onCitasAtendidas(ActionEvent event) {
        abrirVistaGraficaSeleccionada("Estatus de Citas",1);

    }

    private void abrirVistaGraficaSeleccionada(String titulo, int seleccion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/adminViews/vw_graficosEstadisticos.fxml"));
            Parent root = loader.load();

            ChartsController chartsController = loader.getController();
            chartsController.selectedButon(seleccion);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Para bloquear la ventana principal mientras esta está abierta
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGenerarExcel(){
        ExcelReports document = new ExcelReports();
        document.createExcel(DEST7,0);
        openFile(DEST7);
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

    @FXML
    public void terminarApp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
