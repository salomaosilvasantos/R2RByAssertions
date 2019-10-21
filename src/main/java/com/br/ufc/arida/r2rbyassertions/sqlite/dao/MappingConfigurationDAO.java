/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.sqlite.dao;

import com.br.ufc.arida.r2rbyassertions.db.DbConnection;
import com.br.ufc.arida.r2rbyassertions.model.MappingConfiguration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author tiagovinuto
 */
public class MappingConfigurationDAO { 
    
    public int add(MappingConfiguration mc) throws SQLException, IOException {
        Connection connSQLite = DbConnection.getConnSQLite();
        Statement stm = connSQLite.createStatement();

      stm.executeUpdate("INSERT INTO MappingConfiguration(targetOntology, sourceOntology, creationDate, targetOntologyFilePath, targetOntologyURL, targetOntologyLang ,sourceOntologyFilePath, sourceOntologyURL, sourceOntologyLang, R2RSourceFilePath,R2RTargetFilePath) VALUES ('"
                + mc.getTargetOntology() +"','" +mc.getSourceOntology() + "','" + mc.getCreationDate() + "','" + mc.getTargetOntologyFilePath() + "','" + mc.getTargetOntologyURL() + "','" + mc.getTargetOntologyLang() + "','" +
                  mc.getSourceOntologyFilePath() + "','"+ mc.getSourceOntologyURL() + "','" + mc.getSourceOntologyLang()
                  + "','" + mc.getR2rSourceFilePath() 
                  + "','" + mc.getR2rTargetFilePath() + "')");
        ResultSet rs = stm.executeQuery("SELECT id FROM MappingConfiguration WHERE targetOntology='"+ mc.getTargetOntology()+ "' AND sourceOntology='" + mc.getSourceOntology() + "'");
        rs.next();
        
        //JOptionPane.showConfirmDialog(null, "Iniciar");
        Conexao.getInstance().copyFile(mc.getR2rSourceFilePath(), Conexao.ARQ_SOURCE_R2R_FILE);
        Conexao.getInstance().copyFile(mc.getR2rtargetFilePath(), Conexao.ARQ_TARGET_R2R_FILE);

        int id = rs.getInt("id");

        rs.close();
        stm.close();
        connSQLite.close();

        return id;
    }
    
    public void remove(int id) throws SQLException {
        Connection connSQLite = DbConnection.getConnSQLite();
        Statement stm = connSQLite.createStatement();

        stm.executeUpdate("DELETE FROM MappingConfiguration WHERE id=\"" + id
                + "\"");
        stm.close();
        connSQLite.close();
    }

    public List<MappingConfiguration> findAll() throws SQLException {
        Connection connSQLite = DbConnection.getConnSQLite();
        Statement stm = connSQLite.createStatement();
        List<MappingConfiguration> cList = new ArrayList<>();
        ResultSet rs;
        rs = stm.executeQuery("SELECT * FROM MappingConfiguration "
                + "ORDER BY creationDate DESC ");
        while (rs.next()) {
           cList.add(new MappingConfiguration(rs.getInt("id"),rs.getString("targetOntology"), rs.getString("sourceOntology"), rs.getString("creationDate")));
        }
        rs.close();
        stm.close();
        connSQLite.close();
        return cList;
    }

    public MappingConfiguration findById(int id) throws SQLException {
        Connection connSQLite = DbConnection.getConnSQLite();
        Statement stm = connSQLite.createStatement();

        ResultSet rs = stm.executeQuery("SELECT * FROM MappingConfiguration Where id =" + id);
        MappingConfiguration mc = null;
        if (rs.next()) {
            mc = new MappingConfiguration(rs.getInt("id"),rs.getString("targetOntology"),
                    rs.getString("sourceOntology"),
                    rs.getString("creationDate"));
            
            mc.setSourceOntologyLang(rs.getString("sourceOntologyLang"));
            String oFpS = rs.getString("sourceOntologyFilePath");
            mc.setSourceOntologyFilePath("null".equals(oFpS) ? "" : oFpS);
            String oURLT = rs.getString("sourceOntologyURL");
            mc.setSourceOntologyURL("null".equals(oURLT) ? "" : oURLT);
            
            mc.setTargetOntologyLang(rs.getString("targetOntologyLang"));
            String oFpT = rs.getString("targetOntologyFilePath");
            mc.setTargetOntologyFilePath("null".equals(oFpT) ? "" : oFpT);
            String oURLS = rs.getString("targetOntologyURL");
            mc.setTargetOntologyURL("null".equals(oURLS) ? "" : oURLS);
            
            /*
            // R2R Config
            String r2rFileS = rs.getString("R2RSourceFilePath");
            mc.setR2rSourceFilePath("null".equals(r2rFileS) ? "" : r2rFileS);
            String r2rFileT = rs.getString("R2RTargetFilePath");
            mc.setR2rtargetFilePath("null".equals(r2rFileT) ? "" : r2rFileT);
            String r2rUrl = rs.getString("R2RUrl");
            mc.setR2rUrl("null".equals(r2rUrl) ? "" : r2rUrl);
            
            */
            
        }
        rs.close();
        stm.close();
        connSQLite.close();

        return mc;
    }

    public void update(MappingConfiguration mc) throws SQLException {
        Connection connSQLite = DbConnection.getConnSQLite();
        Statement stm = connSQLite.createStatement();

        stm.executeUpdate("UPDATE MappingConfiguration "
                + " SET TargetontologyFilePath='" + mc.getTargetOntologyFilePath() + "'"
                + ", TargetontologyURL='" + mc.getTargetOntologyURL() + "'"
                + ", TargetontologyLang='" + mc.getTargetOntologyLang() + "'"
                + ", SourceontologyFilePath = '" + mc.getSourceOntologyFilePath() + "'"
                + ", SourceontologyURL='" + mc.getSourceOntologyURL() + "'"
                + ", SourceontologyLang='" + mc.getSourceOntologyLang() + "'"
                + ", R2RSourceFilePath = '" + mc.getR2rSourceFilePath() + "'"
                + ", R2RTargetFilePath = '" + mc.getR2rtargetFilePath() + "'"
                + ", R2RUrl = '" + mc.getR2rUrl() + "'"
                + " WHERE targetOntology='" + mc.getTargetOntology() + "' AND sourceOntology='" + mc.getSourceOntology() + "'");
          
       
        stm.close();
        connSQLite.close();
    }
    
    public void delete(int id) throws SQLException {
        Connection connSQLite = DbConnection.getConnSQLite();
        Statement stm = connSQLite.createStatement();

        stm.executeUpdate("DELETE FROM MappingConfiguration "
                + " WHERE id=" + id);
        stm.close();
        connSQLite.close();
    }
    
   public int lastRegister() throws SQLException {
    Connection connSQLite = DbConnection.getConnSQLite();
    Statement stm = connSQLite.createStatement();
   int idmax = -1;
     
        
        ResultSet rs;
        rs = stm.executeQuery("SELECT MAX(id) FROM MappingConfiguration;");
         
        if (rs.next()) {
            idmax = rs.getInt(1);
        }   
        
        rs.close();
        stm.close();
        connSQLite.close();  
        return idmax;
    }
    
}

