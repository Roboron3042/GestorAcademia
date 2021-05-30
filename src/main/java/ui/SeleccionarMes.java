package ui;

import java.util.List;

import entity.Mes;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.Origen;
import ui_utils.UIElement;

public class SeleccionarMes {
	
	private BarraEstado barra;
	private BorderPane previousPane, borderPane;
	private Stage stage;
	HBox vhbox;
	private List<Mes> listaMeses;
    TableView<Mes> tableView;

	public  SeleccionarMes(BorderPane previousPane, Stage stage) {

		this.stage = stage;
		this.previousPane = previousPane;
		initializeData();
		borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Selección de mes");
		
		/* top */
		UIElement.Titulo("Selecciona el mes a consultar", borderPane);
		
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
		barra = new BarraEstado("Selecciona un mes y pulsa continuar");
		borderPane.setBottom(barra.getHbox());
		
		stage.getScene().setRoot(borderPane);
	}
	
	public TableView<Mes> crearTabla() {
	    tableView = new TableView<Mes>(); 

	    TableColumn<Mes, String> column1 = new TableColumn<>("Mes");
	    column1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    column1.setMinWidth(150);

	    TableColumn<Mes, Integer> column2 = new TableColumn<>("Pagos pendientes");
	    column2.setCellValueFactory(new PropertyValueFactory<>("impagos"));
	    column2.setMinWidth(150);

	    tableView.getColumns().add(column1);
	    tableView.getColumns().add(column2);
	    
	    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    tableView.setPrefHeight(App.ALTO*2);
	    tableView.setMaxWidth(App.ANCHO/2);
		
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
					Mes seleccionado = tableView.getSelectionModel().getSelectedItems().get(0);
					new VistaMes(previousPane, stage, seleccionado, Origen.SELECCION);
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
	private void initializeData() {
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
	}
}
