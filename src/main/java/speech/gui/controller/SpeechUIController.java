package speech.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import speechrecognition.SpeechInitializer;

public class SpeechUIController {

	@FXML
	ToggleButton startStop;
	@FXML
	Text listnenerState;

	@FXML
	public void startStopSpeech() {
		if (startStop.isSelected()) {
			SpeechInitializer.getSpeechInitializer().startSpeechThread();
			listnenerState.setText("I am Listening.");
		} else {
			SpeechInitializer.getSpeechInitializer().setThreadState(false);
			listnenerState.setText("I have stopped Listening.");
		}
	}
	
}
