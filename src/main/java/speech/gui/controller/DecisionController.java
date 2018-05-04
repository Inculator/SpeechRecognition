package speech.gui.controller;

import com.mg.speechFacade.SpeechFacade;

public class DecisionController {

	public static void decisionMaker(String result) {
		System.out.println(result);
		new SpeechFacade().controllerForSpeechCommand(result);
	}
}
