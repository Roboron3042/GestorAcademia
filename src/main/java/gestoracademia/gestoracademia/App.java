package gestoracademia.gestoracademia;

import bd.BD;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
	
	private Integer TAMFUENTE = 14;
	private Text estado;
	private Stage principal;
	private VBox vbox;
	

    @Override
    public void start(Stage stage) {
    	
    	principal = stage;

		BorderPane borderPane = new BorderPane();
		
		/* top */
		Text titulo = new Text("Ritmo Latino Gestión");
		titulo.setFont(new Font(32));
		borderPane.setTop(titulo);
		BorderPane.setMargin(titulo, new Insets(30, 0, 0, 0));
		BorderPane.setAlignment(titulo, Pos.TOP_CENTER);
		
		/* bottom */
		estado = new Text("Iniciando la base de datos, por favor espere...");
		estado.setFont(new Font(TAMFUENTE));
		HBox hbox = new HBox();
		hbox.setStyle("-fx-background-color: #CCCCCC;");
		hbox.getChildren().add(estado);
		borderPane.setBottom(hbox);

		/* center */
	    vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(10);
		borderPane.setCenter(vbox);
		
		Scene scene = new Scene(borderPane);
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
		    	BD bd = new BD();
		    	bd.iniciar();
				return null;
			}
		};
		new Thread(task).start();
		task.setOnSucceeded(event -> mostrarBotones());
		
		
		principal.setTitle("Ritmo Latino Gestión");
		principal.setScene(scene);
		principal.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void mostrarBotones() {
    	
    	Button botones[] = new Button[] {
    			new Button("Lista de Meses"),
    			new Button("Lista de Alumnos"),
    			new Button("Registrar nuevo alumno"),
    			new Button("Ayuda"),
    			new Button("Acerca de"),
    			new Button("Salir")
    	};
    	
    	for(Button b : botones) {
        	b.setPrefSize(300, 20);
    		vbox.getChildren().add(b);
    	}
		
    	botones[0].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
    	botones[1].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
    	botones[2].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
    	botones[3].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
    	botones[4].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
    	botones[5].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});
		
		estado.setText("Base de datos inicializada. Listo.");
		estado.setFill(Color.GREEN);
		
    }

}