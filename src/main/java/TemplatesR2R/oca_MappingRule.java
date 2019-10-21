/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemplatesR2R;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.OCA;

/**
 *
 * @author Tiagovinuto
 */
public class oca_MappingRule extends TemplateMappingRule{
    
    private OCA oca;
    public String templateMappingRule;
    
    public oca_MappingRule(CA _ca) {
        this.oca = (OCA)_ca;
    }

    @Override
    public String createTemplateMappingRule() {
       String mappingRule = "";
       
       if (oca.getObjectP().size() > 1) {
          mappingRule = "moa:birthPlace(s, v) dbo:MusicalArtist(s); dbp:birthPlace/dbp:country(s, v)";
       }
               
       this.templateMappingRule = mappingRule;
       return mappingRule;
    }
    
    
    @Override
    public String toString(){
        return templateMappingRule;
    }
    
}
