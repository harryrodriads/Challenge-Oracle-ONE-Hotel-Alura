package Controladores;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import DAO.HospedesDAO;
import br.com.hotelalura.jdbc.ConnectionFactory;
import modelo.Hospedes;

public class HospedesController {

	private HospedesDAO hospedesDao;
	
	public HospedesController() {
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.hospedesDao = new HospedesDAO(connection);
	}
	
	public void guardar(Hospedes hospedes) {
		this.hospedesDao.guardar(hospedes);
	}
	
	public List<Hospedes> mostrarHospedes() {
		return this.hospedesDao.mostrar();
	}
	
	public List<Hospedes> buscarHospedes(String id) {
		return this.hospedesDao.buscarId(id);
	}
	
	public void atualizarH(String nome, String sobrenome, Date dataNascimento, String nacionalidade, String telefone, Integer id_reserva, Integer id) {
		this.hospedesDao.AtualizarH(nome, sobrenome, dataNascimento, nacionalidade, telefone, id_reserva, id);
	}
	
	public void Eliminar(Integer idReserva) {
		this.hospedesDao.Eliminar(idReserva);
	}
	
}
