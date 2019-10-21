/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;

/**
 *
 * @author Tiagovinuto
 */
public class MapNodes {
    
    /** 
     * REFERENCIA OS PAINEIS COM AS CLASSES
     * 
     */
    private static Map<Integer, Node> mapNodes;
    
    public static synchronized Map<Integer, Node> getInstance() {
        if (mapNodes == null) 
            mapNodes = new HashMap<>();
        
        return mapNodes;
    }
}
