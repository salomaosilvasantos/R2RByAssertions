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
public class DataProperty extends Property {

    private String range;
    private int idProp;
    private Prefix prefix;
    private boolean isEmbedded = false;
    private boolean isFilterSelected = false;
    
    
    
    public DataProperty(Prefix _prefix, String name, Class_ domain, String range) {
        super(_prefix,name);
        //this.prefix = prefix;
        this.prefix = _prefix;
        this.name = name;
        this.domain = domain;
        this.range = range;
        idProp = super.generateId();
    }

    public DataProperty(Prefix prefix, String name) {
        super(prefix, name);
    }
    
    public DataProperty() {

    }
    
    public void setSelected(){
        this.isFilterSelected = true;
    }
    
    public boolean isSelected(){
        return this.isFilterSelected;
    }
    
    public void setEmbedded(){
        this.isEmbedded = true;
    }
    
    public boolean isEmbedded(){
        return this.isEmbedded;
    }
    
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public String getRangeName() {
        return getRange();
    }
    
    public int getId(){
        return this.idProp;
    }
}
