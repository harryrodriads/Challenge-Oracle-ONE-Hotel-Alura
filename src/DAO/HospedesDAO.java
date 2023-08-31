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

import modelo.Hospedes;
import modelo.Reserva;

public class HospedesDAO {
	
	private Connection con;

	public HospedesDAO(Connection con) {
		super();
		this.con = con;
	}
	
	public void guardar(Hospedes hospedes) {
			String sql = "INSERT INTO hospedes (nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva) VALUES (?,?,?,?,?,?)";
			try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setString(1, hospedes.getNome());
				pstm.setString(2, hospedes.getSobrenome());
				pstm.setObject(3, hospedes.getDataNascimento());
				pstm.setString(4, hospedes.getNacionalidade());
				pstm.setString(5, hospedes.getTelefone());
				pstm.setInt(6, hospedes.getIdReserva());
				pstm.execute();
				try (ResultSet rst = pstm.getGeneratedKeys()){
					while( rst.next()) {
						hospedes.setId(rst.getInt(1));
					}
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	
		
		public List<Hospedes> mostrar(){
			List<Hospedes> hospedes = new ArrayList<Hospedes>();
			try {
				String sql = "SELECT id, nome , sobrenome, data_nascimento, nacionalidade, telefone, id_reserva FROM hospedes";
				
				try(PreparedStatement pstm = con.prepareStatement(sql)){
					pstm.execute();
					transformarResultado(hospedes, pstm);
				}
				return hospedes;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void transformarResultado(List<Hospedes> hospedes, PreparedStatement pstm) throws SQLException{
			try(ResultSet rst = pstm.executeQuery()){
				while(rst.next()) {
					int id = rst.getInt("id");
					String nome = rst.getString("nome");
					String sobrenome = rst.getString("sobrenome");
					LocalDate dataNascimento2 = rst.getDate("data_nascimento").toLocalDate().plusDays(1);
					String nacionalidade = rst.getString("nacionalidade");
					String telefone = rst.getString("telefone");
					int idReserva = rst.getInt("id_reserva");
					
					Hospedes hospede = new Hospedes(id, nome, sobrenome, dataNascimento2, nacionalidade, telefone, idReserva);
					hospedes.add(hospede);
				}
			}
		}
		
		public List<Hospedes> buscarId(String id){
			List<Hospedes> hospedes = new ArrayList<Hospedes>();
			try {
				String sql = "SELECT id, nome , sobrenome, data_nascimento, nacionalidade, telefone, id_reserva FROM hospedes WHERE id_reserva=?";
				
				try(PreparedStatement pstm = con.prepareStatement(sql)){
					pstm.setString(1, id);
					pstm.execute();
					
					transformarResultado(hospedes, pstm);
				}
				return hospedes;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		
		
		public void Eliminar(Integer id) {
			try(PreparedStatement stm = con.prepareStatement("DELETE FROM hospedes WHERE id = ?")) {
				stm.setInt(1, id);
				stm.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} 
		
		public void AtualizarH(String nome, String sobrenome, Date dataNascimento, String nacionalidade, String telefone, Integer id_reserva, Integer id) {
			try (PreparedStatement stm = con.prepareStatement("UPDATE hospedes SET nome=? , sobrenome=?, nacionalidade=?, nacionalidade telefone=?, id_reserva=? WHERE  id= ?")) {
				stm.setString(1, nome);
				stm.setString(2, sobrenome);
				stm.setDate(3, dataNascimento);
				stm.setString(4, nacionalidade);
				stm.setString(5, telefone);
				stm.setInt(6, id_reserva);
				stm.setInt(7, id);
				stm.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		
	}
