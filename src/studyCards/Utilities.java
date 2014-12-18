package studyCards;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class Utilities {
	private static int lengthOfTextLine = 30;
	
	public static int randomInt(int from, int too){
		Random rand = new Random();
		return rand.nextInt(too-from) + from;
	}

	public static void saveCollectionList(ArrayList<CardCollection> collections) throws FileNotFoundException, CardNotFoundException{
		PrintWriter writer1;
		PrintWriter writer2;
		try {
			//Will first try to create new files for the collections.
			for (int i = 0; i < collections.size(); i++){
				writer2 = new PrintWriter("savedCollections//"+collections.get(i).getName(), "UTF-8");
				for (int j = 0; j < collections.get(i).size(); j++){
					writer2.println(collections.get(i).getCard(j).getQuestion());
					writer2.println(collections.get(i).getCard(j).getAnswer());
				}
				writer2.close();
			}
			//Once all files are created the files are inserted into the All collections file. If any error occur the old All collections will not be affected.
			writer1 = new PrintWriter("savedCollections//"+"All collections", "UTF-8");
			for (int i = 0; i < collections.size();i++){
				writer1.println(collections.get(i).getName());
			}
			writer1.close();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	public static CardCollection loadCollection(String collectionName){
		CardCollection tempCollection = new CardCollection(collectionName);
	    boolean needNewCard = true;
	    BufferedReader br;
	    
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("savedCollections//"+collectionName),"UTF-8"));
		    try {
//		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
//		            sb.append(line);
		            if (needNewCard){
		            	tempCollection.addCard(new Card());
		            	tempCollection.getCard(0).setQuestion(line);
		            	needNewCard = false;
		            } else {
		            	tempCollection.getCard(0).setAnswer(line);
		            	needNewCard = true;
		            }
		            line = br.readLine();
		            
		        }
		    } catch (IOException | CardNotFoundException e) {
				e.printStackTrace();
			} finally {
		        try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    return tempCollection;
	}

	public static ArrayList<CardCollection>  loadCollectionList(){
		ArrayList<String> tempList = new ArrayList<String>();
	    BufferedReader br;
	    
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("savedCollections//"+"All collections"),"UTF-8"));
		    try {
//		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
//		            sb.append(line);
		            tempList.add(line);

		            line = br.readLine();
		            
		        }
		    } catch (IOException e) {
				e.printStackTrace();
			} finally {
		        try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ArrayList<CardCollection> tempColList = new ArrayList<CardCollection>();
		for (int i = 0; i < tempList.size();i++){
				tempColList.add(loadCollection(tempList.get(i)));
		}
		return tempColList;
		
	}
	


	/**
	 * Returns a HTML, center, formated string with line breaks at the interval given by lengthOfTextLine.
	 * @param String the String to format to a line with line breaks.
	 */
	public static String stringToRBString(String str){
		if (str == null){
			return "";
		} else if (str.length() < lengthOfTextLine){
			return "<html><div style=\"text-align: center;\"><body>"+str+"</body></html>";
		} else {
			int spaceAt = str.substring(lengthOfTextLine).indexOf(" ");
			if (spaceAt == -1){
				return "<html><div style=\"text-align: center;\"><body>"+str+"</body></html>";
			}
			return "<html><div style=\"text-align: center;\"><body>" + str.substring(0, spaceAt+lengthOfTextLine+1) + "<br>" + stringToRBStringWrapper(str.substring(spaceAt+lengthOfTextLine+1));
		}
	}
	
	private static String stringToRBStringWrapper(String str){
		if (str.length() < lengthOfTextLine){
			return str+"</body></html>";
		} else {
			int spaceAt = str.substring(lengthOfTextLine).indexOf(" ");
			if (spaceAt == -1){
				return str+"</body></html>";
			}
			return str.substring(0, spaceAt + lengthOfTextLine +1) + "<br>" + stringToRBStringWrapper(str.substring(spaceAt+lengthOfTextLine+1));
		}
	}
	
	/**
	 * Calculates a point for the upper left corner for a frame which can be used to set the frame at the center of the screen.
	 * @param width of the frame.
	 * @param height of the frame.
	 * @return Point which should be the top left corner of the frame.
	 */
	public static Point getFrameAtCenterOfScreen(int width, int height){
		return new Point(Resources.getScreenWidth()/2-(width/2),Resources.getScreenHeight()/2-(height/2));
	}
}
