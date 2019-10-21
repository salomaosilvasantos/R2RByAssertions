/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author tiagovinuto
 */

public abstract class CA{

    protected Class_ class_Source;
    protected MappingConfiguration mc;
    public abstract String key();
    private static int idCA = 0;
    public static ArrayList<Integer> ids = new ArrayList<>();
    
    protected List<DataProperty> listDataTypeP = new ArrayList<>();
    
    public abstract CA build(String toString, MappingConfiguration mc);
    
    /***
     *propriedades
     * @return 
     */
    
    public static boolean addCA(CA _ca){
        if(_ca instanceof OCA){
            return OCA.addOCA((OCA)_ca);
        }
        if(_ca instanceof CCA){
            return CCA.addCCA((CCA)_ca);
        }
        if(_ca instanceof DCA){
            return DCA.addDCA((DCA)_ca);
        }
        return false;
    }
    
    public List<DataProperty> getDataTypeP() {
        return listDataTypeP;
    }

    public void setDataTypeP(List<DataProperty> dataTypeP) {
        this.listDataTypeP = dataTypeP;
    }

    public Class_ getClass_Source() {
        return class_Source;
    }

    public void setClass_Source(Class_ class_Source) {
        this.class_Source = class_Source;
    }
        
    public String pattenrsAC() {
        return "";
    }

    public String mappingRuler() {
        return "";
    }
    
    /**
     * gera um identificador para cada instancia criada
     * @return int identificador
     */
    
    protected int generateId() {
        System.out.println("Gerou nova identificacao " + idCA);
        ids.add(idCA);
        return ++idCA;
    }
    
    public MappingConfiguration getMc() {
        return mc;
    }

    public void setMc(MappingConfiguration mc) {
      this.mc = mc;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CA) {
            CA ca = (CA) obj;
            return this.key().equals(ca.key());
        }

        return false;
    }
   
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.key());
        return hash;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Thiago");
        return sb.toString();
    }
    
    
    public static CA getCAByID(List<CA> listCA, int _id){
        for(CA ca : listCA){
            if(idCA == _id)
                return ca;
        }
        return null;
    }

    

}
