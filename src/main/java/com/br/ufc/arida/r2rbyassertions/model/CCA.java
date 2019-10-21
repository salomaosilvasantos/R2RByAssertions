/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import TemplatesR2R.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ListView;

/**
 *
 * @author tiagovinuto
 */

public class CCA extends CA{
     
    /*****
     * Atributos 
     */
    
    private Class_ class_Target;
    private int idCCA = 0;
    private int idList = 0;  
    private boolean initialized;
    private String filter = "";

    private List<DCA> dcaList = new ArrayList<>();
    private List<OCA> ocaList = new ArrayList<>();
    
    private boolean embedded = false;
    //Lista com todas CCAs criadas
    protected static List<CCA> listCCA = new ArrayList<CCA>();
    protected CCATemplateR2R templateR2R;
    protected cca_MappingRule templateMappingRule;
    
    /*****
     * Construtores 
     */
    
    protected static boolean addCCA(CCA _cca) {
        _cca.setIdList(listCCA.size()+1);
        listCCA.add(_cca);
        return true;
    }
    
    public CCA() {
        idCCA = super.generateId();//Armazena o identificador
        this.initialized = false;
    }

    public CCA(Class_ class_Source, Class_ class_Target, List<DataProperty> dataTypeP, String selCondition) {
        this.class_Target = class_Target;
        this.listDataTypeP = dataTypeP;
        this.initialized = false;
        this.class_Source = class_Source;
    }
    
    /*****
     * Métodos Herdados
     * @return 
     */
    
    @Override
    public String pattenrsAC(){
        templateR2R = (CCATemplateR2R) TemplateR2R.getTemplateInstance(this,TemplateR2R.CCA);
        templateR2R.createTemplateR2R();
      
        return templateR2R.toString();
    } 
    
    @Override
    public String mappingRuler(){
        
        templateMappingRule = (cca_MappingRule) templateMappingRule.getTemplateInstance(this,templateMappingRule.CCA);
        templateMappingRule.createTemplateMappingRule();
        
        return templateMappingRule.toString();

    }
    
    public void setEmbedded(){
        this.embedded = true;
    }
    
    public boolean isEmbedded(){
        return this.embedded;
    }
    
    public List<DataProperty> getListDataTypeP() {
        return listDataTypeP;
    }
    /*****
     * Métodos
     * @return 
     */
    @Override
    public String toString() {
        String strCCA = class_Target + " ≡ " + class_Source;
              
        if (listDataTypeP.size() == 1) {
           if(listDataTypeP.get(0).isEmbedded()){
               strCCA += " [ " + listDataTypeP.get(0) + " ]"; 
               setEmbedded();
           }
           else{
               strCCA += " / " + listDataTypeP.get(0); 
           }
           
        }else if(listDataTypeP.size() > 1){
            strCCA += " [";
            int i = 0;
            for (DataProperty op : listDataTypeP) {
                String dataP = op.toString();
                if (i++ > 0) {
                    strCCA += ", " + dataP;
                } else {
                    strCCA += dataP;
                }
            }
            strCCA += "] ";
        }
        
        if (!filter.equals("")) {
            strCCA += " / ";
            strCCA += filter;
        }
        return strCCA;
    }

    @Override
    public CA build(String toString, MappingConfiguration mc) {
        /*CCA ca = new CCA();
        
        String v1[] = toString.split(":");
        String v2[] = v1[1].split(" ");
        
        ca.setClass_Target(new Class_(v1[0].trim(), v2[1]));
        ca.setClass_Source(new Class_(v1[0].trim(), v2[3]));

        List<String> dtype = new ArrayList<>();
        ca.setDataTypeP(dtype);
        
        for (int i = 4; i < v2.length; i++) {
            String att = v2[i].replaceAll(",", "");
            if (att.indexOf('{') != -1) {
                att = att.substring(1);
            }
            if (att.indexOf('}') != -1) {
                att = att.substring(0, att.length() - 1);
            }
            dtype.add(att);
        }*/

        return null;//ca
    }

    @Override
    public String key() {
        return class_Target.getName();
    }

    public static CCA getCcaFromClass(ListView<CA> assertionsList, Class_ class_Target) {
        for (CA ca : assertionsList.getItems()) {
            if (ca instanceof CCA) {
                CCA cca = (CCA) ca;

                Class_ class2_ = cca.getClass_Target();
                if (class_Target.equals(class2_)) {
                    return cca;
                }
            }
        }

        return null;
    }
    
    /*****
     * Métodos de Atributos
     * @return 
     */

    public Class_ getClass_Target() {
        return class_Target;
    }

    public void setClass_Target(Class_ class_Target) {
        this.class_Target = class_Target;
    }
    
       /**
     * @return the dataTypeP
     */
    @Override
    public List<DataProperty> getDataTypeP() {
        return listDataTypeP;
    }

    /**
     * @param dataTypeP the dataTypeP to set
     */
    @Override
    public void setDataTypeP(List<DataProperty> dataTypeP) {
        this.listDataTypeP = dataTypeP;
    }
    
    public int getIdCCA() {
        return idCCA;
    }
    
    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }
    
    /**
     * @return the dcaList
     */
    public List<DCA> getDcaList() {
        return dcaList;
    }

    /**
     * @param dcaList the dcaList to set
     */
    public void setDcaList(List<DCA> dcaList) {
        this.dcaList = dcaList;
    }
    
    /**
     * @return the ocaList
     */
    public List<OCA> getOcaList() {
        return ocaList;
    }

    /**
     * @param ocaList the ocaList to set
     */
    public void setOcaList(List<OCA> ocaList) {
        this.ocaList = ocaList;
    }
    
    public void setFilter(String _text) {
        this.filter = _text;
    }

    public String getFilter() {
        return filter;
    }


    
}

