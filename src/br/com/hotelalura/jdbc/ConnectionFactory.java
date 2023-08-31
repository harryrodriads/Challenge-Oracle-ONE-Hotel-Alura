package br.com.hotelalura.jdbc;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource dataSource;
	
	public ConnectionFactory() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/hotel_alura");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("117975");
		
		this.dataSource = comboPooledDataSource;
		
	}
	
	public Connection recuperarConexao() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("Há um Erro!");
			throw new RuntimeException(e);
		}
		
	}

}
