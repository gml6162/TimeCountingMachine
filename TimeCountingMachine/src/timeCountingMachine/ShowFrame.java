package timeCountingMachine;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class ShowFrame extends JFrame {
	private JPanel totalDrivingTimerPanel = new JPanel(new GridLayout(1, 1));
	private JPanel lapTimerPanel = new JPanel();
	private JPanel personPanel = new JPanel();
	private JPanel currentNamePanel = new JPanel(new GridLayout(4,1));
	private JPanel recordPanel = new JPanel(new GridLayout(3,1));
	private JTable rankTable = new JTable();


	private JLabel nameLabel = new JLabel();
	private JLabel robotNameLabel = new JLabel();
	private JLabel schoolLabel = new JLabel();
	private JLabel fieldLabel = new JLabel();
	private JLabel record1Label = new JLabel();
	private JLabel record2Label = new JLabel();
	private JLabel record3Label = new JLabel();
	
	private Long record1 = (long) 300000;
	private Long record2 = (long) 300000;
	private Long record3 = (long) 300000;
	
	
	private JLabel totalDrivingTimerLabel = new JLabel("05:00:00");
	private JLabel lapTimerLabel = new JLabel("00:00:00");

	// layout
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	public void setTotalDrivingTimerLabel(String str1) {
		totalDrivingTimerLabel.setText(str1);
	}

	public void setLapTimerLabel(String str2) {
		lapTimerLabel.setText(str2);
	}
	
	public void setRecordLabel(long record) {
		int record1TimeMinute, record1TimeSecond, record1TimeMillisec, record2TimeMinute, record2TimeSecond, record2TimeMillisec, record3TimeMinute, record3TimeSecond, record3TimeMillisec;
		
		if(record < record1) {
			record3 = record2;
			record2 = record1;			
			record1 = record;

		}
		else if(record < record2) {
			record3 = record2;
			record2 = record;
		} 
		else if (record < record3) {
			record3 = record;
		}
		
		
		record1TimeMinute = (int) (record1 / 60000);
		record1TimeSecond = (int) ((record1 / 1000) % 60);
		record1TimeMillisec = (int) ((record1 / 10) % 100);
		record1Label.setText(String.format("%02d:%02d:%02d", record1TimeMinute, record1TimeSecond, record1TimeMillisec));

		record2TimeMinute = (int) (record2 / 60000);
		record2TimeSecond = (int) ((record2 / 1000) % 60);
		record2TimeMillisec = (int) ((record2 / 10) % 100);
		record2Label.setText(String.format("%02d:%02d:%02d", record2TimeMinute, record2TimeSecond, record2TimeMillisec));
		
		record3TimeMinute = (int) (record3 / 60000);
		record3TimeSecond = (int) ((record3 / 1000) % 60);
		record3TimeMillisec = (int) ((record3 / 10) % 100);
		record3Label.setText(String.format("%02d:%02d:%02d", record3TimeMinute, record3TimeSecond, record3TimeMillisec));
		
	}

	public void setcurrentNamePanel(String key){
		nameLabel.setText("이름 : "+key);
		robotNameLabel.setText("로봇 이름 : " + FileManager.userData.get(key)[0].toString());
		schoolLabel.setText("학교 : " + FileManager.userData.get(key)[1].toString());
		fieldLabel.setText("분야 : "+ FileManager.userData.get(key)[2].toString());
	}
	
	private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c, int gridx, int gridy, int gridwidth,
			int gridheight, int weightx, int weighty) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(c, gbc);
		this.add(c);
	}

	public ShowFrame() {
		super("Show");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 200, 300, 300);
		this.initalize();
	}
	


	private void initalize() {
		this.setVisible(false);
		// layout
		gbc.fill = GridBagConstraints.BOTH;
		this.setLayout(gbl);

		
		
		
		// gridboxlayout
		addGrid(gbl, gbc, totalDrivingTimerPanel, 0, 0, 2, 1, 3, 1);
		addGrid(gbl, gbc, lapTimerPanel, 0, 1, 2, 1, 3, 1);
		addGrid(gbl, gbc, rankTable, 3, 0, 1, 3, 1, 1);
		addGrid(gbl, gbc, currentNamePanel, 0, 2, 1, 1, 1, 1);
		addGrid(gbl, gbc, recordPanel, 1, 2, 1, 1, 2, 1);
		// mainPanel.add(totalDrivingTimerPanel);
		// mainPanel.add(lapTimerPanel);
		// mainPanel.add(personPanel);
		//setcurrentNamePanel("abc");

		totalDrivingTimerPanel.add(totalDrivingTimerLabel);
		lapTimerPanel.add(lapTimerLabel);
		
		currentNamePanel.add(nameLabel);
		currentNamePanel.add(robotNameLabel);
		currentNamePanel.add(schoolLabel);
		currentNamePanel.add(fieldLabel);
		
		recordPanel.add(record1Label);
		recordPanel.add(record2Label);
		recordPanel.add(record3Label);

		// Property
		totalDrivingTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lapTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// this.add(mainPanel);
		this.setVisible(true);
	}

}
