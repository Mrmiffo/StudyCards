package studyCards;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class VerificationPopUp {
	private JFrame frame = new JFrame();
	private JPanel messagePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JLabel messageText = new JLabel();
	private JButton yesButton = new JButton("Yes");
	private JButton noButton = new JButton("No");
	private boolean answer;
	private boolean hasGivenAnswer = false;

	
	public VerificationPopUp(String name, String message){
		frame.setTitle(name);
		messageText.setText(Utilities.stringToRBString(message));
		messageText.setFont(Resources.getLabelfont());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setActionListeners();

		messagePanel.setLayout(new GridBagLayout());
		messagePanel.add(messageText);
		
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);
		
		frame.setLayout(new BorderLayout());
		frame.add(messagePanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.setSize(Resources.getPopUpDimension());
		frame.setLocation(Utilities.getFrameAtCenterOfScreen(frame.getWidth(), frame.getHeight()));
		frame.setVisible(true);
		
	}

	private void setActionListeners() {
		yesButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				answer = true;
				hasGivenAnswer = true;
				frame.setVisible(false);
			}
			
		});
		
		noButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				answer = false;
				hasGivenAnswer = true;
				frame.setVisible(false);
			}
		});
		
	}
	public boolean getAnswer(){
		return answer;
	}
	
	public boolean getHasGivenAnswer(){
		return hasGivenAnswer;
	}

}
