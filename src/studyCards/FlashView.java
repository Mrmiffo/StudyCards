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
	
	//Add card/collection Frame
	private JFrame popUpFrame;
	private JPanel popUpPanel;
	private JPanel popUpButtonPanel;
	private JPanel popUpLabelPanel;
	private JPanel popUpTextPanel;
	private JLabel label1 = new JLabel();
	private JLabel label2 = new JLabel();
	private JTextField text1 = new JTextField();
	private JTextField text2 = new JTextField();
	private JButton popUpAddCardButton = new JButton("Add");
	private JButton popUpAddCollectionButton = new JButton("Add");
	private JButton popUpCancelButton = new JButton("Cancel");
	

	
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
				addCardPopUp();	
			}
		});
		
		removeCardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				final VerificationPopUp vPopUp = FlashFrameFactory.verificationPopUp("Remove card", "Are you sure you want to remove this card?");
				final Timer tempTimer = new Timer(50, null);
				tempTimer.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if (vPopUp.getHasGivenAnswer() && vPopUp.getAnswer()){
							try {
								controller.removeCard();
							} catch (FileNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Error save collection", "There was an error while trying to save the collection.", e1);
							} catch (CardNotFoundException e1) {
								FlashFrameFactory.errorPopUp("Card not found", "No card was removed as the card was not found.", e1);
							}
							tempTimer.stop();
						} else if (vPopUp.getHasGivenAnswer()){
							
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
			public void actionPerformed(ActionEvent arg0) {
				addCollectionPopUp();
			}
		});
		
		popUpAddCardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!text1.getText().equals("") && !text2.getText().equals("")){
					try {
						controller.addCard(text1.getText(), text2.getText());
						text1.setText("");
						text2.setText("");
						popUpFrame.setVisible(false);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (CardNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		
		popUpCancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text1.setText("");
				text2.setText("");
				popUpFrame.setVisible(false);
			}
		});
		
		popUpAddCollectionButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (!text1.getText().equals("")){
					try {
						controller.addCollection(text1.getText());
						selectCollectionPane.addItem(text1.getText());
						selectCollectionPane.setSelectedItem(text1.getText());
						text1.setText("");
						popUpFrame.setVisible(false);
						addCardPopUp();
					} catch (FileNotFoundException e1) {
						text1.setText("");
						FlashFrameFactory.errorPopUp("Incorrect file name error", "Incorrect file name. Please only use letters and numbers.", null);
					} catch (CardNotFoundException e1) {
						FlashFrameFactory.errorPopUp("Card not found exception", "Something went wrong when trying to save the cards", e1);
					}

				}
			}
		});
		
		selectCollectionPane.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				controller.setActiveCollection((String)selectCollectionPane.getSelectedItem());
			}
		});
	}

	private void addCardPopUp() {
		popUpFrame = new JFrame("Enter card details");
		popUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popUpPanel = new JPanel();
		popUpLabelPanel = new JPanel();
		popUpTextPanel = new JPanel();
		popUpButtonPanel = new JPanel();
		
		label1.setText("Question: ");
		label2.setText("Answer: ");
		text1.setText("");
		text2.setText("");
		label1.setFont(Resources.getLabelfont());	
		label2.setFont(Resources.getLabelfont());
		text1.setFont(Resources.getLabelfont());
		text2.setFont(Resources.getLabelfont());
		
		popUpLabelPanel.setLayout(new GridLayout(2,1));
		popUpLabelPanel.add(label1);
		popUpLabelPanel.add(label2);

		popUpTextPanel.setLayout(new GridLayout(2,1));
		popUpTextPanel.add(text1);
		popUpTextPanel.add(text2);
		
		popUpPanel.setLayout(new BorderLayout());
		
		popUpPanel.add(popUpLabelPanel, BorderLayout.WEST);
		popUpPanel.add(popUpTextPanel,BorderLayout.CENTER);
		
		popUpButtonPanel.setLayout(new FlowLayout());
		popUpButtonPanel.add(popUpAddCardButton);
		popUpButtonPanel.add(popUpCancelButton);
		
		popUpFrame.setLayout(new BorderLayout());
		popUpFrame.setSize(800,200);
		popUpFrame.add(popUpPanel, BorderLayout.CENTER);
		popUpFrame.add(popUpButtonPanel, BorderLayout.SOUTH);
		popUpFrame.setVisible(true);
		
	}
	
	private void addCollectionPopUp() {
		popUpFrame = new JFrame("Enter collection details");
		popUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popUpPanel = new JPanel();
		popUpLabelPanel = new JPanel();
		popUpTextPanel = new JPanel();
		popUpButtonPanel = new JPanel();
		
		label1.setText("Collection name: ");
		text1.setText("");
		label1.setFont(Resources.getLabelfont());	
		text1.setFont(Resources.getLabelfont());
		
		popUpLabelPanel = new JPanel();
		popUpLabelPanel.setLayout(new GridLayout(2,1));
		popUpLabelPanel.add(label1);

		popUpTextPanel.setLayout(new GridLayout(2,1));
		popUpTextPanel.add(text1);
		
		popUpPanel.setLayout(new BorderLayout());
		
		popUpPanel.add(popUpLabelPanel, BorderLayout.WEST);
		popUpPanel.add(popUpTextPanel,BorderLayout.CENTER);
		
		popUpButtonPanel.setLayout(new FlowLayout());
		popUpButtonPanel.add(popUpAddCollectionButton);
		popUpButtonPanel.add(popUpCancelButton);
		
		popUpFrame.setLayout(new BorderLayout());
		popUpFrame.setSize(800,200);
		popUpFrame.add(popUpPanel, BorderLayout.CENTER);
		popUpFrame.add(popUpButtonPanel, BorderLayout.SOUTH);
		popUpFrame.setVisible(true);
		
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
				addCardPopUp();
			} catch (CardNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
