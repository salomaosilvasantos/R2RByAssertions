/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.template;

/**
 *
 * @author Tiagovinuto
 */
public class R2rTemplates {
   
    public String pattenrsClass(){
      String R2R = "# Class Mappings"+ '\n' +
            //"mp:"+"ΨC"+class_Target.getName()+idList +'\n' +
            "a r2r:ClassMapping ;" + '\n' +
            "r2r:prefixDefinitions \"" + "mo: <http://purl.org/ontology/mo/> . mbo:<http://creativeartefact.org/ontology/> . moa:<http://www.example.org/moa/>\";"+ '\n' ;
            //"r2r:sourcePattern "+"?SUBJ a "+this.class_Source+";"+ '\n' +
            //"r2r:tranformation "+
            //"r2r:targetPattern "+"?SUBJ a "+ this.class_Target +";";
      return R2R;
    } 
    
    public String pattenrsObproperty(){
    
        String R2R = "# Property Mappings" + '\n'+ 
                "mp:" + "ΨC" +
                "a r2r:PropertyMapping;" + '\n';
        return R2R;
    }
    
    public String pattenrsDataProperty(){
    
        String R2R = "# Property Mappings" + '\n'+ 
                "mp:" + "ΨC" +
                "a r2r:PropertyMapping;" + '\n';
        return R2R;
    }
}
