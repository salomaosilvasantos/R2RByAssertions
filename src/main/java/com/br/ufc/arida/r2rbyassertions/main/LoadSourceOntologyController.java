/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.CCA;
import com.br.ufc.arida.r2rbyassertions.model.Class_;
import com.br.ufc.arida.r2rbyassertions.model.DCA;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;
import com.br.ufc.arida.r2rbyassertions.model.MapNodes;
import com.br.ufc.arida.r2rbyassertions.model.MappingConfiguration;
import com.br.ufc.arida.r2rbyassertions.model.OCA;
import com.br.ufc.arida.r2rbyassertions.model.ObjProperty;
import com.br.ufc.arida.r2rbyassertions.model.ObjectOWL;
import com.br.ufc.arida.r2rbyassertions.model.ObjetoPublico;
import com.br.ufc.arida.r2rbyassertions.model.Prefix;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.MappingConfigurationDAO;
import com.br.ufc.arida.r2rbyassertions.util.MapIcons;
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

import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Tiagovinuto
 */
public class LoadSourceOntologyController implements Initializable {
    
    //Componentes FXML
    
    @FXML //  fx:id="ontoTreeSource"
    protected TreeView<ObjectOWL> ontoTreeS;

    @FXML
    Button cancel;

    @FXML
    Button finish;

    @FXML
    Label lblAssertion;

    @FXML
    AnchorPane paneSourceOntology;
    
    MappingConfiguration mc = null;

    public static LoadSourceOntologyController loadsource;

    MappingConfigurationDAO mcDAO = new MappingConfigurationDAO();
    
    HashMap<String, String> mapPrefixes = new HashMap<>(); // Lista para guadar os prefixos
    HashMap<TreeItem, Object> TSourceMap = new HashMap<>(); // Lista para guardar as assertivas da Source
    HashMap<String, List<ObjProperty>> mapTableFks = new HashMap<>();
    HashMap<String, List<ObjProperty>> mapTableFksInv = new HashMap<>();
   
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        loadsource = this;
        desabilitaFinish();
        tratarEventoOntoTreeSource();

    }

    public void cancelFired(ActionEvent event) throws IOException {
        this.finish.getScene().getWindow().hide();
        desabilitaFinish();
    }

    @FXML
    public void finishTreeSource(ActionEvent event) throws IOException {
        this.finish.getScene().getWindow().hide();
        desabilitaFinish();
        
    }

   
    
    public void buildOntoTreeSource(MappingConfiguration mc) {
        try {
            loadOntologySource(mc);

            // Crio o nÃ³ pai que serÃ¡ o nome da ontologia  // PEGAR O METODO QUE FOI MODIFICADO
            TreeItem<ObjectOWL> ontoRoot = new TreeItem<>();

            for (Class_ class_ : ObjetoPublico.getClassesS()) {
                if (class_.getName() == null) {
                    continue;
                }
                
                Node classIcon = new ImageView(new Image(getClass().getResourceAsStream("/imgO/class.gif")));
                TreeItem<ObjectOWL> item = new TreeItem<>((ObjectOWL)class_, classIcon);
                
                
                CCA cca = new CCA();
                cca.setClass_Source(class_);
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
            ontoTreeS.setRoot(ontoRoot);

            ontoTreeS.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
  
    private void loadOntologySource(MappingConfiguration mc) throws FileNotFoundException {
       OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        List<Class_> classes = ObjetoPublico.getClassesS();
        
        String filePath = mc.getSourceOntologyFilePath();
        if (filePath != null && !"".equals(filePath)) {
            ontModel.read(new FileInputStream(new File(filePath)),
                    "http://ufc.br/rdf2rdfmb/", mc.getSourceOntologyLang());// VER ESSE ENDEREÃ‡O
        } else {
            ontModel.read(mc.getSourceOntologyURL(), mc.getSourceOntologyLang());
        }

        ExtendedIterator<OntClass> i = ontModel.listClasses();
        while (i.hasNext()) {
            OntClass ontClass = (OntClass) i.next();
            String prefix = ontModel.getNsURIPrefix(ontClass.getNameSpace());
            /*
            Adicionado Gabriel 16.09 16:35
            */
            if(prefix == null)
                continue;
            
            String URI = ontModel.getNsPrefixURI(prefix);
            String name = ontClass.getLocalName();

            if (Class_.getByPrefixAndName(classes, prefix, name) == null) {
                Class_ c = new Class_(new Prefix(prefix,URI), name);
                classes.add(c);
                OntClass superClass = ontClass.getSuperClass();
                if (superClass != null) {
                    String superName = superClass.getLocalName();
                    String superPrefix = ontModel.getNsURIPrefix(superClass.getNameSpace());
                    Class_ superC = Class_.getByPrefixAndName(classes, superPrefix, superName);

                    if (superC == null) {
                        superC = new Class_(superPrefix, superName);
                        classes.add(superC);
                    }

                    c.setSuperClass(superC);
                }
            }
        }

        ExtendedIterator<DatatypeProperty> i2 = ontModel.listDatatypeProperties();
        while (i2.hasNext()) {
            DatatypeProperty datatypeProperty = (DatatypeProperty) i2.next();

            if (datatypeProperty.getDomain() != null && datatypeProperty.getRange() != null) {
                ExtendedIterator iDomains = datatypeProperty.listDomain();
                while (iDomains.hasNext()) {
                    OntClass cDomain = (OntClass) iDomains.next();

                    String dClassPrefix = ontModel.getNsURIPrefix(cDomain.getNameSpace());
                    String dClassName = cDomain.getLocalName();
                    Class_ dClass = Class_.getByPrefixAndName(classes, dClassPrefix, dClassName); 
                    String dpName = datatypeProperty.getLocalName();
                    String dpPrefix = ontModel.getNsURIPrefix(datatypeProperty.getNameSpace());
                    String URI = ontModel.getNsPrefixURI(dpPrefix);
                    String rangeName = datatypeProperty.getRange().getLocalName();

                    DataProperty dp = new DataProperty(new Prefix(dpPrefix,URI), dpName, dClass, rangeName);
                    if (dClass != null) {
                        dClass.getdProperties().add(dp);
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

                ObjProperty op = new ObjProperty(new Prefix(opPrefix,URI), opName, dClass, rClass,false);
                ObjProperty opInv = new ObjProperty(new Prefix(opPrefix,URI), "^"+opName, rClass, dClass,true);
                
                dClass.getRanges().add(rClass);
                
				
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
                
                OntClass ontClass = mcr.getSubClass();
                OntProperty p = mcr.getOnProperty();

                String prefix = ontModel.getNsURIPrefix(ontClass.getNameSpace());
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
    
    
    public void tratarEventoOntoTreeSource() {
    	
        ontoTreeS.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int id = 0;
                TreeItem selectedItemS = ontoTreeS.getSelectionModel().getSelectedItem();

                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                    if (selectedItemS != null) {
                        TreeItem selectedItemT = MainController.m.ontoTreeT.getSelectionModel().getSelectedItem();
                        
                        ObjectOWL obSource = (ObjectOWL) selectedItemS.getValue();
                        ObjectOWL obTarget = (ObjectOWL) selectedItemT.getValue();
                        
                        CA ca = (CA) obTarget.getLastCA();
                        id = obTarget.getId();
                        
                        if(obSource instanceof ObjProperty) {
                            if(obTarget instanceof Class_){
                                JOptionPane.showMessageDialog(null, "ESTE ITEM NÃO PODE SER SELECIONADO!");
                            }
                        }
                        
                        if (obSource instanceof Class_) {
                            if(ca instanceof CCA) {
                                habilitaFinish();
                            }
                            
                            ca.setClass_Source((Class_) obSource);
                        }
                        
                        if (obSource instanceof DataProperty) {
                            //ca = (ca instanceof CCA) ? (CCA) ca : (DCA)ca; //Acho que tem que mudar aqui para aparecer a OCA no textField (Mudar o IF)
                            if(ca instanceof CCA) {
                                ca = (CCA)ca;//mudei essa linha em 06/09
                                
                               // DataProperty op = (DataProperty) selectedItemS.getValue(); // incluí esta linha em 25/10/16 as 10:13
                                //((DCA)ca).setdPropertyS(op);
                                
                                ((CCA)ca).getListDataTypeP().add((DataProperty) obSource);
                                ((DataProperty) obSource).setEmbedded();

                                //TbpaneCCAController.tbCCA.habilitaTransFunction();
                                TbpaneCCAController.tbCCA.addProperty((DataProperty) obSource);
                            }
                            
                            if(ca instanceof DCA) {
                                //DataProperty op = (DataProperty) selectedItemS.getValue(); // incluí esta linha em 25/10/16 as 10:13
                                //((DCA)ca).setdPropertyS(op);
                                
                                ca = (DCA)ca;//mudei essa linha em 06/09
                                

                                if(((DCA)ca).addListDataTypeP((DataProperty) obSource)) {
                                    habilitaFinish();
                                    TbpaneDCAController.tbDCA.habilitaTransFunction();
                                    TbpaneDCAController.tbDCA.addProperty((DataProperty) obSource);
                                }
                            }
                            
                            if(ca instanceof OCA) {
                                
                                 ca = (OCA) ca;//mudei essa linha em 06/09           
                                ((OCA) ca).getListDataPropertyOCA().add((DataProperty) obSource); // Linha add em 02/09 as 14:20
                                 habilitaFinish();
                            }
                        }
                        
                        if (obSource instanceof ObjProperty) {
                            if (ca instanceof OCA) {
                                //((OCA) ca).setoPropertyS((ObjProperty)selectedItemS.getValue());
                                ObjProperty op = (ObjProperty) selectedItemS.getValue();
                                ((OCA)ca).setoPropertyS(op);
                                if (ca.getClass_Source() != null && !((OCA) ca).getObjectP().contains((ObjProperty) obSource)) {
                                    ((OCA) ca).getObjectP().add((ObjProperty) obSource);
                                    habilitaFinish();
                                }
                            }
                            
                            if (ca instanceof DCA){
                                
                               ((DCA)ca).getListObjectD().add((ObjProperty) obSource);
                               habilitaFinish();
                            }
                        }
                        if (ca != null) {
                            
                            Node node = MapNodes.getInstance().get(id);
                            AnchorPane ap = (AnchorPane) node;
                            SplitPane split = (SplitPane) ap.getChildren().get(0);
                            Pane p = (Pane) split.getItems().get(0);

                            TextField t = (TextField) p.getChildren().get(3); // Mudei aqui TextField t = (TextField) p.getChildren().get(4);

                            t.setText(ca.toString());
                        }
                    } else {

                    }
                }
            }
        });
    }
    
    public void habilitaFinish() {
        finish.setDisable(false);
    }
    
    /** Método DpItem com parâmetros diferentes */
    private void createDpItem(TreeItem<ObjectOWL> item, DataProperty dp) {
        TreeItem<ObjectOWL> subItem = new TreeItem<>((ObjectOWL)dp, MapIcons.getInstance().DATA_PROPERTY_ICON);
        item.getChildren().add(subItem);
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
        //assertions.put(subItem, listDCA);
        cca.getDcaList().add(dca);
    }
   
    /** Método OpItem com parâmetros diferentes */
	/*
	 * private void createOpItem(TreeItem<ObjectOWL> item, Class_ class_, boolean
	 * isInv) { //Responsavel por gerar os Object Propertys da árvore
	 * TreeItem<ObjectOWL> subItemOP, subItemRange, subItemDP, subInv;
	 * 
	 * if(class_ == null) { return; }
	 * 
	 * for(DataProperty dp : class_.getdProperties()) { createDpItem(item, dp); }
	 * 
	 * for (ObjProperty op : class_.getoProperties()) { subItemOP = op.isInverse() ?
	 * new TreeItem<>((ObjectOWL)op, MapIcons.getInstance().OBJ_PROPERTY_ICONINV) :
	 * new TreeItem<>((ObjectOWL)op, MapIcons.getInstance().OBJ_PROPERTY_ICON);
	 * 
	 * if(op.isInverse()){ item.getChildren().add(subItemOP);
	 * 
	 * if (op.hasRange()) { subItemRange = new TreeItem<>((ObjectOWL)op.getRange(),
	 * MapIcons.getInstance().CLASS_ICON);
	 * subItemOP.getChildren().add(subItemRange); createOpItem(subItemRange,
	 * op.getRange(), op.isInverse()); }
	 * 
	 * } else { if(!isInv) { item.getChildren().add(subItemOP); if (op.hasRange()) {
	 * subItemRange = new TreeItem<>((ObjectOWL)op.getRange(),
	 * MapIcons.getInstance().CLASS_ICON);
	 * subItemOP.getChildren().add(subItemRange); createOpItem(subItemRange,
	 * op.getRange(), op.isInverse()); } } }
	 * 
	 * } }
	 */
    
    private void createOpItem(TreeItem<ObjectOWL> item, ObjProperty op, CCA cca) {
        Node objectPIcon = new ImageView(
                new Image(getClass().getResourceAsStream("/imgO/objectP.gif")));
        TreeItem<ObjectOWL> subItem = new TreeItem<>((ObjectOWL) op, objectPIcon);

        item.getChildren().add(subItem);

        OCA oca = new OCA(cca);
        oca.setoPropertyT(op);

        List<CA> listOCA = new ArrayList<>();
        listOCA.add(oca);
        //assertions.put(subItem, listOCA);
        cca.getOcaList().add(oca);
                        
    }

    private void desabilitaFinish() {
        finish.setDisable(true);
    }
}

