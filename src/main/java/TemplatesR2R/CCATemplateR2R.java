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
import com.br.ufc.arida.r2rbyassertions.model.Property;

/**
 *
 * @author Tiagovinuto
 */
public class CCATemplateR2R extends TemplateR2R {

    private DCA dca;
    private CCA cca;
    public String templateR2R;

    public CCATemplateR2R(CA _ca) {
        this.cca = (CCA) _ca;
    }

    @Override
    public String createTemplateR2R() {
        String prefix_t = cca.getClass_Target().getPrefix();
        String URI_t = cca.getClass_Target().getURI();

        String prefix_s = cca.getClass_Source().getPrefix();
        String URI_s = cca.getClass_Source().getURI();

        String R2R = "";

        if (cca.getListDataTypeP().isEmpty()) { // if (cca.getListDataTypeP().size() == 0){

            R2R = "# Class Mappings" + '\n'
                    + "mp:ΨC" + cca.getClass_Source().getName() + cca.getClass_Source().getId() + '\n' // FAZER ISSO PARA TODOS OS MAPEAMENTOS
                    + "a r2r:ClassMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + prefix_t + ": " + "<" + URI_t + ">" + " . " + prefix_s + ": " + "<" + URI_s + "> \";" + '\n'
                    + "r2r:sourcePattern " + "\"" + "?SUBJ a " + cca.getClass_Source() + "\";" + '\n'
                    + "r2r:targetPattern " + "\"" + "?SUBJ a " + cca.getClass_Target() + "\".";
        }
        
        Property prop = cca.getClass_Source().getCurrentPropertySelected();
        
        if (prop != null && !cca.getFilter().equals("")) { // if (cca.getListDataTypeP().size() == 0){
            //ΨC
            DataProperty dp = (DataProperty)prop;
            R2R = "# Class Mappings" + '\n'
                    + "mp:ΨC" + cca.getClass_Source().getName() + cca.getClass_Source().getId() + '\n' // FAZER ISSO PARA TODOS OS MAPEAMENTOS
                    + "a r2r:ClassMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + prefix_t + ":" + "<" + URI_t + ">" + " . " + prefix_s + ":" + "<" + URI_s + "> \";" + '\n'
                    + "r2r:sourcePattern " + "\"" + "?SUBJ a " + cca.getClass_Source() + "; " + dp.toString() +" ?"+ dp.getName() + " . Filter(" + cca.getFilter() + ")" + "\";" + '\n'
                    + "r2r:targetPattern " + "\"" + "?SUBJ a " + cca.getClass_Target() + "\".";
        } //+ cca.getListDataTypeP()+ "?"

        if (cca.isEmbedded() == true) { // if (cca.getListDataTypeP().size() == 0){

            R2R = "#Class Mappings" + '\n'
                    + "mp:X " + cca.getClass_Source().getName() + cca.getClass_Source().getId() + '\n' // FAZER ISSO PARA TODOS OS MAPEAMENTOS
                    + "a r2r:classMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + prefix_t + ":" + "<" + URI_t + ">" + " . " + prefix_s + ":" + "<" + URI_s + "> ;" + '\n'
                    + "r2r:sourcePattern " + "\"" + "?SUBJ a " + cca.getClass_Source() + "\";" + '\n';

            for (DataProperty dp : cca.getListDataTypeP()) {
                if (cca.getListDataTypeP().size() > 1) {
                    R2R += "?" + dp.getName() + " " + dp.getPrefix() + ":" + dp.getName();
                    R2R += ";";
                } else {
                    R2R += "?" + dp.getName() + " " + dp.getPrefix() + ":" + dp.getName() + ";";
                }
            }
            R2R += "r2r:targetPattern " + "\"" + "?SUBJ a " + cca.getClass_Target() + "\"."
                    + "r2r:tranformation " + "\"" + "?" + " = " + "generateUri" + "\"." + '\n';
        }

        if (cca.isEmbedded() == true && !cca.getFilter().equals("")) { // if (cca.getListDataTypeP().size() == 0){

            R2R = "#Class Mappings" + '\n'
                    + "mp:X " + cca.getClass_Source().getName() + cca.getClass_Source().getId() + '\n' // FAZER ISSO PARA TODOS OS MAPEAMENTOS
                    + "a r2r:classMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + prefix_t + ":" + "<" + URI_t + ">" + " . " + prefix_s + ":" + "<" + URI_s + "> ;" + '\n'
                    + "r2r:sourcePattern " + "\"" + "?SUBJ a " + cca.getClass_Source() + ";" + '\n';

            for (DataProperty dp : cca.getListDataTypeP()) {
                if (cca.getListDataTypeP().size() > 1) {
                    R2R += "?" + dp.getName() + " " + dp.getPrefix() + ":" + dp.getName();
                    R2R += ";";
                } else {
                    R2R += "?" + dp.getName() + " " + dp.getPrefix() + ":" + dp.getName() + ";";
                }
            }

            R2R += " . Filter(" + cca.getFilter() + ")" + "\";" + '\n'
                    + "r2r:targetPattern " + "\"" + "?SUBJ a " + cca.getClass_Target() + "\".";
        }

        this.templateR2R = R2R;
        return R2R;
    }

    @Override
    public String toString() {
        return templateR2R;
    }
//    @Override
    //  public TemplateR2R createTemplate(CA _ca, int type_ca) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}

}
