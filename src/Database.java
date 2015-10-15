import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */

/**
 * @author Franco
 *
 */
public class Database {

	Connection c = null; // Objeto de tipo coneccion donde se guardar�n los datos de coneccion
	Statement stmt = null; // Objeto de tipo sentencia SQL
	Compania empresa = null; // Objeto de tipo empresa donde se guardar�n los datos leidos desde la BD
	ResultSet rs = null; // Objeto de tipo resultado Query SQL

	public Database() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/vomistar", "postgres", "12345");
			c.setAutoCommit(false);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			c.close();
		}
	}

	public boolean ingresarDatosBD(Cliente datosCliente) throws SQLException {
		if(c !=null )
		{
			// Si se cre� la conexi�n a la BD exitosamente se contin�a
			// Se crea una nueva sentencia SQL
			stmt = c.createStatement();
			String sql = "INSERT INTO clientes (nombre1,apellido1,id_compania,"
					+ "nombre2,fono_celular,fono_fijo,email,direccion1,direccion2,apellido2,rut) "
					+ "VALUES ('"+datosCliente.getNombre1()+"','"+datosCliente.getApellido1()
					+ "','"+datosCliente.getIdCompania()+"','"+datosCliente.getNombre2()
					+ "',"+datosCliente.getFonoCel()+","+datosCliente.getFonoFijo()+",'"+datosCliente.getEmail()
					+ "','"+datosCliente.getDireccion1()+"','"+datosCliente.getDireccion2()+"','"+datosCliente.getApellido2()
					+  "','"+datosCliente.getRut()+"'); commit";
			stmt.executeUpdate(sql);

			stmt.close();
			// Se cierra conexi�n a la BD
			//c.commit();
			c.close();
			return true;
		} else
			return false;
			// Si no se pudo establecer la conexi�n a la BD se retorna null;

	}
	// Establece todos los m�todos de lecturas desde la Base de datos
	public Compania leerDatosBD() throws SQLException {
		if (c != null){
			// Si se cre� la conexi�n a la BD exitosamente se contin�a

			// Se crea una nueva sentencia SQL
			stmt = c.createStatement();
			// Se ejecuta la sentencia SQL y se guarda
			rs = stmt.executeQuery("SELECT * FROM compania;");
			while (rs.next())
				// Se obtienen datos de las tablas
				// Se crear� un objeto compa�ia con los datos obtenidos de la DB
				empresa = new Compania(rs.getString("nombre"), rs.getString("id_compania"));

			// Se leen datos de Planes desde la BD
			// Se ejecuta la sentencia SQL y se guarda
			rs = stmt.executeQuery("SELECT * FROM planes;");
			while (rs.next()) {
				// Se obtienen datos de la plan
				Plan p = new Plan(rs.getInt("id_plan"), rs.getString("nombrePlan"), rs.getInt("precio")
						, rs.getInt("minutos"), rs.getInt("gigas"), rs.getString("id_compania"));

				empresa.getPlanes().add(p);
			}

			// Se leen datos de Equipos desde la BD
			// Se crea una nueva sentencia SQL
			stmt = c.createStatement();
			// Se ejecuta la sentencia SQL y se guarda
			rs = stmt.executeQuery("SELECT * FROM equipos;");
			while (rs.next()) {
				// Se obtienen datos de la equipos
				Equipo e = new Equipo(rs.getInt("id_equipo"), rs.getString("nombreEquipo")
						, rs.getInt("valor_con_plan"), rs.getInt("valor_sin_plan")
						, rs.getString("capacidad"), rs.getString("id_compania"));

				empresa.getMoviles().add(e);
			}

			rs = stmt.executeQuery("SELECT * FROM clientes;");
			while (rs.next()) {
				// Se obtienen datos de cliente y se asignan
				Cliente cli = new Cliente(rs.getString("nombre1"),rs.getString("nombre2"),rs.getString("apellido1"),rs.getString("apellido2")
						,rs.getString("rut"),rs.getInt("fono_celular"),rs.getInt("fono_fijo"),rs.getString("direccion1")
						,rs.getString("direccion2"),rs.getString("rut"),rs.getString("id_compania"));
				empresa.getListaClientes().add(cli);
			}

			rs.close();
			stmt.close();

			// Se cierra conexi�n a la BD
			c.close();

			// Se retorna toda la colecci�n empresa
			return empresa;
		}else
			// Si no se pudo establecer la conexi�n a la BD se retorna null;
			return null;
	}
}
