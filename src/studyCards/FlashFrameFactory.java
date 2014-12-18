package studyCards;

public class FlashFrameFactory {
	/**
	 * A General verificationPopUp message.
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
	
	public static OneTextBoxPopUp oneTextBoxPopUp(String frameName, String nameBox, String boxText){
		return new OneTextBoxPopUp(frameName, nameBox, boxText);
	}

	public static TwoTextBoxPopUp twoTextBoxPopUp(String frameName, String boxName1, String boxText1, String boxName2, String boxText2){
		return new TwoTextBoxPopUp(frameName, boxName1, boxText1, boxName2, boxText2);
	}

}


