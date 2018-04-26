package speechrecognition;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import javax.speech.recognition.ResultAdapter;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class HelloWorld extends ResultAdapter {

	private static Logger logger = Logger.getLogger("HelloWorld");
	private static Runnable speechRunnable;
	private static Thread speechThread;
	private static Boolean exitFlag = true;
	private static Thread resourcesThread;

	public static void main(String args[]) throws FileNotFoundException {
		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		configuration.setGrammarPath("resource:/speechGrammer");
		configuration.setGrammarName("speechGrammer");
		configuration.setUseGrammar(true);

		try {
			LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
			recognizer.startRecognition(true);
			startSpeechThread(recognizer);
			startResourcesThread();
		} catch (IOException e) {
		}

	}

	private static void startSpeechThread(LiveSpeechRecognizer recognizer) {
		speechRunnable = () -> {
			System.out.println("I am Listening !!!");
			while (exitFlag) {
				SpeechResult result = recognizer.getResult();
				String result1 = result.getHypothesis();
				System.out.println(result1);
				makeDecision(result1);
			}
		};

		speechThread = new Thread(speechRunnable);
		speechThread.start();
	}

	private static void makeDecision(String result1) {
		String[] array = result1.split(" ");
		System.out.println(array);

		if (result1.contains("terminate"))
			exitFlag = false;
	}

	public static void startResourcesThread() {

		// alive?
		if (resourcesThread != null && resourcesThread.isAlive())
			return;

		resourcesThread = new Thread(() -> {
			try {

				// Detect if the microphone is available
				while (true) {
					if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
						// logger.log(Level.INFO, "Microphone is available.\n")
					} else {
						logger.log(Level.INFO, "Microphone is not available.\n");
					}

					// Sleep some period
					Thread.sleep(350);
				}

			} catch (InterruptedException ex) {
				logger.log(Level.WARNING, null, ex);
				resourcesThread.interrupt();
			}
		});

		// Start
		resourcesThread.start();
	}
}