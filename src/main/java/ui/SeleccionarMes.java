package ui;

import java.util.List;

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

public class SeleccionarMes {
	
	private BarraEstado barra;
	private Scene currentScene;

	public  SeleccionarMes(Scene previousScene, Stage stage) {
		
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Selección de mes");
		
		/* top */
		UIElement.Titulo("Selecciona el mes a consultar", borderPane);
		
		/* center */
	    VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(50);
		borderPane.setCenter(vbox);
		
		
		ComboBox<String> cmb = new ComboBox<String>();
		List<Mes> listaMeses = Mes.ListaMeses();
		for(Mes mes : listaMeses) {
			cmb.getItems().add(mes.toString());
		}
		cmb.getSelectionModel().selectFirst();
		vbox.getChildren().add(cmb);

		HBox vhbox = new HBox();
		vhbox.setAlignment(Pos.CENTER);
	    vhbox.setSpacing(10);
		vbox.getChildren().add(vhbox);
		
    	Button botones[] = new Button[] {
    			new Button("Atrás"),
    			new Button("Continuar")
    	};
    	for(Button b : botones) {
        	b.setPrefSize(300, 20);
    		vhbox.getChildren().add(b);
    	}
    	botones[0].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setScene(previousScene);
			}
		});
    	botones[1].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setScene(new VistaMes(currentScene, stage, listaMeses.get(cmb.getItems().indexOf(cmb.getValue()))).getScene());
			}
		});
		
		/* bottom */
		barra = new BarraEstado("Selecciona un mes y pulsa continuar");
		borderPane.setBottom(barra.getHbox());
		
		currentScene = new Scene(borderPane);
	}
	
	public Scene getScene() {
		return currentScene;
	}
}
