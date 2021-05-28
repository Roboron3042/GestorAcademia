package entity;

import java.util.ArrayList;
import java.util.List;

import bd.BD;

public class Alumno {
	
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
		this.telefono = telefono;
		this.telefono_sec = telefono_sec;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.modalidad = modalidad;
		this.cuantia = cuantia;
		this.baja = baja;
	}
	public Alumno(int telefono, String nombre, String apellidos, String modalidad, double cuantia, boolean baja) {
		this.telefono = telefono;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.modalidad = modalidad;
		this.cuantia = cuantia;
		this.baja = baja;
	}
	public Alumno(int telefono, int telefono_sec, String nombre, String apellidos, String modalidad, double cuantia, boolean baja, boolean pagado) {
		this.telefono = telefono;
		this.telefono_sec = telefono_sec;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.modalidad = modalidad;
		this.cuantia = cuantia;
		this.baja = baja;
		this.pagado = pagado;
	}
	public Alumno(int telefono, String nombre, String apellidos, String modalidad, double cuantia, boolean baja, boolean pagado) {
		this.telefono = telefono;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.modalidad = modalidad;
		this.cuantia = cuantia;
		this.baja = baja;
		this.pagado = pagado;
	}
	
	public static Alumno AlumnoFromDB(int telefono, boolean pagado) {
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " WHERE telefonoPrimario = " + telefono);
		bd.finalize();
		Object[] alumno = listaObj.get(0);
		if(alumno[1] != null) {
			return new Alumno((Integer) alumno[0], (Integer) alumno[1], (String) alumno[2], (String) alumno[3], (String) alumno[4], (Double) alumno[5], (boolean) alumno[6], pagado);
		} else {
			return new Alumno((Integer) alumno[0], (String) alumno[2], (String) alumno[3], (String) alumno[4], (Double) alumno[5], (boolean) alumno[6], pagado);
		}
	}
	
	public static List<Alumno> ListaAlumnosMes(Mes mes){
		
		List<Alumno> listaAlumnos = new ArrayList<Alumno>();
		List<MesAlumno> listaMesAlumno = MesAlumno.ListaMesAlumno(mes);
		
		for(MesAlumno ma : listaMesAlumno) {
			Alumno a = AlumnoFromDB(ma.getTelefono(), ma.isPagado());
			listaAlumnos.add(a);
		}
		
		return listaAlumnos;
	}
	
	public static void DarBaja(Alumno alumno) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET baja = " + alumno.isBaja() + " WHERE telefonoPrimario = " + alumno.getTelefono());
		bd.finalize();
	}
	
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public int getTelefono_sec() {
		return telefono_sec;
	}
	public void setTelefono_sec(int telefono_sec) {
		this.telefono_sec = telefono_sec;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public double getCuantia() {
		return cuantia;
	}
	public void setCuantia(double cuantia) {
		this.cuantia = cuantia;
	}
	public boolean isBaja() {
		return baja;
	}
	public void setBaja(boolean baja) {
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
	

}
