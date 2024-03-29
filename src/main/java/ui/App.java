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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.Origen;
import ui_utils.UIElement;
import java.net.*;


/**
 * JavaFX App
 */
public class App extends Application {
	
	final static int ANCHO = 1200;
	final static int ALTO = 800;
	private BarraEstado barra;
	private BorderPane borderPane;
	private Stage stage;
	private VBox vbox;
	private Scene scene;
	private Mes mes;

    @Override
    public void start(Stage stage) {

		this.stage = stage;
		justOne();
		borderPane = new BorderPane();
		initializeData();
		stage.setTitle("Ritmo Latino Gestión");
		
		/* top */
		//UIElement.Titulo("Ritmo Latino Gestión", borderPane);
		
		/* bottom */
		barra = new BarraEstado("Iniciando la base de datos, por favor espere...");
		borderPane.setBottom(barra.getHbox());

		/* center */
	    vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(10);
	    vbox.setPadding(new Insets(0, 10, 0, ANCHO/6));
		BorderPane.setAlignment(vbox, Pos.CENTER);
		borderPane.setLeft(vbox);

		Text titulo = new Text("Ritmo Latino Gestión\n\n");
		titulo.setFont(new Font(32));
		vbox.getChildren().add(titulo);
		
		Image logo = new Image("file:rma.jpg", true);
		ImageView imagen = new ImageView(logo);
		borderPane.setCenter(imagen);
	
		scene = new Scene(borderPane);

		stage.setMinWidth(ANCHO);
		stage.setMinHeight(ALTO);
		//stage.setMaximized(true);
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
				new VistaMes(borderPane, stage, mes);
			}
    	});
    	botones[1].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new SeleccionarMes(borderPane, stage);
			}
		});
    	botones[2].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new SeleccionarAlumno(borderPane, stage);
			}
		});
    	botones[3].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new VistaAlumno(borderPane, stage, null, Origen.CREACION);
			}
		});
    	botones[4].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new Ayuda(borderPane, stage);
			}
		});
    	botones[5].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				new AcercaDe(borderPane, stage);
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
	public void justOne() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Socket clientSocket = new Socket("localhost", 111);
					clientSocket.close();
					System.out.println("*** Already running!");
					System.exit(1);
					return null;
				}
				catch (Exception e) {
					// Create the server socket
					ServerSocket serverSocket = new ServerSocket(111, 1);
					// Wait for a connection
					Socket clientSocket = serverSocket.accept();
					serverSocket.close();
					clientSocket.close();
					return null;
				}
			}
		};
		new Thread(task).start();
		task.setOnSucceeded(event -> {
			stage.setIconified(true);
			stage.setIconified(false);
			justOne();
		});
	}
    @Override
    public void stop() {
		System.exit(0);
    }
}