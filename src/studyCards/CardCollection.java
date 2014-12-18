package studyCards;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * CardCollection is a collection of cards.
 * @author Anton
 *
 */
public class CardCollection {
	private String name;
	private ArrayList<Card> collection = new ArrayList<Card>();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private Date createDate;
	
	/**
	 * Main constructor, will create a collection with the given name.
	 * @param name
	 */
	public CardCollection(String name){
		createDate = new Date();
		this.name = name;
		
	}
	
	/**
	 * Default constructor to create a collection without a name. Name will be defaulted to "New collection yyyy-MM-dd"
	 */
	public CardCollection(){
		createDate = new Date();
		this.name = "New collection " + dateFormat.format(createDate);
	}
	
	public void setName(String name){
		this.name = name;
	}
	/**
	 * Adds the given card to the first position in the collection.
	 * @param cardToAdd The card to add to the collection.
	 */
	public void addCard(Card cardToAdd){
		collection.add(0, cardToAdd);
	}
	
	/**
	 * Removes the specified card from the collection.
	 * @param cardToRemove The card number to remove.
	 * @throws CardNotFoundException When the given card number is higher than the number of cards or is negative.
	 */
	public void removeCard(int cardToRemove) throws CardNotFoundException{
		if (cardToRemove >= collection.size()){
			throw new CardNotFoundException("Not that many cards in collection");
		} else if (cardToRemove < 0){
			throw new CardNotFoundException("Negative value not accepted");
		}
		collection.remove(cardToRemove);
	}
	
	/**
	 * Returns the card at the given position in the collection. 
	 * @param cardAtPos CPosition in collection.
	 * @return Card The card at the given position.
	 * @throws CardNotFoundException When the value given is higher than the number of cards or is negative.
	 */
	public Card getCard(int cardAtPos) throws CardNotFoundException{
		if (cardAtPos >= collection.size()){
			throw new CardNotFoundException("Not that many cards in collection");
		} else if (cardAtPos < 0){
			throw new CardNotFoundException("Negative value not accepted");
		}
		return (Card)collection.get(cardAtPos);
	}
	
	public String getName(){
		return name;
	}
	
	public String getCreateDate(){
		return dateFormat.format(createDate);
	}
	

	public int size(){
		return collection.size();

	}
	

}
