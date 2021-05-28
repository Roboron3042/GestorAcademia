package ui_utils;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class BarraEstado {

	private Text estado;
	private HBox hbox;

	public BarraEstado (String texto) {
		estado = new Text(texto);
		hbox = new HBox();
		hbox.setStyle("-fx-background-color: #CCCCCC;");
		hbox.getChildren().add(estado);
	}

	public Text getEstado() {
		return estado;
	}

	public void setEstado(String texto) {
		estado.setText(texto);
	}

	public HBox getHbox() {
		return hbox;
	}

	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}
	
}
