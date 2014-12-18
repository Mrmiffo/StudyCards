package studyCards;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 * The flash controller is the heart of the program. This controller will take resolve any requests that the viewer has. Any exceptions encountered by the controller should be passed forward to the UI for user action.
 * @author Anton
 *
 */
public class FlashController implements IController{
	//The controller contain a list of all the collections, loaded from the All collections file.
	private ArrayList<CardCollection> collectionList;
	//The active collection is the collections which cards are drawn from.
	private CardCollection activeCollection;
	//The active card is the card from which Q/A is read.
	private Card activeCard;
	//The active card number is used by the nextCard method to see which is the next card in line if user would like to cycle through cards
	private int activeCardNumber;
	//A boolean used to keep track on what is currently showing to the user in the UI.
	private boolean isShowingQuestion;
	
	//The property change support is used to fire property changes to the viewer. 
	//The current (2014-12-18) version sends a property change each time the mouse key is klicked, this will cause the viewer to request a new text from the controller.
	private PropertyChangeSupport action = new PropertyChangeSupport(this);
	
	private MouseEvent mouseEvent;
		
	//Constructor will just load all collections and set the first collection as the active one.
	public FlashController(){
		collectionList = Utilities.loadCollectionList();
		activeCollection = collectionList.get(0);
	}
	

	@Override
	public String[] getCollectionList(){
		String[] tempArray = new String[collectionList.size()];
		for (int i = 0; i < collectionList.size();i++){
			tempArray[i] = collectionList.get(i).getName();
		}
		return tempArray;
	}
	

	//Adds a collection and saves it to file.
	@Override
	public void addCollection(String name) throws FileNotFoundException, CardNotFoundException{
		collectionList.add(0,new CardCollection(name));
		try {
			Utilities.saveCollectionList(collectionList);
		} catch (FileNotFoundException e){
			collectionList.remove(findCollection(name));
			throw e;
		}
		
	}
	

	//Add a card to the active collection and save it to file.
	@Override
	public void addCard(String question, String answer) throws FileNotFoundException, CardNotFoundException{
		activeCollection.addCard(new Card(question,answer));
		Utilities.saveCollectionList(collectionList);
	}
	
	//Sets the activeCard to a random new card which is not the same card.
	private void randomNextCard() throws CardNotFoundException, NullPointerException{
		boolean newCard = false;
		Card tempCard;
		int nextCardNumber;
		//Only try to get a new card if there are more than 1 card in the collection.
		while (!newCard){
			if (activeCollection.size() > 1){
				try {
					nextCardNumber = Utilities.randomInt(0, activeCollection.size());
					tempCard = activeCollection.getCard(nextCardNumber);
					if (!tempCard.equals(activeCard)){
						newCard = true;
						activeCardNumber = nextCardNumber;
						activeCard = tempCard;
					}
				} catch (CardNotFoundException e) {
					System.out.println(e);
					//This should not happen as the random generator checks for the size of the collection.
				}
			} else if (activeCollection.size() == 1){
					activeCard = activeCollection.getCard(0);
					newCard = true;
			//If there are no cards in the collection, send an error to the User interface.
			} else if (activeCollection.size() == 0){
				throw new NullPointerException("There are no card in the collection");
			}

			
		}
	}
	
	/**
	 * Sets the next card in the collection as the active card.
	 * @throws CardNotFoundException if there are no cards in the collection.
	 */
	private void nextCard() throws CardNotFoundException{
		if (isShowingQuestion){
			
		} else if (mouseEvent.getButton() == mouseEvent.BUTTON1 && mouseEvent.isShiftDown()){
			activeCard = activeCollection.getCard((activeCardNumber+1)%activeCollection.size());
			activeCardNumber = (activeCardNumber+ 1)%activeCollection.size();
		} else if (mouseEvent.getButton() == mouseEvent.BUTTON1){
			randomNextCard();
		}
		

	}

	@Override
	public String getCardText() throws NullPointerException, CardNotFoundException{
		if (isShowingQuestion){
			isShowingQuestion = false;
			return Utilities.stringToRBString(activeCard.getAnswer());
		}
		nextCard();
		isShowingQuestion = true;
		return Utilities.stringToRBString(activeCard.getQuestion());
	}

	
	private void gameUpdate(){
		action.firePropertyChange("NextTextEvent", null, null);

	}
	
	@Override
	public void addObserver(PropertyChangeListener observer){
		action.addPropertyChangeListener(observer);
	}
	
	@Override
	public void removeObserver(PropertyChangeListener observer){
		action.removePropertyChangeListener(observer);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		mouseEvent = arg0;
		gameUpdate();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		
	}

	@Override
	public void setActiveCollection(String collectionName) {
		activeCollection = collectionList.get(findCollection(collectionName));
		isShowingQuestion = false;
		
	}
	//This method searches through the collection list to find the specified collection and return the number it is on in the collection. Used to find which collection is chosen by the select collection drop down.
	private int findCollection(String collectionName) {
		for (int i = 0; i < collectionList.size();i++){
			if (collectionList.get(i).getName().equals(collectionName)) return i;
		}
		return 0;
	}

	@Override
	public void removeCard() throws CardNotFoundException, FileNotFoundException {
		activeCollection.removeCard(activeCardNumber);
		Utilities.saveCollectionList(collectionList);
	}

	
}
