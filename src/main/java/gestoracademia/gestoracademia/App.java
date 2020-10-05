package gestoracademia.gestoracademia;

import bd.BD;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * JavaFX App
 */
public class App extends Application {
	
	private Integer ANCHO = 500;
	private Integer ALTO = 350;
	private Integer TAMFUENTE = 14;
	private ObservableList<Node> list;
	private Text estado;
	private Stage principal;
	private Group group;

    @Override
    public void start(Stage stage) {
    	
    	principal = stage;
		
		// Creating a line object
		Line line = new Line();
		line.setStartX(0); 
		line.setStartY(ALTO - 18); 
		line.setEndX(ANCHO); 
		line.setEndY(ALTO - 18);
		
		estado = new Text();
		estado.setFont(new Font(TAMFUENTE)); 
		estado.setX(0); 
		estado.setY(ALTO-4);
		estado.setText("Iniciando la base de datos, por favor espere...");

		
		Text titulo = new Text();
		titulo.setFont(new Font(32)); 
		titulo.setX(80); 
		titulo.setY(64);
		titulo.setText("Ritmo Latino Gestión");
		
		// creating a Group object
		group = new Group();
		list = group.getChildren(); 
		list.add(line);
		list.add(estado);
		list.add(titulo);

		// Creating a Scene by passing the group object, height and width
		//Scene scene = new Scene(group, primaryStage.getMaxWidth(), primaryStage.getMaxHeight());
		Scene scene = new Scene(group, ANCHO, ALTO);

		// setting color to the scene
		// scene.setFill(Color.BROWN);
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
		    	BD bd = new BD();
		    	bd.iniciar();
				// TODO Auto-generated method stub
				return null;
			}
		};
		//ProgressBar bar = new ProgressBar();
		//bar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(event -> actualizar());
		
		principal.setTitle("Ritmo Latino Gestión");
		principal.setScene(scene);
		principal.show();
		
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void actualizar() {
    	
    	Button botonListaMeses = new Button();
		botonListaMeses.setLayoutX(100);
		botonListaMeses.setLayoutY(100);
		botonListaMeses.setMinWidth(300);
		botonListaMeses.setText("Lista de Meses");
		botonListaMeses.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
		list.add(botonListaMeses);
		
		Button botonListaAlumnos = new Button();
		botonListaAlumnos.setLayoutX(100);
		botonListaAlumnos.setLayoutY(100 + 30);
		botonListaAlumnos.setMinWidth(300);
		botonListaAlumnos.setText("Lista de Alumnos");
		botonListaAlumnos.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
		list.add(botonListaAlumnos);
		
		Button botonRegistrarAlumno = new Button();
		botonRegistrarAlumno.setLayoutX(100);
		botonRegistrarAlumno.setLayoutY(100 + 30*2);
		botonRegistrarAlumno.setMinWidth(300);
		botonRegistrarAlumno.setText("Registrar nuevo alumno");
		botonRegistrarAlumno.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
		list.add(botonRegistrarAlumno);
		
		Button botonAyuda = new Button();
		botonAyuda.setLayoutX(100);
		botonAyuda.setLayoutY(100 + 30*3);
		botonAyuda.setMinWidth(300);
		botonAyuda.setText("Ayuda");
		botonAyuda.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
		list.add(botonAyuda);
		
		Button botonAcercaDe = new Button();
		botonAcercaDe.setLayoutX(100);
		botonAcercaDe.setLayoutY(100 + 30*4);
		botonAcercaDe.setMinWidth(300);
		botonAcercaDe.setText("Acerca de");
		botonAcercaDe.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.out.println("Botón pulsado");
				
			}
		});
		list.add(botonAcercaDe);
		
		Button botonSalir = new Button();
		botonSalir.setLayoutX(100);
		botonSalir.setLayoutY(100 + 30*5);
		botonSalir.setMinWidth(300);
		botonSalir.setText("Salir");
		botonSalir.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});
		list.add(botonSalir);
		
		estado.setText("Base de datos inicializada. Listo.");
		estado.setFill(Color.GREEN);
		
    }

}