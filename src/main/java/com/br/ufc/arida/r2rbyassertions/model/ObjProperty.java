/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

/**
 *
 * @author tiagovinuto
 */
public class ObjProperty extends Property{
     private Class_ range;
     private boolean hasRange = false;
  
     private boolean isInverse;

     private ObjProperty g;
     private int idObjP;
     
     private Prefix prefix;
     
     /*public ObjProperty(boolean inverse, ObjProperty class1) {
        this.inverse = inverse;
        this.range = class1;
     }*/
     
    public ObjProperty(Prefix _prefix, String name, Class_ domain, Class_ range,boolean _isInverse) {
        super(_prefix,name);
        this.prefix = _prefix;
        this.name = name;
        this.domain = domain;
        this.setRange(range);
        this.isInverse = _isInverse;
        idObjP = super.generateId();
    }
    
    public ObjProperty(){}

    public ObjProperty(Prefix prefix, String name) {
        super(prefix, name);
    }
    
     public boolean isInverse() {
        return isInverse;
    }

    public void setInverse(boolean inverse) {
        this.isInverse = inverse;
    }

    public Class_ getRange() {
        return range;
    }

    public boolean hasRange() {
        return hasRange;
    }
    
    public void setRange(Class_ range) {
        this.range = range;
        if(range != null)
            this.hasRange = true;
    }

    @Override
    public String getRangeName() {
        return range.getName();
    }
    
    public int getId(){
        return this.idObjP;
    }
}
