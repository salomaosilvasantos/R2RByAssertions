package com.br.ufc.arida.r2rbyassertions.main;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.ObjetoPublico;
import com.br.ufc.arida.r2rbyassertions.sqlite.dao.Conexao;

//import de.fuberlin.wiwiss.r2r.examples.GenericExamples;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Tiagovinuto
 */
public class AssertionsListController implements Initializable {
    private static Stage loadAssertionsList;
    @FXML
    protected ListView<CA> assertionsList;
    @FXML
    protected Button cancelBtn;
    @FXML
    protected Button eMappingR2rBtn;
    
    protected static AssertionsListController aListControl;
        
    private ObservableList<CA> AssertionsList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aListControl = this;
        assertionsList.setItems(AssertionsList);
        
    }  
    
    @FXML
    public void executeMappingR2R(ActionEvent event) {
        //GenericExamples g = new GenericExamples();
        Conexao con;
        
        try {
            
            con = Conexao.getInstance();

           // g.GerarExemplo(con.getArqSourceR2RPath(), con.getOutputR2RPath(), con.getMappingsR2RPath(), con.getArqTargetR2RPath());

            System.out.println("Finished.");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void dispose() {
        loadAssertionsList.close();
    }
    
    protected static void show() throws IOException {
        loadAssertionsList = new Stage(StageStyle.UTILITY);
        AnchorPane a_pane = (AnchorPane) FXMLLoader.load(R2rByAsseertions.class.getResource("/fxml/AssertionsList.fxml"));
        Scene sceneFilter = new Scene(a_pane);
        loadAssertionsList.setScene(sceneFilter);
        loadAssertionsList.initModality(Modality.APPLICATION_MODAL);
        loadAssertionsList.initOwner(R2rByAsseertions.pStage);
        
        aListControl.initAssertions();
        loadAssertionsList.show();
    }
    
    private void initAssertions(){
        
        if(ObjetoPublico.getAssertions() != null)
            AssertionsList.setAll(ObjetoPublico.getAssertions());
    }
    
    /**
     * Called when the Cancel button is fired.
     *
     * @param event the action event.
     * @throws java.io.IOException
     */
    @FXML
    public void generateMappingsR2R(ActionEvent event) throws IOException {
        String str = "";
        
        for(CA _ca : ObjetoPublico.getAssertions()){
            str  += _ca.pattenrsAC() + "\n\n";
        }
        
        Conexao.getInstance().insertsFile(str, Conexao.ARQ_R2RMAPPING, true);
    }
    
    @FXML
    public void cancelFired(ActionEvent event) throws IOException {
        dispose(); 
    }   
}
