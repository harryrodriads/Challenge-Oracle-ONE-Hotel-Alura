package Controladores;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import DAO.ReservaDAO;
import br.com.hotelalura.jdbc.ConnectionFactory;
import modelo.Reserva;

public class ReservaController {
	
	private ReservaDAO reservaDao;

	public ReservaController() {
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.reservaDao = new ReservaDAO(connection);
	}
	
	public void salvar( Reserva reserva) {
		this.reservaDao.salvar(reserva);
	}
	
	public List<Reserva> buscar() {
		return this.reservaDao.buscar();
	}
	
	public List<Reserva> buscarId(String id) {
		return this.reservaDao.buscarId(id);
	}
	
	public void atualizarReserva(Date dataEntrada, Date dataSaida, String valor, String formaPagamento, Integer id) {
		this.reservaDao.atualizar(dataEntrada, dataSaida, valor, formaPagamento, id);
	}
	
	public void Eliminar(Integer id) {
		this.reservaDao.deletar(id);
	}
	

}
