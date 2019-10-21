/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.MappingConfiguration;
import com.br.ufc.arida.r2rbyassertions.model.OCA;
import com.br.ufc.arida.r2rbyassertions.model.ObjectOWL;
import com.br.ufc.arida.r2rbyassertions.model.ObjetoPublico;
import com.br.ufc.arida.r2rbyassertions.model.Property;
import com.br.ufc.arida.r2rbyassertions.model.TabPaneCA;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.Conexao;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.MappingConfigurationDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TbpaneOCAController extends TbpaneController implements Initializable {

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

    MappingConfiguration mc = null;

    // private final Image img = new Image("/imgB/add.png");
    public static Stage loadSStage;
    public static final Stage FilterStage = new Stage(StageStyle.UTILITY);

    public static boolean isOntoSourceInitialized = false;

    ObservableList<CA> dataAssertions = FXCollections.observableArrayList();
    ArrayList<CA> g = new ArrayList<>();

    public static TbpaneOCAController tbOCA;

    private boolean isInitialized = false;
    private Property currentProperty;
    /**
     * Initializes the controller class.
     *
     */
    public TbpaneOCAController() {
        super();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbOCA = this;
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file '/fxml/TbpaneOCA.fxml'.";

        assertionsList.setItems(dataAssertions);

        //ImageView imgview = new ImageView(img);
        // add.setGraphic(imgview);
    }

    @FXML
    private void filterOCA(ActionEvent action){
        try {
            TreeItem item = LoadSourceOntologyController.loadsource.ontoTreeS.getSelectionModel().getSelectedItem();
            this.currentProperty = ((Property) item.getValue());
            
            FilterController.show(tbOCA);
        } catch (IOException ex1) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    @FXML
    private void generateR2RFired(MouseEvent arg0) {

        OCA ca = (OCA) assertionsList.getSelectionModel().getSelectedItem();

        MainController.m.textAreaTeste.setText(ca.pattenrsAC());
        MainController.m.textAreaMR.setText(ca.mappingRuler());

        try {
            Conexao.getInstance().insertsFile(MainController.m.textAreaTeste.getText(), Conexao.ARQ_R2RMAPPING, true);
        } catch (IOException ex) {
            Logger.getLogger(TbpaneOCAController.class.getName()).log(Level.SEVERE, null, ex);
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
        //System.out.println("Este print foi no TbpaneCCA linha 143: " + this.getClass().getSimpleName() + ".initialize");
        /*SETANDO O STAGE DA CLASSE PAI*/
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
    protected void addFilter(String _text) {
        String tmp = txtAssertion.getText() + " / " + _text;
        final TreeItem selectedItem = MainController.m.ontoTreeT.getSelectionModel().getSelectedItem();
        
        OCA ultCA = (OCA)((ObjectOWL) selectedItem.getValue()).getLastCA();
        ultCA.setFilter(_text);
        
        txtAssertion.setText(tmp);
    }
    
    @Override
    public Property getCurrentProperty() {
        return this.currentProperty;
    }
    
   // private void addFilterFired(ActionEvent event) throws IOException {
        /* CCA cca = (CCA) assertions.get(ontoTreeT.getSelectionModel().getSelectedItem());

     NewFilterController.nm.lblTable.setText("Table: " + cca.getRelationName());
     DatabaseSchemaInspector schema = DbConnection.getSchemaInspector(mc.getDatabaseDriver(), mc.getDatabaseUrl(), mc.getDatabaseUser(), mc.getDatabasePassword());

     RelationName relationName = new RelationName(null, cca.getRelationName());

     List<Attribute> listCols = schema.listColumns(relationName);
     final ObservableList<String> items = NewFilterController.nm.cbxColumn.getItems();
     items.clear();
     for (Attribute attribute : listCols) {
     items.add(attribute.attributeName() + " [" + schema.columnType(attribute).name() + "]");
     }*/

    //   FilterStage.setTitle("Add a Selection Filter");
    //  FilterStage.show();
    //}

    /**
     * Called when the Save button is fired.
     *
     * @param event the action event.
     * @throws java.io.IOException
     */

    /* public void addAssertions(ActionEvent event) throws IOException {

     final TreeItem selectedItem = MainController.m.ontoTreeT.getSelectionModel().getSelectedItem();

     List<CA> listCCA = MainController.m.assertions.get(selectedItem);
     for (CA ca : listCCA) {

     OCA ca2 = (OCA) ca;
     //ca2.setdPropertyS(((DCA)ca).getdPropertyS());
     //ca2.setdPropertyT(((DCA)ca).getdPropertyT());

     ca2.setIdList(assertionsList.getItems().size() + 1);

     dataAssertions.add(ca2);
     assertionsList.getSelectionModel().select(ca2);
     Conexao.getInstance().insertsFile(txtAssertion.getText(), Conexao.ARQ_ASSERTIONS, true);
     txtAssertion.setText("");
     }
     }*/
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
        Conexao.getInstance().insertsFile(txtAssertion.getText(), Conexao.ARQ_ASSERTIONS, true);
        txtAssertion.setText("");
    }

    @Override
    protected void addTransformationFunction(String _text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}


	

