package DAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Reserva;

public class ReservaDAO {
	
	private Connection connection;

	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	
	public void salvar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reserva (data_entrada, data_saida, valor, forma_Pagamento) VALUES (?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setDate(1, reserva.getDataEntrada());
				pstm.setDate(2, reserva.getDataSaida());
				pstm.setString(3, reserva.getValor());
				pstm.setString(4, reserva.getFormaPagamento());

				pstm.executeUpdate();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Reserva> buscar(){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_pagamento FROM reserva";

			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.execute();

				try(ResultSet rst = pstm.getResultSet()){
					while(rst.next()) {
						Reserva reserva = new Reserva(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5));
						reservas.add(reserva);
					}
				}
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
		
	public List<Reserva> buscarId(String id){
			List<Reserva> reservas = new ArrayList<Reserva>();
			try {
				String sql = "SELECT id, data_entrada, data_saida, valor, forma_pagamento FROM reserva WHERE  id= ?";
				
				try(PreparedStatement pstm = connection.prepareStatement(sql)){
					pstm.setString(1, id);
					pstm.execute();
					
					transformarResultSetEmReserva(reservas,pstm);
				}
				return reservas;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		
	public void atualizar(Date dataEntrada, Date dataSaida, String valor, String formaPagamento, Integer id) {
			try (PreparedStatement stm = connection.prepareStatement("UPDATE reserva SET data_entrada=?, data_saida=?, valor=?,forma_pagamento=? WHERE  id= ?")){
				stm.setObject(1, dataEntrada);
				stm.setObject(2, dataSaida);
				stm.setString(3, valor);
				stm.setString(4, formaPagamento);
				stm.setInt(5, id);
				stm.execute();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
		
	public void deletar(Integer id) {
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM reserva WHERE id = ?")) {
				stm.setInt(1, id);
				stm.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		
	private void transformarResultSetEmReserva(List<Reserva> reservas, PreparedStatement pstm) throws SQLException{
			try(ResultSet rst = pstm.getResultSet()){
				while(rst.next()) {
					Reserva reserva = new Reserva(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5));
					reservas.add(reserva);
				}
			}
		}
	}
