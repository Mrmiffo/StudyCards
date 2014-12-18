package studyCards;

import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

public interface IController extends MouseListener {

	public abstract String[] getCollectionList();

	/**
	 * Adds a collection.
	 * @param name the name of the new Collection.
	 * @throws FileNotFoundException 
	 * @throws CardNotFoundException 
	 */
	public abstract void addCollection(String name) throws FileNotFoundException, CardNotFoundException;

	/**
	 * Adds a new card to the collection with a question and an answer.
	 * @param question
	 * @param answer
	 * @throws FileNotFoundException 
	 * @throws CardNotFoundException 
	 */
	public abstract void addCard(String question, String answer) throws FileNotFoundException, CardNotFoundException;

	/**
	 * Returns the answer if a question is showing or the question of a random card (which is not the same card as the last, unless there is only 1 card in the collection).
	 * @return String an HTML formated String to be used in JLabels.
	 * @throws CardNotFoundException 
	 * @throws NullPointerException 
	 */
	public abstract String getCardText() throws NullPointerException, CardNotFoundException;
	/**
	 * Sets the next card in the collection as the active card.
	 */
	
	public abstract void setActiveCollection(String collectionName);
	
	public void addObserver(PropertyChangeListener observer);
	public void removeObserver(PropertyChangeListener observer);

	public abstract void removeCard() throws CardNotFoundException, FileNotFoundException;
}