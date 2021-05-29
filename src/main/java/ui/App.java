package ui;

import java.time.LocalDate;
import bd.BD;
import entity.Mes;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.UIElement;


/**
 * JavaFX App
 */
public class App extends Application {
	
	private BarraEstado barra;
	private Stage stage;
	private VBox vbox;
	private Scene scene;
	

    @Override
    public void start(Stage stage) {

		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión");
		
		/* top */
		UIElement.Titulo("Ritmo Latino Gestión", borderPane);
		
		/* bottom */
		barra = new BarraEstado("Iniciando la base de datos, por favor espere...");
		borderPane.setBottom(barra.getHbox());

		/* center */
	    vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(10);
		borderPane.setCenter(vbox);
		
		scene = new Scene(borderPane);
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
		    	BD bd = new BD();
		    	bd.iniciar();
		    	//bd.poblar();
		    	bd.finalize();
				return null;
			}
		};
		new Thread(task).start();
		task.setOnSucceeded(event -> {
	    	barra.setEstado("Base de datos inicializada. Listo.");
			mostrarBotones(); 
		});
		
		stage.setScene(scene);
		stage.show();
		this.stage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void mostrarBotones() {
    	
    	Button botones[] = new Button[] {
    			new Button("Ir al mes actual"),
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
				LocalDate hoy = LocalDate.now();
				Mes mes = new Mes(hoy.getMonthValue(), hoy.getYear());
				stage.setScene(new VistaMes(scene, stage, mes, false).getScene());
			}
		});
    	botones[1].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setScene(new SeleccionarMes(scene, stage).getScene());
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
    	botones[6].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});
    }

}