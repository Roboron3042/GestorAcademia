package ui_utils;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BarraEstado {

	private Text estado;
	private HBox hbox;

	public BarraEstado (String texto) {
		estado = new Text(texto);
		estado.setFont(new Font(16));
		hbox = new HBox();
		hbox.setStyle("-fx-background-color: #CCCCCC;");
		//hbox.setAlignment(Pos.CENTER);
		HBox.setMargin(estado, new Insets(0, 0, 0, 10));
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
