package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Info {
	
	final static int CI = 0;
	final static int EI = 1;
	final static int OI = 2;
	
	private Connection conn = Main.getConnection();
	private ArrayList<String> customers = new ArrayList<String>();
	private ArrayList<Integer> addresses = new ArrayList<Integer>();
	
	private ArrayList<String> employees = new ArrayList<String>();
	private ArrayList<String> jobTitles = new ArrayList<String>();
	private ArrayList<Double> salaries = new ArrayList<Double>();
	private ArrayList<Double> empl_avg_ratings = new ArrayList<Double>();
	

	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel generateInfo(int infoID) {
		String query = "SELECT * FROM customers";
		String query2 = "SELECT * FROM employees";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				customers.add(rs.getString("name"));
				addresses.add(rs.getInt("address"));
			}
			
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			while(rs2.next()) {
				employees.add(rs2.getString("name"));
				jobTitles.add(rs2.getString("job_title"));
				salaries.add(rs2.getDouble("salary"));
				empl_avg_ratings.add(rs2.getDouble("avg_rating"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*try {
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			while(rs2.next()) {
				employees.add(rs2.getString("name"));
				//prices.add(rs.getDouble("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		for(int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i)); 
		}
		
		
		
		//JPanel panel = new JPanel(new GridBagLayout());
		//Border border = BorderFactory.createEmptyBorder(20,20,20,20);
		//GridBagConstraints c = new GridBagConstraints();
		JPanel panel = new JPanel();
		
		if(infoID == CI) {
			panel.setLayout(new GridLayout(2,1));
			
			
			JComboBox custList = new JComboBox(customers.toArray());
			//custList.setSelectedIndex(0);
			panel.add(custList);
			
			JLabel addr = new JLabel("");
			panel.add(addr);
			custList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//for(int i = 0; i < addresses.size(); i++) {
					//	System.out.println(addresses.get(i)); 
					//}
					
					
					
					
					//addr.setBorder(border);
					addr.setFont(new Font("monospaced", Font.PLAIN, 20));
					//c.gridx = 0; c.gridy = 1;
					//panel.remove(addr);
					addr.setText("Address: Area "+ addresses.get(custList.getSelectedIndex()).toString());
					
					
				
				}
			});
		}
		
		if(infoID == EI) {
			panel.setLayout(new GridLayout(4,1));
			
			JComboBox emplList = new JComboBox(employees.toArray());
			panel.add(emplList);
			
			JLabel jtitle = new JLabel("");
			panel.add(jtitle);
			
			JLabel sal = new JLabel("");
			panel.add(sal);
			
			JLabel empl_av_rate = new JLabel("");
			panel.add(empl_av_rate);
			emplList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//for(int i = 0; i < addresses.size(); i++) {
					//	System.out.println(addresses.get(i)); 
					//}
					
					
					
					
					//addr.setBorder(border);
					jtitle.setFont(new Font("monospaced", Font.PLAIN, 20));
					//c.gridx = 0; c.gridy = 1;
					//panel.remove(addr);
					jtitle.setText("Job title: "+ jobTitles.get(emplList.getSelectedIndex()).toString());
					
					sal.setFont(new Font("monospaced", Font.PLAIN, 20));
					sal.setText("Salary: "+ salaries.get(emplList.getSelectedIndex()).toString());
					
					empl_av_rate.setFont(new Font("monospaced", Font.PLAIN, 20));
					empl_av_rate.setText("Average Rating: "+ empl_avg_ratings.get(emplList.getSelectedIndex()).toString());
					
					
				
				}
			});
		}
		
		return panel;
	}

}
