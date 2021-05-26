package gestoracademia.gestoracademia;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SeleccionarMes {
	
	private Scene currentScene;
	private Text estado;

	public  SeleccionarMes(Scene previousScene, Stage stage) {
		
		BorderPane borderPane = new BorderPane();
		
		/* top */
		Text titulo = new Text("Selecciona el mes a consultar");
		titulo.setFont(new Font(32));
		borderPane.setTop(titulo);
		BorderPane.setAlignment(titulo, Pos.TOP_LEFT);
		
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
				stage.setTitle("Ritmo Latino Gestión");
				stage.setScene(previousScene);
			}
		});
    	botones[1].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setTitle("Ritmo Latino Gestión - Vista de alumnos de " + cmb.getValue());
				System.out.print(cmb.getItems().indexOf(cmb.getValue()));
			}
		});
		
		/* bottom */
		estado = new Text("Selecciona un mes y pulsa continuar");
		HBox hbox = new HBox();
		hbox.setStyle("-fx-background-color: #CCCCCC;");
		hbox.getChildren().add(estado);
		borderPane.setBottom(hbox);
		BorderPane.setAlignment(hbox, Pos.BOTTOM_RIGHT);
		
		currentScene = new Scene(borderPane);
	}
	
	public Scene getScene() {
		return currentScene;
	}
}
