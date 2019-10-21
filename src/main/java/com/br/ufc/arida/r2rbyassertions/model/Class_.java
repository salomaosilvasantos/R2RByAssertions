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
 * @author tiagovinuto
 */
public class Class_ extends ObjectOWL{
    
    //public static ArrayList<Integer> ids = new ArrayList<>();
    //private String prefix;
    private Prefix prefix;
    private String name;
    private int idClass;
    private List<DataProperty> dProperties = new ArrayList<>(); 
    private List<ObjProperty> oProperties = new ArrayList<>(); 
    private List<Class_> ranges = new ArrayList<Class_>();
    private Class_ superClass;
    
    private Property propertySelectedFilter = null;
    
    public List<Class_> getRanges() {
        return this.ranges;
    }
    
    public boolean containRange(Class_ _range) {
        return ranges.contains(_range);
    }
    
    public int getId() {
        return idClass;
    }

   // protected int geraId() {
  //      ids.add(idCA);
  //      return ++idCA;
 //   }
    
    

    public Class_(String prefix, String name) {
        super();
        //this.prefix = prefix;
        this.name = name;
        idClass = super.generateId();
    }

    public Class_(Prefix _prefix, String name) {
        super();
        this.prefix = _prefix;
        this.name = name;
        idClass = super.generateId();
    }
    
    public Property getCurrentPropertySelected() {
        return this.propertySelectedFilter;
    }
    
    public void addPropertyFilter(String txt) {
        for(DataProperty dp : dProperties){
            if(dp.getName().contains(txt)){
                this.propertySelectedFilter = dp;
                break;
            }
        }
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Class_) {
            Class_ c = (Class_) obj;
            return (this.prefix.equals(c.prefix) && this.name.equals(c.name));
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
        return (prefix == null ? "" : prefix.getPrefix()) + ":" + ("" + name.charAt(0)).toUpperCase() + name.substring(1);
    }

       
    public String getPrefix() {
        return prefix.getPrefix();
      //      return (prefix == null ? "" : prefix);
    }
    
    public String getURI(){
        return prefix.getURI_prefix();
    }
    
    public static List<Class_> getSubClasses(Class_ c, Collection<Class_> values) {
        List<Class_> list = new ArrayList<>();

        for (Class_ class_ : values) {
            if (class_.getSuperClass() == c) {
                list.add(class_);
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

    public List<DataProperty> getdProperties() {
        return dProperties;
    }

    public void setdProperties(List<DataProperty> dProperties) {
        this.dProperties = dProperties;
    }

    public List<ObjProperty> getoProperties() {
        return oProperties;
    }

    public void setoProperties(List<ObjProperty> oProperties) {
        this.oProperties = oProperties;
    }

    public Class_ getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class_ superClass) {
        this.superClass = superClass;
    }
    
    /**
     * 
     *  
     * @param _classes
     * @param _prefix
     * @param _name
     * @return 
     */
    public static Class_ getByPrefixAndName(List<Class_> _classes, String _prefix, String _name){
        boolean prefixEqual = false;
        boolean nameEqual = false;
        
        for(Class_ c : _classes){
            if(c == null || c.getName() == null || c.getPrefix() == null)
                continue;
            
            //JOptionPane.showMessageDialog(null,"vou comparar: "+c.getPrefix()+ " com: "+_prefix);
            if(c.getPrefix().equals(_prefix)){
                prefixEqual = true;
            }
            if(c.getName().equals(_name)){
                nameEqual = true;
            }
            
            if(prefixEqual && nameEqual)
                return c;
            else {
                prefixEqual = false;
                nameEqual = false;
            }
        }
        
        return null;
    }
}
