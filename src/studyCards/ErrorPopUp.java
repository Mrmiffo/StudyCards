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
/**
 * The error pop-up is a simple pop-up which will display a provided message and, if provided, the details of an error message.
 * @author Anton
 *
 */
public class ErrorPopUp {
	private JFrame frame = new JFrame();
	private JPanel labelPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel errorPanel = new JPanel();
	private JButton okButton = new JButton("Ok");
	private JLabel messageText = new JLabel();


	public ErrorPopUp(String name, String message, Exception e){
		frame.setTitle(name);
		messageText.setText(Utilities.stringToRBString(message));
		messageText.setFont(Resources.getLabelfont());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		
		frame.setLayout(new BorderLayout());
		labelPanel.setLayout(new GridBagLayout());
		buttonPanel.setLayout(new FlowLayout());
		
		buttonPanel.add(okButton);
		labelPanel.add(messageText);
		//If no exception is provided the error panel will be empty.
		if (!(e == null)){
			errorPanel.add(new JLabel(e.toString()));
		}
		frame.add(labelPanel, BorderLayout.NORTH);
		frame.add(errorPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.setSize(Resources.getPopUpDimension());
		frame.setLocation(Utilities.getFrameAtCenterOfScreen(frame.getWidth(), frame.getHeight()));
		frame.setVisible(true);
	}
}
