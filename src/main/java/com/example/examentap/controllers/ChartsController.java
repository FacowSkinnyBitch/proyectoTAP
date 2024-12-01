package com.example.examentap.controllers;

import com.example.examentap.databases.dao.CitasDao;
import com.example.examentap.databases.dao.PropiedadesDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ChartsController implements Initializable {
    @FXML
    private GridPane chartsContainer;
    @FXML private Label lblTitle;

    PropiedadesDao propiedadesDao = new PropiedadesDao();
    CitasDao citasDao = new CitasDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void selectedButon(int selected){
        if(selected == 0){
            //muestra grafica de propiedades por status y por tipo
            lblTitle.setText("Graficas de Propiedades");
            chartsContainer.add(barChartPropiedadesStatus(), 0, 0);
            chartsContainer.add(pieChart_PropiedadesTipo(), 1, 0);

        }else{
            //muestra citas programadas
            lblTitle.setText("Citas Programadas");
            chartsContainer.add(barChartPropiedadesStatus(), 0, 0);

        }
    }

    private BarChart<String, Number> barChartPropiedadesStatus() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);

        bc.setTitle("Propiedades vendidas y no vendidas");
        xAxis.setLabel("Estado de las propiedades");
        yAxis.setLabel("Número de propiedades");

        long propVendidas = propiedadesDao.countPropByStatus("venta");
        long propNoVendidas = propiedadesDao.countPropByStatus("renta");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Propiedades");

        series.getData().add(new XYChart.Data<>("Vendidas", propVendidas));
        series.getData().add(new XYChart.Data<>("No Vendidas", propNoVendidas));

        bc.getData().add(series);

        return bc;
    }
    private PieChart pieChart_PropiedadesTipo() {
        //productDao.totalProductsByCategory();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(Map.Entry item:propiedadesDao.countPropsByTipoProp().entrySet()){
            pieChartData.add(new PieChart.Data(item.getKey().toString(),(int)item.getValue()));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Tipo de propieddades");
        return chart;
    }
    private BarChart<String, Number> barChartStatusCitas() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);

        // Configuración de la gráfica
        bc.setTitle("Citas programadas y confirmadas");
        xAxis.setLabel("Estado de las citas");
        yAxis.setLabel("Número de citas hechas");

        long citaConfirmada = citasDao.countCitaByStatus("confirmada");
        long citaNoConfirmada = citasDao.countCitaByStatus("programada");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Propiedades");

        series.getData().add(new XYChart.Data<>("confirmadas", citaConfirmada));
        series.getData().add(new XYChart.Data<>("programadas", citaNoConfirmada));

        bc.getData().add(series);

        return bc;
    }

    @FXML
    public void onCloseCurrentView(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
