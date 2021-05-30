package ui;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import entity.Alumno;
import entity.Mes;
import entity.MesAlumno;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.Origen;
import ui_utils.UIElement;

public class VistaAlumno {

	private BarraEstado barra;
	private Scene currentScene, previousScene;
	private Stage stage;
	private Alumno alumno;
	private TableView<MesAlumno> tablaMeses;
	private List<MesAlumno> listaMeses;
	private HBox buttons_bot, buttons_table;
	private VBox centro;
	private Origen origen;
	private ComboBox<String> month, pagadoStr;
	private ComboBox<Integer> year;
	private TextField nombre, apellidos, telefono, telefono_sec, modalidad, cuantia;
	private RadioButton rbAlta, rbBaja;
	private ToggleGroup tgBaja;
	
	public  VistaAlumno(Scene previousScene, Stage stage, Alumno alumno) {
		this(previousScene, stage, alumno, Origen.ESTANDAR);
	}

	public  VistaAlumno(Scene previousScene, Stage stage, Alumno alumno, Origen origen) {
		
		this.stage = stage;
		this.previousScene = previousScene;
		this.origen = origen;
		this.alumno = alumno;
		initializeData();
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Consultar o modificar alumno " + alumno.toString());
		
		/* top */
		UIElement.Titulo("Detalles del alumno " + alumno.toString(), borderPane);
		
		/* left */
		VBox izquierda = new VBox();
		izquierda.setPadding(new Insets(0, 10, 10, 10));
		izquierda.setSpacing(10);
		izquierda.setMinWidth(300);
		
		izquierda.getChildren().add(new Text("Nombre:"));
		nombre = new TextField();
		nombre.setText(alumno.getNombre());
		izquierda.getChildren().add(nombre);
		
		izquierda.getChildren().add(new Text("Apellidos:"));
		apellidos = new TextField();
		apellidos.setText(alumno.getApellidos());
		izquierda.getChildren().add(apellidos);
		
		izquierda.getChildren().add(new Text("Teléfono 1:"));
		telefono = new TextField();
		telefono.setText(Integer.toString(alumno.getTelefono()));
		izquierda.getChildren().add(telefono);
		
		izquierda.getChildren().add(new Text("Teléfono 2:"));
		telefono_sec = new TextField();
		telefono_sec.setText(Integer.toString(alumno.getTelefono_sec()));
		izquierda.getChildren().add(telefono_sec);
		
		izquierda.getChildren().add(new Text("Modalidad de baile:"));
		modalidad = new TextField();
		modalidad.setText(alumno.getModalidad());
		izquierda.getChildren().add(modalidad);
		
		izquierda.getChildren().add(new Text("Cuantía:"));
		cuantia = new TextField();
		cuantia.setText(Double.toString(alumno.getCuantia()));
		izquierda.getChildren().add(cuantia);
	
		izquierda.getChildren().add(new Text("Estado de la baja:"));
		
		tgBaja = new ToggleGroup();
		rbAlta = new RadioButton("Alta");
		rbAlta.setToggleGroup(tgBaja);
		rbBaja = new RadioButton("Baja");
		rbBaja.setToggleGroup(tgBaja);
		if(alumno.isBaja()) {
			rbBaja.setSelected(true);
		} else {
			rbAlta.setSelected(true);
		}
		izquierda.getChildren().add(rbAlta);
		izquierda.getChildren().add(rbBaja);


		borderPane.setLeft(izquierda);
		
		/* centro */
		centro = new VBox();
		centro.setPadding(new Insets(0, 10, 10, 10));
		centro.setSpacing(10);
		
		centro.getChildren().add(new Text("Lista de meses inscritos"));
		centro.getChildren().add(crearTabla());
		buttons_table = new HBox();
		buttons_table.setSpacing(10);
		centro.getChildren().add(buttons_table);

		centro.getChildren().add(new Text("Apuntar al alumno a un mes:"));
		
		HBox cmbs = new HBox();
		cmbs.setSpacing(10);
		month = new ComboBox<String>();
		month.setPrefSize(200, 20);
		String[] listaMeses = new DateFormatSymbols().getMonths();
		month.getItems().addAll(listaMeses);
		month.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
		cmbs.getChildren().add(month);
		
		year = new ComboBox<Integer>();
		year.setPrefSize(200, 20);
		for(int i = LocalDate.now().getYear(); year.getItems().size() < 50; i--) {
			year.getItems().add(i);
		}
		year.getSelectionModel().selectFirst();
		cmbs.getChildren().add(year);
		
		pagadoStr = new ComboBox<String>();
		pagadoStr.setPrefSize(200, 20);
		pagadoStr.getItems().add("Pagado");
		pagadoStr.getItems().add("No pagado");
		pagadoStr.getSelectionModel().selectFirst();
		cmbs.getChildren().add(pagadoStr);

		centro.getChildren().add(cmbs);

		borderPane.setCenter(centro);
		
		/* bottom */
		VBox bottom_vbox = new VBox();
		bottom_vbox.setSpacing(10);
		bottom_vbox.setPadding(new Insets(10, 0, 0, 0));
		
		buttons_bot = new HBox();
		buttons_bot.setSpacing(10);
		buttons_bot.setPadding(new Insets(0, 10, 0, 10));
    	bottom_vbox.getChildren().add(buttons_bot);
    	
    	HBox volver_hbox = new HBox();
    	volver_hbox.setPadding(new Insets(0, 00, 0, 10));
    	Button volver = new Button("Volver");
    	volver.setPrefSize(200, 20);
    	volver_hbox.getChildren().add(volver);
    	bottom_vbox.getChildren().add(volver_hbox);
    	volver.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(origen == Origen.SELECCION) {
					new SeleccionarAlumno(previousScene, stage);
				} else {
					stage.setScene(previousScene);
				}
			}
		});
		barra = new BarraEstado("Pulsa Guardar para guardar los cambios o Volver para salir sin guardar");
    	bottom_vbox.getChildren().add(barra.getHbox());
		borderPane.setBottom(bottom_vbox);
		
		currentScene = new Scene(borderPane);
		stage.setScene(currentScene);
	}
	
	private void rellenarTabla() {
		tablaMeses.getItems().clear();
		for(MesAlumno ma : listaMeses) {
			tablaMeses.getItems().add(ma);
		}
	}
	private void addBotones() {
    	Button botones[] = new Button[] {
    			new Button("Guardar"),
    			new Button("Eliminar alumno"),
    			new Button("Pagar mes"),
    			new Button("Eliminar mes del alumno"),
    			new Button("Generar recibo"),
    			new Button("Añadir mes")
    	};
    	
    	for(Button b : botones) {
    		b.setPrefSize(200, 20);
    	}

    	buttons_bot.getChildren().add(botones[0]);
    	botones[0].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(nombre.getText().isEmpty()) {
					barra.setEstado("El nombre del alumno no puede estar en blanco");
				} else if(apellidos.getText().isEmpty()) {
					barra.setEstado("Los apellidos del alumno no pueden estar en blanco");
				} else if(!(telefono.getText().length() == 9 && telefono.getText().matches("\\d+")) && !telefono.getText().equals("0")){
					barra.setEstado("El teléfono 1 no es válido (ponlo a 0 para omitir)");
				} else if(!(telefono_sec.getText().length() == 9 && telefono_sec.getText().matches("\\d+")) && !telefono_sec.getText().equals("0")){
					barra.setEstado("El teléfono 2 no es válido (ponlo a 0 para omitir)");
				} else if(!cuantia.getText().matches("\\d+.?\\d+") || cuantia.getText().isEmpty()){
					barra.setEstado("La cuantía debe ser numérica");
				} else {
					boolean bajaValue = false;
					if(tgBaja.getSelectedToggle().equals(rbBaja)) {
						bajaValue = true;
					}
					alumno.setNombre(nombre.getText());
					alumno.setApellidos(apellidos.getText());
					alumno.setTelefono(Integer.parseInt(telefono.getText()));
					alumno.setTelefono_sec(Integer.parseInt(telefono_sec.getText()));
					alumno.setModalidad(modalidad.getText());
					alumno.setCuantia(Double.parseDouble(cuantia.getText()));
					alumno.setBaja(bajaValue);
					barra.setEstado("Alumno guardado correctamente");
				}
			}
		});
    	buttons_bot.getChildren().add(botones[1]);
    	botones[1].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmar Acción");
				alert.setHeaderText("¿Seguro que quieres eliminar este alumno?");
				alert.setContentText("Al eliminar este alumno se eliminarán todos los meses en los que estuvo apuntado. Quizás prefiera darlo de baja en su lugar. Esta acción no puede deshacerse.");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    for(MesAlumno ma : MesAlumno.listaMesAlumno(alumno)) {
				    	ma.eliminar();
				    }
					alumno.eliminar();
					if(origen == Origen.SELECCION) {
						new SeleccionarAlumno(previousScene, stage);
					} else {
						stage.setScene(previousScene);
					}
				}
			}
		});
    	buttons_table.getChildren().add(botones[2]);
    	botones[2].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(tablaMeses.getSelectionModel().getSelectedItems().size() > 0) {
					MesAlumno seleccionado = tablaMeses.getSelectionModel().getSelectedItems().get(0);
					seleccionado.setPagado(!seleccionado.isPagado());
					tablaMeses.refresh();
					barra.setEstado("Se ha pagado el mes " + (new Mes(seleccionado.getMes())).toString() + " del alumno " + alumno.toString());
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para pagar");
				}
			}
		});
    	buttons_table.getChildren().add(botones[3]);
    	botones[3].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(tablaMeses.getSelectionModel().getSelectedItems().size() > 0) {
					MesAlumno seleccionado = tablaMeses.getSelectionModel().getSelectedItems().get(0);
					listaMeses.remove(seleccionado);
					tablaMeses.getItems().remove(seleccionado);
					String mes = seleccionado.toString();
					seleccionado.eliminar();
					barra.setEstado("Se ha eliminado el mes " + mes + " del alumno " + alumno.toString());
				} else {
					barra.setEstado("No se ha seleccionado ningún alumno para borrar del mes");
				}
			}	
		});
    	buttons_table.getChildren().add(botones[4]);
    	botones[4].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(tablaMeses.getSelectionModel().getSelectedItems().size() > 0) {
					MesAlumno seleccionado = tablaMeses.getSelectionModel().getSelectedItems().get(0);
					if(seleccionado.isPagado()) {
						new VistaRecibo(currentScene, stage, new Mes(seleccionado.getMes()), alumno);
					} else {
						barra.setEstado("No se puede generar recibo de un alumno que no ha pagado el mes");
					}
				} else {
					barra.setEstado("No se ha seleccionado ningún mes para generar recibo");
				}
			}
		});
    	centro.getChildren().add(botones[5]);
    	botones[5].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				Mes mes = new Mes(month.getSelectionModel().getSelectedIndex() + 1, year.getValue());
				if(MesAlumno.listaMesAlumno(alumno, mes).isEmpty()) {
					boolean pagado = false;
					if(pagadoStr.getValue().equals("Pagado")) {
						pagado = true;
					}
					MesAlumno ma = new MesAlumno(alumno.getId(), mes.getId(), pagado);
					tablaMeses.getItems().add(0, ma);
					barra.setEstado("Alumno añadido al mes seleccionado");
				} else {
					barra.setEstado("El alumno ya está apuntado en el mes seleccionado");
				}
			}
		});
	}
	private void initializeData() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				listaMeses = MesAlumno.listaMesAlumno(alumno);
				return null;
			}
		};
		new Thread(task).start();
		task.setOnSucceeded(event -> {
			barra.setEstado("Lista de meses cargada con éxito.");
		    rellenarTabla();
		    addBotones();
		});
	}
	private TableView<MesAlumno> crearTabla(){
		tablaMeses = new TableView<MesAlumno>(); 
		
	    TableColumn<MesAlumno, String> column1 = new TableColumn<>("Mes");
	    column1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    column1.setMinWidth(150);

	    TableColumn<MesAlumno, String> column2 = new TableColumn<>("Pagado");
	    column2.setCellValueFactory(new PropertyValueFactory<>("impagos"));
	    column2.setCellValueFactory(cellData -> {
            boolean pagado = cellData.getValue().isPagado();
            String str;
            if(pagado) {
            	str = "Sí";
            } else {
            	str = "No";
            }
            return new ReadOnlyStringWrapper(str);
	    });

	    tablaMeses.getColumns().add(column1);
	    tablaMeses.getColumns().add(column2);
	    
	    return tablaMeses;
	}
}
