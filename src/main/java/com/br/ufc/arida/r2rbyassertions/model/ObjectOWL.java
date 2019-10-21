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


/** 
 * 
 * Classe mãe de Class_, DataProperty e ObjProperty. 
 * 
 * @author Tiagovinuto
 */
public class ObjectOWL {
    
    private List<CA> listCA = new ArrayList<>();
    private int id;
    private static int idOWL = 0;
    public static ArrayList<Integer> ids = new ArrayList<>();
    
    protected int generateId() {
        ids.add(idOWL);
        id = ++idOWL;
        return id;
    }
    
    public List<CA> getListCA() {
        return this.listCA;
    }
    
    public int getId(){
        return this.id;
    }
    
    public CA getLastCA(){
        if(listCA.size() > 0)
            return listCA.get(listCA.size()-1);
        
        return null;
    }
    
}
