/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import com.br.ufc.arida.r2rbyassertions.model.Debug;
import com.br.ufc.arida.r2rbyassertions.model.MappingConfiguration;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.MappingConfigurationDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author tiagovinuto
 */
public class NewMappingController implements Initializable {

    @FXML
    Button cancel;
    
    @FXML
    Button searchT;
    
    @FXML
    Button searchS;
    
    @FXML
    Button save;
    
    @FXML
    TextField targetOntology;
    
    @FXML
    TextField sourceOntology;
    
    @FXML
    TextField filePathTarget;
    
    @FXML
    TextField filePathR2RSource;
    
    @FXML
    TextField filePathR2RTarget;
    
    @FXML
    TextField filePathSource;
    
    @FXML
    TextField ontoUrlTarget;
    
    @FXML
    TextField ontoUrlSource;
    
    @FXML
    ComboBox<String> SourceOntoLangs;
    
    @FXML
    ComboBox<String> TargetOntoLangs;

    public static NewMappingController nm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";
        assert searchT != null : "fx:id=\"searchT\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";
        assert searchS != null : "fx:id=\"searchS\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";
        assert filePathTarget != null : "fx:id=\"filePathTarget\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";
        assert filePathSource != null : "fx:id=\"filePathSource\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";

        nm = this;

        System.out.println(this.getClass().getSimpleName() + ".initialize");
        
        if(Debug.isOn())
            this.save.requestFocus();
    }

    /**
     * Called when the Cancel button is fired.
     *
     * @param event the action event.
     */
    public void cancelFired(ActionEvent event) throws IOException {
        MainController.secondaryStage.close();
    }

    /**
     * Called when the Search button is fired.
     *
     * @param event the action event.
     * @throws java.io.IOException
     */
    public void searchFiredTarget(ActionEvent event) throws IOException { // Mudar para searchFiredTarget
        FileChooser fc1 = new FileChooser();
       
        File f1 = fc1.showOpenDialog(MainController.secondaryStage);
        
        filePathTarget.setText(f1.getAbsolutePath()); 
    }

   public void searchFiredSource(ActionEvent event) throws IOException {
        FileChooser fc2 = new FileChooser();

        File f2 = fc2.showOpenDialog(MainController.secondaryStage);

        filePathSource.setText(f2.getAbsolutePath());
    }

   @FXML
    public void searchRDFSourceFile(ActionEvent event) throws IOException { // Mudar para searchFiredTarget
        FileChooser fc1 = new FileChooser();
       
        File f1 = fc1.showOpenDialog(MainController.secondaryStage);
        
        filePathR2RSource.setText(f1.getAbsolutePath()); 
    }
    
   @FXML
    public void searchRDFTargetFile(ActionEvent event) throws IOException { // Mudar para searchFiredTarget
        FileChooser fc1 = new FileChooser();
       
        File f1 = fc1.showOpenDialog(MainController.secondaryStage);
        
        filePathR2RTarget.setText(f1.getAbsolutePath()); 
    }
    /**
     * Called when the TestConnection button is fired.
     *
     * @param event the action event.
     */
    public void saveFired(ActionEvent event) throws IOException { // Aqui esta errado, preciso pegar a URL e só estou pegando o arquivo em todas as opções!
        MappingConfiguration mc = null;
        
        if (!("".equals(filePathTarget.getText().trim())) && !("".equals(filePathSource.getText().trim()))) {
            System.out.println("Entrou no 1");
            mc = new MappingConfiguration(targetOntology.getText(), sourceOntology.getText(), filePathTarget.getText(), filePathSource.getText(), 1, 1, TargetOntoLangs.getValue(), SourceOntoLangs.getValue(),filePathR2RSource.getText(),filePathR2RTarget.getText());
        }

        if (("".equals(filePathTarget.getText().trim())) && !("".equals(filePathSource.getText().trim()))) {
            System.out.println("Entrou no 2");
            mc = new MappingConfiguration(targetOntology.getText(), sourceOntology.getText(), filePathTarget.getText(), filePathSource.getText(), 2, 1, TargetOntoLangs.getValue(), SourceOntoLangs.getValue(),filePathR2RSource.getText(),filePathR2RTarget.getText());
        }

        if (!("".equals(filePathTarget.getText().trim())) && ("".equals(filePathSource.getText().trim()))) {
            System.out.println("Entrou no 3");
            mc = new MappingConfiguration(targetOntology.getText(), sourceOntology.getText(), filePathTarget.getText(), filePathSource.getText(), 1, 2, TargetOntoLangs.getValue(), SourceOntoLangs.getValue(),filePathR2RSource.getText(),filePathR2RTarget.getText());
        }

        if (("".equals(filePathTarget.getText().trim())) && ("".equals(filePathSource.getText().trim()))) {
            System.out.println("Entrou no 4");
            mc = new MappingConfiguration(targetOntology.getText(), sourceOntology.getText(), filePathTarget.getText(), filePathSource.getText(), 2, 2, TargetOntoLangs.getValue(), SourceOntoLangs.getValue(),filePathR2RSource.getText(),filePathR2RTarget.getText());
        }

        MappingConfigurationDAO mcDAO = new MappingConfigurationDAO();
        try {
             if (targetOntology.isDisable() && sourceOntology.isDisable()){
                mcDAO.update(mc);   
            } else {
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mc.setCreationDate(formatDate.format(new Date()));
                //int id = mcDAO.add(mc);
                //mc.setId(id);
                MainController.m.buildOntoTreeTarget(mc);

             }

            JOptionPane.showMessageDialog(null, "Mapping Configuration Saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
            MainController.secondaryStage.close();
        } catch (SQLException ex) {
            Logger.getLogger(NewMappingController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}