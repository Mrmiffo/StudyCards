package studyCards;

import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
/**
 * Interface for the controller. Look here for javadoc :)
 * @author Anton
 *
 */
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
	 * Returns the answer if a question is showing or the question of a random card (which is not the same card as the last, unless there is only 1 card in the collection). Will also save the collection, which cause the exceptions.
	 * @return String an HTML formated String to be used in JLabels.
	 * @throws CardNotFoundException is thrown if the card was not found.
	 * @throws NullPointerException 
	 */
	public abstract String getCardText() throws NullPointerException, CardNotFoundException;

	/**
	 * Sets the provided collection as the active collection. The active collection is the one which cards are drawn from.
	 * @param collectionName
	 */
	public abstract void setActiveCollection(String collectionName);
	
	/**
	 * Sets an observer. TYpically the Viewer (or user interface). The controller will fire events which will be sent to the observers.
	 * @param observer
	 */
	public void addObserver(PropertyChangeListener observer);
	
	/**
	 * Remove an observer so that it's no longer listening to the controller.
	 * @param observer
	 */
	public void removeObserver(PropertyChangeListener observer);

	/**
	 * Removes the active card from the collection and save the data to file. Save is causing the exceptions.
	 * @throws CardNotFoundException
	 * @throws FileNotFoundException
	 */
	public abstract void removeCard() throws CardNotFoundException, FileNotFoundException;
}