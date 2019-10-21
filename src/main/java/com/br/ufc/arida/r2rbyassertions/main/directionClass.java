/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.main;

import com.br.ufc.arida.r2rbyassertions.model.Class_;
import com.hp.hpl.jena.ontology.ObjectProperty;

import java.util.List;

/**
 *
 * @author Tiagovinuto
 */
public class directionClass {
    
    private List<ObjectProperty> ob1;
    private List<ObjectProperty> ob2;
    private Class_ class1;
    private Class_ class2;
    public static final int DIRECTION_UNDIRECTED = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
   

    public List<ObjectProperty> getOb1() {
        return ob1;
    }

    public void setOb1(List<ObjectProperty> ob1) {
        this.ob1 = ob1;
    }

    public List<ObjectProperty> getOb2() {
        return ob2;
    }

    public void setOb2(List<ObjectProperty> ob2) {
        this.ob2 = ob2;
    }

    public Class_ getClass1() {
        return class1;
    }

    public void setClass1(Class_ class1) {
        this.class1 = class1;
    }

    public Class_ getClass2() {
        return class2;
    }

    public void setClass2(Class_ class2) {
        this.class2 = class2;
    }
}
