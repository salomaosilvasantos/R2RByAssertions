/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

import com.br.ufc.arida.r2rbyassertions.model.DCA;
import com.br.ufc.arida.r2rbyassertions.model.Property;

/**
 * FXML Controller class
 *
 * @author Tiagovinuto
 */
public class FunctionTransformationController implements Initializable {

    @FXML
    Button cancel;

    @FXML
    Button saveFunction;

    @FXML
    TextField textFunction;
    
    @FXML
    MenuButton functionsR2R;
    
    @FXML
    MenuButton  generateFunction;

    @FXML
    HBox hboxFunction;
    
    @FXML
    MenuButton varProperty;
    
    
    public static FunctionTransformationController funcTrans;

    private String transformationFunction = "";
    private static Stage loadtransFunction;
    private TbpaneController tbPane;

    public String getTransformationFunction() {
        return this.transformationFunction;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcTrans = this;
        TbpaneOCAController t = TbpaneOCAController.tbOCA;

        //desabilitaProperty();
        desabilitaSave();
        
        //funcT1.setExpanded(true);
        //funcTrans.funcT1.expandedProperty();
        //funcTrans.funcT1.
    }

    private static void initTransformation(TbpaneController _tbPane) throws IOException {
        loadtransFunction = new Stage(StageStyle.UTILITY);
        AnchorPane a_pane = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/TransformationFunction_Master.fxml")); // alterei em 30/08 ás 12:02
        funcTrans.tbPane = _tbPane;
        Scene sceneFilter = new Scene(a_pane);
        loadtransFunction.setScene(sceneFilter);
        loadtransFunction.initModality(Modality.APPLICATION_MODAL);
        loadtransFunction.initOwner(R2rByAsseertions.pStage);
        //funcTrans.funcT1.setExpanded(true);
        //funcTrans.funcT1.expandedProperty();
        //funcTrans.funcT1.isExpanded();
        //funcTrans.funcT1.gete;
        //funcTrans.funcT2.setCollapsible(false);
        //funcTrans.funcT1.
        //funcTrans.funcT1.setExpanded(true);
        addCurrentProperty();
        
    }


    protected static void show(TbpaneController _tbPane) throws IOException {
        initTransformation(_tbPane);

        loadtransFunction.setTitle("Transformation Function");
        loadtransFunction.setResizable(false);
        loadtransFunction.show();
    }

    /**
     * Initializes the controller class.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void cancelFired(ActionEvent event) throws IOException {
        dispose();
    }

    @FXML
    private void saveFunction(ActionEvent action) {

        //TbpaneOCAController t = TbpaneOCAController.tbOCA;
        //closeFunctionText();

        this.transformationFunction = textFunction.getText();
        tbPane.addTransformationFunction(textFunction.getText());
        dispose();
    }

    private static void addCurrentProperty() {
         for (Property _prop : funcTrans.tbPane.getListProperty()) {
            MenuItem _menuProp = new MenuItem();
            _menuProp.setText(_prop.getName());
            _menuProp.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    funcTrans.propertyFunction(t);                        
                }
            });
            funcTrans.varProperty.getItems().add(_menuProp);
        }
    }

    @FXML
    private void propertyFunction(ActionEvent e){
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = menuItem.getText();
        
        varProperty.setText(tmp);
        
        addText("?"+tmp);
        //closeFunctionText();
        habilitaSave();
    }
    
    private void addText(String _str) {
        String tmp = textFunction.getText();
        tmp=tmp.replace(")", "");
        if (tmp.contains("?"))
               
               tmp += ", " + _str + ")";      
        
        else
            tmp += _str + ")"; 
        //if "verricar se tmp tem ?"
        //se sim tirar ) e tmp += ", " _str + ")"
        //se não tmp += ", " _str + ")"
        
        //tmp += _str;
        textFunction.setText(tmp);
    }
    
    public void captureVarProperty() {

        this.varProperty.getItems().add(null);

        DCA c = new DCA();
        c.getdPropertyS();
        c.getObjectP();
    }

//    @FXML
//    private void transfomationFunction(ActionEvent e) {
//
//        MenuItem menuItem = (MenuItem) e.getSource();
//        String tmp = "";
//        boolean isVarProperty = false;
//        //JOptionPane.showMessageDialog(null, r);
//        if (stringFunction.getItems().contains(menuItem)) {
//            stringFunction.setText(menuItem.getText());
//        }
//
//        if (listFunction.getItems().contains((menuItem))) {
//            listFunction.setText(menuItem.getText());
//        }
//
//        if (arithmeticFunction.getItems().contains(menuItem)) {
//            arithmeticFunction.setText(menuItem.getText());
//        }
//
//        if (xpathFunction.getItems().contains(menuItem)) {
//            xpathFunction.setText(menuItem.getText());
//        }
//
//        if (varProperty.getItems().contains(menuItem)) {
//            varProperty.setText(menuItem.getText());
//            habilitaSave();
//            isVarProperty = true;
//        }
//
//        tmp = textFunction.getText();
//
//        if (!isVarProperty) {
//            tmp += " " + menuItem.getText();
//            tmp += "(";
//        } else {
//            tmp += " ?" + menuItem.getText();
//        }
//
//        textFunction.setText(tmp);
//
//        habilitaProperty();
//    }

//    private void habilitaProperty() {
//        varProperty.setDisable(false);
//    }
//
//    private void desabilitaProperty() {
//        varProperty.setDisable(true);
//    }
    
    @FXML
    public void functionsR2RFired(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = menuItem.getText();
        
        JOptionPane.showMessageDialog(null, tmp);
        
        functionsR2R.setText(tmp);
    }
    
    @FXML
    public void listFunction(ActionEvent a){
        MenuItem item = (MenuItem) a.getSource();
        functionsR2R.setText(item.getText());
        
        generateFunction.getItems().clear();
        
        
        
        MenuItem item1 = new MenuItem("list");
        MenuItem item2 = new MenuItem("sublist");
        MenuItem item3 = new MenuItem("subListByIndex");
        MenuItem item4 = new MenuItem("listConcat");
        MenuItem item5 = new MenuItem("getByIndex");
        MenuItem item6 = new MenuItem("length");
        
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item =(MenuItem)actionEvent.getSource();
                generateFunction.setText(item.getText());
                addText(item.getText() + "( ");        
                desabilitaSave();
            }
        });
        
        item2.setOnAction(item1.getOnAction());
        item3.setOnAction(item1.getOnAction());
        item4.setOnAction(item1.getOnAction());
        item5.setOnAction(item1.getOnAction());
        item6.setOnAction(item1.getOnAction());
         
        generateFunction.getItems().add(item1);
        generateFunction.getItems().add(item2);
        generateFunction.getItems().add(item3);
        generateFunction.getItems().add(item4);
        generateFunction.getItems().add(item5);
        generateFunction.getItems().add(item6);
        
    }
        
    public void stringFunction(ActionEvent a){
        MenuItem item = (MenuItem) a.getSource();
        functionsR2R.setText(item.getText());
        
        generateFunction.getItems().clear();
       
        MenuItem item1 = new MenuItem("join");
        MenuItem item2 = new MenuItem("concat");
        MenuItem item3 = new MenuItem("split");
        MenuItem item4 = new MenuItem("listJoin");
        MenuItem item5 = new MenuItem("regexToList");
        MenuItem item6 = new MenuItem("replaceAll");
        
        
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                generateFunction.setText(item.getText());
                addText(item.getText() + "(");
                desabilitaSave();
            }
        });
        
        item2.setOnAction(item1.getOnAction());
        item3.setOnAction(item1.getOnAction());
        item4.setOnAction(item1.getOnAction());
        item5.setOnAction(item1.getOnAction());
        item6.setOnAction(item1.getOnAction());
        
        generateFunction.getItems().add(item1);
        generateFunction.getItems().add(item2);
        generateFunction.getItems().add(item3);
        generateFunction.getItems().add(item4);
        generateFunction.getItems().add(item5);
        generateFunction.getItems().add(item6);
    }
    
    public void arithmeticFunctionFunction(ActionEvent a){
        MenuItem item = (MenuItem) a.getSource();
        functionsR2R.setText(item.getText());
        
        generateFunction.getItems().clear();
       
        MenuItem item1 = new MenuItem("join");
        MenuItem item2 = new MenuItem("subtract");
        MenuItem item3 = new MenuItem("split");
        MenuItem item4 = new MenuItem("divide");
        MenuItem item5 = new MenuItem("integer");
        MenuItem item6 = new MenuItem("mod");
        
        generateFunction.getItems().add(item1);
        generateFunction.getItems().add(item2);
        generateFunction.getItems().add(item3);
        generateFunction.getItems().add(item4);
        generateFunction.getItems().add(item5);
        generateFunction.getItems().add(item6);
    }
    
    public void xpathFunctionFunction(ActionEvent a){
        MenuItem item = (MenuItem) a.getSource();
        functionsR2R.setText(item.getText());
        
        generateFunction.getItems().clear();
       
        MenuItem item1 = new MenuItem("xpath:abs");
        MenuItem item2 = new MenuItem("xpath:ceiling");
        MenuItem item3 = new MenuItem("xpath:floor");
        MenuItem item4 = new MenuItem("xpath:round");
        MenuItem item5 = new MenuItem("xpath:round-half-to-even");
        MenuItem item6 = new MenuItem("xpath:codepoints-to-string");
        MenuItem item7 = new MenuItem("xpath:string-to-codepoints");
        MenuItem item8 = new MenuItem("xpath:compare");
        MenuItem item9 = new MenuItem("xpath:codepoint-equal");
        MenuItem item10 = new MenuItem("xpath:concat");
        MenuItem item11 = new MenuItem("xpath:string-join");
        MenuItem item12 = new MenuItem("xpath:substring");
        MenuItem item13 = new MenuItem("xpath:string-length");
        MenuItem item14 = new MenuItem("xpath:normalize-space");
        MenuItem item15 = new MenuItem("xpath:normalize-unicode");
        MenuItem item16 = new MenuItem("xpath:upper-case");
        MenuItem item17 = new MenuItem("xpath:lower-case");
        MenuItem item18 = new MenuItem("xpath:translate");
        MenuItem item19 = new MenuItem("xpath:encode-for-uri");
        MenuItem item20 = new MenuItem("xpath:iri-to-uri");
        MenuItem item21 = new MenuItem("xpath:escape-html-uri");
        MenuItem item22 = new MenuItem("xpath:contains");
        MenuItem item23 = new MenuItem("xpath:starts-with");
        MenuItem item24 = new MenuItem("xpath:ends-with");
        MenuItem item25 = new MenuItem("xpath:substring-before");
        MenuItem item26 = new MenuItem("xpath:substring-after");
        MenuItem item27 = new MenuItem("xpath:matches");
        MenuItem item28 = new MenuItem("xpath:replace");
        MenuItem item29 = new MenuItem("xpath:tokenize");
        
        generateFunction.getItems().add(item1);
        generateFunction.getItems().add(item2);
        generateFunction.getItems().add(item3);
        generateFunction.getItems().add(item4);
        generateFunction.getItems().add(item5);
        generateFunction.getItems().add(item6);
    }
    
    private void closeFunctionText() {
        String tmp = textFunction.getText();
        
        
        int qtdPar = tmp.split("[(]").length;

        while (qtdPar > 1) {
            tmp += " ,";
            qtdPar--;
        }
        textFunction.setText(tmp);
    }

    private void dispose() {
        loadtransFunction.close();
    }

    private void desabilitaSave() {
        saveFunction.setDisable(true);
    }

    private void habilitaSave() {
        saveFunction.setDisable(false);
    }

}
