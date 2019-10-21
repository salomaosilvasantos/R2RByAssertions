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
public class DCATemplateR2R extends TemplateR2R {

    private DCA dca;
    private OCA oca;
    public String templateR2R;

    public DCATemplateR2R(CA _ca) {
        this.dca = (DCA) _ca;
    }

    //DCAs
    //1. Ψ: CT/PT ≡ CS/PS/f/T
    //Se existir objectproperty
    //2. Ψ: CT/PT ≡ CS//PR/f/T 
    //3. Ψ: CT/PT ≡ CS/{PS1,..., PSn}/T   ---> São as dataproperty da classe Fonte
    //Se existir objectproperty ou ....
    //4. Ψ: CT/PT ≡ CS//{PR1,..., PRm}/T    ---> São as dataproperty da ultima classe do caminho 
    //5. Ψ: CT/PT ≡ CS[A1,..., An]/Ai (1 ≤ i ≤ n)   ---> AMD embutida 
    @Override
    public String createTemplateR2R() {
        String R2R = "";
        DataProperty _dp = dca.getListDataTypeP().get(0);
        
        if (dca.getListDataTypeP().size() == 1) {
        
            R2R = "#Property Mappings" + "\n\n"
                    + "mp:" + "ΨD" + dca.getdPropertyT().getName() + dca.getdPropertyT().getId()+ '\n'
                    + "a r2r:PropertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + _dp.getPrefix() + ":" + "<" + _dp.getURIPrefix() + ">" + " . " + dca.getdPropertyT().getPrefix() + ":" + "<"+dca.getdPropertyT().getURIPrefix() + "> \";" + '\n'
                    + "r2r:MappingRef mp:" + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n' // FAZER ISSSO PARA TODOS OS MAPEAMENTOS;
                    + "r2r:sourcePattern " + "\"" +"?SUBJ " + _dp.getPrefix() + ":" + _dp.getName() + " " + " ?" + _dp.getName() + "\";" + '\n'
                    + "r2r:targetPattern " + "\"" + "?SUBJ " + dca.getdPropertyT().getPrefix() +":"+ dca.getdPropertyT().getName()+ " " + " ?" + _dp.getName() + "\".";
        }

        if (dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("")) {
             
            R2R = "#Property Mappings;" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + _dp.getPrefix() + ":" + "<" + _dp.getURIPrefix()+">" + " . " +dca.getdPropertyT().getPrefix() + ":" + "<"+dca.getdPropertyT().getURIPrefix() + "> \";" + '\n'
                    + "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " +"\""+ "?SUBJ " + _dp.getPrefix() + ":" + _dp.getName() + " " + "?" + _dp.getName() + " . Filter(" + dca.getFilter() + ")" + "\";" + '\n'
                    + "r2r:targetPattern " +"\""+ "?SUBJ " + dca.getdPropertyT() + " " + "?" + _dp.getName() + "\".";
        }
        
        if (dca.getListDataTypeP().size() == 1 && !dca.getTransformationFunction().equals("")) {
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping ;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + _dp.getPrefix() + ":" + "<" + _dp.getURIPrefix()+">" + " . " +dca.getdPropertyT().getPrefix() + ":" + "<"+dca.getdPropertyT().getURIPrefix() + "> \";" + '\n'
                    + "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " +"\""+ "?SUBJ " + _dp.getPrefix() + ":" + _dp.getName() + " " + "?" + _dp.getName() + ". Filter" + "(" + dca.getFilter() + ")" + "\";" + '\n'
                    + "r2r:targetPattern " +"\""+ "?SUBJ " + dca.getdPropertyT() + " " + "?" + _dp.getName() + "\";" + '\n'
                    + "r2r:tranformation " +"\""+ "?" + _dp.getName() + " = " + dca.getTransformationFunction() + "\".";
        }

        if (dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("") && !dca.getTransformationFunction().equals("")) {
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping ;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" + _dp.getPrefix() + ":" + "<" + _dp.getURIPrefix()+">" + " . " +dca.getdPropertyT().getPrefix() + ":" + "<"+dca.getdPropertyT().getURIPrefix() + "> \";" + '\n'
                    + "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " +"\""+ "?SUBJ " + _dp.getPrefix() + ":" + _dp.getName() + " " + "?" + _dp.getName() + ". Filter" + "(" + dca.getFilter() + ")" + "\";" + '\n'
                    + "r2r:targetPattern " +"\""+ "?SUBJ " + dca.getdPropertyT() + " " + "?" + _dp.getName() + "\";" + '\n'
                    + "r2r:tranformation " +"\""+ "?" + _dp.getName() + " = " + dca.getTransformationFunction() + "\".";
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() == 1) {
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
                    for (ObjProperty ob : dca.getListObjectD()) {
                        R2R += ob.getPrefix() +" : "+ "<" + ob.getURIPrefix() + "> . ";
                    }
                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix() + "> \";" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " +"\""+ "?SUBJ ";

                    for (ObjProperty ob : dca.getListObjectD()) {
                        if (dca.getListObjectD().size() > 1) {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . " + "?" + ob.getName();
                        } else {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . ";
                          }
                    }

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName() + " . ";
                        } else {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName();
                          }
                    }

                    R2R += "\";" + '\n'
                        + "r2r:targetPattern " +"\""+ "?SUBJ " + dca.getdPropertyT() + " " + "?" + _dp.getName() + "\"."+ '\n'; 
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("")) {
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
                    for (ObjProperty ob : dca.getListObjectD()) {
                        R2R += ob.getPrefix() +" : "+ "<" + ob.getURIPrefix() + "> . ";
                    }
                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix() + "> \";" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " +"\""+ "?SUBJ ";

                    for (ObjProperty ob : dca.getListObjectD()) {
                        if (dca.getListObjectD().size() > 1) {
                           R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . " + "?" + ob.getName();
                        } else {
                           R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . ";
                        }
                    }

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                           R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName() + " . ";
                        } else {
                           R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName();
                        }
                    }

                    R2R += " . Filter(" + dca.getFilter() + "\";" + '\n';
                    R2R += "r2r:targetPattern " +"\""+ "?SUBJ " + dca.getdPropertyT() + " " + "?" + _dp.getName() + "\"."+ '\n';
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() == 1 && !dca.getFilter().equals("") && !dca.getTransformationFunction().equals("")) {
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
                    
                    for (ObjProperty ob : dca.getListObjectD()) {
                        R2R += ob.getPrefix() +" : "+ "<" + ob.getURIPrefix() + "> . ";
                    }
                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix() + "> \";" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " +"\""+ "?SUBJ ";

                    for (ObjProperty ob : dca.getListObjectD()) {
                        if (dca.getListObjectD().size() > 1) {
                           R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . " + "?" + ob.getName();
                        } else {
                           R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . ";
                        }
                    }

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName() + " . ";
                        } else {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName();
                        }
                    }

                    R2R += " . Filter(" + dca.getFilter()  + "\";" + '\n';

                    R2R += "r2r:targetPattern " + "?SUBJ " + dca.getdPropertyT() + "?" + _dp.getName() + ";" + '\n'
                         + "r2r:tranformation " +"\""+ "?" + _dp.getName() + " = " + dca.getTransformationFunction() + "\"."+ '\n';
        }

        if (dca.getListDataTypeP().size() > 1 && !dca.getTransformationFunction().equals("")) {
             
            R2R = "#Property Mappings" + "\n\n"
                    + "mp:" + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + '\n'
                    + "a r2r:PropertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" +"moa: <http://www.example.org/moa/>  .  dbp: <http://dbpedia.org/property/> \";" + '\n';
                    /*+ "r2r:prefixDefinitions " + "\"" ;

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix()  + "> \";" + '\n';*/
                    
                    R2R += "r2r:MappingRef mp:" + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " +"\""+ "?SUBJ ";

                    /*for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                            R2R += dp.getPrefix() + ":" + dp.getName() + " ?" + dp.getName() + "; ";
                        } else {
                            R2R += dp.getPrefix() + ":" + dp.getName() + " ?" + dp.getName();
                        }
                    }*/
                    if (dca.getListDataTypeP().size() == 1){
                        R2R +=  _dp.getPrefix() + ":" + _dp.getName() + " ?" + _dp.getName();
                    }
                    else{
                    Iterator<DataProperty> it = dca.getListDataTypeP().iterator();
                        while (it.hasNext()) {
                            DataProperty dp = it.next();
                            R2R += dp.getPrefix() + ":" + dp.getName() + " " + "?" + dp.getName();
                            if (it.hasNext()) {
                                R2R += "; ";
                            }
                        }
                    }
                    

                    R2R += "\";" + '\n'
                        + "r2r:targetPattern " +"\""+ "?SUBJ " + dca.getdPropertyT().getPrefix() +":" + dca.getdPropertyT().getName() + " ?" + dca.getdPropertyT().getName() + "\";" + '\n'
                        + "r2r:transformation " +"\""+ "?" + dca.getdPropertyT().getName() + " = " + dca.getTransformationFunction() + "\"." + '\n';
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() > 1) {  // esse cara tá nulo (oca.getObjectP())
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
            
                    for (ObjProperty ob : dca.getListObjectD()) {
                        R2R += ob.getPrefix() +" : "+ "<" + ob.getURIPrefix() + "> . ";
                    }
                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix() + "> \";" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " + "\"" +  "?SUBJ ";

                    for (ObjProperty ob : dca.getListObjectD()) {
                        if (dca.getListObjectD().size() > 1) {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . " + "?" + ob.getName();
                        } else {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . ";
                        }
                    }

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName() + " . ";
                        } else {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName();
                        }
                    }

                    R2R +=  "\";" + '\n'
                        + "r2r:targetPattern " + "?SUBJ " + dca.getdPropertyT() + "?" + _dp.getName() + "\"." + '\n';
        }

        if (dca.getListObjectD().size() > 1 && dca.getListDataTypeP().size() > 1 && !dca.getTransformationFunction().equals("")) {
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
            
                    for (ObjProperty ob : dca.getListObjectD()) {
                        R2R += ob.getPrefix() +" : "+ "<" + ob.getURIPrefix() + "> . ";
                    }
                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix() + "> \";" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " + "\"" +  "?SUBJ ";

                    for (ObjProperty ob : dca.getListObjectD()) {
                        if (dca.getListObjectD().size() > 1) {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . " + "?" + ob.getName();
                        } else {
                            R2R += ob.getPrefix() + ":" + ob.getName() + " " + "?" + ob.getName() + " . ";
                        }
                    }

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName() + " . ";
                        } else {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName();
                        }
                    }

                    R2R += "\";" + '\n'
                        + "r2r:targetPattern " + "?SUBJ " + dca.getdPropertyT() + "?" + _dp.getName() + "\";" + '\n'
                        + "r2r:tranformation " + "?" + _dp.getName() + " = " + dca.getTransformationFunction() + "\"." + '\n';
        }

        if (dca.getListDataTypeP().size() > 1 && dca.isDCAEmbedded() == true) {
             
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
            
                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix() + "> \";" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                    + "r2r:sourcePattern " + "\"" +  "?SUBJ ";

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName() + " . ";
                        } else {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName();
                        }
                    }

                    R2R += "\";" + '\n'
                        + "r2r:targetPattern " + "?nomeDaVariável" + dca.getdPropertyT() + "?" + _dp.getName() + "\";" + '\n'
                        + "r2r:tranformation " + "?" + _dp.getName() + " = " + "generateUri[](?SUBJ)" + "\"." + '\n';
        }
        
        /*if (dca.getListDataTypeP().size() > 1 && dca.isDCAEmbedded() == true) {
             System.out.println("Entrei no 12");
            R2R = "#Property Mappings" + '\n'
                    + "mp: " + "ΨD" + dca.getdPropertyT().getName() + dca.getIdList() + ";" + '\n'
                    + "a r2r:propertyMapping;" + '\n'
                    + "r2r:prefixDefinitions " + "\"" ;
            
                    for (DataProperty dp : dca.getListDataTypeP()) {
                        R2R += dp.getPrefix() +" : "+ "<" + dp.getURIPrefix() + "> . ";
                    }
                    R2R +=  dca.getdPropertyT().getPrefix() + " : " + "<" + dca.getdPropertyT().getURIPrefix() + "> \";" + '\n';
                    
                    R2R += "r2r:MappingRef mp: " + "ΨC" + _dp.getDomain().getName() + _dp.getDomain().getId() + ";" + '\n'
                        + "r2r:sourcePattern " + "\"" +  "??SUBJ ";

                    for (DataProperty dp : dca.getListDataTypeP()) {
                        if (dca.getListDataTypeP().size() > 1) {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName() + " . ";
                        } else {
                            R2R += dp.getPrefix() + ":" + dp.getName() + "?" + dp.getName();
                        }
                    }

                    R2R += "\";" + '\n'
                        + "r2r:targetPattern " + "?SUBJ" + dca.getdPropertyT() + " " + "?" + _dp.getName() + "\"." + '\n';
        }*/

        this.templateR2R = R2R;
        return R2R;
    }

    @Override
    public String toString() {
        return templateR2R;
    }

    /* @Override
     public TemplateR2R createTemplate(CA _ca, int type_ca) {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }*/
}
