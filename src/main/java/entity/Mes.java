package entity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;


import bd.BD;

public class Mes {
	
	private String id;
	private Integer mes;
	private Integer año;
	private boolean procesado;
	private static final String TABLA = "mes";
	
	public Mes(String id) {
		BD bd = new BD();
		Object[] mes = bd.select("SELECT * FROM " + TABLA + " WHERE id = '" + id + "'").get(0);
		bd.finalize();
		this.id = id;
		this.mes = (Integer) mes[1];
		this.año = (Integer) mes[2];
		this.procesado = (boolean) mes[3];
	}
	
	public Mes(Integer mes, Integer año) {
		if(mes < 10) {
			this.id = año + "-0" + mes;
		} else {
			this.id = año + "-" + mes;
		}
		BD bd = new BD();
		if(bd.select("SELECT * FROM " + TABLA + " WHERE id = '" + id + "'").isEmpty()) {
			bd.insert("INSERT INTO " + TABLA + " VALUES ('" + id + "'," + mes + "," + año + "," + false + ")");	
			this.mes = mes;
			this.año = año;	
			this.procesado = false;
		} else {
			Object[] m = bd.select("SELECT * FROM " + TABLA + " WHERE id = '" + id + "'").get(0);
			this.mes = (Integer) m[1];
			this.año = (Integer) m[2];
			this.procesado = (boolean) m[3];
		}
		bd.finalize();	
	}
	
	public String toString() {
		String str = (new DateFormatSymbols().getMonths()[mes - 1] + " de " + año);
		return  str.substring(0, 1).toUpperCase() + str.substring(1);
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
	
	public int compareTo(Mes m) {
		return m.getId().compareTo(this.getId());
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

	public boolean isProcesado() {
		return procesado;
	}

	public void setProcesado(boolean procesado) {
		BD bd = new BD();
		bd.update("UPDATE " + TABLA + " SET procesado = " + procesado + " WHERE id = '" + id + "'");
		bd.finalize();
		this.procesado = procesado;
	}
	
	
	

}
