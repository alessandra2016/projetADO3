/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads3.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fernando.tsuda
 */
public class ConexaoBD {

  protected Connection obterConexao() throws SQLException, ClassNotFoundException {
    Connection conn = null;

    // Passo 1: Registrar o driver JDBC
    Class.forName("org.apache.derby.jdbc.ClientDataSource");
    
    // Passo 2: Abrir a conexão
    conn = DriverManager.getConnection(
	    "jdbc:derby://localhost:1527/bdagenda",
	    "SibeleNepomuceno", // usuário BD
	    "beli1771"); // senha BD
    return conn;
  }

}
