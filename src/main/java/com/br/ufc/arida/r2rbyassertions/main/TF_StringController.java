/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import static com.br.ufc.arida.r2rbyassertions.main.FunctionTransformationController.funcTrans;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.br.ufc.arida.r2rbyassertions.model.DCA;
import com.br.ufc.arida.r2rbyassertions.model.Property;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Tiagovinuto
 */
public class TF_StringController implements Initializable{
    
    @FXML
    MenuButton stringFunction;
    
    @FXML
    MenuButton varProperty;
    
    public static TF_StringController tf_String;
    private static Stage loadtfString;
    private TbpaneController tbPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_String = this;
    }
    
    private static void initStringFunction(TbpaneController _tbPane)throws IOException{
    
        loadtfString = new Stage(StageStyle.UTILITY);
        AnchorPane a_pane = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/TransformationFunction_String.fxml"));
        tf_String.tbPane = _tbPane;
        Scene sceneFilter = new Scene(a_pane);
        loadtfString.setScene(sceneFilter);
        loadtfString.initModality(Modality.APPLICATION_MODAL);
        loadtfString.initOwner(R2rByAsseertions.pStage);
        
        addCurrentProperty();
    }
    
    private static void addCurrentProperty() {
        Property _prop = tf_String.tbPane.getCurrentProperty();

        MenuItem _menuProp = new MenuItem();

        _menuProp.setText(_prop.getName());
        _menuProp.setOnAction( new EventHandler<ActionEvent>() {

        @Override
            public void handle(ActionEvent t) {
               //tf_String.transfomationFunction(t);
            }
        });
        tf_String.varProperty.getItems().add(_menuProp);
    }
    
    protected static void show(TbpaneController _tbPane) throws IOException {
        initStringFunction(_tbPane);

        loadtfString.setTitle("Transformation Function");
        loadtfString.setResizable(false);
        loadtfString.show();
    }
    
    public void captureVarProperty() {

        this.varProperty.getItems().add(null);

        DCA c = new DCA();
        c.getdPropertyS();
        c.getObjectP();
    }
    
    
    @FXML
    private void transfomationFunction(ActionEvent e) {

        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = "";
        //boolean isVarProperty = false;
        //JOptionPane.showMessageDialog(null, r);
        if (stringFunction.getItems().contains(menuItem)) {
            stringFunction.setText(menuItem.getText());
            FunctionTransformationController.funcTrans.textFunction.setText(tmp);
        }
    }
    
//    private void habilitaProperty() {
//        varProperty.setDisable(false);
//    }
//
//    private void desabilitaProperty() {
//        varProperty.setDisable(true);
//    }
    
}
