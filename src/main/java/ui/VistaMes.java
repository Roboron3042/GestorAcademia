package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Alumno;
import entity.Mes;
import entity.MesAlumno;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.Origen;
import ui_utils.UIElement;

public class VistaMes {
	
	private TableView<Alumno> tableView;
	private Stage stage;
	private BorderPane borderPane;
	private BarraEstado barra;
	private HBox buttons_hbox;
	private Mes mes;
    private List<Alumno> listaAlumnos = new ArrayList<Alumno>();

    public  VistaMes(BorderPane previousPane, Stage stage, Mes mes) {
    	this(previousPane, stage, mes, Origen.ESTANDAR);
    }
    
	public  VistaMes(BorderPane previousPane, Stage stage, Mes mes, Origen origen) {
		
		this.mes = mes;
		this.stage = stage;
		initializeData();
		borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Vista de alumnos de " + mes.toString());
		
		/* top */
		UIElement.Titulo("Vista del mes - " + mes.toString(), borderPane);
			
		/* center */
		VBox center_vbox = new VBox();
		center_vbox.setPadding(new Insets(0, 10, 0, 10));
		tableView = crearTabla(mes);
		center_vbox.getChildren().add(tableView);
		borderPane.setCenter(center_vbox);
		
		/* right */
		VBox right_vbox = new VBox();
		right_vbox.setPadding(new Insets(0, 20, 0, 10));
		right_vbox.setSpacing(10);
		Text texto_busqueda = new Text("Búsqueda de alumnos");
		right_vbox.getChildren().add(texto_busqueda);
		Text nombre_busqueda = new Text("   Nombre:");
		right_vbox.getChildren().add(nombre_busqueda);
		TextField nombre = new TextField();
		nombre.setTranslateX(10);
		right_vbox.getChildren().add(nombre);
		Text apellidos_busqueda = new Text("   Apellidos:");
		right_vbox.getChildren().add(apellidos_busqueda);
		TextField apellidos = new TextField();
		apellidos.setTranslateX(10);
		right_vbox.getChildren().add(apellidos);
    	Button buscar = new Button("Buscar");
    	buscar.setPrefSize(200, 20);
    	right_vbox.getChildren().add(buscar);
    	
    	buscar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				tableView.getItems().removeAll(listaAlumnos);
				for(Alumno a : listaAlumnos) {
					if(!nombre.getText().isEmpty()) {
						if(!apellidos.getText().isEmpty()) {
							if(	a.getNombre().contains(nombre.getText()) &&
								a.getApellidos().contains(apellidos.getText())){
							    tableView.getItems().add(a);
							}
						} else if(a.getNombre().contains(nombre.getText())) {
						    tableView.getItems().add(a);
						}
						
					} else if(!apellidos.getText().isEmpty()) {
						if(a.getApellidos().contains(apellidos.getText())) {
							if(!tableView.getItems().contains(a)) {
							    tableView.getItems().add(a);
							}
						}
					}
				}
			}
		});

    	
    	Button limpiarBusqueda = new Button("Limpiar búsqueda");
    	limpiarBusqueda.setPrefSize(200, 20);
    	right_vbox.getChildren().add(limpiarBusqueda);
    	
    	limpiarBusqueda.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				rellenarTabla();
			}
		});
    	

		Text texto_filtrar = new Text("Filtrar por:");
		right_vbox.getChildren().add(texto_filtrar);
		
		ToggleGroup group = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Mes NO pagado");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		RadioButton rb2 = new RadioButton("Mes SI pagado");
		rb2.setToggleGroup(group);
		right_vbox.getChildren().add(rb1);
		right_vbox.getChildren().add(rb2);
		
    	Button filtrar = new Button("Filtrar");
    	filtrar.setPrefSize(200, 20);
    	right_vbox.getChildren().add(filtrar);
    	
    	filtrar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				tableView.getItems().removeAll(listaAlumnos);
				boolean option;
				if(group.getSelectedToggle().equals(rb1)){
					option = false;
				} else {
					option = true;
				}
				for(Alumno a : listaAlumnos) {
					if(a.isPagado() == option) {
					    tableView.getItems().add(a);
					}
				}
			}
		});
    	
    	Button limpiarFiltro = new Button("Limpiar filtros");
    	limpiarFiltro.setPrefSize(200, 20);
    	right_vbox.getChildren().add(limpiarFiltro);
    	
    	limpiarFiltro.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				rellenarTabla();
			}
		});

		borderPane.setRight(right_vbox);
		
		/* bottom */
		VBox bottom_vbox = new VBox();
		bottom_vbox.setSpacing(10);
		bottom_vbox.setPadding(new Insets(10, 0, 0, 0));
		
		buttons_hbox = new HBox();
		buttons_hbox.setSpacing(10);
		buttons_hbox.setPadding(new Insets(0, 10, 0, 10));
    	bottom_vbox.getChildren().add(buttons_hbox);
    	
    	HBox volver_hbox = new HBox();
    	volver_hbox.setPadding(new Insets(0, 00, 0, 10));
    	Button volver = new Button("Volver");
    	volver.setPrefSize(200, 20);
    	volver_hbox.getChildren().add(volver);
    	bottom_vbox.getChildren().add(volver_hbox);
    	
    	volver.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(origen == Origen.SELECCION) {
					new SeleccionarMes(previousPane, stage);
				} else {
					stage.getScene().setRoot(previousPane);
				}
			}
		});
    	
		barra = new BarraEstado("Cargando vista de alumnos, por favor espere...");
    	bottom_vbox.getChildren().add(barra.getHbox());
		borderPane.setBottom(bottom_vbox);

		stage.getScene().setRoot(borderPane);
	}
	
	public TableView<Alumno> crearTabla(Mes mes) {
	    TableView<Alumno> tableView = new TableView<Alumno>();

	    TableColumn<Alumno, String> column1 = new TableColumn<>("Nombre");
	    column1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    column1.setPrefWidth(150);

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
	    
	    TableColumn<Alumno, String> column6 = new TableColumn<>("Pagado");
	    column6.setCellValueFactory(new PropertyValueFactory<>("pagado"));
	    column6.setCellValueFactory(cellData -> {
            boolean pagado = cellData.getValue().isPagado();
            String str;
            if(pagado) {
            	str = "Sí";
            } else {
            	str = "No";
            }
            return new ReadOnlyStringWrapper(str);
	    });

	    tableView.getColumns().add(column1);
	    tableView.getColumns().add(column2);
	    tableView.getColumns().add(column3);
	    tableView.getColumns().add(column4);
	    tableView.getColumns().add(column5);
	    tableView.getColumns().add(column6);
	    
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setPrefHeight(App.ALTO*2);
		
		return tableView;
	}
	
	public void mostrarBotones() {
		Button botones[] = new Button[] {
    			new Button("Consultar alumno"),
    			new Button("Pagar mes"),
    			new Button("Eliminar del mes"),
    			new Button("Generar recibo"),
    			new Button("Dar de baja"),
    			new Button("Condonación masica")
    	};
    	
    	for(Button b : botones) {
        	b.setPrefSize(200, 20);
        	buttons_hbox.getChildren().add(b);
    	}
		
    	botones[0].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(tableView.getSelectionModel().getSelectedItems().size() > 0) {
					Alumno seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					new VistaAlumno(borderPane, stage, seleccionado);
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para consultar");
				}
			}
		});
    	botones[1].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(tableView.getSelectionModel().getSelectedItems().size() > 0) {
					Alumno seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					MesAlumno ma = MesAlumno.listaMesAlumno(seleccionado, mes).get(0);
					ma.setPagado(!seleccionado.isPagado());
					seleccionado.setPagado(!seleccionado.isPagado());
					tableView.refresh();
					barra.setEstado("Se ha pagado el mes " + mes.toString() + " del alumno " + seleccionado.toString());
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para pagar");
				}
			}
		});
    	botones[2].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(tableView.getSelectionModel().getSelectedItems().size() > 0) {
					Alumno seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					listaAlumnos.remove(seleccionado);
					tableView.getItems().remove(seleccionado);
					MesAlumno ma = MesAlumno.listaMesAlumno(seleccionado, mes).get(0);
					ma.eliminar();
					barra.setEstado("Se ha eliminado el mes " + mes.toString() + " del alumno " + seleccionado.toString());
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para borrar del mes");
				}
				
			}
		});
    	botones[3].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(tableView.getSelectionModel().getSelectedItems().size() > 0) {
					Alumno seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					if(seleccionado.isPagado()) {
						new VistaRecibo(borderPane, stage, mes, seleccionado);
					} else {
						barra.setEstado("No se puede generar recibo de un alumno que no ha pagado el mes");
					}
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para generar recibo");
				}
			}
		});
    	botones[4].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(tableView.getSelectionModel().getSelectedItems().size() > 0) {
					Alumno seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Dar de baja a alumno");
					alert.setHeaderText("¿Quieres eliminar también al alumno del mes actual");
					alert.setContentText("Elije si quieres eliminar al alumno " + seleccionado.toString()
							+ " del mes actual al darlo de baja o si por el contrario quieres mantener su pago este mes. "
							+ "Al dar de baja a un alumno no se apuntará automáticamente a los nuevos meses. "
							+ "Para dar de alta de nuevo a un alumno, edítalo desde la lista de alumnos.");

					ButtonType buttonTypeOne = new ButtonType("Eliminar del mes");
					ButtonType buttonTypeTwo = new ButtonType("Mantener");
					ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

					alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() != buttonTypeCancel){
					    seleccionado.setBaja(true);
						barra.setEstado("El alumno " + seleccionado.toString() + " se ha dado de baja");
						if (result.get() == buttonTypeOne) {
							listaAlumnos.remove(seleccionado);
							tableView.getItems().remove(seleccionado);
							MesAlumno ma = MesAlumno.listaMesAlumno(seleccionado, mes).get(0);
							ma.eliminar();
						}
					} 
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para darlo de baja");
				}
				
			}
		});
    	botones[5].setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmar Acción");
				alert.setHeaderText("¿Seguro que quieres condonar el mes?");
				alert.setContentText("La condonación masiva marcará el mes como pagado a todos los alumnos. Esta acción no puede deshacerse.");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    for(Alumno a : listaAlumnos) {
				    	if(!a.isPagado()) {
				    		a.setPagado(true);
							MesAlumno ma = MesAlumno.listaMesAlumno(a, mes).get(0);
							ma.setPagado(true);
				    	}
				    }
					tableView.refresh();
					barra.setEstado("Se ha ejecutado una condonación masiva para el mes " + mes.toString());
				} else {
					barra.setEstado("Se ha cancelado la condonación masiva ");
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
				listaAlumnos = Alumno.listaAlumnosMes(mes);
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
