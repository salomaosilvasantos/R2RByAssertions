/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.mapping;


import java.util.ArrayList;
import java.util.List;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.CCA;
import com.br.ufc.arida.r2rbyassertions.model.DCA;

import javafx.scene.control.ListView;

/**
 *
 * @author Tiagovinuto
 */
public class BuildMapping {
    
    DCA ca = new DCA();

    public static String buildR2RML(ListView<CA> assertionsList) {

        StringBuilder r2r = new StringBuilder("");

        r2r.append("@prefix r2r: <http://www4.wiwiss.fu-berlin.de/bizer/r2r/>");
        r2r.append("# Made up Mapping publisher");
        r2r.append("@prefix mp: <http://www.example.org/>");

        List<String> prefixes = new ArrayList<>();

        for (CA ca : assertionsList.getItems()) {
            if (ca instanceof CCA) {
                CCA cca = (CCA) ca;
                List<String> specialTriplesMap = new ArrayList<>();
                String prefixClass = cca.getClass_Target().getPrefix();

                if (!prefixes.contains(prefixClass)) {
                    prefixes.add(prefixClass);
                }
            }

        }
        return r2r.toString();
    }
    
    //public String mappingRuler(){
        //String MR = dPropertyT + "(?SUBJ)" + "←" + this.dPropertyS + "(?SUBJ)";
        //return MR;
   // }
}
