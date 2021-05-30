package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import ui_utils.UIElement;

public class AcercaDe {
	
	public  AcercaDe(BorderPane previousPane, Stage stage) {
		BorderPane borderPane = new BorderPane();
		stage.setTitle("Ritmo Latino Gestión - Acerca De");
		
		/* top */
		UIElement.Titulo("Ritmo Latino Gestión", borderPane);
		
		/* center */
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 0, 10, 10));
		
		TextFlow tf = new TextFlow();

		Text text[] = {
				new Text("Ritmo Latino Gestión Versión 1.0 (c) Copyright Roberto Michán Sánchez\n\n"),
				new Text("    This program is free software: you can redistribute it and/or modify\n"
						+ "    it under the terms of the GNU General Public License as published by\n"
						+ "    the Free Software Foundation, either version 3 of the License, or\n"
						+ "    (at your option) any later version.\n"
						+ "\n"
						+ "    This program is distributed in the hope that it will be useful,\n"
						+ "    but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
						+ "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
						+ "    GNU General Public License for more details.\n"
						+ "\n"
						+ "    You should have received a copy of the GNU General Public License\n"
						+ "    along with this program.  If not, see <https://www.gnu.org/licenses/>."),
				};
		for(int i = 0; i < text.length; i++) {
			if(i % 2 == 0) {
				text[i].setStyle("-fx-font-weight: bold");
			} 
			tf.getChildren().add(text[i]);
		}
		
		vbox.getChildren().add(tf);
		vbox.setAlignment(Pos.CENTER);
		tf.setTextAlignment(TextAlignment.CENTER);
		borderPane.setCenter(vbox);

		/* bottom */
		HBox buttons_hbox = new HBox();
		buttons_hbox.setSpacing(10);
		buttons_hbox.setPadding(new Insets(0, 0, 10, 10));
		Button volver = new Button("Volver");
		volver.setPrefSize(300, 20);
    	volver.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				stage.getScene().setRoot(previousPane);
			}
		});
    	buttons_hbox.getChildren().add(volver);
		borderPane.setBottom(buttons_hbox);

		stage.getScene().setRoot(borderPane);
	}
}
