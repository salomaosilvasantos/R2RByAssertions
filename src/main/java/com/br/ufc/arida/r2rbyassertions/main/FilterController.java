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

import com.br.ufc.arida.r2rbyassertions.model.CCA;
import com.br.ufc.arida.r2rbyassertions.model.Property;

/**
 * FXML Controller class
 *
 * @author Tiagovinuto
 */
public class FilterController implements Initializable {

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
    Button optionalBtn;

    @FXML
    MenuButton filterPropertyMenu;

    @FXML
    MenuButton xsdBtn;

    @FXML
    MenuButton unaryFun;

    //@FXML
    //MenuButton unaryFunctionF2Btn;
    @FXML
    MenuButton binaryFun;

    @FXML
    TextField typeValue;

    private String filterScree = "";

    public String getFilter() {
        return this.filterScree;
    }

    public static FilterController filter;

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
        filter = this;

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
        AnchorPane a_pane = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/draftFilter.fxml"));
        filter.tbPane = _tbPane;
        Scene sceneFilter = new Scene(a_pane);
        loadFilter.setScene(sceneFilter);
        loadFilter.initModality(Modality.APPLICATION_MODAL);
        loadFilter.initOwner(R2rByAsseertions.pStage);

        addCurrentProperty();
        addCurrentClass();
    }

    protected static void show(TbpaneController _tbPane) throws IOException {
        initFilter(_tbPane);

        loadFilter.setTitle("Filter");
        loadFilter.setResizable(false);
        loadFilter.show();
    }

    /**
     * *********
     * PROPERTY * *********
     */
    @FXML
    private static void addCurrentProperty() {

        for (Property _prop : filter.tbPane.getListProperty()) {
            MenuItem _menuProp = new MenuItem();
            _menuProp.setText(_prop.getName());

            _menuProp.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    filter.filterPropertyFired(t);
                }
            });
            filter.filterPropertyMenu.getItems().add(_menuProp);
        }
    }

    @FXML
    private static void addCurrentClass() {
        CCA ca = new CCA();
        if (filter.tbPane.getCurrentClass() != null) {
            for (Property _prop : filter.tbPane.getCurrentClass().getdProperties()) {

                MenuItem _menuProp = new MenuItem();
                _menuProp.setText(_prop.getName());
                
                _menuProp.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {
                        
                        filter.filterPropertyFired(t);
                    }
                });
                filter.filterPropertyMenu.getItems().add(_menuProp);
            }

            /* OBJECT PROPERTIES 
        
        
             for (Property _prop : filter.tbPane.getCurrentClass().getoProperties()) {
             MenuItem _menuProp = new MenuItem();
             _menuProp.setText(_prop.getName());
             _menuProp.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
             filter.filterPropertyFired(t);
             }
             });

             filter.filterPropertyMenu.getItems().add(_menuProp);
             }*/
        }
    }
    
    @FXML
    public void filterPropertyFired(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = menuItem.getText();
        
        filter.tbPane.getCurrentClass().addPropertyFilter(tmp);
        
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

    /**
     * Called when the Cancel button is fired.
     *
     * @param event the action event.
     * @throws java.io.IOException
     */
    @FXML
    public void cancelFired(ActionEvent event) throws IOException {
        dispose(); //Não tá funcionando 
    }

    /**
     * Called when the Save button is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void saveFired(ActionEvent event) throws IOException {
        tbPane.addFilter(txtFilter.getText());
        dispose();
    }


    @FXML
    public void openParenthesisFired(ActionEvent event) throws IOException {
        String filter = txtFilter.getText();

        if (!"".equals(filter.trim())) {
            txtFilter.setText("(");
        } else {
            if (filter.trim().endsWith(")")) {
                filter += " AND\n(";
            } else if (filter.trim().endsWith("AND") || filter.trim().endsWith("OR")) {
                filter += "\n(";
            }

            txtFilter.setText(filter);
        }
    }

    /**
     * Called when the ) button is fired.
     *
     * @param event the action event.
     */
    @FXML
    public void closeParenthesisFired(ActionEvent event) throws IOException {
        String filter = txtFilter.getText();

        if (!"".equals(filter.trim())
                && !filter.trim().endsWith("(")
                && !filter.trim().endsWith("AND")
                && !filter.trim().endsWith("OR") && hasOpenedParenthesis(filter)) {
            txtFilter.setText(filter + ")");
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
     * OPTIONAL * *********
     */
    @FXML
    public void optionalFired(ActionEvent event) throws IOException {
        String filter = txtFilter.getText();

        if (fSteps.addOptional()) {
            if ("".equals(filter.trim())) {
                addText("Optional (");
            }
        }
    }

    /**
     * *********
     * NOT * *********
     */
    @FXML
    public void notFired(ActionEvent event) throws IOException { // Falta consertar
        String filter = txtFilter.getText();

        if (fSteps.addNot()) {
            addText(" !");
        }
    }

    @FXML
    private boolean hasOpenedParenthesis(String filter) {
        boolean ret = false;
        for (int i = 0; i < filter.length(); i++) {
            if (filter.charAt(i) == '(') {
                ret = true;
            } else if (filter.charAt(i) == ')' && ret) {
                ret = false;
            }
        }

        return ret;
    }

    @FXML
    private void functFilterUnary(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = "";

        if (fSteps.addFunctionUnary()) {
            if (unaryFun.getItems().contains(menuItem)) {
                unaryFun.setText(menuItem.getText());
            }
            tmp = menuItem.getText() + "(";
            tmp += filterPropertyMenu.getText() + ")";
            addText(tmp);
        }
    }

    @FXML
    private void functFilterBinary(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        String tmp = "";

        if (fSteps.addFunctionBinary()) {
            if (binaryFun.getItems().contains(menuItem)) {
                binaryFun.setText(menuItem.getText());
            }
            tmp = menuItem.getText() + "(";
            addText(tmp);
        }
    }

    @FXML
    private void filterValue(ActionEvent e) {
        //JOptionPane.showMessageDialog(null,"estou filter value");
        xsdBtn.setDisable(false);
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

        //tmp = typeValue.getText();
        tmp += "^^" + menuItem.getText();

        if (xsdBtn.getItems().contains(menuItem)) {
            xsdBtn.setText(menuItem.getText());

            if (fSteps.addTypeValue(tmp)) {
            }
        }
    }

    private void dispose() {
        removeCurrentProperties();
        loadFilter.close();
    }

    private void removeCurrentProperties() {
        tbPane.clearProperties();
    }

    private void addText(String _text) {
        txtFilter.setText(txtFilter.getText() + "" + _text);
    }

    private static class FilterSteps {

        public static final int QUERY = 1;
        public static final int COND = 2;

        private static final int OPTIONAL = 3;
        private static final int PROPERTY = 4;
        private static final int UNARY_FUNCTION = 5;
        private static final int UNARY_FUNCTION_F2 = 6;
        private static final int BINARY_FUNCTION = 7;
        private static final int AND = 8;
        private static final int OR = 9;
        private static final int NOT = 12;
        private static final int VALUE = 10;
        private static final int OPERATOR = 11;

        private String filter;
        private FilterController fController;
        private List<Integer> pilha;

        public FilterSteps(FilterController _fController) {
            this.fController = _fController;
            filter = "";
            pilha = new ArrayList<Integer>();
            initSteps();
        }

        public void nextStep(int _step) {
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

                case FilterSteps.QUERY: {
                    queryStep();
                    break;
                }

                case FilterSteps.COND: {
                    condStep();
                    break;
                }

                case FilterSteps.UNARY_FUNCTION: {
                    unaryFuncStep();
                    break;
                }

                case FilterSteps.UNARY_FUNCTION_F2: {
                    unaryFuncF2Step();
                    break;
                }

                case FilterSteps.BINARY_FUNCTION: {
                    binaryFuncStep();
                    break;
                }

                case FilterSteps.PROPERTY: {
                    propertyStep();
                    break;
                }

                case FilterSteps.OPERATOR: {
                    operatorStep();
                    break;
                }

                case FilterSteps.VALUE: {
                    valueStep();
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
            // QUERY STEP

            if (pilha.isEmpty()) {
                nextStep(PROPERTY);
                fController.addText("?" + _prop);
                return pilha.add(PROPERTY);
            }

            if (last() == OPTIONAL) {
                nextStep(COND);

                fController.addText(_prop + ") / ");

                return pilha.add(PROPERTY);
            }
            // FUNCT STEP
            if (last() == BINARY_FUNCTION) {

                fController.addText(_prop + " ,");

                return pilha.add(PROPERTY);
            }

//            if (last() == PROPERTY) {
//                nextStep(PROPERTY);
//
//                fController.addText(_prop + " ");
//                return pilha.add(PROPERTY);
//            }
            return false;
        }

        public boolean addOptional() {
            if (pilha.isEmpty()) {
                return pilha.add(FilterSteps.OPTIONAL);
            }
            return false;
        }

        public boolean addFunctionUnary() {
            if (last() == AND || last() == OR || last() == NOT || last() == PROPERTY || last() == OPTIONAL) {

                nextStep(UNARY_FUNCTION);
                return pilha.add(UNARY_FUNCTION);
            }
            return false;
        }

        public boolean addUnaryFunctionF2(String _function) {
            if (last() == AND || last() == OR || last() == NOT || last() == BINARY_FUNCTION || last() == PROPERTY || last() == OPTIONAL) {

                if (last() == BINARY_FUNCTION) {

                    fController.addText(_function + " , ");
                    nextStep(BINARY_FUNCTION);
                } else {
                    fController.addText(_function + " ) ");
                    nextStep(UNARY_FUNCTION_F2);
                }
                return pilha.add(UNARY_FUNCTION_F2);
            }
            return false;
        }

        public boolean addFunctionBinary() {
            if (last() == AND || last() == OR || last() == NOT || last() == PROPERTY || last() == OPTIONAL) {

                nextStep(BINARY_FUNCTION);
                return pilha.add(BINARY_FUNCTION);
            }
            return false;
        }

        public boolean addAND() {
            if (last() == UNARY_FUNCTION || last() == BINARY_FUNCTION || last() == VALUE) {
                nextStep(AND);

                return pilha.add(AND);
            }
            return false;
        }

        public boolean addOR() {
            if (last() == UNARY_FUNCTION || last() == BINARY_FUNCTION || last() == VALUE) {

                return pilha.add(OR);
            }
            return false;
        }

        private boolean addNot() {
            if (last() == OPTIONAL || last() == PROPERTY || last() == AND || last() == OR) {
                return pilha.add(NOT);
            }
            return false;
        }

        private boolean addOperator() {
            if (last() == UNARY_FUNCTION_F2 || last() == PROPERTY) {
                nextStep(OPERATOR);
                return pilha.add(OPERATOR);
            }
            return false;
        }

        public boolean addTypeValue(String _value) {
            if (last() == PROPERTY || last() == UNARY_FUNCTION_F2) {

                fController.addText(_value);

                nextStep(VALUE);
                return pilha.add(VALUE);
            }
            if (last() == OPERATOR) {
                fController.addText(" " + _value);

                nextStep(VALUE);
                return pilha.add(VALUE);
            }
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
            fController.unaryFun.setDisable(false);
            fController.binaryFun.setDisable(false);
            fController.notBnt.setDisable(false);

            fController.optionalBtn.setDisable(true);
        }

        private void valueStep() {
            fController.andFired.setDisable(false);
            fController.orFired.setDisable(false);
            
            fController.xsdBtn.setDisable(false);
        }

        private void unaryFuncStep() {
            fController.andFired.setDisable(false);
            fController.orFired.setDisable(false);

            //fController.unaryFunctionF2Btn.setDisable(false);
            fController.optionalBtn.setDisable(true);
        }

        private void unaryFuncF2Step() {
            fController.cbxOperator.setDisable(false);

            fController.typeValue.setDisable(false);
            fController.optionalBtn.setDisable(true);
            fController.xsdBtn.setDisable(false);
        }

        private void binaryFuncStep() {
            fController.unaryFun.setDisable(true);
            fController.typeValue.setDisable(false);
            fController.xsdBtn.setDisable(false);

            //fController.unaryFunctionF2Btn.setDisable(false);
            fController.andFired.setDisable(true);
            fController.orFired.setDisable(true);
        }

        private void initSteps() {
            fController.cbxOperator.setDisable(true);
            fController.binaryFun.setDisable(true);
            fController.unaryFun.setDisable(true);
            fController.typeValue.setDisable(true);
            fController.xsdBtn.setDisable(true);

            fController.andFired.setDisable(false);
            fController.orFired.setDisable(true);
            fController.notBnt.setDisable(true);

            //fController.unaryFunctionF2Btn.setDisable(true);
        }

        private void and_orStep() {
            fController.typeValue.setDisable(true);
            fController.xsdBtn.setDisable(true);
            fController.cbxOperator.setDisable(true);

            fController.binaryFun.setDisable(false);
            fController.unaryFun.setDisable(false);
            fController.andFired.setDisable(true);
            fController.orFired.setDisable(true);
        }

        private void operatorStep() {
            fController.unaryFun.setDisable(true);
            fController.binaryFun.setDisable(true);

            fController.andFired.setDisable(true);
            fController.orFired.setDisable(true);

            fController.typeValue.setDisable(false);
            fController.xsdBtn.setDisable(true);
        }

        //P Op A
        private void propertyStep() {
            fController.cbxOperator.setDisable(false);
            fController.unaryFun.setDisable(true);
            fController.binaryFun.setDisable(true); //Mudei aqui
        }
    }
}
