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
public class ObjetoPublico {
    private static List<Class_> classesT;
    private static List<Class_> classesS;
    private static boolean isOntoSourceInitialized = false;
    private static List<CA> assertivas;
    
    public static synchronized List<Class_> getClassesT() {
        if(classesT == null)
            classesT = new ArrayList<>();
        
        return classesT;
    }
    
    public static void addAssertion(CA _ca){
        if(assertivas == null)
            assertivas = new ArrayList<CA>();
        
        assertivas.add(_ca);
    }
    
    public static List<CA> getAssertions(){
        return assertivas;
    }
    
    
    public static synchronized boolean isOntoSourceInitialized() {
        return isOntoSourceInitialized;
    }
    
    public static synchronized void setOntoSourceInitialized(boolean _flag) {
        isOntoSourceInitialized = _flag;
    }
    
    public static synchronized List<Class_> getClassesS() {
        if(classesS == null)
            classesS = new ArrayList<>();
        
        return classesS;
    }
    
}
