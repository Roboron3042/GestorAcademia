package ui;

import java.util.List;

import entity.Alumno;
import entity.Mes;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.UIElement;

public class VistaAlumno {

	private BarraEstado barra;
	private Scene currentScene;

	public  VistaAlumno(Scene previousScene, Stage stage, Alumno alumno) {
		
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Detalles del alumno " + alumno.toString());
		
		/* top */
		UIElement.Titulo("Detalles del alumno " + alumno.toString(), borderPane);
		
		/* bottom */
		barra = new BarraEstado("Pulsa Guardar para guardar los cambios o Volver para salir sin guardar");
		borderPane.setBottom(barra.getHbox());
		
		currentScene = new Scene(borderPane);
	}
	
	public Scene getScene() {
		return currentScene;
	}
}
