package ui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui_utils.UIElement;

public class Ayuda {

	public  Ayuda(Scene previousScene, Stage stage) {
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Ayuda");
		
		/* top */
		UIElement.Titulo("Ayuda de Ritmo Latino Gestión", borderPane);
		
		/* center */
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 0, 10, 10));

		Text text[] = {
				new Text("Funcionalidad general"),
				new Text("Ritmo Latino Gestión chequea al inicio de cada ejecución el estado de sus alumnos. Si se detecta que la aplicación se ha abierto por primera vez desde el mes anterior, añadirá automáticamente a los alumnos dados de alta al nuevo mes, por lo que no es necesario añadirlos manualmente.\nNo obstante, si la aplicación pasa varios meses sin abrirse, los meses pasados no se procesarán, sólo el actual."),
				new Text("Selección de mes"),
				new Text("La pantalla de selección de mes te permite seleccionar un mes de los registrados en la aplicación. La aplicación añadirá automáticamente nuevos meses, por lo que no es necesario añadir un mes manualmente. Si aún así necesita añadir un mes manualmente, puede hacerlo apuntando a un alumno al mes en los detalles del alumno."),
				new Text("Vista del mes"),
				new Text("Tras seleccionar un mes se abre la pantalla de vista del mes, conteniendo los alumnos apuntados a dicho mes. Aquí puedes realizar las acciones principales: consultar cada alumno, pagar su mes, eliminarlo del mes, generar un recibo, darlo de baja, y 'condonación masiva' (que paga el mes a todos los alumnos), así como filtrar por el estado del pago y hacer búsqueda por nombres y apellidos. \nUn detalle importante a destacar es que no se puede añadir a un alumno al mes desde esta vista, para añadir un mes nuevo a un alumno debe hacerse desde la vista de alumnos.\nOtro detalle importantes es que al pulsar en 'Pagar mes' sobre un alumno que ya ha pagado el mes, el mes se marcará como No Pagado de nuevo."),
				new Text("Selección de alumno"),
				new Text("La pantalla de selección de alumno muestra todos los alumnos apuntados en la academia, junto con la información más relevante, ya estén dados de baja o de alta. \nDesde esta pantalla no se puede apuntar a un nuevo alumno, para ello está la opción 'Registrar nuevo alumno' en el menú principal."),
				new Text("Detalles del alumno"),
				new Text("Tras escoger un alumno se mostrará la ventana de detalles del alumno. Aquí se pueden modificar todos los detalles de un alumno, incluyendo los meses a los que está apuntado. \nPresta especial atención a la barra de estado inferior al pulsar 'Guardar' tras editar los detalles de un alumno, pues mostrará información relevante si uno de los campos se ha introducido de forma incorrecta."),
				new Text("Registrar nuevo alumno"),
				new Text("La pantalla de registrar nuevo alumno es similar a la de consultar un alumno existente, con la diferencia de que no puedes añadir meses al alumno hasta que el alumno haya sido guardado. Una vez hayas pulsado en guardar, el alumno se creará y pasarás automáticamente a la vista de detalles del nuevo alumno, donde puedes añadir los meses que sean necesarios."),
		};
		for(int i = 0; i < text.length; i++) {
			if(i % 2 == 0) {
				text[i].setStyle("-fx-font-weight: bold");
			} 
			text[i].setWrappingWidth(stage.getWidth() - 40);
			vbox.getChildren().add(text[i]);
		}
		

		borderPane.setCenter(vbox);

		/* bottom */
		HBox buttons_hbox = new HBox();
		buttons_hbox.setSpacing(10);
		buttons_hbox.setPadding(new Insets(0, 0, 10, 10));
		Button volver = new Button("Volver");
		volver.setPrefSize(300, 20);
    	volver.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				stage.setScene(previousScene);
			}
		});
    	buttons_hbox.getChildren().add(volver);
		borderPane.setBottom(buttons_hbox);
		
		Scene currentScene = new Scene(borderPane);
		stage.setScene(currentScene);
	}
}
