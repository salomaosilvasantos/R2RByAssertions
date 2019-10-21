/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.sqlite.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.br.ufc.arida.r2rbyassertions.main.MainController;

/**
 *
 * @author Tiagovinuto
 */
public class Conexao {
    
    private String headerR2R = "@prefix r2r: <http://www4.wiwiss.fu-berlin.de/bizer/r2r/> .\n" +
                               "\n" +
                               "# Made up Mapping publisher\n" +
                               "@prefix mp: <http://www.example.org/> . \n\n";
    
    private static Conexao conexao = null;
    
    private final String ARQ_ASSERTIONS_PATH = ".//assertions.txt";
    private final String ARQ_R2RMAPPINGS_PATH = ".//R2R2//r2rApi-0.2.3//example_data//r2rMappings.ttl"; // Vai para a configuração
    
    private String ARQ_SOURCE_R2R_FILEPATH = ".//R2R2//r2rApi-0.2.3//example_data//"; // Vai para a configuração
    private String ARQ_TARGET_R2R_FILEPATH = ".//R2R2//r2rApi-0.2.3//example_data//"; // Vai para a configuração
    
    private final String ARQ_OUTPUT_R2R_FILEPATH = ".//R2R2//r2rApi-0.2.3//example_data//output.nt"; // Vai para a configuração
    //    private final String ARQ_TARGET_RDF_FILEPATH = ".//R2R2//r2rApi-0.2.3//src//main//java//de//fuberlin//wiwiss//r2r//examples//"; // Vai para a configuração
    
    public static final int ARQ_ASSERTIONS = 1;
    public static final int ARQ_R2RMAPPING = 2;
    public static final int ARQ_SOURCE_R2R_FILE = 3;
    public static final int ARQ_TARGET_R2R_FILE = 4;
    
    public static final int ARQ_OUTPUT_R2R = 5;
    
    public static synchronized Conexao getInstance() throws IOException {
        if(conexao == null){
            conexao = new Conexao();
            
           // conexao.createFiles();
        }
        return conexao;
    }
    
    public String getArqSourceR2RPath() {
        return this.ARQ_SOURCE_R2R_FILEPATH;
    }
    
    public String getArqTargetR2RPath() {
        return this.ARQ_TARGET_R2R_FILEPATH;
    }
    
    public String getOutputR2RPath(){
        return this.ARQ_OUTPUT_R2R_FILEPATH;
    }
    
    public String getMappingsR2RPath(){
        return this.ARQ_R2RMAPPINGS_PATH;
    }
    
    private void createFiles() throws IOException {
        /*
        * ARQUIVO ASSERTIONS
        */
    	
        File f = new File(conexao.ARQ_ASSERTIONS_PATH);
        if (!f.exists()) {
            f.createNewFile();
        }
        
        /*
        * ARQUIVO MAPPINGS
        */
        f = new File(conexao.ARQ_R2RMAPPINGS_PATH);
        if (!f.exists()) {
            f.createNewFile();
            //insertsFile(headerR2R, ARQ_R2RMAPPING, true);
        }
        /*
        * ARQUIVO R2R OUTPUT
        */
        f = new File(ARQ_OUTPUT_R2R_FILEPATH);
        if (!f.exists()) {
            f.createNewFile();
        }
    }
    
    public void insertsFile(String _txt, int _arq, boolean _append) throws IOException {
        BufferedWriter inArq = null;
        String _path = "";
        File resourcesDirectory = new File("src/main/resources/data/output");
    	String path = resourcesDirectory.getAbsolutePath();
    	String arq_assertions_path = path + File.separatorChar + "assertions.txt";
    	String arq_r2rmappings_path = path + File.separatorChar + "r2rMappings.ttl";
        String message = "";
        switch (_arq) {
            case ARQ_ASSERTIONS: {
                _path = arq_assertions_path;
                message = "File generated with MAPPING ASSERTIONS, in the path: ";
                break;
            }
            case ARQ_R2RMAPPING: {
                _path = arq_r2rmappings_path;
                _txt = this.headerR2R + _txt;
                message = "File generated with R2R MAPPING, in the path:  ";
                break;
            }
            
            default: {
                JOptionPane.showMessageDialog(null, "File not found");
                break;
            }
        }
        //inArq = new BufferedWriter(new FileWriter(_path, _append));
        
        PrintWriter pw = new PrintWriter (new OutputStreamWriter (new FileOutputStream (_path), "UTF-8"));
        pw.write(_txt+'\n');
        //inArq.write(_txt + '\n');
        //inArq.close();
        pw.close();
        Logger.getLogger(MainController.class.getName()).log(Level.INFO, message + _path);;
    }

    public void copyFile(String _path, int _type) throws IOException {
        File _file = new File(_path);
        
        switch(_type){
            case ARQ_SOURCE_R2R_FILE : {
                String path = ARQ_SOURCE_R2R_FILEPATH + _file.getName();
                Files.copy(_file.toPath(), new File(path).toPath(),StandardCopyOption.REPLACE_EXISTING);
                this.ARQ_SOURCE_R2R_FILEPATH = path;
                break;
            }
            
            case ARQ_TARGET_R2R_FILE : {
                String path = ARQ_TARGET_R2R_FILEPATH + _file.getName();
                Files.copy(_file.toPath(), new File(path).toPath(),StandardCopyOption.REPLACE_EXISTING);
                this.ARQ_TARGET_R2R_FILEPATH = path;
                break;
            }
        }
    }
}
