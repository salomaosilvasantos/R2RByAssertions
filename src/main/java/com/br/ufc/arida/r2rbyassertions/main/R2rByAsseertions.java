/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class R2rByAsseertions extends Application {
    
    public static Stage pStage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(R2rByAsseertions.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/main.fxml"));
            Scene scene = new Scene(page);
            pStage = primaryStage;
            primaryStage.setScene(scene);
            primaryStage.setTitle("R2R - By Assertions");
            primaryStage.getIcons().add( new Image(getClass().getResourceAsStream("/img/icon.png")));
           
            primaryStage.setResizable(false);
            primaryStage.show();
            
            pStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    System.exit(1);
            }
            });
        } catch (Exception ex) {
            Logger.getLogger(R2rByAsseertions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
