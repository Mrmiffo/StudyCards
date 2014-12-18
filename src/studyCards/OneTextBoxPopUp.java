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

public class OneTextBoxPopUp {
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private JPanel textPanel;
	private JLabel label = new JLabel();
	private JTextField text = new JTextField();
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hasGivenAnswer = true;
				text.setText("");
				frame.setVisible(false);				
			}
		});
	}

	public boolean getHasGivenAnswer(){
		return hasGivenAnswer;
	}
	
	public String getText(){
		return text.getText();
	}
}
