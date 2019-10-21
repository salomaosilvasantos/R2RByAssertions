/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import TemplatesR2R.OCATemplateR2R;
import TemplatesR2R.TemplateMappingRule;
import TemplatesR2R.TemplateR2R;
import TemplatesR2R.oca_MappingRule;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author tiagovinuto
 */
public class OCA extends CA {

    /**
     * ***
     * Atributos
     */
    private ObjProperty oPropertyT; 
    private ObjProperty oPropertyS; 
    private CCA classePai;
    private int idOCA = 0;
    private int idList = 0;
    private List<DataProperty> listDataPropertyOCA = new ArrayList<>();
    private List<ObjProperty> ObjectP = new ArrayList<>();
    private Class_ classePaiT;
    private String filter = "";
    private boolean isOCAEmbedded = false;
    protected OCATemplateR2R templateR2R;
    protected oca_MappingRule templateMappingRule;

    //Lista com todas OCAS criadas
    protected static List<OCA> listOCA = new ArrayList<OCA>();

    protected static boolean addOCA(OCA _oca) {

        Class_ targetDominio = _oca.getoPropertyT().getDomain();
        Class_ targetRange = _oca.getoPropertyT().getRange();
        Class_ sourceDominio = _oca.getoPropertyS().getDomain();
        Class_ sourceRange = _oca.getoPropertyS().getRange();
        boolean rest1 = false;
        boolean rest2 = false;

        for (ObjProperty op : _oca.getObjectP()) {
            for (CCA cca : CCA.listCCA) {
                if (cca.getClass_Target() == targetRange
                        && cca.getClass_Source() == op.getRange()) {
                    rest1 = true;
                }
                if (cca.getClass_Target() == targetDominio
                        && cca.getClass_Source() == op.getDomain()) {
                    rest2 = true;
                }
            }
        }

        if (rest1 && rest2) {
            listOCA.add(_oca);
            return true;
        }

        JOptionPane.showMessageDialog(null, "The OMA can not be added. Constraint Error!");
        _oca.toString();
        return false;
    }

    protected static synchronized List<OCA> getOCAS() {
        if (listOCA == null) {
            listOCA = new ArrayList<OCA>();
        }
        return listOCA;
    }

    public static boolean isOCAValid(OCA oca) {
        return true;
    }

    public OCA(ObjProperty _oPropertyT, ObjProperty _oPropertyS, List<ObjProperty> ObjectP) {
        this.oPropertyT = _oPropertyT;
        this.oPropertyT = _oPropertyS;
        this.ObjectP = ObjectP;
    }

    public OCA(CCA _classePai) {
        this.classePai = _classePai;
        idOCA = super.generateId();//Armazena o identificador
    }

    public OCA(Class_ _classePai) {
        this.classePaiT = _classePai;
        idOCA = super.generateId();
    }

    public OCA() {
        idOCA = super.generateId();
    }
    
    public void setFilter(String _text) {
        this.filter = _text;
    }

    public String getFilter() {
        return filter;
    }

    public CCA getClassePai() {
        return classePai;
    }

    public int getIdOCA() {
        return idOCA;
    }
    
    @Override
    public String pattenrsAC(){
        templateR2R = (OCATemplateR2R) TemplateR2R.getTemplateInstance(this,TemplateR2R.OCA);
        templateR2R.createTemplateR2R(); 
        
        return templateR2R.toString();
    } 
    
    @Override
    public String mappingRuler() {
        
        templateMappingRule = (oca_MappingRule) TemplateMappingRule.getTemplateInstance(this,templateMappingRule.OCA);
        templateMappingRule.createTemplateMappingRule();
        
        return templateMappingRule.toString();
    }

    @Override
    public String toString() {

        String strOCA = oPropertyT + " ≡ " + class_Source;

        if (ObjectP.size() == 1) {
            strOCA += " / " + ObjectP.get(0);

        } else if (ObjectP.size() > 1) {
            strOCA += " / [";
            int i = 0;
            for (ObjProperty op : ObjectP) {
                String path = op.toString();
                if (i++ > 0) {
                    strOCA += ", " + path;
                } else {
                    strOCA += path;
                }
            }
            strOCA += "] ";
        }
        
        if(getListDataPropertyOCA().size() == 1){
            strOCA += "/" + getListDataPropertyOCA().get(0);
        }
        else if(getListDataPropertyOCA().size() > 1){
            isOCAEmbedded = true;
            strOCA += " / [";
            int i = 0;
            for (DataProperty op : getListDataPropertyOCA()) {
                if (i++ > 0) {
                    strOCA += ", " + op.toString();
                } else {
                    strOCA += op.toString();
                  }
            }
            strOCA += "] ";
        }
        
        if (!filter.equals("")) {
            strOCA += filter;
        }
        return strOCA;
    }

    @Override
    public CA build(String toString, MappingConfiguration mc) {
        /*OCA ca = new OCA();
      
         String v1[] = toString.split(":");
         String v2[] = v1[0].split(" ");

         ca.setoPropertyT(new ObjProperty(v1[0].trim(), v2[0]));    
         ca.setClass_Source(new Class_(v2[2], v1[2]));
     
         List<ObjProperty> acpath = new ArrayList<ObjProperty>();
         ca.setObjectP(acpath);
        
         int i = 4;
         if (v2[i].indexOf('[') != -1) {
         v2[i] = v2[i].substring(1);
         while (v2[i].indexOf("]") == -1) {
         v2[i] = v2[i].replaceAll(",", "");
         acpath.add(v2[i]);
         i++;
         }
         v2[i] = v2[i].substring(0, v2[i].length() - 1);
         acpath.add(v2[i]);
         } else {
         acpath.add(v2[i]);
         }
         return ca;*/
        return null;
    }

    public ObjProperty getoPropertyT() {
        return oPropertyT;
    }

    public void setoPropertyT(ObjProperty oPropertyT) {
        this.oPropertyT = oPropertyT;
    }

    public ObjProperty getoPropertyS() {
        return oPropertyS;
    }

    public void setoPropertyS(ObjProperty oPropertyS) {
        this.oPropertyS = oPropertyS;
    }
    
    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    public void setIdOCA(int idOCA) {
        this.idOCA = idOCA;
    }

    @Override
    public String key() {
        return oPropertyT.getName();
    }

    public List<ObjProperty> getObjectP() {
        return ObjectP;
    }

    public void setObjectP(List<ObjProperty> ObjectP) {
        this.ObjectP = ObjectP;
    }

   public boolean isOCAEmbedded() {
        return this.isOCAEmbedded;
    }

    /**
     * @return the listDataPropertyOCA
     */
    public List<DataProperty> getListDataPropertyOCA() {
        return listDataPropertyOCA;
    }

    /**
     * @param listDataPropertyOCA the listDataPropertyOCA to set
     */
    public void setListDataPropertyOCA(List<DataProperty> listDataPropertyOCA) {
        this.listDataPropertyOCA = listDataPropertyOCA;
    }

}
