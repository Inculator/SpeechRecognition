package speechrecognition;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import speech.gui.controller.DecisionController;

public class SpeechInitializer {

	private static Logger logger = Logger.getLogger("HelloWorld");
	private static Runnable speechRunnable;
	private static Thread speechThread;
	private static Thread resourcesThread;
	private static SpeechInitializer speechInitializer;
	private static LiveSpeechRecognizer recognizer;
	private static Configuration configuration;
	private static boolean waitFlag = true;

	private SpeechInitializer() {
		configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		configuration.setGrammarPath("resource:/speechGrammer");
		configuration.setGrammarName("speechGrammer");
		configuration.setUseGrammar(true);
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
			recognizer.startRecognition(true);
		} catch (IOException e) {
		}
		startResourcesThread();
	}

	public static SpeechInitializer getSpeechInitializer() {
		if (speechInitializer == null)
			speechInitializer = new SpeechInitializer();
		return speechInitializer;
	}

	public void startSpeechThread() {
		if (speechThread != null && speechThread.isAlive())
			return;

		speechRunnable = () -> {
			waitFlag = true;
			while (waitFlag)
				if (waitFlag)
					DecisionController.decisionMaker(recognizer.getResult().getHypothesis());
		};

		speechThread = new Thread(speechRunnable);
		speechThread.start();
	}

	public void setThreadState(Boolean flag) {
		if (speechThread != null && speechThread.isAlive())
			waitFlag = false;
	}

	public static void startResourcesThread() {
		if (resourcesThread != null && resourcesThread.isAlive())
			return;
		resourcesThread = new Thread(() -> {
			try {
				while (true) {
					if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
					} else {
						logger.log(Level.INFO, "Microphone is not available.\n");
					}
					Thread.sleep(350);
				}
			} catch (InterruptedException ex) {
				logger.log(Level.WARNING, null, ex);
				resourcesThread.interrupt();
			}
		});
		resourcesThread.start();
	}
}
