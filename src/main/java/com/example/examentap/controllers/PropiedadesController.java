package com.example.examentap.controllers;

import com.example.examentap.databases.dao.PropiedadesDao;
import com.example.examentap.models.Propiedades;
import com.example.examentap.models.Tipo_Propiedad;
import com.example.examentap.models.Usuario;
import com.example.examentap.reports.PDFEspecificReport;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PropiedadesController implements Initializable {
    @FXML
    private TableView<Propiedades> propiedadesTable;
    @FXML
    private TableColumn<Propiedades, Integer> tv_id;
    @FXML
    private TableColumn<Propiedades, String> tv_direccion;
    @FXML
    private TableColumn<Propiedades, Double> tv_precio;
    @FXML
    private TableColumn<Propiedades, Integer> tv_tipoProp;
    @FXML
    private TableColumn<Propiedades, Boolean> tv_status;
    @FXML
    private TableColumn<Propiedades, String> tv_Ciudad;

    @FXML
    private ComboBox cb_filtroStatusProp, cb_filtroTipoProp, cb_filtroCiudad;
    @FXML
    private Button btn_CreateNewProp;

    private PropiedadesDao propDao = new PropiedadesDao();
    private List<Propiedades> propiedadesList = new ArrayList<Propiedades>();

    public static final String DEST1 = "results/pdf/Propiedades.pdf";
    public static final String DEST2 = "results/pdf/TipoPropiedades.pdf";
    PropiedadesDao dao = new PropiedadesDao();

    ContextMenu contextMenu = new ContextMenu();
    MenuItem menuItemSelectUser = new MenuItem("Info User");
    MenuItem menuItemDeleteUser = new MenuItem("Delete");
    MenuItem menuItemUpdate = new MenuItem("Update");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_id.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        tv_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tv_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tv_tipoProp.setCellValueFactory(new PropertyValueFactory<>("tipo_propiedad"));
        tv_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tv_Ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        initTable();
        initContextMenu();

        String[] status = {"Renta", "Venta", "Todo"};
        String[] tipo_prop = {"Casa", "Negocio", "Condominio", "Todo"};
        String[] ciudadUbicada = {"León", "Guadalajara", "Querétaro", "Morelia", "Todo"};
        cb_filtroStatusProp.setItems(FXCollections.observableArrayList(status));
        cb_filtroTipoProp.setItems(FXCollections.observableArrayList(tipo_prop));
        cb_filtroCiudad.setItems(FXCollections.observableArrayList(ciudadUbicada));


        //filtros de la tabla
        cb_filtroStatusProp.valueProperty().addListener(event -> {
            if (cb_filtroStatusProp.getSelectionModel().getSelectedItem().equals("Renta")) {
                propiedadesList = propDao.filterPropByStatus("renta");
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));

            } else if (cb_filtroStatusProp.getSelectionModel().getSelectedItem().equals("Venta")) {
                propiedadesList = propDao.filterPropByStatus("venta");
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else {
                propiedadesList = propDao.findAll();
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }
        });
        cb_filtroTipoProp.valueProperty().addListener(event -> {
            if (cb_filtroTipoProp.getSelectionModel().getSelectedItem().toString().equals("Casa")) {
                propiedadesList = propDao.filterPropByTipoProp(1);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else if (cb_filtroTipoProp.getSelectionModel().getSelectedItem().equals("Negocio")) {
                propiedadesList = propDao.filterPropByTipoProp(2);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else if (cb_filtroTipoProp.getSelectionModel().getSelectedItem().equals("Condominio")) {
                propiedadesList = propDao.filterPropByTipoProp(3);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else {
                propiedadesList = propDao.findAll();
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }
        });
        cb_filtroCiudad.valueProperty().addListener(event -> {
            if (cb_filtroCiudad.getSelectionModel().getSelectedItem().toString().equals("León")) {
                propiedadesList = propDao.filterPropByCiudad(1);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else if (cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Guadalajara")) {
                propiedadesList = propDao.filterPropByCiudad(2);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else if (cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Querétaro")) {
                propiedadesList = propDao.filterPropByCiudad(3);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else if (cb_filtroCiudad.getSelectionModel().getSelectedItem().equals("Morelia")) {
                propiedadesList = propDao.filterPropByCiudad(4);
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            } else {
                propiedadesList = propDao.findAll();
                propiedadesTable.setItems(FXCollections.observableList(propiedadesList));
            }
        });


    }

    private void initTable() {
        propiedadesList = propDao.findAll();
        propiedadesTable.setItems(FXCollections.observableList(propiedadesList));

        propiedadesTable.setOnMouseClicked(mouseEvent -> {
            Propiedades p = (Propiedades) propiedadesTable.getSelectionModel().getSelectedItem();
            switch (mouseEvent.getClickCount()) {
                case 1:
                    break;
                case 2:
                    if (propiedadesTable.getSelectionModel().getSelectedItem() == null) {
                        System.out.println("Nada seleccionado");
                    } else {
                        onMostrarPropiedad(p);
                    }
            }
        });

        menuItemUpdate.setOnAction(event -> {
            Propiedades propiedadSeleccionada = (Propiedades) propiedadesTable.getSelectionModel().getSelectedItem();
            onEditProp();
        });
        btn_CreateNewProp.setOnAction(event -> {
            onCreateProp();
        });

    }

    private void initContextMenu() {
        FontIcon iconDelete = new FontIcon();
        iconDelete.setIconLiteral("antf-info-circle");
        iconDelete.setIconSize(20);
        iconDelete.setIconColor(Color.BLUE);
        menuItemSelectUser.setGraphic(iconDelete);


        FontIcon iconComplete = new FontIcon();
        iconComplete.setIconLiteral("antf-read");
        iconComplete.setIconSize(20);
        iconComplete.setIconColor(Color.GREEN);
        menuItemUpdate.setGraphic(iconComplete);

        FontIcon iconIncomplete = new FontIcon();
        iconIncomplete.setIconLiteral("antf-delete");
        iconIncomplete.setIconSize(20);
        iconIncomplete.setIconColor(Color.RED);
        contextMenu.getItems().addAll(menuItemSelectUser, menuItemUpdate, menuItemDeleteUser);
        menuItemDeleteUser.setGraphic(iconIncomplete);
    }

    @FXML
    private void onReturn(ActionEvent event) throws IOException {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/examentap/adminViews/vw_modoAdmin.fxml"));  // Ruta absoluta sugerida
            Stage stage = new Stage();
            Scene scene = new Scene(root, 350, 500);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onMostrarPropiedad(Propiedades p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/userViews/vw_propSelected.fxml"));
            Parent root = loader.load();
            propiedadSeleccionada ps = loader.getController();
            ps.mostrarDatos(p);

            Stage stage = new Stage();
            stage.setTitle("Propiedad Seleccionada");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Para bloquear la ventana principal mientras esta está abierta
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generarPDF() throws IOException {
        File file = new File(DEST1);
        file.getParentFile().mkdirs();
        new PropiedadesPDFReports().createPdf(DEST1);


        if (showMessage("PDF Usuarios")) {
            openFile(DEST1);
        }
    }

    @FXML
    private void generarExcel() {

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
    private void generarPdfFiltrado() throws IOException {
        Tipo_Propiedad tpPropiedad = new Tipo_Propiedad();
        if (tpPropiedad == null) {
            showMessage("Select a category");
        } else {
            List<Propiedades> tipoPropiedades = (tpPropiedad.getId_tipo_propiedad() == 0) ? dao.findAll() : dao.filterPropByTipoProp(tpPropiedad.getId_tipo_propiedad());
            File file = new File(DEST2);
            file.getParentFile().mkdirs();
            new PDFEspecificReport().createPdf(DEST2, tipoPropiedades);
            showMessage("The products report with esoecific id was generated");
            openFile(DEST2);
        }
    }

    //metodo para mostrar mensajes
    private boolean showMessage(String message) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("PDF generated...");
        a.setContentText(message);
        Optional<ButtonType> result = a.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    @FXML
    private void onCreateProp() {
        // Crear un nuevo objeto Propiedades vacío
        Propiedades nuevaPropiedad = new Propiedades();

        // Crear un diálogo de entrada para capturar los datos de la propiedad
        Dialog<Propiedades> dialog = crearDialogoCreacion(nuevaPropiedad);
        Optional<Propiedades> resultado = dialog.showAndWait();

        resultado.ifPresent(propiedadCreada -> {
            if (propDao.save(propiedadCreada)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Propiedad creada correctamente.", ButtonType.OK);
                alert.showAndWait();
                initTable();  // Recargar la tabla de propiedades
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo crear la propiedad.", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void onEditProp() {
        // Obtener la propiedad seleccionada
        Propiedades propiedadSeleccionada = propiedadesTable.getSelectionModel().getSelectedItem();

        if (propiedadSeleccionada != null) {
            Dialog<Propiedades> dialog = crearDialogoEdicion(propiedadSeleccionada);
            Optional<Propiedades> resultado = dialog.showAndWait();

            resultado.ifPresent(propiedadEditada -> {
                if (propDao.update(propiedadEditada)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Propiedad actualizada correctamente.", ButtonType.OK);
                    alert.showAndWait();
                    initTable();  // Recargar la tabla de propiedades
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar la propiedad.", ButtonType.OK);
                    alert.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione una propiedad para editar.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Dialog<Propiedades> crearDialogoEdicion(Propiedades propiedad) {
        Dialog<Propiedades> dialog = new Dialog<>();
        dialog.setTitle("Editar Propiedad");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/adminViews/vw_editProp.fxml"));
        DialogPane dialogPane;
        try {
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Obtener campos del controlador
        TextField txtDireccion = (TextField) dialogPane.lookup("#txtDireccion");
        TextField txtPrecio = (TextField) dialogPane.lookup("#txtPrecio");
        TextField txtDescripcion = (TextField) dialogPane.lookup("#txtDescripcion");
        TextField txtNumCuartos = (TextField) dialogPane.lookup("#txtNumCuartos");
        TextField txtNumBanos = (TextField) dialogPane.lookup("#txtNumBanos");
        TextField txtMetrosCuadrados = (TextField) dialogPane.lookup("#txtMetrosCuadrados");
        ComboBox<String> cbTipoPropiedad = (ComboBox<String>) dialogPane.lookup("#cbTipoPropiedad");
        ComboBox<String> cbEstado = (ComboBox<String>) dialogPane.lookup("#cbEstado");
        DatePicker dpAnyoConstruccion = (DatePicker) dialogPane.lookup("#dpAnyoConstruccion");
        TextField txtImagen = (TextField) dialogPane.lookup("#txtImagen");
        ComboBox<String> cbCiudad = (ComboBox<String>) dialogPane.lookup("#cbCiudad");

        // Cargar valores iniciales
        txtDireccion.setText(propiedad.getDireccion());
        txtPrecio.setText(String.valueOf(propiedad.getPrecio()));
        txtDescripcion.setText(propiedad.getDescripcion());
        txtNumCuartos.setText(String.valueOf(propiedad.getNum_cuartos()));
        txtNumBanos.setText(String.valueOf(propiedad.getNum_bayos()));
        txtMetrosCuadrados.setText(String.valueOf(propiedad.getMetros_cuadrados()));
        cbTipoPropiedad.setValue(propiedad.getTipo_propiedad());
        cbEstado.setValue(String.valueOf(propiedad.getStatus()));
        txtImagen.setText(propiedad.getImagen());
        cbCiudad.setValue(propiedad.getCiudad());

        dialog.setDialogPane(dialogPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialogPane.getButtonTypes().get(0)) { // Botón Guardar
                try {
                    propiedad.setDireccion(txtDireccion.getText());
                    propiedad.setPrecio(Double.parseDouble(txtPrecio.getText()));
                    propiedad.setDescripcion(txtDescripcion.getText());
                    propiedad.setNum_cuartos(Integer.parseInt(txtNumCuartos.getText()));
                    propiedad.setNum_bayos(Integer.parseInt(txtNumBanos.getText()));
                    propiedad.setMetros_cuadrados(Double.parseDouble(txtMetrosCuadrados.getText()));
                    propiedad.setTipo_propiedad(cbTipoPropiedad.getSelectionModel().getSelectedItem());
                    propiedad.setStatus(cbEstado.getSelectionModel().getSelectedItem());
                    propiedad.setImagen(txtImagen.getText());
                    propiedad.setCiudad(cbCiudad.getSelectionModel().getSelectedItem());
                    return propiedad;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, ingrese datos válidos.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
            return null;
        });

        return dialog;
    }

    private Dialog<Propiedades> crearDialogoCreacion(Propiedades propiedad) {
        Dialog<Propiedades> dialog = new Dialog<>();
        dialog.setTitle("Crear Nueva Propiedad");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examentap/adminViews/vw_createProp.fxml"));
        DialogPane dialogPane;
        try {
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Obtener campos del controlador
        TextField txtDireccion = (TextField) dialogPane.lookup("#txtDireccion");
        TextField txtPrecio = (TextField) dialogPane.lookup("#txtPrecio");
        TextField txtDescripcion = (TextField) dialogPane.lookup("#txtDescripcion");
        TextField txtNumCuartos = (TextField) dialogPane.lookup("#txtNumCuartos");
        TextField txtNumBanos = (TextField) dialogPane.lookup("#txtNumBanos");
        TextField txtMetrosCuadrados = (TextField) dialogPane.lookup("#txtMetrosCuadrados");
        ComboBox<String> cbTipoPropiedad = (ComboBox<String>) dialogPane.lookup("#cbTipoPropiedad");
        ComboBox<String> cbEstado = (ComboBox<String>) dialogPane.lookup("#cbEstado");
        DatePicker dpAnyoConstruccion = (DatePicker) dialogPane.lookup("#dpAnyoConstruccion");
        TextField txtImagen = (TextField) dialogPane.lookup("#txtImagen");
        ComboBox<String> cbCiudad = (ComboBox<String>) dialogPane.lookup("#cbCiudad");

        // Cargar valores en ComboBox
        cbTipoPropiedad.setItems(FXCollections.observableArrayList("Casa", "Negocio", "Condominio"));
        cbEstado.setItems(FXCollections.observableArrayList("Renta", "Venta"));
        cbCiudad.setItems(FXCollections.observableArrayList("León", "Guadalajara", "Querétaro", "Morelia"));

        dialog.setDialogPane(dialogPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == dialogPane.getButtonTypes().get(0)) { // Botón Guardar
                try {
                    propiedad.setDireccion(txtDireccion.getText());
                    propiedad.setPrecio(Double.parseDouble(txtPrecio.getText()));
                    propiedad.setDescripcion(txtDescripcion.getText());
                    propiedad.setNum_cuartos(Integer.parseInt(txtNumCuartos.getText()));
                    propiedad.setNum_bayos(Integer.parseInt(txtNumBanos.getText()));
                    propiedad.setMetros_cuadrados(Double.parseDouble(txtMetrosCuadrados.getText()));
                    propiedad.setTipo_propiedad(cbTipoPropiedad.getSelectionModel().getSelectedItem());
                    propiedad.setStatus(cbEstado.getSelectionModel().getSelectedItem());
                    //propiedad.setAyo_construccion(dpAnyoConstruccion.getValue());
                    propiedad.setImagen(txtImagen.getText());
                    propiedad.setCiudad(cbCiudad.getSelectionModel().getSelectedItem());
                    return propiedad;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, ingrese datos válidos.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
            return null;
        });

        return dialog;
    }
}
