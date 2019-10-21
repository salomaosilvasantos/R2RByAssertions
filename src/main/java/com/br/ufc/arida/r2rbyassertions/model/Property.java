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
public abstract class Property extends ObjectOWL {

    protected Prefix prefix;
    protected String name;
    protected Class_ domain;
    protected int maxCardinality = 1;
    
    
    public Property() {
        super();
    }

    
    public Property(Prefix _prefix, String name) {
        this.prefix = _prefix;
        this.name = name;
    }

    @Override
    public String toString() {
        return (prefix == null ? "" : prefix.getPrefix()) + ":" + name.toLowerCase();
        //return "1231321";
    }

    public abstract String getRangeName();

    public int getMaxCardinality() {
        return maxCardinality;
    }

    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    public String getPrefix() {
        return (prefix == null ? "" : prefix.getPrefix());
    }
    
    public String getURIPrefix(){ 
        return (prefix == null ? "" : prefix.getURI_prefix());
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class_ getDomain() {
        return domain;
    }

    public void setDomain(Class_ domain) {
        this.domain = domain;
    }
    
}
