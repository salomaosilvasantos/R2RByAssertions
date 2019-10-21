/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemplatesR2R;

import java.util.Iterator;

import com.br.ufc.arida.r2rbyassertions.model.CA;
import com.br.ufc.arida.r2rbyassertions.model.DCA;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;
import com.br.ufc.arida.r2rbyassertions.model.OCA;
import com.br.ufc.arida.r2rbyassertions.model.ObjProperty;

/**
 *
 * @author Tiagovinuto
 */
public class OCATemplateR2R extends TemplateR2R {

    private OCA oca;
    public String templateR2R;

    public OCATemplateR2R(CA _ca) {
        this.oca = (OCA) _ca;
    }

    @Override
    public String createTemplateR2R() {
        String R2R = "";
        ObjProperty _ob = oca.getObjectP().get(0);

        if (oca.getObjectP().size() > 1 && oca.isOCAEmbedded()) {

            R2R = "# Property Mappings" + '\n'
                    + "mp: " + "ΨD" + oca.getoPropertyT().getName() + oca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping ;" + '\n'
                    + "r2r:prefixDefinitions" ;
                           
                    for (DataProperty dp : oca.getListDataPropertyOCA()) {
                       R2R += dp.getPrefix() + ":" + "<"+ dp.getURIPrefix()+ ">";
                    }
                    R2R += oca.getoPropertyT().getPrefix() + ":" + "<" +oca.getoPropertyT().getURIPrefix() + "> ;" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + oca.getoPropertyT().getDomain().getName() + " " + oca.getoPropertyT().getDomain().getId() + ";" + '\n'
                        + "r2r:MappingRef mp: " + "ΨC" + _ob.getDomain().getName() + _ob.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " + "?SUBJ ";

                    for (DataProperty dp : oca.getListDataPropertyOCA()) {
                        if (oca.getListDataPropertyOCA().size() > 1) {
                            R2R += "?" + dp.getName() + " " + dp.getPrefix() + ":" + dp.getName() + " . ";
                        } else {
                            R2R += "?" + dp.getName() + " " + dp.getPrefix() + ":" + dp.getName();
                        }
                    }

                    R2R += ";" + '\n'
                        + "r2r:targetPattern " + "SUBJ " + oca.getoPropertyT() + "?" + _ob.getName() + ";" + '\n'
                        + "r2r:tranformation" + "?" + _ob.getName() + " = " + "generateUri[](?SUBJ)" + ".";
        }

        if (oca.getObjectP().size() == 1) {
            R2R = "# Property Mappings" + '\n'
                    + "mp: " + "ΨD" + oca.getoPropertyT().getName() + oca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping ;" + '\n'
                    + "r2r:prefixDefinitions" + _ob.getPrefix() + " : " + "<" + _ob.getURIPrefix() + "> . " + oca.getoPropertyT().getPrefix() + ":" + "<" + oca.getoPropertyT().getURIPrefix() + ">"
                    + "r2r:MappingRef mp: " + "ΨC" + _ob.getDomain().getName() + _ob.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " + "?SUBJ " + _ob.getPrefix() + ":" + _ob.getName() + " " + "?" + _ob.getName() + ";" + '\n'
                    + "r2r:targetPattern " + "?SUBJ " + oca.getoPropertyT() + " " + "?" + _ob.getName() + ".";
        }

        if (oca.getObjectP().size() == 1 && !oca.getFilter().equals("")) {

            R2R = "# Property Mappings" + '\n'
                    + "mp: " + "ΨD" + oca.getoPropertyT().getName() + oca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping ;" + '\n'
                    + "r2r:prefixDefinitions" + _ob.getPrefix() + " : " + "<" + _ob.getURIPrefix() + "> . " + oca.getoPropertyT().getPrefix() + ":" + "<" + oca.getoPropertyT().getURIPrefix() + ">"
                    + "r2r:MappingRef mp: " + "ΨC" + _ob.getDomain().getName() + _ob.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " + "?SUBJ " + _ob.getPrefix() + ":" + _ob.getName() + " " + "?" + _ob.getName() + ". Filter(" + oca.getFilter() + ")" + ";" + '\n'
                    + "r2r:targetPattern " + "?SUBJ " + oca.getoPropertyT() + " " + "?" + _ob.getName() + ".";
        }

        if (oca.getObjectP().size() > 1) {

            R2R = "# Property Mappings" + '\n'
                    + "mp:" + "ΨD" + oca.getoPropertyT().getName() + oca.getIdList() + '\n'
                    + "a r2r:PropertyMapping ;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
                  //+"moa: <http://www.example.org/moa/>  .  dbp: <http://dbpedia.org/property/> \";" + '\n'
                    
                    String prefix = "";
                    for(int i = 0 ; i < oca.getObjectP().size(); i++){
                        ObjProperty op = oca.getObjectP().get(i);
                        if(i > 0) {
                            if(op.getPrefix().equals(oca.getObjectP().get(0).getPrefix()));
                            continue;
                        }
                        R2R += op.getPrefix() + " : " + "<" + op.getURIPrefix() + "> \" . ";
                        
                        i++;
                    }
                    
                    /*Iterator<ObjProperty> it1 = oca.getObjectP().iterator();
                    while(it1.hasNext()){
                         ObjProperty ob = it1.next();
                            R2R += ob.getPrefix() + " : " + "<" + ob.getURIPrefix() + "> \";" + '\n';

                    }*/
            
                    R2R += oca.getoPropertyT().getPrefix() + ":" + "<" +oca.getoPropertyT().getURIPrefix() + "> \";" + '\n';
                    R2R += "r2r:MappingRef mp:" + "ΨC" + _ob.getDomain().getName() + _ob.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " + "\"" + "?SUBJ ";

                    Iterator<ObjProperty> it = oca.getObjectP().iterator();
                    while(it.hasNext()){
                        ObjProperty ob = it.next();
                        R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName();
                        if(it.hasNext()){
                            R2R +=  " . " + "?" + ob.getName()+" ";
                        }
                    }
                    R2R += "\";" + '\n'
                        + "r2r:targetPattern " + "\"" + "?SUBJ " + oca.getoPropertyT().getPrefix()+ ":" + oca.getoPropertyT().getName() + " ";
                    
                    String ultOp = "";
                    Iterator<ObjProperty> it3 = oca.getObjectP().iterator();
                    while(it3.hasNext()){
                        ObjProperty ob = it3.next();
                        ultOp =  ob.getName() ;  
                    }
                    R2R += "?" + ultOp + "\".";
        }

        if (oca.getObjectP().size() > 1 && !oca.getFilter().equals("")) {

            R2R = "# Property Mappings" + '\n'
                    + "mp: " + "ΨD" + oca.getoPropertyT().getName() + oca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping ;" + '\n'
                    + "r2r:prefixDefinitions ";
            
                    for (ObjProperty ob : oca.getObjectP()) {
                        R2R += ob.getPrefix() + " : " + "<" + ob.getURIPrefix() + "> . ";
                    }
            
                    R2R += oca.getoPropertyT().getPrefix() + ":" + "<" +oca.getoPropertyT().getURIPrefix() + "> ;" + '\n';
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _ob.getDomain().getName() + _ob.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " + "?SUBJ ";

                    for (ObjProperty ob : oca.getObjectP()) {
                        if (oca.getObjectP().size() > 1) {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . " + "?" + ob.getName();
                        } else {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName();
                        }
                    }
                    R2R += ";" + '\n';

                    R2R += ". Filter(" + oca.getFilter() + ");" + '\n'
                        + "r2r:targetPattern " + "?SUBJ " + oca.getoPropertyT() + " " + "?" + _ob.getName() + ".";
        }

        this.templateR2R = R2R;
        return R2R;
    }

    @Override
    public String toString() {
        return templateR2R;
    }
}
