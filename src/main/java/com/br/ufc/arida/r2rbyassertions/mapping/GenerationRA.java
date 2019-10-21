/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.mapping;


import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.CCA;

import javafx.scene.control.ListView;

/**
 *
 * @author Tiagovinuto
 */
public class GenerationRA {
    public String R2R;
    public String NameAssertions;
    public CA cTarget;
    public CA cSource;
    
    public static String buildR2RML(ListView<CA> assertionsList) {
        
        StringBuilder r2r = new StringBuilder("");
        
        r2r.append("@prefix r2r: <http://www4.wiwiss.fu-berlin.de/bizer/r2r/>");
        r2r.append("# Made up Mapping publisher");
        r2r.append("@prefix mp: <http://www.example.org/>");

        for (CA ca : assertionsList.getItems()) {
            if (ca instanceof CCA) {
                CCA cca = (CCA) ca;
               
            }
        }
        return r2r.toString();
    }
     
    public String pattenrsACC(){
      R2R = "# Class Mappings"+
            "mp:"+NameAssertions+
            "a r2r:ClassMapping ;"+
            "r2r:prefixDefinitions \"mo: <http://purl.org/ontology/mo/> . mbo:<http://creativeartefact.org/ontology/> . moa:<http://www.example.org/moa/>\";"+
            "r2r:sourcePattern "+"?SUBJ a "+cSource+";"+
            "r2r:targetPattern "+"?SUBJ a"+ cTarget +";";
      return R2R;
    } 
    
    public String getR2R() {
        return R2R;
    }

    public void setR2R(String R2R) {
        this.R2R = R2R;
    }

    public String getNameAssertions() {
        return NameAssertions;
    }

    public void setNameAssertions(String NameAssertions) {
        this.NameAssertions = NameAssertions;
    }



}
