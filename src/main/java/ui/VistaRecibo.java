package ui;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.time.LocalDate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import entity.Alumno;
import entity.Mes;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui_utils.BarraEstado;
import ui_utils.UIElement;

public class VistaRecibo {

	private Stage stage;
	private Scene currentScene;
	private Scene previousScene;
	private BarraEstado barra;
	private Mes mes;
	private Alumno alumno;
	private HBox buttons_hbox;
	private final String titulo = "Recibo academia Ritmo Latino";

	public  VistaRecibo(Scene previousScene, Stage stage, Mes mes, Alumno alumno) {
		
		this.previousScene = previousScene;
		this.stage = stage;
		this.alumno = alumno;
		this.mes = mes;
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Generar recibo");
		
		/* top */
		UIElement.Titulo(titulo, borderPane);
		
		/* center */
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 0, 10, 0));
		String s[] = getDescripcion();
		
		for(int i = 0; i < s.length; i++) {
			if(i % 2 == 0) {
				vbox.getChildren().add(new Text("\n\t" + s[i]));	
			} else {
				Text text = new Text("\n\t\t" + s[i]);
				text.setStyle("-fx-font-weight: bold");
				vbox.getChildren().add(text);
			}
		}
		
		buttons_hbox = new HBox();
		buttons_hbox.setSpacing(10);
    	vbox.getChildren().add(buttons_hbox);
    	
    	mostrarBotones();

		borderPane.setCenter(vbox);
		
		
		/* bottom */
		barra = new BarraEstado("Pulsa Exportar a PDF para generar un fichero .pdf del recibo.");
		borderPane.setBottom(barra.getHbox());
		
		currentScene = new Scene(borderPane);
	}
	
	public Scene getScene() {
		return currentScene;
	}
	public String[] getDescripcion() {
		LocalDate hoy = LocalDate.now();
		return 	new String[]{"Este documento acredita que el alumno: ",
				alumno.toString(),
				"ha pagado las clases de:",
				alumno.getModalidad(),
				"impartidas en la academia Ritmo Latino durante el mes de:",
				mes.toString(),
				"por una cuantía de:",
				alumno.getCuantia() + "€",
				"Algeciras, a " + hoy.getDayOfMonth() + " de " + 
				new DateFormatSymbols().getMonths()[hoy.getMonthValue() - 1] + " de " + hoy.getYear()};
	}
	private void mostrarBotones() {
    	Button botones[] = new Button[] {
    			new Button("Volver"),
    			new Button("Exportar a PDF")
    	};
    	for(Button b : botones) {
        	b.setPrefSize(300, 20);
        	buttons_hbox.getChildren().add(b);
    	}
    	botones[0].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				stage.setScene(previousScene);
			}
		});
    	botones[1].setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(MouseEvent arg0) {
				PDDocument document = new PDDocument();
				PDPage page = new PDPage();
				document.addPage(page);

				PDPageContentStream contentStream;
				try {
					contentStream = new PDPageContentStream(document, page);

					contentStream.beginText();
					contentStream.setFont(PDType1Font.COURIER_BOLD, 32);
					contentStream.setLeading(14.5f);
					contentStream.newLineAtOffset(25, 725);
					contentStream.showText("Recibo academia Ritmo Latino");
					String s[] = getDescripcion();
					
					for(int i = 0; i < s.length; i++) {
						if(i == s.length - 1) {
							for(int e = 0; e < 10; e++) {
								contentStream.newLine();
							}
							contentStream.setFont(PDType1Font.COURIER, 16);
							contentStream.showText(s[i]);
						} else if(i % 2 == 0) {
							contentStream.setFont(PDType1Font.COURIER, 16);
							contentStream.newLine();
							contentStream.newLine();
							contentStream.showText(s[i]);	
						} else {
							contentStream.setFont(PDType1Font.COURIER_BOLD, 16);
							contentStream.newLine();
							contentStream.newLine();
							contentStream.showText("    " + s[i]);
						}
					}
					contentStream.endText();
					contentStream.close();
					
					String dir = "recibos/" + mes.getId() + "/";
					String filename = mes.getId() + "-" + alumno.getNombre() + "-" + alumno.getApellidos() + ".pdf";
					Files.createDirectories(Paths.get(dir));
					document.save(dir + filename);
					document.close();
					barra.setEstado("Documento exportado en: " + dir + filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					barra.setEstado(e.toString());
				}
			}
		});
	}
}
