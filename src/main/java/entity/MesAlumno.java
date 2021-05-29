package entity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import bd.BD;

public class MesAlumno {

	private Integer id;
	private Integer alumno;
	private String mes;
	private static final String TABLA = "MESALUMNO";
	private Boolean pagado;
	
	public MesAlumno(Integer alumno, String mes, boolean pagado) {
		BD bd = new BD();
		this.id = bd.insertAuto("INSERT INTO " + TABLA + " VALUES (DEFAULT," + alumno + ",'" + mes + "'," + pagado + ")");
		bd.finalize();
		this.alumno = alumno;
		this.mes = mes;
		this.pagado = pagado;
	}
	
	private MesAlumno() {
		//Para uso interno
	}

	public static List<MesAlumno> listaMesAlumno(Mes mes){
		
		List<MesAlumno> listaMesAlumno = new ArrayList<MesAlumno>();
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " WHERE mes = '" + mes.getId() + "'");
		for(Object[] o : listaObj) {
			MesAlumno ma = new MesAlumno();
			ma.id = (Integer) o[0];
			ma.alumno = (Integer) o[1];
			ma.mes = (String) o[2];
			ma.pagado = (boolean) o[3];
			listaMesAlumno.add(ma);
		}
		bd.finalize();
		
		return listaMesAlumno;
	}
	
	public static List<MesAlumno> listaMesAlumno(Alumno alumno){
		
		List<MesAlumno> listaMesAlumno = new ArrayList<MesAlumno>();
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " WHERE alumno = " + alumno.getId() + " ORDER BY mes DESC");
		for(Object[] o : listaObj) {
			MesAlumno ma = new MesAlumno();
			ma.id = (Integer) o[0];
			ma.alumno = (Integer) o[1];
			ma.mes = (String) o[2];
			ma.pagado = (boolean) o[3];
			listaMesAlumno.add(ma);
		}
		bd.finalize();
		
		return listaMesAlumno;
	}
	
	public static List<MesAlumno> listaMesAlumno(Alumno alumno, Mes mes){
		
		List<MesAlumno> listaMesAlumno = new ArrayList<MesAlumno>();
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " WHERE alumno = " + alumno.getId() + " AND mes = '" + mes.getId() + "'" + " ORDER BY mes DESC");
		for(Object[] o : listaObj) {
			MesAlumno ma = new MesAlumno();
			ma.id = (Integer) o[0];
			ma.alumno = (Integer) o[1];
			ma.mes = (String) o[2];
			ma.pagado = (boolean) o[3];
			listaMesAlumno.add(ma);
		}
		bd.finalize();
		
		return listaMesAlumno;
	}
	
	public static int impagosMes(Mes mes){
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " WHERE mes = '" + mes.getId() + "' AND pagado = false");
		bd.finalize();
		
		return listaObj.size();
	}
	
	public static int impagosAlumno(Alumno alumno){
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " WHERE alumno = " + alumno.getId() + " AND pagado = false");
		bd.finalize();
		
		return listaObj.size();
	}
	public void eliminar() {
		BD bd = new BD();
		bd.update("DELETE FROM " + TABLA + " WHERE id = " + id);
		bd.finalize();
	}

	public String toString() {
		return getNombre();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTelefono() {
		return alumno;
	}

	public void setTelefono(int telefono) {
		this.alumno = telefono;
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
	public String getNombre() {
		StringTokenizer st = new StringTokenizer(mes);
		Integer y = Integer.parseInt(st.nextToken("-"));
		Integer m = Integer.parseInt(st.nextToken("-"));

		return  new DateFormatSymbols().getMonths()[m - 1] + " de " + y;
	}

	public void setPagado(boolean pagado) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET pagado = " + pagado + " WHERE id = " + id);
		bd.finalize();
		this.pagado = pagado;
	}
}
