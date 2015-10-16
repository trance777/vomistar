import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class FrameEliminarCliente extends JFrame {

	private JPanel contentPane;
	private JTextField textRut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Compania datosEmpresa) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameEliminarCliente frame = new FrameEliminarCliente(datosEmpresa);
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
	public FrameEliminarCliente(Compania datosEmpresa) {
		setTitle("Eliminar cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 382, 331);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Eliminaci\u00F3n de cliente", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 21, 284, 75);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblIngreseRut = new JLabel("Ingrese rut del cliente a eliminar");
		lblIngreseRut.setBounds(21, 11, 175, 14);
		panel.add(lblIngreseRut);

		textRut = new JTextField();
		textRut.setBounds(21, 36, 151, 20);
		panel.add(textRut);
		textRut.setColumns(10);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rut;
				rut=textRut.getText();
				// Creaci�n de conecci�n a base de datos
				Database bd = null;
				try {
					bd = new Database();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Llama a metodo en clase Compa�ia para eliminar Cliente
				if (datosEmpresa.interfazEliminarCliente(rut) == true){
					System.out.println("Cliente eliminado...");
					// Si el cliente se elimino exitosamente se eliminar� cliente en la BD
					try {
						bd.eliminarClienteBD(rut);
						System.out.println("Cliente eliminado de la base de datos...");						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.err.println("Cliente no se pudo eliminar de la Base de Datos.\n"
								+ "\nDetalles de la excepci�n:");
						System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
					}
				}
				else
					//Sino, se informa que el cliente no existe
					System.err.println("Cliente no existe...");
			}
		});

		btnEliminar.setBounds(182, 36, 98, 20);
		panel.add(btnEliminar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 107, 224, 143);
		contentPane.add(scrollPane);

		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel listmodel = new DefaultListModel();
				for(int i=0; i<datosEmpresa.getListaClientes().size();i++){
				listmodel.addElement("rut: "+datosEmpresa.getListaClientes().get(i).getRut());
				list.setModel(listmodel);
				}
			}
		});
		btnMostrar.setBounds(260, 126, 97, 23);
		contentPane.add(btnMostrar);

		JLabel lblListaClientes = new JLabel("Lista clientes");
		lblListaClientes.setBounds(260, 107, 136, 14);
		contentPane.add(lblListaClientes);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameInterfaz fInterfaz = new FrameInterfaz (datosEmpresa);
				fInterfaz.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(258, 258, 97, 23);
		contentPane.add(btnVolver);

	}
}