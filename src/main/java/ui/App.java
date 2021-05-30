package ui;

import java.time.LocalDate;

import bd.BD;
import entity.Alumno;
import entity.Mes;
import entity.MesAlumno;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.Origen;
import ui_utils.UIElement;


/**
 * JavaFX App
 */
public class App extends Application {
	
	private BarraEstado barra;
	private Stage stage;
	private VBox vbox;
	private Scene scene;
	private Mes mes;
	

    @Override
    public void start(Stage stage) {

		this.stage = stage;
		BorderPane borderPane = new BorderPane();
		initializeData();
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
		
		stage.setScene(scene);
		stage.show();
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
    	botones[0].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new VistaMes(scene, stage, mes);
			}
    	});
    	botones[1].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new SeleccionarMes(scene, stage);
			}
		});
    	botones[2].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new SeleccionarAlumno(scene, stage);
			}
		});
    	botones[3].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new VistaAlumno(scene, stage, null, Origen.CREACION);
			}
		});
    	botones[4].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new Ayuda(scene, stage);
			}
		});
    	botones[5].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new AcercaDe(scene, stage);
			}
		});
    	botones[6].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
    }
    
	private void initializeData() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
		    	BD bd = new BD();
		    	bd.iniciar();
		    	//bd.poblar();
		    	bd.finalize();
				LocalDate hoy = LocalDate.now();
				mes = new Mes(hoy.getMonthValue(), hoy.getYear());
		    	if(!mes.isProcesado()) {
			    	barra.setEstado("Detectado nuevo mes. Añadiendo alumnos...");
			    	for(Alumno a : Alumno.listaAlumnos()) {
			    		if(!a.isBaja() && MesAlumno.listaMesAlumno(a,mes).isEmpty()) {
			    			System.out.println("Alumno" + a.toString());
			    			new MesAlumno(a.getId(),mes.getId());
			    		}
			    	}
			    	mes.setProcesado(true);
			    	barra.setEstado("Se ha añadido a los alumnos al nuevo mes");
		    	} else {
			    	barra.setEstado("Base de datos inicializada. Listo.");
		    	}
				return null;
			}
		};
		new Thread(task).start();
		task.setOnSucceeded(event -> {
			mostrarBotones(); 
		});
	}

}