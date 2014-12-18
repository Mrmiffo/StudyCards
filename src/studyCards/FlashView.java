package studyCards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;



import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

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

	private void setupListeners(){
		addMouseListener(controller);
		
		newCardButton.addActionListener( new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e) {
				final TwoTextBoxPopUp tempPopUp = FlashFrameFactory.twoTextBoxPopUp("New Card", "Question: ", "", "Answer: ", "");
				final Timer tempTimer = new Timer(50,null);
				tempTimer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (tempPopUp.getHasGivenAnswer() && !tempPopUp.getText1().equals("") && !tempPopUp.getText2().equals("")){
							try {
								controller.addCard(tempPopUp.getText1(), tempPopUp.getText2());
							} catch (FileNotFoundException e2) {
								FlashFrameFactory.errorPopUp("File not found", "There was an error saving the collection(s).", e2);
							} catch (CardNotFoundException e2) {
								FlashFrameFactory.errorPopUp("Card not found", "There was an error saving the collection(s).", e2);
							} finally {
								tempTimer.stop();
							}
						} else if (tempPopUp.getHasGivenAnswer()){
							FlashFrameFactory.errorPopUp("Invalid question or answer", "Question and answer may not be empty.", null);
							tempTimer.stop();
						}
					}
				});
				tempTimer.setRepeats(true);
				tempTimer.start();
			}
		});
		
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
								controller.addCollection(tempPopUp.getText());
								selectCollectionPane.addItem(tempPopUp.getText());
								selectCollectionPane.setSelectedItem(tempPopUp.getText());
							} catch (FileNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Incorrect file name error", "Incorrect file name. Please only use letters and numbers.", null);
							} catch (CardNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Card not found exception", "Something went wrong when trying to save the cards", e1);
							} finally {
								tempTimer.stop();
							}
		
						} else if (tempPopUp.getHasGivenAnswer()){
							FlashFrameFactory.errorPopUp("Invalid Collection name", "Collection name may not be empty", null);
							tempTimer.stop();
							
						}
						
					}
				});

			}
		});
		
		selectCollectionPane.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				controller.setActiveCollection((String)selectCollectionPane.getSelectedItem());
			}
		});
	}

	
	



	private void setupFlashPanel() {
		qaLabel.setText("<html>Welcome to FlashCards! <br> Click here to show the first question!</html>");
		qaLabel.setFont(Resources.getQafont());
		flashPanel.setLayout(new GridBagLayout());
		flashPanel.add(qaLabel);
		flashPanel.setBackground(Color.white);
		flashPanel.setBorder(BorderFactory.createLineBorder(Color.black, 10));
		
	}


	private void setupButtonPanel() {
		buttonPanel.setBackground(Color.GRAY);
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(newCardButton);
		buttonPanel.add(removeCardButton);
		buttonPanel.add(newCollectionButton);
		buttonPanel.add(selectCollectionPane);
	}


	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName() == "NextTextEvent"){
			try {
				qaLabel.setText(controller.getCardText());
			} catch (NullPointerException e){
//				addCardPopUp();
			} catch (CardNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
