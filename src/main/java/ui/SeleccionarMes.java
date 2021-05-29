package ui;

import java.util.List;

import entity.Alumno;
import entity.Mes;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.UIElement;

public class SeleccionarMes {
	
	private BarraEstado barra;
	private Scene previousScene;
	private Scene currentScene;
	private Stage stage;
	HBox vhbox;
	private List<Mes> listaMeses;
    TableView<Mes> tableView = new TableView<Mes>();

	public  SeleccionarMes(Scene previousScene, Stage stage) {

		this.stage = stage;
		this.previousScene = previousScene;
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Selección de mes");
		
		/* top */
		UIElement.Titulo("Selecciona el mes a consultar", borderPane);
		
		/* center */
	    VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 0, 10, 0));
		borderPane.setCenter(vbox);
		
		vbox.getChildren().add(crearTabla());

		vhbox = new HBox();
		vhbox.setAlignment(Pos.CENTER);
	    vhbox.setSpacing(10);
		vbox.getChildren().add(vhbox);
		
		/* bottom */
		barra = new BarraEstado("Selecciona un mes y pulsa continuar");
		borderPane.setBottom(barra.getHbox());
		
		currentScene = new Scene(borderPane);
	}
	
	public TableView<Mes> crearTabla() {

	    TableColumn<Mes, String> column1 = new TableColumn<>("Mes");
	    column1.setCellValueFactory(new PropertyValueFactory<>("nombre"));

	    TableColumn<Mes, Integer> column2 = new TableColumn<>("Impagos");
	    column2.setCellValueFactory(new PropertyValueFactory<>("impagos"));

	    tableView.getColumns().add(column1);
	    tableView.getColumns().add(column2);
	    
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				listaMeses = Mes.ListaMeses();
				return null;
			}
		};
		new Thread(task).start();
		task.setOnSucceeded(event -> {
			barra.setEstado("Lista de meses cargada con éxito.");
		    rellenarTabla();
		    mostrarBotones();
		});
		
		return tableView;
	}
	
	public Scene getScene() {
		return currentScene;
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
    	botones[0].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setScene(previousScene);
			}
		});
    	botones[1].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if(tableView.getSelectionModel().getSelectedItems().size() > 0) {
					Mes seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					stage.setScene(new VistaMes(previousScene, stage, seleccionado, true).getScene());
				} else {
					barra.setEstado("No se ha seleccionado ningún mes para consultar");
				}
			}
		});
	}
	private void rellenarTabla() {
		tableView.getItems().clear();
		for(Mes m : listaMeses) {
			tableView.getItems().add(m);
		}
	}
}
