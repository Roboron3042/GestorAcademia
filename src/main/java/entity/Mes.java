package entity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import bd.BD;

public class Mes {
	
	private String id;
	private Integer mes;
	private Integer año;
	private static final String TABLA = "mes";
	
	public Mes(String id) {
		BD bd = new BD();
		Object[] mes = bd.select("SELECT * FROM " + TABLA + " WHERE id = '" + id + "'").get(0);
		bd.finalize();
		this.id = id;
		this.mes = (Integer) mes[1];
		this.año = (Integer) mes[2];
	}
	
	public Mes(Integer mes, Integer año) {
		if(mes < 10) {
			this.id = año + "-0" + mes;
		} else {
			this.id = año + "-" + mes;
		}
		this.mes = mes;
		this.año = año;		
		BD bd = new BD();
		if(bd.select("SELECT * FROM " + TABLA + " WHERE id = '" + id + "'").isEmpty()) {
			bd.insert("INSERT INTO " + TABLA + " VALUES ('" + id + "'," + mes + "," + año + ")");			
		}
		bd.finalize();
	}
	
	public String toString() {
		return  new DateFormatSymbols().getMonths()[mes - 1] + " de " + año;
	}
	private Mes() {
		// Para uso interno
	}
	
	public static List<Mes> ListaMeses(){
		
		List<Mes> listaMeses = new ArrayList<Mes>();
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " ORDER BY id DESC");
		bd.finalize();
		for(Object[] o : listaObj) {
			Mes m = new Mes();
			m.id = o[0].toString();
			m.mes = (Integer) o[1];
			m.año = (Integer) o[2];
			listaMeses.add(m);
		}
		return listaMeses;
	}
	
	public String getId() {
		return id;
	}

	public Integer getImpagos() {
		return MesAlumno.impagosMes(this);
	}

	public String getNombre() {
		return this.toString();
	}
	
	

}
