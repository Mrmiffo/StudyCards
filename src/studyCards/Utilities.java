package studyCards;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Utilities {

	public static void save(CardCollection collection) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(collection.getName(), "UTF-8");
			for (int i = 0; i < collection.size(); i++){
				writer.println(collection.getCard(i).getQuestion());
				writer.println(collection.getCard(i).getAnswer());
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException | CardNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static CardCollection load(String collectionName){
		CardCollection tempCollection = new CardCollection(collectionName);
	    boolean needNewCard = true;
	    BufferedReader br;
	    
		try {
			br = new BufferedReader(new FileReader(collectionName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            
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
		}

	    return tempCollection;
	}

}
