/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ufc.arida.r2rbyassertions.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author tiagovinuto
 */
public class DbConnection {
 static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, e);
        }

        Statement stm;
        try {
            Connection connSQLite = getConnSQLite();

            stm = connSQLite.createStatement();
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS MappingConfiguration ("
                    + "id integer PRIMARY KEY NOT NULL,"
                    + "targetOntology text NOT NULL,"
                    + "sourceOntology text NOT NULL,"
                    + "creationDate text NOT NULL,"
                    + "targetOntologyFilePath text,"
                    + "targetOntologyURL text,"
                    + "targetOntologyLang text NOT NULL,"
                    + "sourceOntologyFilePath text,"
                    + "sourceOntologyURL text,"
                    + "sourceOntologyLang text NOT NULL,"
                    + "R2RSourceFilePath text NOT NULL,"
                    + "R2RTargetFilePath text NOT NULL,"
                  //+ "R2RUrl text NOT NULL,"
                    + "UNIQUE(targetOntology, sourceOntology));");

        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public static String getDriverClass(String v) {
        switch (v) {
            case "MySQL Connector":
                return "com.mysql.jdbc.Driver";
            case "PostgreSQL":
                return "org.postgresql.Driver";
            case "SQLite":
                return "org.sqlite.JDBC";
            default:
                return "";
        }
    }

    public static Connection getConnSQLite() throws SQLException {
        Connection connSQLite = DriverManager.getConnection("jdbc:sqlite:ferramentatiago01.db");
        connSQLite.setAutoCommit(true);

        return connSQLite;
    }
   
}
