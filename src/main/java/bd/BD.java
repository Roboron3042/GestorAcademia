package bd;

import java.sql.*;
import java.util.*;

public class BD 
{
	
	private Connection con ;
	
	public BD()
	{
		try
		{
			//Al ser una BBDD embebida con derby sólo necesitamos el nombre
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			String db = "database";
			String url = "jdbc:derby:" + db + ";create=true";
			con = DriverManager.getConnection(url);
			
		}
		catch (SQLException ex)
		{
			throw new BDException("Error al Conectar con la base de datos." + ex.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finalize () 
	{
		try
		{
			if (con!=null)  con.close();
			//Al estar usando derby embebido, cerramos la conexión de la siguiente formna:
			//DriverManager.getConnection("jdbc:derby:;shutdown=true");
		}
		catch (SQLException ex)
		{
			if (ex.getSQLState().equals("XJ015")) {
		        System.out.println("Derby se cerró con normalidad");
		    } else {
				throw new BDException("Error al Cerrar la Conexión." + ex.getMessage());
		    }
		}
    }
	
	public void crear (String nombre, String creacion) {
		
		try {
			if(!doesTableExists(nombre)) {
				Statement stmt = con.createStatement();
				stmt.execute(creacion);
				System.out.println("Tabla " + nombre + " creada con éxito.");
			} else {
				System.out.println("La tabla " + nombre + " ya había sido creada.");
			}
		} catch (SQLException e) {
			throw new BDException("Error en la creación: " + creacion + ". " + e.getMessage());
		}
	}
	
	public Object selectEscalar(String sel)
	{
		ResultSet rset;
		Object res = null;
		try
		{
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(sel);
			rset.next();
			res = rset.getObject(1);
			rset.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new BDException("Error en el SELECT: " + sel + ". " + ex.getMessage());
		}		
		
		return res;
	}
	
	public List<Object[]> select(String sel)
	{
		ResultSet rset;
		List<Object[]> lista = new ArrayList<Object[]>();
		try
		{
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(sel);
			ResultSetMetaData meta = rset.getMetaData();
			int numCol = meta.getColumnCount();
			while (rset.next())
			{
				Object[] tupla = new Object[numCol];
				for(int i=0; i<numCol;++i)
				{
					tupla[i] = rset.getObject(i+1);
				}
				lista.add(tupla);
			}
			rset.close();
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new BDException("Error en el SELECT: " + sel+ ". " + ex.getMessage());
		}		
		
		return lista;
	}
	
	public void insert(String ins)
	{
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(ins);
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new BDException("Error en el INSERT: " + ins+ ". " + ex.getMessage());
		}
	}

	public void delete(String del)
	{
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(del);
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new BDException("Error en el DELETE: " + del+ ". " + ex.getMessage());
		}
	}

	public void update(String up)
	{
		try
		{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(up);
			stmt.close();
		}
		catch (SQLException ex)
		{
			throw new BDException("Error en el UPDATE: " + up+ ". " + ex.getMessage());
		}
	}

	public boolean doesTableExists(String tableName)
	        throws SQLException {
	    DatabaseMetaData meta = con.getMetaData();
	    ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);
	 
	    return result.next();
	}
	
	public void iniciar () {

    	crear(	"alumno", "CREATE TABLE alumno "
    			+ "(telefonoPrimario int PRIMARY KEY, "
    			+ "telefonoSecundario int,"
    			+ "nombre varchar(128),"
    			+ "apellidos varchar(128),"
    			+ "modalidad varchar(1024),"
    			+ "cuantia double,"
    			+ "baja boolean)");
    	crear(	"mes", "CREATE TABLE mes "
    			+ "(id varchar(128) PRIMARY KEY,"
    			+ "mes int,"
    			+ "ano int)");
    	crear(	"mesAlumno", "CREATE TABLE mesAlumno "
    			+ "(id varchar(128) PRIMARY KEY,"
    			+ "alumno int REFERENCES alumno(telefonoPrimario),"
    			+ "mes varchar(128) REFERENCES mes(id),"
    			+ "pagado boolean)");
	}
}