package ui_utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UIElement {

	public static void Titulo (String texto, BorderPane borderPane) {
		Text titulo = new Text(texto);
		titulo.setFont(new Font(32));
		borderPane.setTop(titulo);
		BorderPane.setMargin(titulo, new Insets(10, 0, 10, 0));
		BorderPane.setAlignment(titulo, Pos.TOP_CENTER);
	}
	
}
