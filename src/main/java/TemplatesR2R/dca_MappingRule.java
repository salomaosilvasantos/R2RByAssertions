/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemplatesR2R;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.CCA;
import com.br.ufc.arida.r2rbyassertions.model.DCA;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;

/**
 *
 * @author Tiagovinuto
 */

public class dca_MappingRule  extends TemplateMappingRule{
        private DCA dca;
        private CCA cca;
        public String templateMappingRule;

    
    public dca_MappingRule(CA _ca) {
        this.dca = (DCA) _ca;
    }
    
    @Override
    public String createTemplateMappingRule() {
       DataProperty _dp = dca.getListDataTypeP().get(0);
       String mappingRule = "";
       
        if (dca.getListDataTypeP().size() == 1) {

            mappingRule = dca.getdPropertyT() + " (s, v)" + " ← " +_dp.getDomain().getPrefix() +":"+ _dp.getDomain().getName() + "(s)" + "; " + _dp.getPrefix() + ":" + _dp.getName() + "(s, x)";
        }
        
        //moa:careerDuration(s,v) ← dbo:MusicalArtist(s); dbp:startCareer(s,v1); dbp:endCareer(s,v2); cocat(v1, v2, v)
        

        if (dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("")) {
            mappingRule = "";
      
        }
        
        if (dca.getListDataTypeP().size() == 1 && !dca.getTransformationFunction().equals("")) {
             mappingRule = "moa:careerDuration(s, v) ← dbo:MusicalArtist(s); dbp:startCareer(s, v1); dbp:endCareer(s, v2); cocat(v1; v2, v)";
            // mappingRule = dca.getdPropertyT() + " (s, v)" + " ← " +_dp.getDomain().getPrefix() +":"+ _dp.getDomain().getName() + "(s)" + "; " + _dp.getPrefix() + ":" + _dp.getName() + "(s, x)";
          
        }

        if (dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("") && !dca.getTransformationFunction().equals("")) {
            mappingRule = "";
            
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() == 1) {
            mappingRule = "";
            
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("")) {

            mappingRule = "";
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("") && !dca.getTransformationFunction().equals("")) {

            mappingRule = "";
        }

        if (dca.getListDataTypeP().size() > 1 && !dca.getTransformationFunction().equals("")) {

            mappingRule = "";
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() > 1) {  // esse cara tá nulo (oca.getObjectP())

            mappingRule = "";
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() > 1 && !dca.getTransformationFunction().equals("")) {

            mappingRule = "";
        }

        if (dca.getListDataTypeP().size() > 1 && dca.isDCAEmbedded() == true) {
            
            mappingRule = "";
        } else if (dca.getListDataTypeP().size() > 1 && dca.isDCAEmbedded() == false) {

            mappingRule = "";
        }
               
       this.templateMappingRule = mappingRule;
       return mappingRule;
    }
    
    
    @Override
    public String toString(){
        return templateMappingRule;
    }
    
}
