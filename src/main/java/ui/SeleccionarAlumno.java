package ui;

import java.util.List;

import entity.Alumno;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.Origen;
import ui_utils.UIElement;

public class SeleccionarAlumno {
	private BarraEstado barra;
	private BorderPane previousPane;
	private BorderPane borderPane;
	private Stage stage;
	HBox vhbox;
	private List<Alumno> listaAlumnos;
    TableView<Alumno> tableView;

	public  SeleccionarAlumno(BorderPane previousPane, Stage stage) {

		this.stage = stage;
		this.previousPane = previousPane;
		initializeData();
		borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Selección de alumno");
		
		/* top */
		UIElement.Titulo("Selecciona el alumno a consultar", borderPane);
		
		/* center */
	    VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 10, 10, 10));
		borderPane.setCenter(vbox);
		vbox.getChildren().add(crearTabla());

		vhbox = new HBox();
		vhbox.setAlignment(Pos.CENTER);
	    vhbox.setSpacing(10);
		vbox.getChildren().add(vhbox);
		
		/* bottom */
		barra = new BarraEstado("Selecciona un alumno y pulsa continuar");
		borderPane.setBottom(barra.getHbox());
		
		stage.getScene().setRoot(borderPane);
	}
	
	public TableView<Alumno> crearTabla() {

	    tableView = new TableView<Alumno>(); 

	    TableColumn<Alumno, String> column1 = new TableColumn<>("Nombre");
	    column1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    column1.setMinWidth(150);
	    
	    TableColumn<Alumno, String> column2 = new TableColumn<>("Apellidos");
	    column2.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
	    column2.setPrefWidth(200);
	    
	    TableColumn<Alumno, Integer> column3 = new TableColumn<>("Teléfono");
	    column3.setCellValueFactory(new PropertyValueFactory<>("telefono"));
	    column3.setPrefWidth(100);
	    
	    TableColumn<Alumno, Double> column4 = new TableColumn<>("Cuantía");
	    column4.setCellValueFactory(new PropertyValueFactory<>("cuantia"));
	    
	    TableColumn<Alumno, Double> column5 = new TableColumn<>("Modalidad");
	    column5.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
	    column5.setPrefWidth(200);
	    
	    TableColumn<Alumno, String> column6 = new TableColumn<>("Baja");
	    column6.setCellValueFactory(new PropertyValueFactory<>("baja"));
	    column6.setCellValueFactory(cellData -> {
            boolean baja = cellData.getValue().isBaja();
            String str;
            if(baja) {
            	str = "Baja";
            } else {
            	str = "Alta";
            }
            return new ReadOnlyStringWrapper(str);
	    });

	    TableColumn<Alumno, Integer> column7 = new TableColumn<>("Pagos pendientes");
	    column7.setCellValueFactory(new PropertyValueFactory<>("impagos"));
	    column7.setMinWidth(150);

	    tableView.getColumns().add(column1);
	    tableView.getColumns().add(column2);
	    tableView.getColumns().add(column3);
	    tableView.getColumns().add(column4);
	    tableView.getColumns().add(column5);
	    tableView.getColumns().add(column6);
	    tableView.getColumns().add(column7);
	    
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    tableView.setPrefHeight(App.ALTO*2);
		
		return tableView;
	}
	
	private void mostrarBotones() {
    	Button botones[] = new Button[] {
    			new Button("Volver"),
    			new Button("Seleccionar")
    	};
    	for(Button b : botones) {
        	b.setPrefSize(300, 20);
    		vhbox.getChildren().add(b);
    	}
    	botones[0].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				stage.getScene().setRoot(previousPane);
			}
		});
    	botones[1].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(tableView.getSelectionModel().getSelectedItems().size() > 0) {
					Alumno seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					new VistaAlumno(previousPane, stage, seleccionado, Origen.SELECCION);
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para consultar");
				}
			}
		});
	}
	private void rellenarTabla() {
		tableView.getItems().clear();
		for(Alumno a : listaAlumnos) {
			tableView.getItems().add(a);
		}
	}
	
	private void initializeData() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				listaAlumnos = Alumno.listaAlumnos();
				return null;
			}
		};
		new Thread(task).start();
		task.setOnSucceeded(event -> {
			barra.setEstado("Lista de alumnos cargada con éxito.");
		    rellenarTabla();
		    mostrarBotones();
		});
	}
}
