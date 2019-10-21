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
public class MappingConfiguration {

    private int id;
    private String creationDate;
    private String targetOntology;
    private String targetOntologyFilePath;
    private String targetOntologyURL;
    private String targetOntologyLang;

    private String sourceOntology;
    private String sourceOntologyFilePath;
    private String sourceOntologyURL;
    private String sourceOntologyLang;
    
    private String r2rSourceFilePath;
    private String r2rtargetFilePath;
    private String r2rUrl;

    public MappingConfiguration(int id) {
        this.id = id;
    }

    public MappingConfiguration(int id, String targetOntology, String sourceOntology, String creationDate) {
        this.id = id;
        this.targetOntology = targetOntology;
        this.sourceOntology = sourceOntology;
        this.creationDate = creationDate;
    }

   public MappingConfiguration(String targetOntology, String sourceOntology, String ontologyLocationT, String ontologyLocationS, int locationTypeT, int locationTypeS, String targetOntologyLang, String _sourceOntologyLang, String _r2rSourceFilePath, String _r2rTargetFilePath){
        this.targetOntology = targetOntology;
        this.sourceOntology = sourceOntology;
            
        if (locationTypeT == 1 && locationTypeS == 1) {
            this.targetOntologyFilePath = ontologyLocationT;
            this.sourceOntologyFilePath = ontologyLocationS;
        }
        if (locationTypeT == 2 && locationTypeS == 1) {
            this.targetOntologyURL = ontologyLocationT;
            this.sourceOntologyFilePath = ontologyLocationS;
        }
        if (locationTypeT == 1 && locationTypeS == 2) {
            this.targetOntologyFilePath = ontologyLocationT;
            this.sourceOntologyURL = ontologyLocationS;
        } 
        if (locationTypeT == 2 && locationTypeS == 2) {
            this.targetOntologyURL = ontologyLocationT;
            this.sourceOntologyURL = ontologyLocationS;
        } 
          this.targetOntologyLang = targetOntologyLang;
          this.sourceOntologyLang = _sourceOntologyLang;
          this.r2rSourceFilePath = _r2rSourceFilePath;
          this.r2rtargetFilePath = _r2rTargetFilePath;
    }

    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the targetOntology
     */
    public String getTargetOntology() {
        return targetOntology;
    }

    /**
     * @param targetOntology the targetOntology to set
     */
    public void setTargetOntology(String targetOntology) {
        this.targetOntology = targetOntology;
    }

    /**
     * @return the targetOntologyFilePath
     */
    public String getTargetOntologyFilePath() {
        return targetOntologyFilePath;
    }

    /**
     * @param targetOntologyFilePath the targetOntologyFilePath to set
     */
    public void setTargetOntologyFilePath(String targetOntologyFilePath) {
        this.targetOntologyFilePath = targetOntologyFilePath;
    }

    /**
     * @return the targetOntologyURL
     */
    public String getTargetOntologyURL() {
        return targetOntologyURL;
    }

    /**
     * @param targetOntologyURL the targetOntologyURL to set
     */
    public void setTargetOntologyURL(String targetOntologyURL) {
        this.targetOntologyURL = targetOntologyURL;
    }

    /**
     * @return the targetOntologyLang
     */
    public String getTargetOntologyLang() {
        return targetOntologyLang;
    }

    /**
     * @param targetOntologyLang the targetOntologyLang to set
     */
    public void setTargetOntologyLang(String targetOntologyLang) {
        this.targetOntologyLang = targetOntologyLang;
    }

    /**
     * @return the sourceOntology
     */
    public String getSourceOntology() {
        return sourceOntology;
    }

    /**
     * @param sourceOntology the sourceOntology to set
     */
    public void setSourceOntology(String sourceOntology) {
        this.sourceOntology = sourceOntology;
    }

    /**
     * @return the sourceOntologyFilePath
     */
    public String getSourceOntologyFilePath() {
        return sourceOntologyFilePath;
    }

    /**
     * @param sourceOntologyFilePath the sourceOntologyFilePath to set
     */
    public void setSourceOntologyFilePath(String sourceOntologyFilePath) {
        this.sourceOntologyFilePath = sourceOntologyFilePath;
    }

    /**
     * @return the sourceOntologyURL
     */
    public String getSourceOntologyURL() {
        return sourceOntologyURL;
    }

    /**
     * @param sourceOntologyURL the sourceOntologyURL to set
     */
    public void setSourceOntologyURL(String sourceOntologyURL) {
        this.sourceOntologyURL = sourceOntologyURL;
    }

    /**
     * @return the sourceOntologyLang
     */
    public String getSourceOntologyLang() {
        return sourceOntologyLang;
    }

    /**
     * @param sourceOntologyLang the sourceOntologyLang to set
     */
    public void setSourceOntologyLang(String sourceOntologyLang) {
        this.sourceOntologyLang = sourceOntologyLang;
    }
    
    //Métodos R2R

    /**
     * @return the r2rSourceFilePath
     */
    public String getR2rSourceFilePath() {
        return r2rSourceFilePath;
    }
    
    public String getR2rTargetFilePath() {
        return r2rtargetFilePath;
    }

    /**
     * @param r2rSourceFilePath the r2rSourceFilePath to set
     */
    public void setR2rSourceFilePath(String r2rSourceFilePath) {
        this.r2rSourceFilePath = r2rSourceFilePath;
    }

    /**
     * @return the r2rtargetFilePath
     */
    public String getR2rtargetFilePath() {
        return r2rtargetFilePath;
    }

    /**
     * @param r2rtargetFilePath the r2rtargetFilePath to set
     */
    public void setR2rtargetFilePath(String r2rtargetFilePath) {
        this.r2rtargetFilePath = r2rtargetFilePath;
    }

    /**
     * @return the r2rUrl
     */
    public String getR2rUrl() {
        return r2rUrl;
    }

    /**
     * @param r2rUrl the r2rUrl to set
     */
    public void setR2rUrl(String r2rUrl) {
        this.r2rUrl = r2rUrl;
    }

}
