package studyCards;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class Resources {
	//Fonts
	private static final Font qaFont = new Font("Comic Sans MS", 0,30);
	private static final Font labelFont = new Font("Arial",0,20);
	private static final Dimension popUpDimenstion = new Dimension(500,200);
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static double screenWidth = screenSize.getWidth();
	private static double screenHeight = screenSize.getHeight();


	/**
	 * @return the labelfont
	 */
	public static Font getLabelfont() {
		return new Font(labelFont.getFontName(),labelFont.getStyle(),labelFont.getSize());
	}


	/**
	 * @return the Question and answer font
	 */
	public static Font getQafont() {
		return new Font(qaFont.getFontName(),qaFont.getStyle(),qaFont.getSize());
	}
	
	public static Dimension getPopUpDimension(){
		return new Dimension(popUpDimenstion);
	}
	
	public static int getScreenWidth(){
		return (int)screenWidth;
	}
	
	public static int getScreenHeight(){
		return (int)screenHeight;
	}
	
}
