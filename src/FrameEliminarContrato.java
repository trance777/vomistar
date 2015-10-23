import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class FrameEliminarContrato extends JFrame {

	private JPanel contentPane;
	private JTextField textRut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Compania datosEmpresa) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameEliminarContrato frame = new FrameEliminarContrato(datosEmpresa);
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
	public FrameEliminarContrato(Compania datosEmpresa) {
		setResizable(false);
		setTitle("Eliminar Contrato");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIngreseRutDe = new JLabel("Ingrese rut de cliente");
		lblIngreseRutDe.setBounds(10, 26, 126, 14);
		contentPane.add(lblIngreseRutDe);

		textRut = new JTextField();
		textRut.setBounds(10, 51, 111, 20);
		contentPane.add(textRut);
		textRut.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(141, 11, 283, 194);
		contentPane.add(panel);
		panel.setLayout(null);

		JList list = new JList();
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list.setBounds(10, 26, 263, 157);
		panel.add(list);

		JLabel lblContratos = new JLabel("Contratos");
		lblContratos.setBounds(10, 11, 69, 14);
		panel.add(lblContratos);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contrato contratoAEliminar = null;
				Cliente c = datosEmpresa.buscarCliente(textRut.getText());
				for (int i=0; i<c.getContratos().size(); i++){
					if(list.isSelectedIndex(i)){
						contratoAEliminar = c.buscarContrato(list.getSelectedIndex());
						if(contratoAEliminar!=null){
							if(c.eliminarContrato(contratoAEliminar)){
								//CONTRATO ELIMINADO
								System.out.println("Contrato eliminado");
								JOptionPane.showMessageDialog(null, "Contrato removido con ex�to!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
							}else{
								System.err.println("No se elimin� contrato");
								JOptionPane.showMessageDialog(null, "No se elimin� contrato!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
							}
						}else{
							System.err.println("No se encontr� contrato en la lista de contratos del cliente!");
							JOptionPane.showMessageDialog(null, "No se encontr� contrato en la lista de contratos del cliente!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(240, 216, 89, 23);
		contentPane.add(btnEliminar);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameInterfaz fInterfaz = new FrameInterfaz(datosEmpresa);
				fInterfaz.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(10, 116, 89, 23);
		contentPane.add(btnVolver);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelar.setEnabled(false);
				btnEliminar.setEnabled(false);
				btnVolver.setEnabled(true);
			}
		});
		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(335, 216, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblAviso = new JLabel("");
		lblAviso.setBounds(10, 220, 134, 19);
		contentPane.add(lblAviso);
		
		
		// BUSCA CONTRATOS DE X CLIENTE Y LO MUESTRA EN LISTA
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel listContratos = new DefaultListModel();
	
				Cliente c = datosEmpresa.buscarCliente(textRut.getText());
				if (c!=null){
					lblAviso.setForeground(Color.BLUE);
					lblAviso.setText("Cliente encontrado!");
				for (int i=0;i<c.getContratos().size();i++){
					listContratos.addElement(c.getContratos().get(i).getIdContrato());	//AGREGA ELEMENTO A LA LISTA
					list.setModel(listContratos);	//APARECE ELEMENTO EN LA LISTA
				}
				btnEliminar.setEnabled(true);
				btnCancelar.setEnabled(true);
				btnVolver.setEnabled(false);
				}
				else {
					lblAviso.setForeground(Color.RED);
					lblAviso.setText("Cliente no existe!");
					}
			}
		});
		btnBuscar.setBounds(10, 82, 89, 23);
		contentPane.add(btnBuscar);
	}
	
}
