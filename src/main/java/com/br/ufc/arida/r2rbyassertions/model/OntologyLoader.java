/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
//Imports para usar novas libs
/*import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.MaxCardinalityRestriction;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
*/
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import com.br.ufc.arida.r2rbyassertions.model.Class_;
import com.br.ufc.arida.r2rbyassertions.model.DataProperty;
import com.br.ufc.arida.r2rbyassertions.model.MappingConfiguration;
import com.br.ufc.arida.r2rbyassertions.model.ObjProperty;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.MaxCardinalityRestriction;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.Restriction;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class OntologyLoader {

    public HashMap<String, Class_> classes;
    public HashMap<String, String> mapPrefixes;
    public MappingConfiguration mc;
    
    public OntologyLoader(MappingConfiguration mc) {
        this.mapPrefixes = new HashMap<>();
        this.classes = new HashMap<>();
        this.mc = mc;
    }
    
    public void getClasse(String classe){
    
    }
       
    

    /*public void loadOntology() throws FileNotFoundException {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        String filePath = mc.getOntologyFilePath();
        if (filePath != null && !"".equals(filePath)) {
            ontModel.read(new FileInputStream(new File(filePath)), "http://ufc.br/rdb2rdfmb/", mc.getOntologyLang());
        } else {
            //ontModel.read(mc.getOntologyURL(), mc.getOntologyLang());
        }

        ExtendedIterator<OntClass> i = ontModel.listClasses();
        while (i.hasNext()) {
            OntClass ontClass = (OntClass) i.next();
            String prefix = ontModel.getNsURIPrefix(ontClass.getNameSpace());
            String name = ontClass.getLocalName();
               //Sugestão: verificar se nome e prefixo sao nulos
            if (classes.get(name + prefix) == null && (prefix != null || name != null)) {
                Class_ c = new Class_(prefix, name);
                classes.put(prefix + name, c);
                mapPrefixes.put(prefix, ontClass.getNameSpace());

                OntClass superClass = ontClass.getSuperClass();
                if (superClass != null) {
                    String superName = superClass.getLocalName();
                    String superPrefix = ontModel.getNsURIPrefix(superClass.getNameSpace());
                    if(superName != null && superPrefix != null){
                        Class_ superC = classes.get(superPrefix + superName);
                        if (superC == null) {
                            superC = new Class_(superPrefix, superName);
                            classes.put(superPrefix + superName, superC);
                            mapPrefixes.put(superPrefix, superClass.getNameSpace());
                        }
                        c.setSuperClass(superC);
                    }
                }
            }
        }

        ExtendedIterator<DatatypeProperty> i2 = ontModel.listDatatypeProperties();
        while (i2.hasNext()) {
            DatatypeProperty datatypeProperty = (DatatypeProperty) i2.next();

            if (datatypeProperty.getDomain() != null  && datatypeProperty.getRange() != null) {
                ExtendedIterator iDomains = datatypeProperty.listDomain();
                while (iDomains.hasNext()) {
                    OntClass cDomain = (OntClass) iDomains.next();                  
                    String dClassPrefix = ontModel.getNsURIPrefix(cDomain.getNameSpace());
                    String dClassName = cDomain.getLocalName();
                    Class_ dClass = classes.get(dClassPrefix + dClassName);
                    String dpName = datatypeProperty.getLocalName();
                    String dpPrefix = ontModel.getNsURIPrefix(datatypeProperty.getNameSpace());
                    String rangeName = datatypeProperty.getRange().getLocalName();

                    DataProperty dp = new DataProperty(dpPrefix, dpName, dClass, rangeName);
                    if (dClass != null) {
                        dClass.getdProperties().add(dp);
                        mapPrefixes.put(dpPrefix, datatypeProperty.getNameSpace());
                    }
                }
            }
        }

        ExtendedIterator<ObjectProperty> i3 = ontModel.listObjectProperties();
        
        while (i3.hasNext()) {
            ObjectProperty objectProperty = (ObjectProperty) i3.next();
            if (objectProperty.getDomain() != null   && objectProperty.getRange() != null) {
                String dClassPrefix = ontModel.getNsURIPrefix(objectProperty.getDomain().getNameSpace());
                String dClassName = objectProperty.getDomain().getLocalName();
                String rClassPrefix = ontModel.getNsURIPrefix(objectProperty.getRange().getNameSpace());
                String rClassName = objectProperty.getRange().getLocalName();
                String opName = objectProperty.getLocalName();
                String opPrefix = ontModel.getNsURIPrefix(objectProperty.getNameSpace());

                Class_ dClass = classes.get(dClassPrefix + dClassName);
                Class_ rClass = classes.get(rClassPrefix + rClassName);

                ObjProperty op = new ObjProperty(opPrefix, opName, dClass, rClass);
                if (dClass != null) {
                    dClass.getoProperties().add(op);
                    mapPrefixes.put(opPrefix, objectProperty.getNameSpace());
                }
            }
        }
        
        ExtendedIterator<Restriction> iRest = ontModel.listRestrictions();
        while (iRest.hasNext()) {
            Restriction r = (Restriction) iRest.next();
            if (r.isMaxCardinalityRestriction()) {
                MaxCardinalityRestriction mcr = r.asMaxCardinalityRestriction();
                System.out.println(mcr.getMaxCardinality());
                OntClass ontClass = mcr.getSubClass();
                OntProperty p = mcr.getOnProperty();

                String prefix = ontModel.getNsURIPrefix(ontClass.getNameSpace());
                String name = ontClass.getLocalName();

                Class_ c = classes.get(prefix + name);
                if (p.isObjectProperty()) {
                    ObjectProperty objectProperty = p.asObjectProperty();
                    String opName = objectProperty.getLocalName();
                    String opPrefix = ontModel.getNsURIPrefix(objectProperty.getNameSpace());

                    for (ObjProperty objProperty : c.getoProperties()) {
                        if (objProperty.getPrefix().equals(opPrefix) && objProperty.getName().equals(opName)) {
                            objProperty.setMaxCardinality(mcr.getMaxCardinality());
                        }
                    }
                } else {
                    DatatypeProperty datatypeProperty = p.asDatatypeProperty();
                    String dpName = datatypeProperty.getLocalName();
                    String dpPrefix = ontModel.getNsURIPrefix(datatypeProperty.getNameSpace());

                    for (DataProperty dataProperty : c.getdProperties()) {
                        if (dataProperty.getPrefix().equals(dpPrefix)     && dataProperty.getName().equals(dpName)) {
                            dataProperty.setMaxCardinality(mcr.getMaxCardinality());
                        }
                    }
                }
            }
        }
    }*/
}
