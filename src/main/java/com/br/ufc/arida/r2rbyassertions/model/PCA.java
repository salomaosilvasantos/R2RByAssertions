/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiagovinuto
 */
public abstract class PCA  extends CA{
    protected List<String> ObjectP = new ArrayList<>();

    public List<String> getObjectP() {
        return ObjectP;
    }

    public void setObjectP(List<String> ObjectP) {
        this.ObjectP = ObjectP;
    }
    
    public PCA(List<String> ObjectP) {
        this.ObjectP = ObjectP;
    }

    public PCA() {
    }
}
