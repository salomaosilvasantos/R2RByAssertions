/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.CCA;
import com.br.ufc.arida.r2rbyassertions.model.Class_;
import com.br.ufc.arida.r2rbyassertions.model.DCA;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;
import com.br.ufc.arida.r2rbyassertions.model.Debug;
import com.br.ufc.arida.r2rbyassertions.model.MapNodes;
import com.br.ufc.arida.r2rbyassertions.model.MappingConfiguration;
import com.br.ufc.arida.r2rbyassertions.model.OCA;
import com.br.ufc.arida.r2rbyassertions.model.ObjProperty;
import com.br.ufc.arida.r2rbyassertions.model.ObjectOWL;
import com.br.ufc.arida.r2rbyassertions.model.ObjetoPublico;
import com.br.ufc.arida.r2rbyassertions.model.Prefix;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.Conexao;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.MappingConfigurationDAO;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.MaxCardinalityRestriction;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import javafx.scene.layout.HBox;

/**
 *
 * @author Tiagovinuto
 */
public class MainController implements Initializable {
    
    @FXML //  fx:id="ontoTreeTarget"
    protected TreeView<ObjectOWL> ontoTreeT;

    @FXML
    MenuBar manager;

    @FXML
    MenuItem newMapping;

    @FXML
    MenuItem AcList;

    @FXML
    TabPane tabPane;

    @FXML
    TextArea textAreaTeste;

    @FXML
    TextArea textAreaMR;

    @FXML
    Pane borderPaneCAEditor;
    
    @FXML
    HBox hboxEdit;
    
    public static MainController m;
    public static int contadorCCA = 0;

    public static final Stage secondaryStage = new Stage(StageStyle.UTILITY); // Usada para chamar a tela NewMapping
    public static final Stage secondaryStage1 = new Stage(StageStyle.UTILITY); // Usada para chamar a tela NewMapping
    
    protected HashMap<TreeItem, List<CA>> assertions = new HashMap<>(); //Chave, valor(v = valores mapeados, ) Lista para guadar as assertivas 
    protected HashMap<Class_, List<DataProperty>> Classes = new HashMap<>(); //Chave, valor(v = valores mapeados, ) Lista para guadar as assertivas 
    protected HashMap<String, Class_> classes = new HashMap<>(); //Lista para guadar as Classes                               modifiquei protected
    protected HashMap<String, String> mapPrefixes = new HashMap<>(); // Lista para guadar os prefixos                         modifiquei protected

    MappingConfigurationDAO mcDAO = new MappingConfigurationDAO(); // Relacionado aos dados que serão inseridos no banco
    MappingConfiguration mc = null; // Variável que contém a configuração

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert newMapping != null : "fx:id=\"newMapping\" was not injected: check your FXML file '/fxml/main.fxml'.";
        assert AcList != null : "fx:id=\"AcList\" was not injected: check your FXML file '/fxml/main.fxml'.";
        m = this;

        tratarEventoOntoTreeTarget();

        try {
           // Conexao.getInstance().insertsFile("", Conexao.ARQ_ASSERTIONS, false);
        

            AnchorPane page = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/NewMappingConfiguration.fxml"));
            Scene scene = new Scene(page);
            secondaryStage.setScene(scene);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.initOwner(R2rByAsseertions.pStage);
           
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ontoTreeT.getSelectionModel().selectAll();
        System.out.println(this.getClass().getSimpleName() + ".initialize"); 
        
    }

    public void buildOntoTreeTarget(MappingConfiguration mc) {
        try {
            loadOntologyTarget(mc);

            // Crio o nó pai que será o nome da ontologia  // PEGAR O METODO QUE FOI MODIFICADO
            TreeItem<ObjectOWL> ontoRoot = new TreeItem<>();
            //TreeItem<ObjectOWL> ontoRoot = new TreeItem<>(mc.getTargetOntology());

            // Crio nós filhos com os nomes das classes
            for (Class_ class_ : ObjetoPublico.getClassesT()) {
                if (class_.getName() == null) {
                    continue;
                }
                Node classIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgO/class.gif")));
                TreeItem<ObjectOWL> item = new TreeItem<>((ObjectOWL) class_, classIcon);

                CCA cca = new CCA();
                cca.setClass_Target(class_);
                class_.getListCA().add(cca);

                Class_ superClass = class_.getSuperClass();
                if (superClass != null) {
                    //Cria os data properties da super classe 
                    for (int i = 0; i < superClass.getdProperties().size(); i++) {
                        createDpItem(item, superClass.getdProperties().get(i), cca);
                    }
                }

                // Cria os data properties da classe 
                for (int i = 0; i < class_.getdProperties().size(); i++) {
                    createDpItem(item, class_.getdProperties().get(i), cca);
                }

                if (superClass != null) {
                    // Cria os object properties da super classe 
                    for (int i = 0; i < superClass.getoProperties().size(); i++) {
                        createOpItem(item, superClass.getoProperties().get(i), cca);
                    }
                }

                // Cria os object properties da classe 
                for (int i = 0; i < class_.getoProperties().size(); i++) {
                    createOpItem(item, class_.getoProperties().get(i), cca);
                }

                ontoRoot.getChildren().add(item);
            }
            // Insiro o nó raiz na TreeView
            ontoTreeT.setRoot(ontoRoot);
            ontoTreeT.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadOntologyTarget(MappingConfiguration mc) throws FileNotFoundException {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        String filePath = mc.getTargetOntologyFilePath();
        List<Class_> classes = ObjetoPublico.getClassesT();

        if (filePath != null && !"".equals(filePath)) {
            ontModel.read(new FileInputStream(new File(filePath)), "http://ufc.br/rdfbyassertions/", mc.getTargetOntologyLang());// MUDEI O ENDEREÇO EM 18/01/16 AS 10:52
        } else {
            ontModel.read(mc.getTargetOntologyURL(), mc.getTargetOntologyLang());
        }

        ExtendedIterator<OntClass> i = ontModel.listClasses();
        while (i.hasNext()) {
            OntClass ontClass = (OntClass) i.next();
            String prefix = ontModel.getNsURIPrefix(ontClass.getNameSpace());
            String URI = "";
            if(prefix == null)
                continue;
            else 
               URI = ontModel.getNsPrefixURI(prefix);
            
            String name = ontClass.getLocalName();

            if (Class_.getByPrefixAndName(classes, prefix, name) == null) {
                Class_ c;
                if(Debug.isOn())
                    c = new Class_(new Prefix(prefix,URI), name);
                else 
                    c = new Class_(prefix, name);
                
                
                classes.add(c);
                //classes.put(prefix + name, c);
                //mapPrefixes.put(prefix, ontClass.getNameSpace());

                OntClass superClass = ontClass.getSuperClass();
                if (superClass != null) {
                    String superName = superClass.getLocalName();
                    String superPrefix = ontModel.getNsURIPrefix(superClass.getNameSpace());
                    Class_ superC = Class_.getByPrefixAndName(classes, superPrefix, superName);

                    if (superC == null) {
                        superC = new Class_(superPrefix, superName);
                        ObjetoPublico.getClassesT().add(superC);
//                        classes.put(superPrefix + superName, superC);
                        mapPrefixes.put(superPrefix, superClass.getNameSpace());
                    }

                    c.setSuperClass(superC);
                }
            }
        }

        ExtendedIterator<DatatypeProperty> i2 = ontModel
                .listDatatypeProperties();
        while (i2.hasNext()) {
            DatatypeProperty datatypeProperty = (DatatypeProperty) i2.next();

            if (datatypeProperty.getDomain() != null && datatypeProperty.getRange() != null) {
                ExtendedIterator iDomains = datatypeProperty.listDomain();
                while (iDomains.hasNext()) {
                    OntClass cDomain = (OntClass) iDomains.next();

                    String dClassPrefix = ontModel.getNsURIPrefix(cDomain.getNameSpace());
                    String dClassName = cDomain.getLocalName();
                    Class_ dClass = Class_.getByPrefixAndName(classes, dClassPrefix, dClassName);
                    //Class_ dClass = classes.get(dClassPrefix + dClassName);
                    String dpName = datatypeProperty.getLocalName();
                    String dpPrefix = ontModel.getNsURIPrefix(datatypeProperty.getNameSpace());
                    String URI = ontModel.getNsPrefixURI(dpPrefix);
                    String rangeName = datatypeProperty.getRange().getLocalName();

                    //Trocar prefix string por objeto Prefix
                    DataProperty dp = new DataProperty(new Prefix(dpPrefix,URI), dpName, dClass, rangeName);
                    if (dClass != null) {
                        dClass.getdProperties().add(dp);
                       // mapPrefixes.put(dpPrefix, datatypeProperty.getNameSpace());
                    }
                }
            }
        }

        ExtendedIterator<com.hp.hpl.jena.ontology.ObjectProperty> i3 = ontModel.listObjectProperties();
        while (i3.hasNext()) {
            ObjectProperty objectProperty = (ObjectProperty) i3.next();

            if (objectProperty.getDomain() != null && objectProperty.getRange() != null) {
                String dClassPrefix = ontModel.getNsURIPrefix(objectProperty.getDomain().getNameSpace());
                String dClassName = objectProperty.getDomain().getLocalName();
                String rClassPrefix = ontModel.getNsURIPrefix(objectProperty.getRange().getNameSpace());
                String rClassName = objectProperty.getRange().getLocalName();
                String opName = objectProperty.getLocalName();
                String opPrefix = ontModel.getNsURIPrefix(objectProperty.getNameSpace());
                String URI = ontModel.getNsPrefixURI(opPrefix);

                Class_ dClass = Class_.getByPrefixAndName(classes, dClassPrefix, dClassName);
                Class_ rClass = Class_.getByPrefixAndName(classes, rClassPrefix, rClassName);
                //Class_ dClass = classes.get(dClassPrefix + dClassName);
                //Class_ rClass = classes.get(rClassPrefix + rClassName);

                //Troacr opprefix String por Objeto Prefix
                ObjProperty op = new ObjProperty(new Prefix(opPrefix,URI), opName, dClass, rClass, false);
                if (dClass != null) {
                    dClass.getoProperties().add(op);
                   // mapPrefixes.put(opPrefix, objectProperty.getNameSpace());
                }
            }
        }

        ExtendedIterator<Restriction> iRest = ontModel.listRestrictions();
        while (iRest.hasNext()) {
            Restriction r = (Restriction) iRest.next();
            if (r.isMaxCardinalityRestriction()) {
                MaxCardinalityRestriction mcr = r.asMaxCardinalityRestriction();
                //System.out.println("MainController linha 289"+mcr.getMaxCardinality());
                OntClass ontClass = mcr.getSubClass();
                OntProperty p = mcr.getOnProperty();

                String prefix = ontModel.getNsURIPrefix(ontClass.getNameSpace());
                /*String teste1 = ontModel.getNsPrefixMap().values().toString();
                System.out.println("---------------------> Teste Target"+teste1);*/
                String name = ontClass.getLocalName();

                Class_ c = Class_.getByPrefixAndName(classes, prefix, name);
                if (p.isObjectProperty()) {
                    ObjectProperty objectProperty = p.asObjectProperty();
                    String opName = objectProperty.getLocalName();
                    String opPrefix = ontModel.getNsURIPrefix(objectProperty.getNameSpace());

                    for (ObjProperty objProperty : c.getoProperties()) {
                        if (objProperty.getPrefix().equals(opPrefix) && objProperty.getName().equals(opName)) {
                            objProperty.setMaxCardinality(mcr.getMaxCardinality());
                        }
                    }
                } else {
                    DatatypeProperty datatypeProperty = p.asDatatypeProperty();
                    String dpName = datatypeProperty.getLocalName();
                    String dpPrefix = ontModel.getNsURIPrefix(datatypeProperty.getNameSpace());

                    for (DataProperty dataProperty : c.getdProperties()) {
                        if (dataProperty.getPrefix().equals(dpPrefix) && dataProperty.getName().equals(dpName)) {
                            dataProperty.setMaxCardinality(mcr.getMaxCardinality());
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void assertionsListFired(ActionEvent event) throws IOException {
        AssertionsListController.show();
    }
    
    
    @FXML
    public void newMappingFired(ActionEvent event) throws IOException {
    	
    	
        if (Debug.isOn()) {
        	File resourcesDirectory = new File("src/main/resources/data/input");
        	String path = resourcesDirectory.getAbsolutePath();
        	String pathOntologyTarget = path + File.separatorChar + "MyMusic_target.owl";
        	String pathOntologySource = path + File.separatorChar + "Dbpedia_source.owl";
        	
        	String pathR2RSource = path + File.separatorChar + "ontologydbpedia.ttl";
        	String pathR2RTarget = path + File.separatorChar + "vocab_target.txt";
        	
            NewMappingController.nm.targetOntology.setText("MusicOntology");
            NewMappingController.nm.filePathTarget.setText(pathOntologyTarget);
            NewMappingController.nm.TargetOntoLangs.setValue("TURTLE");

            NewMappingController.nm.sourceOntology.setText("DbpediaOntology");
            NewMappingController.nm.filePathSource.setText(pathOntologySource);
            NewMappingController.nm.SourceOntoLangs.setValue("TURTLE");
            
            NewMappingController.nm.filePathR2RSource.setText(pathR2RSource);
            NewMappingController.nm.filePathR2RTarget.setText(pathR2RTarget);

            NewMappingController.nm.save.requestFocus();
        } else {

            NewMappingController.nm.targetOntology.setText("");
            NewMappingController.nm.targetOntology.setDisable(false);
            NewMappingController.nm.sourceOntology.setText("");
            NewMappingController.nm.sourceOntology.setDisable(false);

            NewMappingController.nm.filePathTarget.setText("");
            NewMappingController.nm.ontoUrlTarget.setText("");

            NewMappingController.nm.filePathSource.setText("");
            NewMappingController.nm.ontoUrlSource.setText("");
            
            NewMappingController.nm.filePathR2RSource.setText("");
            NewMappingController.nm.filePathR2RTarget.setText("");
        }
        secondaryStage.setTitle("Config a new Mapping");
        secondaryStage.show();

    }

    public void tratarEventoOntoTreeTarget() {
        ontoTreeT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                    final TreeItem selectedItem = ontoTreeT.getSelectionModel().getSelectedItem();
                    //int id = 0;
                    int idCCA = 0;
                    int idDCA = 0;
                    int idOCA = 0;
                    String _text = "";
                    
                    if (selectedItem != null) {
                        CA ca = new CCA();
                        if (selectedItem.getValue() instanceof Class_) {
                            Class_ class_ = (Class_) selectedItem.getValue();
                            CCA cca = new CCA();
                            
                            cca.setClass_Target(class_);
                            class_.getListCA().add(cca);
                            idCCA = class_.getId();
                            //id = class_.getId();
                            _text = cca.toString();
                            
                            try{
                              Node nodeCCA = MapNodes.getInstance().get(idCCA);
                              hboxEdit.getChildren().clear();
                              textAreaTeste.clear();
                              textAreaMR.clear();

                              if (nodeCCA == null) { //Se não existir, ele é criado
                                  nodeCCA = (Node) FXMLLoader.load(getClass().getResource("/fxml/TbpaneCCA.fxml"));

                                  hboxEdit.getChildren().add(nodeCCA);
                                  MapNodes.getInstance().put(idCCA, nodeCCA);

                              } else { //Se já existir, não se cria um novo
                                  nodeCCA = MapNodes.getInstance().get(idCCA);
                                  hboxEdit.getChildren().add(nodeCCA);
                              }

                              AnchorPane apCCA = (AnchorPane) nodeCCA;
                              SplitPane splitCCA = (SplitPane) apCCA.getChildren().get(0);
                              Pane paneCCA = (Pane) splitCCA.getItems().get(0);

                              TextField textFieldCCA = (TextField) paneCCA.getChildren().get(3); //mudei aqui (  TextField textFieldCCA = (TextField) paneCCA.getChildren().get(4))

                              textFieldCCA.setText(_text);
                            
                            } catch (IOException ex1) {
                              Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex1);
                            }

                        } else if (selectedItem.getValue() instanceof DataProperty) {
                            DataProperty dp = (DataProperty) selectedItem.getValue();
                            
                            DCA dca = new DCA();
                           
                            dca.setdPropertyT(dp);
                            if(CA.addCA(dca)){
                                dp.getListCA().add(dca);
                                idDCA = dp.getId();
                                _text = dca.toString();
                            }
                            try{
                                Node nodeDCA = MapNodes.getInstance().get(idDCA);
                                hboxEdit.getChildren().clear();
                                textAreaTeste.clear();
                                textAreaMR.clear();

                                if (nodeDCA == null) { //Se não existir, ele é criado
                                    nodeDCA = (Node) FXMLLoader.load(getClass().getResource("/fxml/TbpaneDCA.fxml"));

                                    hboxEdit.getChildren().add(nodeDCA);
                                    MapNodes.getInstance().put(idDCA, nodeDCA);

                                } else { //Se já existir, não se cria um novo
                                    nodeDCA = MapNodes.getInstance().get(idDCA);
                                    hboxEdit.getChildren().add(nodeDCA); // Mudei aqui em 31/08
                                }

                                AnchorPane apDCA = (AnchorPane) nodeDCA;
                                SplitPane splitDCA = (SplitPane) apDCA.getChildren().get(0);
                                Pane paneDCA = (Pane) splitDCA.getItems().get(0);

                                TextField textFieldDCA = (TextField) paneDCA.getChildren().get(3);//mudei aqui (  TextField textFieldCCA = (TextField) paneCCA.getChildren().get(4))

                                textFieldDCA.setText(_text);
                            
                            } catch (IOException ex2) {
                               Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex2);
                            }
                              
                        } else if (selectedItem.getValue() instanceof ObjProperty) { //coloquei o código abaixo para chamar a tabPane na janela principal
                            ObjProperty op = (ObjProperty) selectedItem.getValue();
                            OCA oca = new OCA();
                            oca.setoPropertyT(op);
                            op.getListCA().add(oca);
                            idOCA = op.getId();
                            //id = op.getId();
                            _text = oca.toString();
                            
                            try{
                            Node nodeOCA = MapNodes.getInstance().get(idOCA);
                            hboxEdit.getChildren().clear();
                            textAreaTeste.clear();
                            textAreaMR.clear();

                            if (nodeOCA == null) { //Se não existir, ele é criado
                                nodeOCA = (Node) FXMLLoader.load(getClass().getResource("/fxml/TbpaneOCA.fxml"));

                                hboxEdit.getChildren().add(nodeOCA);
                                MapNodes.getInstance().put(idOCA, nodeOCA);

                            } else { //Se já existir, não se cria um novo
                                nodeOCA = MapNodes.getInstance().get(idOCA);
                                hboxEdit.getChildren().add(nodeOCA);
                            }

                            AnchorPane apOCA = (AnchorPane) nodeOCA;
                            SplitPane splitOCA = (SplitPane) apOCA.getChildren().get(0);
                            Pane paneOCA = (Pane) splitOCA.getItems().get(0);

                            TextField textFieldOCA = (TextField) paneOCA.getChildren().get(3); //mudei aqui (  TextField textFieldCCA = (TextField) paneCCA.getChildren().get(4))

                            textFieldOCA.setText(_text);
                            

                            } catch (IOException ex3) {
                               Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex3);
                            }
                        }
                   
                        //try { 
                         
                           //verifica se já existe um node criado para o objeto
                            /*Node node = MapNodes.getInstance().get(id);
                            borderPaneCAEditor.getChildren().clear();
                            textAreaTeste.clear();
                            textAreaMR.clear();

                            if (node == null) { //Se não existir, ele é criado
                                node = (Node) FXMLLoader.load(getClass().getResource("/fxml/TbpaneCCA.fxml"));

                                borderPaneCAEditor.getChildren().add(node);
                                MapNodes.getInstance().put(id, node);

                            } else { //Se já existir, não se cria um novo
                                node = MapNodes.getInstance().get(id);
                                borderPaneCAEditor.getChildren().add(node);
                            }

                            AnchorPane ap = (AnchorPane) node;
                            SplitPane split = (SplitPane) ap.getChildren().get(0);
                            Pane p = (Pane) split.getItems().get(0);

                            TextField t = (TextField) p.getChildren().get(4);

                            t.setText(_text);*/

                  
                    } else {
                        TbpaneCCAController.tbCCA.txtAssertion.setText("");
                        TbpaneCCAController.tbCCA.lblAssertion.setText("Correspondence Assertion (CA):");
                    }
                }
            }
        });
    }

    private void createDpItem(TreeItem<ObjectOWL> item, DataProperty dp, CCA cca) {
        Node datatypePIcon = new ImageView(
                new Image(getClass().getResourceAsStream("/imgO/datatypeP.gif")));
        TreeItem<ObjectOWL> subItem = new TreeItem<>((ObjectOWL) dp, datatypePIcon);

        item.getChildren().add(subItem);

        DCA dca = new DCA(cca);
        dca.setdPropertyT(dp);

        List<CA> listDCA = new ArrayList<>();
        listDCA.add(dca);
        assertions.put(subItem, listDCA);
        cca.getDcaList().add(dca);
    }

    private void createOpItem(TreeItem<ObjectOWL> item, ObjProperty op, CCA cca) {
        Node objectPIcon = new ImageView(
                new Image(getClass().getResourceAsStream("/imgO/objectP.gif")));
        TreeItem<ObjectOWL> subItem = new TreeItem<>((ObjectOWL) op, objectPIcon);

        item.getChildren().add(subItem);

        OCA oca = new OCA(cca);
        oca.setoPropertyT(op);

        List<CA> listOCA = new ArrayList<>();
        listOCA.add(oca);
        assertions.put(subItem, listOCA);
        cca.getOcaList().add(oca);
                        
    }

}
