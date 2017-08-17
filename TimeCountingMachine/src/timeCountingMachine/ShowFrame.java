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
	
	private String currentUser = "";
	private String[] rankColumnNames = {"Names", "Time"};
	private String[][] rankData = new String[12][2];
	
	
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
		
		if(record < record1) {
			record3 = record2;
			record2 = record1;			
			record1 = record;
		}
		else if (record < record2) {
			record3 = record2;
			record2 = record;
		} 
		else if (record < record3) {
			record3 = record;
		}
		
		record1Label.setText(longToString(record1));
		record2Label.setText(longToString(record2));
		record3Label.setText(longToString(record3));
		
		FileManager.setUserRecord(currentUser, record);
	}
	
	public void setRecordLabel() {
		record1Label.setText(String.format("%02d:%02d:%02d", 5, 0, 0));
		record2Label.setText(String.format("%02d:%02d:%02d", 5, 0, 0));
		record3Label.setText(String.format("%02d:%02d:%02d", 5, 0, 0));
		
	}
	
	public void setcurrentNamePanel(String key){
		currentUser = key;
		nameLabel.setText("이름 : "+key);
		robotNameLabel.setText("로봇 이름 : " + FileManager.userData.get(key)[0].toString());
		schoolLabel.setText("학교 : " + FileManager.userData.get(key)[1].toString());
		fieldLabel.setText("분야 : "+ FileManager.userData.get(key)[2].toString());
	
		record1 = (long) 300000;
		record2 = (long) 300000;
		record3 = (long) 300000;
		
		setRecordLabel();
	}
	
	public void setRankTable() {
		int i = 0;
		for(i = 0; i<12; i++) {
			rankData[i][0] = "";
			rankData[i][1] = "";
			
		}
		
		
		rankTable = new JTable(rankData, rankColumnNames);
		
	}
	
	public void setRankTable(long record) {
		int i = 0;
		System.out.println(record);
		for(i=0; i<12; i++) {
			if(rankData[i][0] == "") {
				rankData[i][0] = currentUser;
				rankData[i][1] = longToString(record);
				System.out.println(stringToLong(rankData[i][1]));
				System.out.println("null");
				break;
			}
			else {
				
				//rankData[i][0]
			}
		}
		System.out.println("setRank");
		rankTable = new JTable(rankData, rankColumnNames);
		rankTable.updateUI();
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

	private static String longToString(long time)
	{
		long ms = time % 1000;
		long se = (time / 1000) % 60;
		long mi = (time / (1000 * 60)) % 60;

		return String.format("%02d:%02d:%02d", mi, se, ms);
	}
	
	private long stringToLong(String time)
	{
		return Long.parseLong(time.substring(0, 2)) * 60000 + Long.parseLong(time.substring(3, 5)) * 1000 + Long.parseLong(time.substring(6, 9));
	}
	
	
	
	
	private void initalize() {
		this.setVisible(false);
		// layout
		gbc.fill = GridBagConstraints.BOTH;
		this.setLayout(gbl);

		setRankTable();
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