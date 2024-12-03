package com.example.examentap.controllers;

import com.example.examentap.databases.dao.PropiedadesDao;
import com.example.examentap.models.Propiedades;
import com.example.examentap.models.Tipo_Propiedad;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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
    private TableColumn<Propiedades, String> tv_status;
    @FXML
    private TableColumn<Propiedades, String> tv_Ciudad;
    @FXML
    private Label lblTitleForm;
    @FXML
    private VBox vbForm;
    @FXML
    private Button btn_Aceptar, btn_Cancelar;
    @FXML
    private TextField txtDireccion, txtPrecio, txtDescripcion, txtNumCuartos,
            txtNumBanos, txtMetrosCuadrados, txtImagen;
    @FXML
    private ComboBox<String> cbTipoPropiedad, cbEstado, cbCiudad;
    @FXML
    private DatePicker dpAnyoConstruccion;

    @FXML
    private GridPane gridPane;
    @FXML
    private ComboBox cb_filtroStatusProp, cb_filtroTipoProp, cb_filtroCiudad;

    private PropiedadesDao propDao = new PropiedadesDao();
    private List<Propiedades> propiedadesList = new ArrayList<Propiedades>();

    public static final String DEST1 = "results/pdf/Propiedades.pdf";
    public static final String DEST2 = "results/pdf/TipoPropiedades.pdf";
    PropiedadesDao dao = new PropiedadesDao();
    Propiedades propSeleccionada;
    int id_propSeleccionada,idStatus,idEstado;

    ContextMenu contextMenu = new ContextMenu();
    MenuItem menuItemCrearProp = new MenuItem("Crear");
    MenuItem menuItemDeleteProp = new MenuItem("Delete");
    MenuItem menuItemUpdate = new MenuItem("Update");
    MenuItem menuItemVer = new MenuItem("Mostrar");


    String[] status = {"Renta", "Venta"};
    String[] tipo_prop = {"Casa", "Negocio", "Condominio", "Todo"};
    String[] ciudadUbicada = {"León", "Guadalajara", "Querétaro", "Morelia", "Todo"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        initContextMenu();
        metodosCrud();
        vbForm.setDisable(true);
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
        tv_id.setCellValueFactory(new PropertyValueFactory<>("id_propiedad"));
        tv_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tv_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tv_tipoProp.setCellValueFactory(new PropertyValueFactory<>("tipo_propiedad"));
        tv_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tv_Ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        propiedadesList = propDao.findAll();
        propiedadesTable.setItems(FXCollections.observableList(propiedadesList));


        cb_filtroStatusProp.setItems(FXCollections.observableArrayList(status));
        cb_filtroTipoProp.setItems(FXCollections.observableArrayList(tipo_prop));
        cb_filtroCiudad.setItems(FXCollections.observableArrayList(ciudadUbicada));

        cbTipoPropiedad.setItems(FXCollections.observableArrayList(tipo_prop));
        cbEstado.setItems(FXCollections.observableArrayList(status));
        cbCiudad.setItems(FXCollections.observableArrayList(ciudadUbicada));

        propiedadesTable.setOnMouseClicked(mouseEvent -> {
            Propiedades p = (Propiedades) propiedadesTable.getSelectionModel().getSelectedItem();
            switch (mouseEvent.getClickCount()) {
                case 1:
                    break;
                case 2:
                    onMostrarPropiedad(p);
            }
        });

    }

    private void initContextMenu() {
        FontIcon iconDelete = new FontIcon();
        iconDelete.setIconLiteral("antf-info-circle");
        iconDelete.setIconSize(20);
        iconDelete.setIconColor(Color.BLUE);
        menuItemCrearProp.setGraphic(iconDelete);


        FontIcon iconComplete = new FontIcon();
        iconComplete.setIconLiteral("antf-read");
        iconComplete.setIconSize(20);
        iconComplete.setIconColor(Color.GREEN);
        menuItemUpdate.setGraphic(iconComplete);

        FontIcon iconIncomplete = new FontIcon();
        iconIncomplete.setIconLiteral("antf-delete");
        iconIncomplete.setIconSize(20);
        iconIncomplete.setIconColor(Color.BLUE);
        menuItemDeleteProp.setGraphic(iconIncomplete);

        FontIcon iconLeer = new FontIcon();
        iconLeer.setIconLiteral("antf-eye");
        iconLeer.setIconSize(20);
        iconLeer.setIconColor(Color.BLUE);
        menuItemVer.setGraphic(iconLeer);

        contextMenu.getItems().addAll(menuItemVer,menuItemCrearProp, menuItemUpdate, menuItemDeleteProp);
        propiedadesTable.setContextMenu(contextMenu);
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

    private void metodosCrud() {
        menuItemVer.setOnAction(event -> {
            vbForm.setDisable(false);
            propiedadesTable.setDisable(true);
            btn_Aceptar.setText("Aceptar");
            onCargarDatos();
        });
        menuItemCrearProp.setOnAction(event -> {
            btn_Aceptar.setText("Agregar");
            vbForm.setDisable(false);
            propiedadesTable.setDisable(true);

        });
        menuItemUpdate.setOnAction(event -> {
            vbForm.setDisable(false);
            Propiedades p = propiedadesTable.getSelectionModel().getSelectedItem();
            btn_Aceptar.setText("Actualizar");
            propiedadesTable.setDisable(true);
            onCargarDatos();

        });
        menuItemDeleteProp.setOnAction(event -> {
            Propiedades p = propiedadesTable.getSelectionModel().getSelectedItem();
            if (confirmDelete()) {
                propDao.delete(p.getId_propiedad());
                propiedadesTable.setDisable(true);
                System.out.println("Propiedad eliminada con exito!");
            }
        });
    }

    @FXML
    private void onAceptar(ActionEvent event) {
        try {
            if (btn_Aceptar.getText().equals("Agregar")) {
                //agregar
                onCrearProp();
            } else if (btn_Aceptar.getText().equals("Actualizar")) {
                Propiedades p = propiedadesTable.getSelectionModel().getSelectedItem();
                //actualizar
                if (p != null) {
                    onActualizar();
                }
            } else {

                //ver prop
                vbForm.setDisable(true);
                limpiarForm();
            }

            vbForm.setDisable(false);
        } catch (Exception ex) {
            // Manejar cualquier excepción y mostrar un mensaje de error
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo añadir la propiedad", "Ocurrió un error al intentar añadir la propiedad.");
            ex.printStackTrace();
        }


    }
    private void onCargarDatos() {
        Propiedades p = propiedadesTable.getSelectionModel().getSelectedItem();
        for(Propiedades prop: propDao.findAll()){
            if(p.getId_propiedad() == prop.getId_propiedad()){
                this.id_propSeleccionada=prop.getId_propiedad();
            }
        }



        if (p != null) {
            txtDireccion.setText(p.getDireccion());
            txtPrecio.setText(String.valueOf(p.getPrecio()));
            txtDescripcion.setText(p.getDescripcion());
            txtNumCuartos.setText(String.valueOf(p.getNum_cuartos()));
            txtNumBanos.setText(String.valueOf(p.getNum_bayos()));
            txtMetrosCuadrados.setText(String.valueOf(p.getMetros_cuadrados()));
            cbTipoPropiedad.setValue(p.getTipo_propiedad()); // Asumiendo que es un objeto compatible
        }
    }


    private void onCrearProp() {
        Propiedades newProp = new Propiedades();


        newProp.setDireccion(txtDireccion.getText());
        newProp.setPrecio(Double.parseDouble(txtPrecio.getText()));
        newProp.setDescripcion(txtDescripcion.getText());
        newProp.setNum_bayos(Integer.parseInt(txtNumBanos.getText()));
        newProp.setNum_cuartos(Integer.parseInt(txtNumCuartos.getText()));
        newProp.setMetros_cuadrados(Double.parseDouble(txtMetrosCuadrados.getText()));
        newProp.setTipo_propiedad(String.valueOf(cbTipoPropiedad.getValue()));
        newProp.setStatus(cbEstado.getSelectionModel().getSelectedItem());
        newProp.setAyo_construccion(Date.valueOf(dpAnyoConstruccion.getValue()));
        newProp.setImagen(txtImagen.getText());
        newProp.setCiudad(cbCiudad.getSelectionModel().getSelectedItem());
        if (propDao.save(newProp)) {
            mostrarAlerta(Alert.AlertType.CONFIRMATION, "Éxito", "Propiedad Agregada con exito", "Ya puedes ver la propiedad nueva!");
        }
    }

    private void onActualizar() {
        try {
            Propiedades actualizarProp = new Propiedades();
            actualizarProp.setDireccion(txtDireccion.getText());
            actualizarProp.setPrecio(Double.parseDouble(txtPrecio.getText()));
            actualizarProp.setDescripcion(txtDescripcion.getText());
            actualizarProp.setNum_cuartos(Integer.parseInt(txtNumCuartos.getText()));
            actualizarProp.setNum_bayos(Integer.parseInt(txtNumBanos.getText()));
            actualizarProp.setMetros_cuadrados(Double.parseDouble(txtMetrosCuadrados.getText()));
            actualizarProp.setImagen(txtImagen.getText());
            actualizarProp.setTipo_propiedad(cbTipoPropiedad.getSelectionModel().getSelectedItem());
            actualizarProp.setCiudad(cbCiudad.getSelectionModel().getSelectedItem());
            actualizarProp.setStatus(cbEstado.getSelectionModel().getSelectedItem());
            actualizarProp.setAyo_construccion(Date.valueOf(dpAnyoConstruccion.getValue()));
            propDao.update(actualizarProp);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Propiedad actualizada", "La propiedad se ha actualizado correctamente.");
        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la propiedad", "Ocurrió un error al intentar actualizar la propiedad.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        vbForm.setDisable(true);
        limpiarForm();
        propiedadesTable.setDisable(false);

    }

    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Borrado");
        alert.setContentText("¿Esta seguro que quiere borrar la propiedad seleccionada?");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    @FXML
    private void limpiarForm() {
        txtDireccion.clear();
        txtPrecio.clear();
        txtDescripcion.clear();
        txtNumCuartos.clear();
        txtNumBanos.clear();
        txtMetrosCuadrados.clear();
        txtImagen.clear();
        cbTipoPropiedad.getSelectionModel().clearSelection();
        cbEstado.getSelectionModel().clearSelection();
        cbCiudad.getSelectionModel().clearSelection();
        cbTipoPropiedad.setPromptText("Tipo de Propiedad");
        cbEstado.setPromptText("Estado");
        cbCiudad.setPromptText("Ciudad");
        dpAnyoConstruccion.setValue(null);
    }

    private boolean mostrarAlerta(Alert.AlertType alertType, String title, String header, String content) {
        Alert alerta = new Alert(alertType);
        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(content);

        // Aplicar estilo CSS al DialogPane
        try {
            alerta.getDialogPane().getStylesheets().add(
                    getClass().getResource("/com/example/examentap/cssFiles/alerta.css").toExternalForm()
            );
            alerta.getDialogPane().getStyleClass().add("dialog-pane");
        } catch (NullPointerException e) {
            System.err.println("Error: No se encontró el archivo CSS en /cssFiles/alerta.css");
        }

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
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

}
