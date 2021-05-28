package entity;

import java.util.ArrayList;
import java.util.List;

import bd.BD;

public class MesAlumno {

	private int id;
	private int telefono;
	private String mes;
	private static final String TABLA = "MESALUMNO";
	private boolean pagado;
	
	public MesAlumno(Integer id, Integer telefono, String mes, boolean pagado) {
		this.id = id;
		this.telefono = telefono;
		this.mes = mes;
		this.pagado = pagado;
	}

	public static List<MesAlumno> ListaMesAlumno(Mes mes){
		
		List<MesAlumno> listaMesAlumno = new ArrayList<MesAlumno>();
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " WHERE mes = '" + mes.getId() + "'");
		for(Object[] o : listaObj) {
			MesAlumno ma = new MesAlumno((Integer) o[0], (Integer) o[1], (String) o[2], (boolean) o[3]);
			listaMesAlumno.add(ma);
		}
		bd.finalize();
		
		return listaMesAlumno;
	}
	
	public static void PagarMes(Mes mes, Alumno alumno) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET pagado = " + alumno.isPagado() + " WHERE alumno = " + alumno.getTelefono());
		bd.finalize();
	}
	
	public static void EliminarMesAlumno(Mes mes, Alumno alumno) {
		BD bd = new BD();
		bd.update("DELETE FROM " + TABLA + " WHERE alumno = " + alumno.getTelefono());
		bd.finalize();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
}
