package timeCountingMachine;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ShowFrame extends JFrame {
	
	private JPanel totalDrivingTimerPanel = new JPanel(new GridLayout(1, 1));
	private JPanel lapTimerPanel = new JPanel();
	private JPanel personPanel = new JPanel();
	private JPanel currentNamePanel = new JPanel(new GridLayout(4,1));
	private JPanel recordPanel = new JPanel(new GridLayout(3,1));

	private JLabel nameLabel = new JLabel();
	private JLabel robotNameLabel = new JLabel();
	private JLabel schoolLabel = new JLabel();
	private JLabel fieldLabel = new JLabel();
	private JLabel record0Label = new JLabel();
	private JLabel record1Label = new JLabel();
	private JLabel record2Label = new JLabel();
	
	private String currentUser = "";
	private String[] rankColumnNames = {"Names", "Time"};
	
	private Long record0 = (long) 300000;
	private Long record1 = (long) 300000;
	private Long record2 = (long) 300000;
	private Long record3 = (long) 300000; // record4 is invisible
	private int recentRecordNum = 0; // This is to delete and backup record

	private JLabel totalDrivingTimerLabel = new JLabel("05:00:00");
	private JLabel lapTimerLabel = new JLabel("00:00:00");
	
	private DefaultTableModel model;
	private JTable rankTable;// = new JTable(model);
	// layout
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	public void setTotalDrivingTimerLabel(String str) {
		totalDrivingTimerLabel.setText(str);
	}

	public void setLapTimerLabel(String str) {
		lapTimerLabel.setText(str);
	}
	
	public void setRecordLabel(long record) {
		if (record < record0) {
			record3 = record2;
			record2 = record1;
			record1 = record0;			
			record0 = record;
			recentRecordNum = 0;
		}
		else if (record < record1) {
			record3 = record2;
			record2 = record1;
			record1 = record;
			recentRecordNum = 1;
		} 
		else if (record < record2) {
			record3 = record2;
			record2 = record;
			recentRecordNum = 2;
		}
		else if (record < record3) {
			record3 = record;
			recentRecordNum = 3;
		}
		else {
			recentRecordNum = 4;
		}
		
		record0Label.setText(longToString(record0));
		record1Label.setText(longToString(record1));
		record2Label.setText(longToString(record2));
		
		FileManager.setUserRecord(currentUser, record);
	}
	
	public void deleteRecentRecord() {
		if (recentRecordNum == 0) {
			record0 = record1;
			record1 = record2;
			record2 = record3;
			record3 = (long) 300000;
			FileManager.setUserRecord(currentUser, record0);
		}
		else if (recentRecordNum == 1) {
			record1 = record2;
			record2 = record3;
			record3 = (long) 300000;
		}
		else if (recentRecordNum == 2) {
			record2 = record3;
			record3 = (long) 300000;
		}
		else if (recentRecordNum == 3) {
			record3 = (long) 300000;
		}
		recentRecordNum = 4;
		
		record0Label.setText(longToString(record0));
		record1Label.setText(longToString(record1));
		record2Label.setText(longToString(record2));
	}
	
	public void setRecordLabel() {
		record0Label.setText(String.format("%02d:%02d:%02d", 5, 0, 0));
		record1Label.setText(String.format("%02d:%02d:%02d", 5, 0, 0));
		record2Label.setText(String.format("%02d:%02d:%02d", 5, 0, 0));
	}
	
	public void setcurrentNamePanel(String key){
		currentUser = key;
		nameLabel.setText("Name : "+key);
		robotNameLabel.setText("Robot Name : " + FileManager.userData.get(key)[0].toString());
		schoolLabel.setText("School : " + FileManager.userData.get(key)[1].toString());
		fieldLabel.setText("Field : "+ FileManager.userData.get(key)[2].toString());
	
		record0 = (long) 300000;
		record1 = (long) 300000;
		record2 = (long) 300000;
		record3 = (long) 300000;
		
		setRecordLabel();
	}
	
	public void setRankTable() {
		System.out.println("rank");
		int i = 0;
		String[][] stringArray = FileManager.getRankDataAsStringArray();
		
		model = new DefaultTableModel(stringArray, rankColumnNames)
		{
	        public boolean isCellEditable(int row, int column)
	        {
	        	return false;
	        }
		};
		
		for (String[] strings : stringArray)
		{
			model.setValueAt(strings[0], i, 0);
			model.setValueAt(strings[1], i, 1);
			i++;
		}
		model.fireTableDataChanged();
		if (rankTable != null)
      rankTable.setModel(model);
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
		
		// set rankTable
		setRankTable();
		rankTable = new JTable(model);
		rankTable.setFont(new Font("Sans-serif", Font.BOLD, 32));
		rankTable.setRowHeight(48);
		
		// gridboxlayout
		addGrid(gbl, gbc, totalDrivingTimerPanel, 0, 0, 2, 1, 3, 1);
		addGrid(gbl, gbc, lapTimerPanel, 0, 1, 2, 1, 3, 1);
		addGrid(gbl, gbc, rankTable, 3, 0, 1, 3, 1, 1);
		addGrid(gbl, gbc, currentNamePanel, 0, 2, 1, 1, 1, 1);
		addGrid(gbl, gbc, recordPanel, 1, 2, 1, 1, 2, 1);

		totalDrivingTimerPanel.add(totalDrivingTimerLabel);
		lapTimerPanel.add(lapTimerLabel);
		
		currentNamePanel.add(nameLabel);
		currentNamePanel.add(robotNameLabel);
		currentNamePanel.add(schoolLabel);
		currentNamePanel.add(fieldLabel);
		
		recordPanel.add(record0Label);
		recordPanel.add(record1Label);
		recordPanel.add(record2Label);
		
		// Property
		totalDrivingTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lapTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);

		this.setVisible(true);
	}
}