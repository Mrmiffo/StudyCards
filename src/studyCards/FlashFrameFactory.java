package studyCards;

/**
 * The FlashFrameFactory is a factory which gathers all the different pop-ups in a single place. The viewer will call for this class and grab any of the static methods to get a pop-up which it can detail.
 * @author Anton
 *
 */
public class FlashFrameFactory {
	/**
	 * A General verificationPopUp message. Provides a Yes and No button.
	 * @param name will be displayed in the frame.
	 * @param message will be displayed in the popup.
	 * @return true if use press Yes and false if user press no.
	 */
	public static VerificationPopUp  verificationPopUp(String name, String message){
		return new VerificationPopUp(name, message);
	}
	


	/**
	 * An error message pop-up which will display the name and message along with the Exception details.
	 * @param name of the message, will be shown in the frame header.
	 * @param message to be displayed in the error message
	 * @param e the Exception that triggered the error. May be set to null if the exception is expected.
	 */
	public static void errorPopUp(String name, String message, Exception e){
		new ErrorPopUp(name, message, e);
	}
	
	/**
	 * A pop-up with 1 text box, typical used to get a string from the user for a name.
	 * @param frameName to be displayed at the top.
	 * @param nameBox the text displayed to the left of the input box.
	 * @param boxText the text to be displayed in the text box where user write. Typically left as "" unless user is expected to modify an existing piece of text.
	 * @return
	 */
	public static OneTextBoxPopUp oneTextBoxPopUp(String frameName, String nameBox, String boxText){
		return new OneTextBoxPopUp(frameName, nameBox, boxText);
	}

	/**
	 * A pop-up with 2 text boxes, typical used to get two strings from the user such as a question and an answer.
	 * @param frameName will be displayed at the top
	 * @param boxName1 Text to be displayed to the left of the first input box.
	 * @param boxText1 Text to be displayed inside the first input box.
	 * @param boxName2 Text to be displayed to the left of the second input box.
	 * @param boxText2 Text to be displayed inside the first input box.
	 * @return
	 */
	public static TwoTextBoxPopUp twoTextBoxPopUp(String frameName, String boxName1, String boxText1, String boxName2, String boxText2){
		return new TwoTextBoxPopUp(frameName, boxName1, boxText1, boxName2, boxText2);
	}

}


