import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class FrameContrato extends JFrame {

	private JPanel contentPane;
	private JTextField textCuotas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Compania datosEmpresa, Cliente cliente) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameContrato frame = new FrameContrato(datosEmpresa,cliente);
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
	public FrameContrato(Compania datosEmpresa, Cliente cliente) {	// RECIBE DATOS DE LA EMPRESA Y DATOS DE CLIENTE (cliente para obtencion de nombre mostrado en el panel y rut para su busqueda)
		setTitle("Contrato");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Datos Contrato", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 25, 464, 173);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblPlanes = new JLabel("Planes");
		lblPlanes.setBounds(9, 6, 46, 14);
		panel.add(lblPlanes);

		JLabel lblEquipos = new JLabel("Equipos");
		lblEquipos.setBounds(185, 6, 46, 14);
		panel.add(lblEquipos);

		JList listEquipos = new JList();
		listEquipos.setBounds(198, 25, 150, 137);
		panel.add(listEquipos);

		JList listPlanes = new JList();
		listPlanes.setBounds(20, 25, 150, 137);
		panel.add(listPlanes);

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnMostrar.setBounds(358, 25, 96, 23);
		panel.add(btnMostrar);
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel model1 = new DefaultListModel();	//Instancia para JList Planes
				DefaultListModel model2 = new DefaultListModel();	//Instancia para JList Moviles
				for (int i=0;i<datosEmpresa.getPlanes().size();i++)
					model1.addElement(""+(i+1)+"-"+datosEmpresa.getPlanes().get(i).getNombrePlan());

				for (int i=0;i<datosEmpresa.getMoviles().size();i++)
					model2.addElement(""+(i+1)+"-"+datosEmpresa.getMoviles().get(i).getModelo());

				listEquipos.setModel(model2);
				listPlanes.setModel(model1);
				btnMostrar.setEnabled(false);
			}
		});


		JPanel panel_1 = new JPanel();
		panel_1.setBounds(265, 237, 209, 47);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblAviso = new JLabel("");
		lblAviso.setForeground(Color.RED);
		lblAviso.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAviso.setBounds(116, 237, 114, 14);
		contentPane.add(lblAviso);

		JLabel lblAviso2 = new JLabel("");
		lblAviso2.setForeground(Color.BLUE);
		lblAviso2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAviso2.setBounds(215, 209, 209, 14);
		contentPane.add(lblAviso2);


		//	DATOS INGRESADOS SE ENVIAN A CLASE Compa�ia, metodo: interfazCrearContrato
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numPlan, numEquipo, numCuotas;

				for (int i=0;i < datosEmpresa.getPlanes().size();i++)
				 for (int j=0;j<datosEmpresa.getMoviles().size();j++)
					 // Si estan Seleccionados en LA LISTA, "Plan", "Equipo" y "meses" (entre 1-12 cuotas) , se adjudica el contrato
						if(listPlanes.isSelectedIndex(i) && listEquipos.isSelectedIndex(j) && comprobarMesInt() == true && Integer.parseInt(textCuotas.getText())>=1 && Integer.parseInt(textCuotas.getText())<=12){
							//SE OTORGAN VALORES A VARIABLES PARA PLAN, EQUIPO Y CUOTAS
							numPlan=listPlanes.getSelectedIndex();
							numEquipo=listEquipos.getSelectedIndex();
							numCuotas=Integer.parseInt(textCuotas.getText());

							for(int k=0;k<datosEmpresa.getListaClientes().size();k++)
								if (cliente.getRut().equals(datosEmpresa.getListaClientes().get(k).getRut()))
									datosEmpresa.getListaClientes().get(k).getContratos().add(datosEmpresa.interfazCrearContrato(numPlan, numEquipo, numCuotas));	// SE LE OTORGA NUEVO CONTRATO A CLIENTE DE LA COMPA�IA
							lblAviso2.setForeground(Color.BLUE);
							lblAviso2.setText("Datos Ingresados correctamente");
						}

				//CIERRE INTERFAZ FrameContrato
				FrameInterfaz fInterfaz = new FrameInterfaz(datosEmpresa);
				fInterfaz.setVisible(true);
				dispose();
			}
		});

		btnNewButton.setBounds(10, 11, 89, 23);
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(110, 11, 89, 23);
		panel_1.add(btnNewButton_1);

		textCuotas = new JTextField();
		textCuotas.setBounds(20, 234, 86, 20);
		contentPane.add(textCuotas);
		textCuotas.setColumns(10);

		JLabel lblNewLabel = new JLabel("Ingrese cantidad de cuotas (1-12)");
		lblNewLabel.setBounds(20, 209, 191, 14);
		contentPane.add(lblNewLabel);

		JLabel labelCliente = new JLabel(cliente.getNombre1()+" "+cliente.getNombre2()+" "+cliente.getApellido1()+" "+ cliente.getApellido2());
		labelCliente.setBounds(120, 0, 304, 14);
		contentPane.add(labelCliente);
	}





	//============================== METODOS ==============================

	//Comprueba si el ingreso en casilla Cuotas es un numero INT
	public boolean comprobarMesInt(){
		try {
		     Integer.parseInt(textCuotas.getText());	//Si es INT devuelve true
		     return true;
		}
		catch (Exception a) {
		     //Not an integer
		}
		return false;
	}

}
