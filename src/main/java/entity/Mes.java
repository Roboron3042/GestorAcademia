package entity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import bd.BD;

public class Mes {
	
	private String id;
	private String nombre;
	private Integer mes;
	private Integer año;
	private Integer impagos;
	private static final String TABLA = "mes";
	
	public Mes(String id, Integer mes, Integer año) {
		this.id = id;
		this.mes = mes;
		this.año = año;
		this.nombre = this.toString();
		this.impagos = MesAlumno.Impagos(this);
	}
	
	public Mes(Integer mes, Integer año) {
		this.id = año + "-" + mes;
		this.mes = mes;
		this.año = año;
	}
	
	public String toString() {
		return  new DateFormatSymbols().getMonths()[mes - 1] + " de " + año;
	}
	
	public static List<Mes> ListaMeses(){
		
		List<Mes> listaMeses = new ArrayList<Mes>();
		
		BD bd = new BD();
		List<Object[]> listaObj = bd.select("SELECT * FROM " + TABLA + " ORDER BY id DESC");
		for(Object[] o : listaObj) {
			Mes m = new Mes(o[0].toString(), (Integer) o[1], (Integer) o[2]);
			listaMeses.add(m);
		}
		bd.finalize();
		
		return listaMeses;
	}
	
	public String getId() {
		return id;
	}

	public Integer getImpagos() {
		return impagos;
	}

	public String getNombre() {
		return nombre;
	}
	
	

}
