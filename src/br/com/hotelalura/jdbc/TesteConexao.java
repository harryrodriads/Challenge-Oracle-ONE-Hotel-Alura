package br.com.hotelalura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao();
		
		System.out.println("Conectado!");
		connection.close();
		

	}

}
