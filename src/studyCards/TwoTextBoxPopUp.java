package studyCards;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * The TwoTextBoxPopUp is a simple pop-up box with 2 input fields, an Add and a Cancel button. 
 * @author Anton
 *
 */
public class TwoTextBoxPopUp {
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private JPanel textPanel;
	private JLabel label1 = new JLabel();
	private JLabel label2 = new JLabel();
	private JTextField text1 = new JTextField();
	private JTextField text2 = new JTextField();
	//May need to rename this button and make sure caller specify the button name.
	private JButton addButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");
	private boolean hasGivenAnswer;
	
	public TwoTextBoxPopUp(String frameName, String boxName1, String boxText1, String boxName2, String boxText2){
			frame = new JFrame(frameName);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mainPanel = new JPanel();
			labelPanel = new JPanel();
			textPanel = new JPanel();
			buttonPanel = new JPanel();
			
			label1.setText(boxName1);
			label2.setText(boxName2);
			text1.setText(boxText1);
			text2.setText(boxText2);
			label1.setFont(Resources.getLabelfont());	
			label2.setFont(Resources.getLabelfont());
			text1.setFont(Resources.getLabelfont());
			text2.setFont(Resources.getLabelfont());
			
			setupActionListeners();
			
			labelPanel.setLayout(new GridLayout(2,1));
			labelPanel.add(label1);
			labelPanel.add(label2);

			textPanel.setLayout(new GridLayout(2,1));
			textPanel.add(text1);
			textPanel.add(text2);
			
			mainPanel.setLayout(new BorderLayout());
			
			mainPanel.add(labelPanel, BorderLayout.WEST);
			mainPanel.add(textPanel,BorderLayout.CENTER);
			
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.add(addButton);
			buttonPanel.add(cancelButton);
			
			frame.setLayout(new BorderLayout());
			frame.setSize(800,200);
			frame.add(mainPanel, BorderLayout.CENTER);
			frame.add(buttonPanel, BorderLayout.SOUTH);
			frame.setLocation(Utilities.getFrameAtCenterOfScreen(frame.getWidth(), frame.getHeight()));
			frame.setVisible(true);
			
		}

	private void setupActionListeners() {
		//When user press add the hasGivenAnswer will be set to true and then hide the frame.
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hasGivenAnswer = true;
				frame.setVisible(false);
				
			}
			
		});
		
		cancelButton.addActionListener(new ActionListener() {
			//If user press cancel the text boxes will be set as "" to remove any text given. Might have to be replaced by a cancel boolean.
			@Override
			public void actionPerformed(ActionEvent e) {
				hasGivenAnswer = true;
				text1.setText("");
				text2.setText("");
				frame.setVisible(false);
				
			}
		});
		
	}
	/**
	 * When user press any button this will be set to true, and the caller of the pop-up will know that user has given a response to be fetched.
	 * @return
	 */
	public boolean getHasGivenAnswer(){
		return hasGivenAnswer;
	}
	/**
	 * Returns the text in the first input box.
	 * @return
	 */
	public String getText1(){
		return text1.getText();
	}
	/**
	 * Returns the text in the second input box.
	 * @return
	 */
	public String getText2(){
		return text2.getText();
	}

}
