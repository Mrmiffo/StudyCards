package studyCards;

import java.util.ArrayList;

public class CardCollection {
	private String name;
	private ArrayList<Card> collection = new ArrayList<Card>();
	
	public CardCollection(String name){
		this.name = name;
		
	}
	
	public void addCard(Card cardToAdd){
		collection.add(cardToAdd);
	}
	
	public void removeCard(Card cardToRemove){
		collection.remove(cardToRemove);
	}
	
	public Card getCard(int cardAtPos) throws CardNotFoundException{
		if (cardAtPos >= collection.size()){
			throw new CardNotFoundException("Not that many cards in collection");
		}
		return ((Card)collection.get(cardAtPos)).clone();
	}
	
	public String getName(){
		return name;
	}
	

}
