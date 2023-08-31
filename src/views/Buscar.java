package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controladores.HospedesController;
import Controladores.ReservaController;
import modelo.Hospedes;
import modelo.Reserva;

@SuppressWarnings("serial")
public class Buscar extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHospedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHospedes;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	private ReservaController reservasController;
	private HospedesController hospedesController;

	String reserva;
	String hospedes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Buscar() {
		
		this.reservasController = new ReservaController();
		this.hospedesController = new HospedesController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Buscar.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblTitulo = new JLabel("SISTEMA DE BUSCA");
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblTitulo.setBounds(331, 62, 280, 42);
		contentPane.add(lblTitulo);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
				
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Data Check In");
		modelo.addColumn("Data Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pagamento");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		preencherTabelaReservas();
		
		
		tbHospedes = new JTable();
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHospedes = (DefaultTableModel) tbHospedes.getModel();
		modeloHospedes.addColumn("Numero de Hóspede");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data de Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("Numero de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHospedes);
		panel.addTab("Huéspedes", new ImageIcon(Buscar.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		mostrarTabelaHospedes();
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Buscar.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { // Quando o usuário passa o mouse sobre o botão, ele muda de cor
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Quando o usuário remove o mouse do botão, ele retornará ao estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				preencherTabelaReservas();
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHospedes = tbHospedes.getSelectedRow();
				
				if(filaReservas >= 0) {
					AtualizarReservas();
					limparTabela();
					preencherTabelaReservas();
					mostrarTabelaHospedes();
				} else if (filaHospedes >= 0) {
					AtualizarReservas();
					limparTabela();
					mostrarTabelaHospedes();
					preencherTabelaReservas();
				}
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnDeletar = new JPanel();
		btnDeletar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHospedes = tbHospedes.getSelectedRow();
				
				if(filaReservas >= 0) {
					reserva = tbReservas.getValueAt(filaReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "Deseja apagar a Reserva?");	
					
					if(confirmar == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(filaReservas, 0).toString();
						reservasController.Eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Apagado com Sucesso!");
						limparTabela();
						preencherTabelaReservas();
						mostrarTabelaHospedes();
					}
				} else if(filaHospedes >=0) {
					hospedes = tbHospedes.getValueAt(filaHospedes, 0).toString();
					int confirmarH = JOptionPane.showConfirmDialog(null, "Deseja apagar o Hospede?");
					if(confirmarH == JOptionPane.YES_OPTION) {
						String valor = tbHospedes.getValueAt(filaHospedes, 0).toString();
						hospedesController.Eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Apagado com Sucesso!");
						limparTabela();
						mostrarTabelaHospedes();
						preencherTabelaReservas();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao Apagar!");
				}
			}
		});
		btnDeletar.setLayout(null);
		btnDeletar.setBackground(new Color(12, 138, 199));
		btnDeletar.setBounds(767, 508, 122, 35);
		btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnDeletar);
		
		JLabel lblExcluir = new JLabel("DELETAR");
		lblExcluir.setHorizontalAlignment(SwingConstants.CENTER);
		lblExcluir.setForeground(Color.WHITE);
		lblExcluir.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblExcluir.setBounds(0, 0, 122, 35);
		btnDeletar.add(lblExcluir);
		setResizable(false);
	}
	
	
	private List<Reserva> buscarReservas() {
		return this.reservasController.buscar();
	}
	
	private List<Reserva> buscarReservasId() {
		return this.reservasController.buscar();
	}

	
	private void preencherTabelaReservas() {
		List<Reserva> reservaLista = buscarReservas();
		try {
			for (Reserva reserva : reservaLista) {
				modelo.addRow(new Object[] {
						reserva.getId(), reserva.getDataEntrada(), reserva.getDataSaida(), reserva.getValor(),
						reserva.getFormaPagamento()});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void preencherTabelaReservasID() {
		List<Reserva> reservaLista = buscarReservas();
		try {
			for (Reserva reserva : reservaLista) {
				modelo.addRow(new Object[] {
						reserva.getId(), reserva.getDataEntrada(), reserva.getDataSaida(), reserva.getValor(),
						reserva.getFormaPagamento()});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void AtualizarReservas() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
		.ifPresentOrElse(file -> {
				Date dataE = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString());
				Date dataS = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString());			
				String valor = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 3);
				String formaPagamento = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
				Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
				this.reservasController.atualizarReserva(dataE, dataS, valor, formaPagamento, id);
				JOptionPane.showMessageDialog(this, String.format("Registro Modificado com Sucesso!"));
			}, () -> JOptionPane.showMessageDialog(this, "Erro!"));
	}
	
//	private String calcularValorReserva(Date dataEntrada, Date dataSaida) {
//		if(dataEntrada != null && dataSaida != null) {
//			int dias = (int) ChronoUnit.DAYS.between(java.sql.Date(dataEntrada), dataSaida);
//			int noite = 50;
//			int valor = dias * noite;
//			return Integer.toString(valor);
//		} else {
//			return "";
//		}
//	}
	
	private List<Hospedes> buscarHospedes(){
		return this.hospedesController.mostrarHospedes();
	}
	
	private List<Hospedes> buscarHospedesId(){
		return this.hospedesController.buscarHospedes(txtBuscar.getText());
	}
	
	private void mostrarTabelaHospedes() {
		List<Hospedes> hospedes = buscarHospedes();
		try {
			for (Hospedes hospedes1 : hospedes ) {
				modeloHospedes.addRow(new Object[] {
						hospedes1.getId(), hospedes1.getNome(), hospedes1.getSobrenome(), hospedes1.getDataNascimento(),
						hospedes1.getNacionalidade(), hospedes1.getTelefone(), hospedes1.getIdReserva()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void mostrarTabelaHospedesID() {
		List<Hospedes> hospedes = buscarHospedesId();
		try {
			for (Hospedes hospedes1 : hospedes) {
				modelo.addRow(new Object[] {
						hospedes1.getId(), hospedes1.getNome(), hospedes1.getSobrenome(), hospedes1.getDataNascimento(),
						hospedes1.getNacionalidade(), hospedes1.getTelefone(), hospedes1.getIdReserva()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void atualizarHospedes() {
		Optional.ofNullable(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), tbHospedes.getSelectedColumn()))
		.ifPresentOrElse(filaHospedes -> {
			
			String nome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 1);
			String sobrenome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 2);
			Date dataNascimento = Date.valueOf(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 3).toString());
			String nacionalidade = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 4);
			String telefone = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 5);
			Integer id_reserva = Integer.valueOf(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 6).toString());
			Integer id = Integer.valueOf(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 0).toString());

			
			if(tbHospedes.getSelectedColumn() == 6) {
				JOptionPane.showMessageDialog(this, "O ID não pode ser Modificado!");
			} else {
				this.hospedesController.atualizarH(nome, sobrenome, null, nacionalidade, telefone, id_reserva, id_reserva);
				JOptionPane.showMessageDialog(this, String.format("Registro Modificado com Sucesso!"));
			}
			
			
			
		}, () -> JOptionPane.showInternalMessageDialog(this, "Erro!"));
	}
	
	
	private void limparTabela() {
		((DefaultTableModel) tbHospedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}
	
	
	//Código que permite movimentar a janela pela tela seguindo a posição de "x" e "y"	
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
