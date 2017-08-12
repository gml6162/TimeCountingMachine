package timeCountingMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserDataInputFrame extends JFrame{
	private JPanel userDataInputPanel = new JPanel(); 
	private JButton addPersonButton = new JButton("�Է�");
	private JButton chkButton = new JButton("�Ϸ�");

	private JTextField nameField = new JTextField(10);
	
	public UserDataInputFrame(){
		this.setVisible(false);
		this.setBounds(700, 200, 300, 300);
		initalize();
		this.setVisible(true);
	}
	
	public void initalize(){
		
		userDataInputPanel.add(nameField);
		userDataInputPanel.add(addPersonButton);
		userDataInputPanel.add(chkButton);
		
		addPersonButton.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					if(nameField.getText().length()==0)
					{
						return;
					}
					Main.userData.put(nameField.getText(), 0l);
					nameField.setText("");
				}
			}
		);
		chkButton.addActionListener(new ActionListener() {
		
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println(Main.userData.size());
					dispose();
					ShowFrame showFrame = new ShowFrame();
					ControlFrame ctrlFrame = new ControlFrame();
				}
			}
		);
		this.add(userDataInputPanel);
		
	}
}
