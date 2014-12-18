package studyCards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;



import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * The flashView is the user interface. The viewer will call for information from the controller depening on which buttons are pressed in the UI.
 * @author Anton
 *
 */
public class FlashView extends JPanel implements PropertyChangeListener{
	
	private IController controller;
	
	//Menu panel
	private JPanel buttonPanel = new JPanel();
	private JButton newCardButton = new JButton("Add Card");
	private JButton removeCardButton = new JButton("Remove card");
	private JButton newCollectionButton = new JButton("Add collection");
	private JButton removeCollectionButton = new JButton("Remove collection");
	private JComboBox<String> selectCollectionPane;
	
	//Flash card panel
	private JPanel flashPanel = new JPanel();
	private JLabel qaLabel = new JLabel();
	

	

	/**
	 * Constructor which will setup the controller, the flashpanel and the buttons panel.
	 * @param controller the controller to be used to drive the program.
	 */
	public FlashView(IController controller){
		this.controller = controller;
		selectCollectionPane = new JComboBox(this.controller.getCollectionList());
		
		controller.addObserver(this);
		setupButtonPanel();
		setupFlashPanel();
		setupListeners();
		
		
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.SOUTH);
		add(flashPanel, BorderLayout.CENTER);
		
	}

	/**
	 * A method to setup all the listeners, this is only used by the constructor once.
	 */
	private void setupListeners(){
		//Sets the controller as mouse listener so that it will recognize when user press a button. The view will be alerted of the action through the property change.
		addMouseListener(controller);
		
		// setup the new card button. Once pressed this button will call for a pop-up window where user will input card details. To let the panel know that the user has pressed Add or cancel button in the new pop-up a timer will check every 50ms if the "hasGivenAnswer" has been set to true.
		newCardButton.addActionListener( new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e) {
				final TwoTextBoxPopUp tempPopUp = FlashFrameFactory.twoTextBoxPopUp("New Card", "Question: ", "", "Answer: ", "");
				final Timer tempTimer = new Timer(50,null);
				tempTimer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//Timer checks every 50ms if user has pressed add or cancel button and will then take the propper action.
						if (tempPopUp.getHasGivenAnswer() && !tempPopUp.getText1().equals("") && !tempPopUp.getText2().equals("")){
							try {
								//If data has been put in the view will ask the controller to add the card. The controller will create and save the card, and throw exceptions if there are any issues when saving the file.
								controller.addCard(tempPopUp.getText1(), tempPopUp.getText2());
							} catch (FileNotFoundException e2) {
								FlashFrameFactory.errorPopUp("File not found", "There was an error saving the collection(s).", e2);
							} catch (CardNotFoundException e2) {
								FlashFrameFactory.errorPopUp("Card not found", "There was an error saving the collection(s).", e2);
							} finally {
								tempTimer.stop();
							}
						} else if (tempPopUp.getHasGivenAnswer()){
							//This piece of code is causing issues as the cancel button and pressing Add when the text is empty will look the same. Will need a review.
							FlashFrameFactory.errorPopUp("Invalid question or answer", "Question and answer may not be empty.", null);
							tempTimer.stop();
						}
					}
				});
				//Timer repeats until user has taken an action.
				tempTimer.setRepeats(true);
				tempTimer.start();
			}
		});
		
		//The remove card button works much like the add card button, with the exception that it does not look for a text, but for a boolean in getAnswer (Yes or No)
		removeCardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				final VerificationPopUp tempPopUp = FlashFrameFactory.verificationPopUp("Remove card", "Are you sure you want to remove this card?");
				final Timer tempTimer = new Timer(50, null);
				tempTimer.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if (tempPopUp.getHasGivenAnswer() && tempPopUp.getAnswer()){
							try {
								//Only remove card if user has pressed and the button pressed caused the answer to be true (Yes)
								controller.removeCard();
							} catch (FileNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Error save collection", "There was an error while trying to save the collection.", e1);
							} catch (CardNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Card not found", "No card was removed as the card was not found.", e1);
							}
							tempTimer.stop();
						} else if (tempPopUp.getHasGivenAnswer()){
							
							tempTimer.stop();
						}
					}
						
				});
				tempTimer.setRepeats(true);
				tempTimer.start();

			}
		});
		
		//Adds a new collection, currently not working since it was moved to a new pop-up. Will need a revisit. Works much like the add/remove card methods, only it will also update the dropdown menu to reflect the newly created collection and set it as the active one.
		newCollectionButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				final OneTextBoxPopUp tempPopUp = FlashFrameFactory.oneTextBoxPopUp("Enter collection details", "Collection name: ", "");
				final Timer tempTimer = new Timer(50,null);
				tempTimer.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if (tempPopUp.getHasGivenAnswer() && !tempPopUp.getText().equals("")){
							try {
								//Adds a new collection, sets it to active and adds it to the select pane. 
								controller.addCollection(tempPopUp.getText());
								controller.setActiveCollection(tempPopUp.getText());
								selectCollectionPane.addItem(tempPopUp.getText());
								selectCollectionPane.setSelectedItem(tempPopUp.getText());
							} catch (FileNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Incorrect file name error", "Incorrect file name. Please only use letters and numbers.", null);
							} catch (CardNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Card not found exception", "Something went wrong when trying to save the cards", e1);
							} finally {
								tempTimer.stop();
							}
							//This is most likely buggey if user press cancel...
						} else if (tempPopUp.getHasGivenAnswer()){
							FlashFrameFactory.errorPopUp("Invalid Collection name", "Collection name may not be empty", null);
							tempTimer.stop();
							
						}
						
					}
				});

			}
		});
		//If a change is mad in the select collection pane, it will call for the controller to change the active collection.
		selectCollectionPane.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				controller.setActiveCollection((String)selectCollectionPane.getSelectedItem());
			}
		});
	}

	//Sets all the necessary bits and pieces for the flash panel, such as the text to be show upon start. Only used once by the constructor.
	private void setupFlashPanel() {
		qaLabel.setText("<html>Welcome to FlashCards! <br> Click here to show the first question!</html>");
		qaLabel.setFont(Resources.getQafont());
		flashPanel.setLayout(new GridBagLayout());
		flashPanel.add(qaLabel);
		flashPanel.setBackground(Color.white);
		flashPanel.setBorder(BorderFactory.createLineBorder(Color.black, 10));
		
	}


	//Sets up all the buttons in the panel, only used once by the constructor.
	private void setupButtonPanel() {
		buttonPanel.setBackground(Color.GRAY);
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(newCardButton);
		buttonPanel.add(removeCardButton);
		buttonPanel.add(newCollectionButton);
		buttonPanel.add(selectCollectionPane);
	}


	//This action is called upon by the FlashController through the fire property change in the gameUpdate.
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName() == "NextTextEvent"){
			try {
				qaLabel.setText(controller.getCardText());
			} catch (NullPointerException e){
				//Currently not working as the addCardPopUp was moved to a new class through the FlashFrameFactory
//				addCardPopUp();
			} catch (CardNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
