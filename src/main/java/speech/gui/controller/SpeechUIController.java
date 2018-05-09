package speech.gui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.speechFacade.SpeechFacade;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import speechrecognition.SpeechInitializer;

public class SpeechUIController {

	static int i = 0;

	@FXML
	ToggleButton startStop;
	@FXML
	Text listnenerState;
	private File featureFile;

	@FXML
	public void startStopSpeech() {
		if (startStop.isSelected()) {
			SpeechInitializer.getSpeechInitializer().startSpeechThread();
			listnenerState.setText("I am Listening.");
		} else {
			SpeechInitializer.getSpeechInitializer().setThreadState(false);
			listnenerState.setText("I have stopped Listening.");
			startMakingFeatureFile();
		}
	}

	private void startMakingFeatureFile() {
		ArrayList<String> list = SpeechFacade.arrayList;
		featureFile = new File("C:\\Users\\akanksha\\Desktop" + "\\myScenario" + i++ + ".feature");
		writeObjectToJsonFile(list, featureFile);
		SpeechFacade.arrayList.clear();
	}

	private void writeObjectToJsonFile(ArrayList<String> jsonObject, File file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonObject);
		} catch (IOException e) {
		}
	}

}
