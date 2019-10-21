/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.util;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tiagovinuto
 */
public class MapIcons {
    public final Node DATA_PROPERTY_ICON = new ImageView(new Image(getClass().getResourceAsStream(MapImages.DATA_PROPERTY_ICON_PATH)));
    public final Node CLASS_ICON = new ImageView(new Image(getClass().getResourceAsStream(MapImages.CLASS_ICON_PATH)));
    public final Node OBJ_PROPERTY_ICON = new ImageView(new Image(getClass().getResourceAsStream(MapImages.OBJECT_PROPERTY_ICON_PATH)));
    public final Node OBJ_PROPERTY_ICONINV = new ImageView(new Image(getClass().getResourceAsStream(MapImages.OBJECT_PROPERTY_ICON_PATH_INV)));
    
    public synchronized static MapIcons getInstance(){
        return new MapIcons();
    }
}