/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

import com.br.ufc.arida.r2rbyassertions.model.Property;

/**
 *
 * @author Tiagovinuto
 */
public class FilterDCAController implements Initializable{
    @FXML
    ComboBox<String> cbxOperator;

    @FXML
    TextField txtFilter;

    @FXML
    Button orFired;

    @FXML
    Button andFired;

    @FXML
    Button notBnt;

    @FXML
    Button cancel;

    @FXML
    Button save;

    @FXML
    MenuButton filterPropertyMenu;
    
    @FXML
    MenuButton unaryFunctionF2Btn;

    @FXML
    MenuButton xsdBtn;

    @FXML
    TextField typeValue;

    private String filterScree = "";

    public String getFilter() {
        return this.filterScree;
    }

    public static FilterDCAController filterDCA;

    private static Stage loadFilter;

    private TbpaneController tbPane;
    private FilterSteps fSteps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file '/fxml/NewMapping.fxml'.";
        filterDCA = this;

        fSteps = new FilterSteps(this);
        System.out.println(this.getClass().getSimpleName() + ".initialize");
        
        initHandlers();  
    }
    
    public void initHandlers() {
        typeValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    addText(typeValue.getText());
                }
            }
        });   
    }
    
    private static void initFilter(TbpaneController _tbPane) throws IOException {
        loadFilter = new Stage(StageStyle.UTILITY);
        AnchorPane a_pane = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/FilterDCA.fxml"));
        filterDCA.tbPane = _tbPane;
        Scene sceneFilter = new Scene(a_pane);
        loadFilter.setScene(sceneFilter);
        loadFilter.initModality(Modality.APPLICATION_MODAL);
        loadFilter.initOwner(R2rByAsseertions.pStage);

        addCurrentProperty();
    }

    protected static void show(TbpaneController _tbPane) throws IOException {
        initFilter(_tbPane);

        loadFilter.setTitle("Filter");
        loadFilter.setResizable(false);
        loadFilter.show();
    }

    /**
     * *********
     * PROPERTY *
    **********
     */
    @FXML
    private static void addCurrentProperty() {
        for (Property _prop : filterDCA.tbPane.getListProperty()) {
            MenuItem _menuProp = new MenuItem();
            _menuProp.setText(_prop.getName());
            _menuProp.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    filterDCA.filterPropertyFired(t);
                }
            });
            filterDCA.filterPropertyMenu.getItems().add(_menuProp);
        }
    }

    /**
     * Called when the Cancel button is fired.
     *
     * @param event the action event.
     * @throws java.io.IOException
     */
    @FXML
    public void cancelFired(ActionEvent event) throws IOException {
        dispose(); 
    }

    /**
     * Called when the Save button is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void saveFired(ActionEvent event) throws IOException {
        tbPane.addFilter(txtFilter.getText()); // Adiciona o Filtro na assertiva aqui!!!
        dispose();
    }

    @FXML
    public void filterPropertyFired(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = menuItem.getText();

        if (fSteps.addProperty(tmp)) {
            if (filterPropertyMenu.getItems().contains(menuItem)) {
                filterPropertyMenu.setText(menuItem.getText());
            }
            if (txtFilter.getText().contains("(")) {
                tmp += ")";
            }
            tmp += " / ";
        }
    }

    
    @FXML
    private void unaryFunctionF2(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = "";

        if (fSteps.addUnaryFunctionF2(menuItem.getText())) {
            if (unaryFunctionF2Btn.getItems().contains(menuItem)) {
                unaryFunctionF2Btn.setText(menuItem.getText());
            }
            
            tmp = menuItem.getText() + "(";
            tmp += filterPropertyMenu.getText() + ")";
        }
    }

    /**
     * Called when the AND button is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void andFired(ActionEvent event) throws IOException {
        String filter = txtFilter.getText();

        boolean a1 = "".equals(filter.trim());
        boolean a2 = filter.trim().endsWith("AND");
        boolean a3 = filter.trim().endsWith("(");
        boolean a4 = filter.trim().endsWith("OR");

        if (fSteps.addAND()) {
            addText(" AND (");
        }
    }

    private boolean canAddANDOR(String filter) {
        return !"".equals(filter.trim()) && !filter.trim().endsWith("AND")
                && !filter.trim().endsWith("(")
                && !filter.trim().endsWith("OR");
    }

    /**
     * Called when the OR button is fired.
     *
     * @param event the action event.
     */
    
    @FXML
    public void orFired(ActionEvent event) throws IOException {
        String filter = txtFilter.getText();

        if (fSteps.addOR()) {
            addText(" OR (");
        }
    }


    /**
     * *********
     * NOT *
    **********
     */
    @FXML
    public void notFired(ActionEvent event) throws IOException { // Falta consertar
        String filter = txtFilter.getText();

        if (fSteps.addNot()) {
            addText(" !");
        }
    }

    @FXML
    private void filterOperator(ActionEvent e) {
        String selected = cbxOperator.getSelectionModel().getSelectedItem();

        if (fSteps.addOperator()) {
            cbxOperator.getEditor().setText(selected);
            addText(selected);
        }
    }

    @FXML
    private void filterXsd(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = "";

        tmp = typeValue.getText();
        tmp += menuItem.getText();

        if (xsdBtn.getItems().contains(menuItem)) {
            xsdBtn.setText(menuItem.getText());

            if (fSteps.addTypeValue(tmp)) {
            }

        }
    }

    private void dispose() {
        loadFilter.close();
    }
    
    @FXML
    private void filterValue(ActionEvent e) {
    }

    private void addText(String _text) {
        txtFilter.setText(txtFilter.getText() + "" + _text);
    }

    private static class FilterSteps {

       
        public static final int COND = 1;
        private static final int PROPERTY = 2;
        private static final int UNARY_FUNCTION_F2 = 3;
        
        private static final int AND = 4;
        private static final int OR = 5;
        private static final int NOT = 6;
        private static final int VALUE = 7;
        private static final int OPERATOR = 8;
        private static final int XSD = 9;

        private String filter;
        private FilterDCAController fController;
        private List<Integer> pilha;

        public FilterSteps(FilterDCAController _fController) {
            this.fController = _fController;
            filter = "";
            pilha = new ArrayList<Integer>();
            initSteps();
        }

        public void nextStep(int _step, String _function) {
            switch (_step) {
                case FilterSteps.AND: {
                    and_orStep();
                    break;
                }

                case FilterSteps.OR: {
                    and_orStep();
                    break;
                }

                case FilterSteps.NOT: {
                    //and_orStep();
                    break;
                }
                case FilterSteps.COND: {
                    condStep();
                    break;
                }

                case FilterSteps.UNARY_FUNCTION_F2: {
                    unaryFuncF2Step(_function);
                    break;
                }

                case FilterSteps.PROPERTY: {
                    propertyStep();
                    break;
                }

                case FilterSteps.OPERATOR: {
                    operatorStep(_function);
                    break;
                }

                case FilterSteps.VALUE: {
                    valueStep(_function);
                    break;
                }
                
                case FilterSteps.XSD:{
                
                    break;
                }
                
                default:
                    break;
            }
        }

        /**
         * *********************************
         * FUNÇÕES ADD * *********************************
         */
        public boolean addProperty(String _prop) {
       
            if(last() == UNARY_FUNCTION_F2){
                nextStep(PROPERTY,"");
                fController.addText("?" + _prop + ")");
                return pilha.add(PROPERTY);
            }
  
            return false;
        }        
                
        public boolean addUnaryFunctionF2(String _function) {
            
            if(pilha.isEmpty()){
                 fController.addText(_function + " (");
                 nextStep(UNARY_FUNCTION_F2,_function);
                 
                 return pilha.add(UNARY_FUNCTION_F2);
            }
            return false;
        }

        public boolean addAND() {
            if (last() ==  XSD || last() == VALUE) {
                nextStep(AND,"");

                return pilha.add(AND);
            }
            return false;
        }

        public boolean addOR() {
            if (last() ==  XSD || last() == VALUE) {

                return pilha.add(OR);
            }
            return false;
        }

        private boolean addNot() {
            if (last() == PROPERTY || last() == AND || last() == OR) {
                return pilha.add(NOT);
            }
            return false;
        }

        private boolean addOperator() {
            if (last() == UNARY_FUNCTION_F2 || last() == PROPERTY) {
                nextStep(OPERATOR,"");
                return pilha.add(OPERATOR);
            }
            return false;
        }

        public boolean addTypeValue(String _value) {
            if (last() == PROPERTY || last() == UNARY_FUNCTION_F2) {

                fController.addText(_value);

                nextStep(VALUE,_value);
                return pilha.add(VALUE);
            }
            if (last() == OPERATOR) {
                fController.addText(" " + _value);

                nextStep(VALUE,"");
                return pilha.add(VALUE);
            }
            
            /*
            SeuTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                   if (event.getCode() == KeyCode.ENTER) {
                     //Sua Ação... 
                   }
                }
             });
            */
            
            return false;
        }

        /**
         * *********************************
         * STEPS *********************************
         */
        private int last() {
            return pilha.get(pilha.size() - 1);
        }

        private void queryStep() {

        }

        private void condStep() {
            fController.unaryFunctionF2Btn.setDisable(false);
            fController.notBnt.setDisable(false);
            //fController.optionalBtn.setDisable(true);
        }

        private void valueStep(String _function) {
           JOptionPane.showMessageDialog(null, fController.unaryFunctionF2Btn.getText());
            if(_function.equals("lang"))
                fController.xsdBtn.setDisable(true);
            else
                fController.xsdBtn.setDisable(false);
                fController.andFired.setDisable(false);
                fController.orFired.setDisable(false);
        }
        
        private void unaryFuncF2Step(String _function) {
            fController.cbxOperator.setDisable(true);

            fController.typeValue.setDisable(true);
            //fController.optionalBtn.setDisable(true);
            //fController.xsdBtn.setDisable(true);
            fController.filterPropertyMenu.setDisable(false);      
        }

        private void initSteps() {
            fController.cbxOperator.setDisable(true);
            //fController.typeValue.setDisable(true);
            fController.xsdBtn.setDisable(true);
            fController.filterPropertyMenu.setDisable(true);

            fController.andFired.setDisable(true);
            fController.orFired.setDisable(true);
            fController.notBnt.setDisable(true);

        }

        private void and_orStep() {
            fController.typeValue.setDisable(true);
            fController.xsdBtn.setDisable(true);
            fController.cbxOperator.setDisable(true);


            fController.andFired.setDisable(true);
            fController.orFired.setDisable(true);
        }

        private void operatorStep(String _function) {
            fController.andFired.setDisable(true);
            fController.orFired.setDisable(true);

            fController.typeValue.setDisable(false);
            
        }

        //P Op A
        private void propertyStep() {
            fController.cbxOperator.setDisable(false);
            fController.unaryFunctionF2Btn.setDisable(true);
            fController.notBnt.setDisable(false);
        }
        
        private void xsdSteps(){
        
        }
    }
}
