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
public abstract class TemplateR2R {
    public static final int CCA = 1;
    public static final int DCA = 2;
    public static final int OCA = 3;
    
    public abstract String createTemplateR2R();
    
    public static TemplateR2R getTemplateInstance(CA _ca, int type_ca){
        if(type_ca == CCA)
            return ((CCATemplateR2R) new CCATemplateR2R(_ca));
        if(type_ca == DCA)
            return ((DCATemplateR2R) new DCATemplateR2R(_ca));
        if(type_ca == OCA)
            return ((OCATemplateR2R) new OCATemplateR2R(_ca));
        return null;
    }
    
    
    //public TemplateR2R createTemplate(CA _ca, int type_ca);
}
