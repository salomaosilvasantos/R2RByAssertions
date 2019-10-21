/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Tiagovinuto
 */
public class Property_ {
    private String prefix;
    private String name;
    private Property_ superProperty; //Verificar isso!!!!!

    public Property_(String prefix, String name) {
        super();
        this.prefix = prefix;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Property_) {
            Property_ p = (Property_) obj;
            return (this.prefix.equals(p.prefix) && this.name.equals(p.name));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.prefix);
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return (prefix == null ? "" : prefix) + " : " + ("" + name.charAt(0)).toLowerCase()+ name.substring(1);
    }

    public String getPrefix() {
        return (prefix == null ? "" : prefix);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public static List<Property_> getSubClasses(Property_ c, Collection<Property_> values) {
        List<Property_> list = new ArrayList<>();

        for (Property_ property_ : values) {
            if (property_.getSuperProperty() == c) {
                list.add(property_);
            }
        }
        return list;
    }

    public String getName() {
        if (name == null) {
            return null;
        }

        return ("" + name.charAt(0)).toUpperCase() + name.substring(1);
    }

    public void setName(String name) {
        this.name = name;
    }

   /*public Class_ getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class_ superClass) {
        this.superClass = superClass;
    }*/

    /**
     * @return the superProperty
     */
    
    public Property_ getSuperProperty() {
        return superProperty;
    }

    /**
     * @param superProperty the superProperty to set
     */
    public void setSuperProperty(Property_ superProperty) {
        this.superProperty = superProperty;
    }
    
}
