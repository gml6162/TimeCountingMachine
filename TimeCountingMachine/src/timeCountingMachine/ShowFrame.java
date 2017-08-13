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
	private JPanel currentNamePanel = new JPanel();
	private JPanel recordPanel = new JPanel();
	private JTable rankTable = new JTable();


	private JLabel nameLabel = new JLabel();
	private JLabel robotNameLabel = new JLabel();
	private JLabel schoolLabel = new JLabel();
	private JLabel fieldLabel = new JLabel();
	
	private static JLabel totalDrivingTimerLabel = new JLabel("05:00:00");
	private static JLabel lapTimerLabel = new JLabel("00:00:00");

	// layout
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	public static void setTotalDrivingTimerLabel(String totalDrivingTimerLabel) {
		ShowFrame.totalDrivingTimerLabel.setText(totalDrivingTimerLabel);
	}

	public static void setLapTimerLabel(String lapTimerLabel) {
		ShowFrame.lapTimerLabel.setText(lapTimerLabel);
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
	
	public void setcurrentNamePanel(String key){
		nameLabel.setText("이름 : "+key);
		robotNameLabel.setText("로봇 이름 : " + FileManager.userData.get(key)[0].toString());
		schoolLabel.setText("학교 : " + FileManager.userData.get(key)[1].toString());
		fieldLabel.setText("분야 : "+ FileManager.userData.get(key)[2].toString());
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
		setcurrentNamePanel("김건호");

		totalDrivingTimerPanel.add(totalDrivingTimerLabel);
		lapTimerPanel.add(lapTimerLabel);
		
		currentNamePanel.add(nameLabel);
		currentNamePanel.add(robotNameLabel);
		currentNamePanel.add(schoolLabel);
		currentNamePanel.add(fieldLabel);

		// Property
		totalDrivingTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lapTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// this.add(mainPanel);
		this.setVisible(true);
	}

}
