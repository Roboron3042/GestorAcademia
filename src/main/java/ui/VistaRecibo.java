package ui;

import entity.Alumno;
import entity.Mes;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.UIElement;

public class VistaRecibo {

	private Scene currentScene;

	public  VistaRecibo(Scene previousScene, Stage stage, Mes mes, Alumno alumno) {
		
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Generar recibo ");
		
		/* top */
		UIElement.Titulo("Recibo academia Ritmo Latino", borderPane);
		
		currentScene = new Scene(borderPane);
	}
	
	public Scene getScene() {
		return currentScene;
	}
}
