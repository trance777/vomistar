import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FrameRegistrarEmpresa extends JFrame {

	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfRut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Compania datosEmpresa) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameRegistrarEmpresa frame = new FrameRegistrarEmpresa(datosEmpresa);
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
	public FrameRegistrarEmpresa(Compania datosEmpresa) {
		setResizable(false);
		setTitle("MovilMaker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 353, 153);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Registre su empresa", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Nombre");
		label.setBounds(45, 32, 97, 14);
		contentPane.add(label);
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(152, 29, 153, 20);
		contentPane.add(tfNombre);
		
		tfRut = new JTextField();
		tfRut.setColumns(10);
		tfRut.setBounds(152, 57, 153, 20);
		contentPane.add(tfRut);
		
		JLabel label_1 = new JLabel("RUT");
		label_1.setBounds(45, 60, 70, 14);
		contentPane.add(label_1);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(122, 88, 89, 23);
		contentPane.add(btnRegistrar);
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = tfNombre.getText();
				String rut = tfRut.getText();
				Compania empresaNueva = new Compania (nombre,rut);
				
				// Se escribira empresa en la BD
				try {
					// Creacion de conexion a base de datos
					Database bd = new Database();
					bd.ingresarEmpresaBD(empresaNueva);
					System.out.println("Empresa agregada a la base de datos...");						
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println("Empresa no se pudo escribir en la Base de Datos.\n"
							+ "\nDetalles de la excepci�n:");
					System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				}
				
				//Muestra mensaje que cilente fue ingresado exitosamente!
				JOptionPane.showMessageDialog(null, "Empresa creada!\nProceda a crear un Administrador...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				// Se llamar� a una Interfaz AgregarAdmin para registrar al primer administrador
				FrameAgregarAdmin fAdmin = new FrameAgregarAdmin(datosEmpresa,0);
				fAdmin.setVisible(true);
				dispose();
			}
		});
	}
}
