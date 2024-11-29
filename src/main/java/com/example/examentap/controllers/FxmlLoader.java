package com.example.examentap.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileName){
        try {
            URL fileUrl = getClass().getResource("/com/example/examentap/adminViews/" + fileName);

            if(fileUrl == null){
                throw new FileNotFoundException("File not found: " + fileName);

            }

            view = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            System.out.println("No page found "+ fileName);
        }

        return view;
    }
}
