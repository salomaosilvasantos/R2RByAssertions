/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemplatesR2R;

import com.br.ufc.arida.r2rbyassertions.model.CA;

/**
 *
 * @author Tiagovinuto
 */
public abstract class TemplateMappingRule {
    
    public static final int CCA = 1;
    public static final int DCA = 2;
    public static final int OCA = 3;
    
    public abstract String createTemplateMappingRule();
    
    public static TemplateMappingRule getTemplateInstance(CA _ca, int type_ca){
        if(type_ca == CCA)
            return ((cca_MappingRule) new cca_MappingRule(_ca));
        if(type_ca == DCA)
            return ((dca_MappingRule) new dca_MappingRule(_ca));
        if(type_ca == OCA)
            return ((oca_MappingRule) new oca_MappingRule(_ca));
        return null;
    }
    
    
}
