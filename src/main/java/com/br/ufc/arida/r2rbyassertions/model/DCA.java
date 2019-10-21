/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import TemplatesR2R.DCATemplateR2R;
import TemplatesR2R.TemplateMappingRule;
import TemplatesR2R.TemplateR2R;
import TemplatesR2R.dca_MappingRule;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author tiagovinuto
 */
public class DCA extends PCA {

    /**
     * ***
     * Atributos
     */
    private DataProperty dPropertyT;
    private DataProperty dPropertyS;
    private List<DataProperty> listDataTypeP = new ArrayList<DataProperty>();
    private List<ObjProperty> listObjectD = new ArrayList<ObjProperty>();

    private String selCondition;
    private String nameFuntion;
    private CCA classePai;

    private String transformationFunction = "";
    private String filter = "";
    private int idDCA = 0;
    private int idList = 0;

    private boolean isDCAEmbedded = false;

    protected static List<DCA> listDCA = new ArrayList<DCA>();

    protected DCATemplateR2R templateR2R;
    protected dca_MappingRule templateMappingRule;

    public String getTransformationFunction() {
        return transformationFunction;
    }

    public void setTransformationFunction(String transformationFunction) {
        this.transformationFunction = transformationFunction;
    }

    public void setFilter(String _text) {
        this.filter = _text;
    }

    public String getFilter() {
        return filter;
    }

    /**
     * ***
     * Construtores
     */
    public boolean isDCAEmbedded() {
        return this.isDCAEmbedded;
    }

    protected static boolean addDCA(DCA _dca) {


        Class_ domainOP = _dca.getdPropertyT().getDomain();
        //Class_ dominioS = _dca.getdPropertyS().getDomain();


        if (CCA.listCCA.isEmpty()) {
            JOptionPane.showMessageDialog(null, " The DCA can not be Generated");
        } else {

            for (CCA cca : CCA.listCCA) { 
                _dca.setIdList(listDCA.size() + 1); // Added it's line in 08/09/16
                if (cca.getClass_Target() == domainOP) {
                    listDCA.add(_dca);
                    return true;
                }
            }
            JOptionPane.showMessageDialog(null, " The DCA can not be Generated");
        }
        _dca.toString();
        return false;
    }
    
    
    protected static boolean addDCAEmbedded(DCA _dca) {
        
        Class_ dPtarget = _dca.getdPropertyT().getDomain();
        Class_ dPsouce = _dca.getdPropertyS().getDomain();
        
        for (CCA cca : CCA.listCCA) {
            if (cca.getClass_Target() == dPtarget && cca.isEmbedded() == true){
                if(cca.getClass_Source() == dPsouce){
                
                }
            } 
        }
        return true;
    } 

    /**
     * ***
     * Construtores
     */

    public DCA(CCA _classePai) {
        this.classePai = _classePai;
        idDCA = super.generateId();//Armazena o identificador
    }

    public DCA() {
        idDCA = super.generateId();//Armazena o identificador
    }

    public CCA getClassePai() {
        return classePai;
    }

    public void setClassePai(CCA _ccaPai) {
        this.classePai = _ccaPai;
    }

    /**
     * Métodos Herdados
     *
     * @return
     */
    @Override
    public String pattenrsAC() {
        templateR2R = (DCATemplateR2R) TemplateR2R.getTemplateInstance(this, TemplateR2R.DCA);
        templateR2R.createTemplateR2R();

        return templateR2R.toString();
    }

    @Override
    public String mappingRuler() {

        templateMappingRule = (dca_MappingRule) TemplateMappingRule.getTemplateInstance(this, templateMappingRule.DCA);
        templateMappingRule.createTemplateMappingRule();

        return templateMappingRule.toString();
    }

    @Override
    public String toString() {
        String strDCA = dPropertyT + " ≡ " + class_Source;

        if (getListObjectD().size() == 1) {
            strDCA += " / " + getListObjectD().get(0);

        } else if (getListObjectD().size() > 1) {
            strDCA += " / [";
            int i = 0;
            for (ObjProperty op : getListObjectD()) {
                String path = op.toString();
                if (i++ > 0) {
                    strDCA += ", " + path;
                } else {
                    strDCA += path;
                }
            }
            strDCA += "] ";
        } // Mudei o código acima até o IF em 06/09

        if (getListDataTypeP().size() == 1) {
            strDCA += " / " + getListDataTypeP().get(0);
        } else if (getListDataTypeP().size() > 1) {

            strDCA += isDCAEmbedded ? "[" : "{";
            int i = 0;
            for (DataProperty a : getListDataTypeP()) {
                if (i++ > 0) {
                    strDCA += ", " + a.toString();
                } else {
                    strDCA += a.toString();
                }
            }
            strDCA += isDCAEmbedded ? "]" : "}";
            //strDCA += " /";
        }

        if (!filter.equals("")) {
            strDCA += " / " + filter;
        }

        if (!transformationFunction.equals("")) {
            strDCA += "/" + transformationFunction;
        }

        return strDCA;
    }

    @Override
    public String key() {
        return dPropertyT.getName();
    }

    @Override
    public CA build(String toString, MappingConfiguration mc) {
        DCA ca = new DCA();

        String v1[] = toString.split(":");
        String v2[] = v1[0].split(" ");

        List<String> acpath = new ArrayList<>();
        //ca.setPathAC(acpath);

        for (int i = 4; i < v2.length; i++) {
            String path = v2[i].replaceAll(",", "");
            if (path.indexOf('[') != -1) {
                path = path.substring(1);
            }
            if (path.indexOf(']') != -1) {
                path = path.substring(0, path.length() - 1);
            }
            acpath.add(path);
        }

        List<String> dtype = new ArrayList<>();

        return ca;
    }

    /**
     * ***
     * Métodos de Atributos
     */
    /**
     * @return the dPropertyS
     */
    public DataProperty getdPropertyS() {
        return dPropertyS;
    }

    /**
     * @param dPropertyS the dPropertyS to set
     */
    public void setdPropertyS(DataProperty dPropertyS) {
        this.dPropertyS = dPropertyS;
    }

    /**
     * @return the dPropertyT
     */
    public DataProperty getdPropertyT() {
        return dPropertyT;
    }

    /**
     * @param dPropertyT the dPropertyT to set
     */
    public void setdPropertyT(DataProperty dPropertyT) {
        this.dPropertyT = dPropertyT;
    }

    /**
     * @return the selCondition
     */
    public String getSelCondition() {
        return selCondition;
    }

    /**
     * @param selCondition the selCondition to set
     */
    public void setSelCondition(String selCondition) {
        this.selCondition = selCondition;
    }

    public int getIdDCA() {
        return idDCA;
    }

    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    /**
     * @return the listDataTypeP
     */
    public boolean addListDataTypeP(DataProperty _dProperty) {
        /*String[] options = new String[]{"Yes", "Not", "Cancel"}; //0, 1 e 2
        int optChosen;
        if (this.listDataTypeP.size() > 0) {
            optChosen = JOptionPane.showOptionDialog(null, "You want to create a DCA embedded?", "", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (optChosen == 0) {
                this.isDCAEmbedded = true;
            }
        }*/

        return this.listDataTypeP.add(_dProperty);
    }

    public List<DataProperty> getListDataTypeP() {
        return listDataTypeP;
    }

    /**
     * @param listDataTypeP the listDataTypeP to set
     */
    public void setListDataTypeP(List<DataProperty> listDataTypeP) {
        this.listDataTypeP = listDataTypeP;
    }

    /**
     * @return the listObjectD
     */
    public List<ObjProperty> getListObjectD() {
        return listObjectD;
    }

    /**
     * @param listObjectD the listObjectD to set
     */
    public void setListObjectD(List<ObjProperty> listObjectD) {
        this.listObjectD = listObjectD;
    }
}
