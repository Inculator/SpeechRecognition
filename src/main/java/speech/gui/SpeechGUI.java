package speech.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SpeechGUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("/view/speechLauncher.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/speech-podium.jpg")));
			stage.setScene(scene);
			stage.setTitle("Browser Speech Controller by Mohak Gupta");
			stage.showAndWait();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {
					try {
						stop();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
