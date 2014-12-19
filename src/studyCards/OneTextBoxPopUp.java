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
 * OnTextBoxPopUp is a simple generic pop-up which can be modified by the caller to provided the expected text.
 * @author Anton
 *
 */
public class OneTextBoxPopUp {
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private JPanel textPanel;
	private JLabel label = new JLabel();
	private JTextField text = new JTextField();
	//May need to rename this button and make sure caller specify the button name.
	private JButton addButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");
	private boolean hasGivenAnswer;

	public OneTextBoxPopUp(String frameName, String boxName, String boxText){

		frame = new JFrame(frameName);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainPanel = new JPanel();
		labelPanel = new JPanel();
		textPanel = new JPanel();
		buttonPanel = new JPanel();
		
		label.setText(boxName);
		text.setText(boxText);
		label.setFont(Resources.getLabelfont());	
		text.setFont(Resources.getLabelfont());
		
		setupActionListeners();
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(2,1));
		labelPanel.add(label);

		textPanel.setLayout(new GridLayout(2,1));
		textPanel.add(text);
		
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
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hasGivenAnswer = true;
				frame.setVisible(false);				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			//If user press cancel the text will be set to "". Might have to be replaced with a cancel boolean
			@Override
			public void actionPerformed(ActionEvent e) {
				hasGivenAnswer = true;
				text.setText("");
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
	 * Returns the text in the input box.
	 * @return
	 */
	public String getText(){
		return text.getText();
	}
}
