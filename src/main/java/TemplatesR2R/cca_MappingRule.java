package TemplatesR2R;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.CCA;
import com.br.ufc.arida.r2rbyassertions.model.DCA;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiagovinuto
 */
public class cca_MappingRule extends TemplateMappingRule{
    private CCA cca;
    public String templateMappingRule;
    
    public cca_MappingRule(CA _ca) {
        this.cca = (CCA)_ca;
    }

    @Override
    public String createTemplateMappingRule() {
       String mappingRule = "";
       
       if (cca.getListDataTypeP().isEmpty()){
          mappingRule = cca.getClass_Target() + "(s)" + " ← " + cca.getClass_Source()+ "(s)";
       }
        
       if (cca.getListDataTypeP().isEmpty() && !cca.getFilter().equals("")){
          mappingRule = "mo:Record(s) ← dbo:Album(s); dbo:releaseDate(s, v) > '2013-01-01'";
          //mappingRule = cca.getClass_Target() + "(s)" + " ← " + "(s)" + cca.getClass_Source() + "; " + cca.getFilter();
       }
       
       if(cca.isEmbedded() == true){
           mappingRule = cca.getClass_Target()+" (u, w)" + " ← " + cca.getClass_Source()+ " (s)" + " ; " ; 
           
           for (DataProperty dp : cca.getListDataTypeP()) {
               
               mappingRule += dp.getPrefix() + ":" + dp.getName(); 
           }
           
           mappingRule += "; generateUri[](s, u)";
       }
       
        if (cca.isEmbedded() == true && !cca.getFilter().equals("")) {
            mappingRule = cca.getClass_Target() + " (u, w)" + " ← " + cca.getClass_Source() + " (s)" + " ; ";

            for (DataProperty dp : cca.getListDataTypeP()) {
                mappingRule += dp.getPrefix() + ":" + dp.getName();
            }
            mappingRule += "; generateUri[](s, u)";
        }
          
    //String MR = this.class_Target+"(?SUBJ)" + "←" + this.class_Source+"(?SUBJ)";
       this.templateMappingRule = mappingRule;
       return mappingRule;
    }
    
    
    @Override
    public String toString(){
        return templateMappingRule;
    }
}
