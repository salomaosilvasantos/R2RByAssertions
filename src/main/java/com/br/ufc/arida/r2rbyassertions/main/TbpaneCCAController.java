/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.CCA;
import com.br.ufc.arida.r2rbyassertions.model.Class_;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;
import com.br.ufc.arida.r2rbyassertions.model.MappingConfiguration;
import com.br.ufc.arida.r2rbyassertions.model.ObjectOWL;
import com.br.ufc.arida.r2rbyassertions.model.ObjetoPublico;
import com.br.ufc.arida.r2rbyassertions.model.Property;
import com.br.ufc.arida.r2rbyassertions.model.TabPaneCA;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.Conexao;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.MappingConfigurationDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Tiagovinuto
 */
public class TbpaneCCAController extends TbpaneController implements Initializable {

    @FXML
    Button add;

    @FXML
    Button add1;

    @FXML
    Button Filter;

    @FXML
    protected TextField txtAssertion;

    @FXML
    protected ListView<CA> assertionsList;

    @FXML
    Label lblAssertion;

    private Property currentProperty;
    private Class_ currentClass;
    private List<Property> listProperty;
            
    MappingConfiguration mc = null;

    // private final Image img = new Image("/imgB/add.png");
    public static Stage loadSStage;

    public TbpaneCCAController() {
        super();
    }

    public static boolean isOntoSourceInitialized = false;

    ObservableList<CA> dataAssertions = FXCollections.observableArrayList();
    int contP = 0;
    public static TbpaneCCAController tbCCA;

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       // tbCCA = this;
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file '/fxml/TbpaneCCA.fxml'.";

        assertionsList.setItems(dataAssertions);
        //ImageView imgview = new ImageView(img);
        // add.setGraphic(imgview);
    }

    @FXML
    private void filterCCA(ActionEvent action) {
        try {
            TreeItem item = LoadSourceOntologyController.loadsource.ontoTreeS.getSelectionModel().getSelectedItem();
            if(item.getValue() instanceof DataProperty){
                this.currentProperty = (DataProperty) item.getValue();
            }else if(item.getValue() instanceof Class_) {
                this.currentClass = ((Class_) item.getValue());
            }

            FilterController.show(tbCCA);
        } catch (IOException ex1) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    
    public Class_ getCurrentClass(){
        return this.currentClass;
    }

    @FXML
    private void generateR2RFired(MouseEvent arg0) {

        CCA ca = (CCA) assertionsList.getSelectionModel().getSelectedItem();

        MainController.m.textAreaTeste.setText(ca.pattenrsAC());
        MainController.m.textAreaMR.setText(ca.mappingRuler());

        try {
            Conexao.getInstance().insertsFile(MainController.m.textAreaTeste.getText(), Conexao.ARQ_R2RMAPPING, true);
        } catch (IOException ex) {
            Logger.getLogger(TbpaneDCAController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        try {
            loadSStage = new Stage(StageStyle.UTILITY);

            
            AnchorPane page = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/LoadSourceOntology.fxml"));
            Scene scene = new Scene(page);
            loadSStage.setScene(scene);
            loadSStage.initModality(Modality.APPLICATION_MODAL);

            loadSStage.initOwner(R2rByAsseertions.pStage);

            MappingConfigurationDAO mcDAO = new MappingConfigurationDAO();

            int idmax;
            try {
                idmax = mcDAO.lastRegister();
                mc = mcDAO.findById(idmax);
                LoadSourceOntologyController.loadsource.buildOntoTreeSource(mc);
            } catch (SQLException ex) {
                Logger.getLogger(TbpaneCCAController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Este print foi no TbpaneCCA linha 143: " + this.getClass().getSimpleName() + ".initialize");
        TabPaneCA.tPaneCA.setLoadSStage(loadSStage);
    }

    @FXML
    public void loadS(ActionEvent event) {
        add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!ObjetoPublico.isOntoSourceInitialized()) {
                    init();
                    ObjetoPublico.setOntoSourceInitialized(true);
                }

                TabPaneCA.getLoadSStage().show();
            }
        });
    }

    @Override
    public Property getCurrentProperty() {
        return this.currentProperty;
    }
    public void addAssertions(ActionEvent event) throws IOException {

        final TreeItem selectedItem = MainController.m.ontoTreeT.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            CA ultCA = ((ObjectOWL) selectedItem.getValue()).getLastCA();

            if (CA.addCA(ultCA)) {
                dataAssertions.add(ultCA);
                ObjetoPublico.addAssertion(ultCA);
                Conexao.getInstance().insertsFile(txtAssertion.getText(), Conexao.ARQ_ASSERTIONS, true);
            }
        }

        txtAssertion.setText("");
    }

    @Override
    protected void addTransformationFunction(String _text) {
        
    }

    @Override
    protected void addFilter(String _text) {
        String tmp = txtAssertion.getText() + " / " + _text;
        final TreeItem selectedItem = MainController.m.ontoTreeT.getSelectionModel().getSelectedItem();

        CCA ultCA = (CCA) ((ObjectOWL) selectedItem.getValue()).getLastCA();
        ultCA.setFilter(_text);

        txtAssertion.setText(tmp);
    }
}
