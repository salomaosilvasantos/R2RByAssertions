/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 *
 * @author Tiagovinuto
 */
public class TabPaneCA implements Initializable {
    
    private static Stage loadSStage = null;
    public static TabPaneCA tPaneCA;
    MappingConfiguration mc = null;
    
    
    
    public static Stage getLoadSStage() {
        return loadSStage;
    }

    public static void setLoadSStage(Stage loadSStage) {
        TabPaneCA.loadSStage = loadSStage;
    }
    
    public TabPaneCA(){
        
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tPaneCA = new TabPaneCA();
    }
    
    private void generateR2RFired(MouseEvent arg0) {
    
    }

    private void init() {
     
    }

    @FXML
    public void loadS(ActionEvent event) {
    }

    public void addAssertions(ActionEvent event) throws IOException {
    }
}
