/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import java.util.ArrayList;

import com.br.ufc.arida.r2rbyassertions.model.Class_;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;
import com.br.ufc.arida.r2rbyassertions.model.Property;

/**
 *
 * @author Tiagovinuto
 */
public abstract class TbpaneController {
    
    private Property currentProperty;
    private Class_ currentClass;
    private ArrayList<DataProperty> listProperty;
            
    
    public TbpaneController() {
        listProperty = new ArrayList<DataProperty>();
    }
    
    public Property getCurrentProperty() {
        return this.currentProperty;
    }
    
    public Class_ getCurrentClass() {
        return this.currentClass;
    }
    
    public ArrayList<DataProperty> getListProperty() {
        return this.listProperty;
    }
    
    public void addProperty(DataProperty _prop) {
        this.listProperty.add(_prop);
    }
    

    protected abstract void addTransformationFunction(String _text);
    
    protected abstract void addFilter(String _text);

    public void clearProperties() {
        listProperty = new ArrayList<DataProperty>();
    }
    
}
