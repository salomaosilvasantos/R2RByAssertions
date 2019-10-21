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
public class Prefix {
    private String prefix;
    private String URI_prefix;

    public Prefix(){
        List<Prefix> prefixes = new ArrayList<Prefix>();
        
        prefixes.add(new Prefix("mo","<http://purl.org/ontology/mo/>"));
        prefixes.add(new Prefix("mbo","<http://creativeartefact.org/ontology/>"));
        prefixes.add(new Prefix("moa","<http://www.example.org/moa/>"));
        
        
        System.out.println(patternsAC(prefixes));
    }
    
    private String patternsAC(List<Prefix> _prefixes) {
        
        String R2R = "# Class Mappings"+ '\n' +
            "a r2r:ClassMapping ;"+ '\n' +
//            "r2r:prefixDefinitions \"" + "mo: <http://purl.org/ontology/mo/> . mbo:<http://creativeartefact.org/ontology/> . moa:<http://www.example.org/moa/>\";"+ '\n';
            "r2r:prefixDefinitions \"";
            for(Prefix _p : _prefixes) {
                R2R += _p.toString() + " . ";
            }
            R2R += ";" + '\n';
      return R2R;
    }
    
    public Prefix(String _prefix, String _URI_prefix){
        this.prefix = _prefix;
        this.URI_prefix = _URI_prefix;
    }
    
    
    public static void main (String[] a) {
        new Prefix();
    }
    
    public String getPrefix() {
        return prefix;
    }

    public String getURI_prefix() {
        return URI_prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setURI_prefix(String URI_prefix) {
        this.URI_prefix = URI_prefix;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.prefix + " : " + this.URI_prefix);
        
        return sb.toString();
    }
}
