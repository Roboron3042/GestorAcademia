package entity;

import java.util.ArrayList;
import java.util.List;

import bd.BD;

public class Alumno {
	
	private int id;
	private int telefono;
	private int telefono_sec;
	private String nombre;
	private String apellidos;
	private String modalidad;
	private double cuantia;
	private boolean baja;
	private boolean pagado;
	private static final String TABLA = "ALUMNO";
	
	public Alumno(int telefono, int telefono_sec, String nombre, String apellidos, String modalidad, double cuantia, boolean baja) {
		BD bd = new BD();
		this.id = bd.insertAuto("INSERT INTO " + TABLA + " VALUES (DEFAULT," + telefono + "," + telefono_sec + "'" + nombre + "','" + apellidos + "','" + modalidad + "'," + cuantia + "," + baja + ")");
		bd.finalize();
		this.telefono = telefono;
		this.telefono_sec = telefono_sec;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.modalidad = modalidad;
		this.cuantia = cuantia;
		this.baja = baja;
	}
	public Alumno(int id) {
		this(id, false);
	}
	
	public Alumno(int id, boolean pagado) {
		BD bd = new BD();
		Object[] alumno = bd.select("SELECT * FROM " + TABLA + " WHERE id = " + id).get(0);
		bd.finalize();
		this.id = (Integer) alumno[0];
		this.telefono = (Integer) alumno[1];
		this.telefono_sec = (Integer) alumno[2];
		this.nombre = (String) alumno[3];
		this.apellidos = (String) alumno[4];
		this.modalidad = (String) alumno[5];
		this.cuantia = (Double) alumno[6];
		this.baja = (boolean) alumno[7];
		this.pagado = pagado;
	}
	
	private Alumno() {
		// Solo para uso interno
	}
	public static List<Alumno> listaAlumnos(){
		List<Alumno> listaAlumnos = new ArrayList<Alumno>();
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA);
		bd.finalize();
		for(Object[] alumno : listaObj) {
			Alumno aux = new Alumno();
			aux.id = (Integer) alumno[0];
			aux.telefono = (Integer) alumno[1];
			aux.telefono_sec = (Integer) alumno[2];
			aux.telefono_sec = 0;
			aux.nombre = (String) alumno[3];
			aux.apellidos = (String) alumno[4];
			aux.modalidad = (String) alumno[5];
			aux.cuantia = (Double) alumno[6];
			aux.baja = (boolean) alumno[7];
			listaAlumnos.add(aux);
		}
		return listaAlumnos;
	}
	
	public static List<Alumno> listaAlumnosMes(Mes mes){
		
		List<Alumno> listaAlumnos = new ArrayList<Alumno>();
		List<MesAlumno> listaMesAlumno = MesAlumno.listaMesAlumno(mes);
		
		for(MesAlumno ma : listaMesAlumno) {
			Alumno a = new Alumno(ma.getTelefono(), ma.isPagado());
			listaAlumnos.add(a);
		}
		
		return listaAlumnos;
	}

	public void eliminar() {
		BD bd = new BD();
		bd.update("DELETE FROM " + TABLA + " WHERE id = " + id);
		bd.finalize();
	}
	
	public int getId() {
		return id;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET telefonoPrimario = " + telefono + " WHERE id = " + id);
		bd.finalize();
		this.telefono = telefono;
	}
	public int getTelefono_sec() {
		return telefono_sec;
	}
	public void setTelefono_sec(int telefono_sec) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET telefonoSecundario = " + telefono + " WHERE id = " + id);
		bd.finalize();
		this.telefono_sec = telefono_sec;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET nombre = '" + nombre + "' WHERE id = " + id);
		bd.finalize();
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET apellidos = '" + apellidos + "' WHERE id = " + id);
		bd.finalize();
		this.apellidos = apellidos;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET modalidad = '" + modalidad + "' WHERE id = " + id);
		bd.finalize();
		this.modalidad = modalidad;
	}
	public double getCuantia() {
		return cuantia;
	}
	public void setCuantia(double cuantia) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET cuantia = " + cuantia + " WHERE id = " + id);
		bd.finalize();
		this.cuantia = cuantia;
	}
	public boolean isBaja() {
		return baja;
	}
	public void setBaja(boolean baja) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET baja = " + baja + " WHERE id = " + id);
		bd.finalize();
		this.baja = baja;
	}
	public boolean isPagado() {
		return pagado;
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	public String toString() {
		return nombre + " " + apellidos;
	}
	public int getImpagos() {
		return MesAlumno.impagosAlumno(this);
	}
	
	

}
