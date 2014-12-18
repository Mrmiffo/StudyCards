package studyCards;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FlashController implements IController{
	private ArrayList<CardCollection> collectionList;
	private CardCollection activeCollection;
	private Card activeCard;
	private int activeCardNumber;
	private boolean isShowingQuestion;
	
	private PropertyChangeSupport action = new PropertyChangeSupport(this);
	
	private MouseEvent mouseEvent;
		
	public FlashController(){
		collectionList = Utilities.loadCollectionList();
		activeCollection = collectionList.get(0);
	}
	
	/* (non-Javadoc)
	 * @see studyCards.IController#getCollectionList()
	 */
	@Override
	public String[] getCollectionList(){
		String[] tempArray = new String[collectionList.size()];
		for (int i = 0; i < collectionList.size();i++){
			tempArray[i] = collectionList.get(i).getName();
		}
		return tempArray;
	}
	
	/* (non-Javadoc)
	 * @see studyCards.IController#addCollection(java.lang.String)
	 */
	@Override
	public void addCollection(String name) throws FileNotFoundException, CardNotFoundException{
		collectionList.add(0,new CardCollection(name));
		try {
			Utilities.saveCollectionList(collectionList);
			activeCollection = collectionList.get(findCollection(name));
		} catch (FileNotFoundException e){
			collectionList.remove(findCollection(name));
			throw e;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see studyCards.IController#addCard(java.lang.String, java.lang.String)
	 */
	@Override
	public void addCard(String question, String answer) throws FileNotFoundException, CardNotFoundException{
		activeCollection.addCard(new Card(question,answer));
		Utilities.saveCollectionList(collectionList);
	}
	
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

			} else if (activeCollection.size() == 0){
				throw new NullPointerException("There are no card in the collection");
			}

			
		}
	}
	
	/**
	 * Sets the next card in the collection as the active card.
	 * @throws CardNotFoundException 
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

	/* (non-Javadoc)
	 * @see studyCards.IController#getNextCardText()
	 */
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


	public void gameUpdate(){
		action.firePropertyChange("NextTextEvent", null, null);

	}
	
	public void addObserver(PropertyChangeListener observer){
		action.addPropertyChangeListener(observer);
	}
	
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
