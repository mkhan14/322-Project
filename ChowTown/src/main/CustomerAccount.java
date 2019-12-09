package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomerAccount extends JFrame{
	private Connection conn = Main.getConnection();
	private GridBagConstraints c = new GridBagConstraints();
	
	final static int AREA0 = 0;
	final static int AREA1 = 1;
	final static int AREA2 = 2;
	final static String[] areas = {"AREA0", "AREA1", "AREA2"};

	public CustomerAccount() {
		User customer = Main.getUser();
		int addrsID = -1;
		String statPE = "";
		double ratingPE = 0;
		String statS = "";
		double ratingS = 0;
		String statMC = "";
		double ratingMC = 0;

		String query = "SELECT address, rest_id, avg_rating, status FROM customers JOIN customerratings WHERE id = " + customer.getId() 
				+ " AND cust_id = " + customer.getId() + ";";
		System.out.println(customer.getId());
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				addrsID = rs.getInt("address");
				if(rs.getInt("rest_id") == Menu.PE) {
					statPE = Restaurant.STATUS[rs.getInt("status")];
					ratingPE = rs.getDouble("avg_rating");
				}
				if(rs.getInt("rest_id") == Menu.S) {
					statS = Restaurant.STATUS[rs.getInt("status")];
					ratingS = rs.getDouble("avg_rating");
				}
				if(rs.getInt("rest_id") == Menu.MC) {
					statMC = Restaurant.STATUS[rs.getInt("status")];
					ratingMC = rs.getDouble("avg_rating");
				}
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JPanel panel = new JPanel(new GridBagLayout());
		JLabel name = new JLabel("Name: " + customer.getName());
		name.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel address = new JLabel("Address: "+ areas[addrsID]);
		address.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel statusPE = new JLabel("Status for Panda Express: "+statPE);
		statusPE.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel avgRatingPE = new JLabel("Current Rating for Panda Express: "+ratingPE);
		avgRatingPE.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel statusS = new JLabel("Status for Sakura: "+statS);
		statusS.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel avgRatingS = new JLabel("Current Rating for Sakura: "+ratingS);
		avgRatingS.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel statusMC = new JLabel("Status for Masala Cafe: "+statMC);
		statusMC.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel avgRatingMC = new JLabel("Current Rating for Masala Cafe: "+ratingMC);
		avgRatingMC.setFont(new Font("monospaced", Font.PLAIN, 20));
		
		JButton orderHistory = new JButton("Order History");
		orderHistory.setFont(new Font("monospaced", Font.PLAIN, 15));
		orderHistory.setPreferredSize(new Dimension(150, 40));
		orderHistory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//update account content
				//show all order histories, cancel button, rate button
			}
			
		});
		
		JButton logout = new JButton("Logout");
		logout.setFont(new Font("monospaced", Font.PLAIN, 15));
		logout.setPreferredSize(new Dimension(80, 40));
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.setUser(null);
				dispose();
			}
			
		});
		
		c.gridx = 0; c.gridy = 0; c.insets = new Insets(10,10,10,10);
		panel.add(name, c);
		c.gridy = 1;
		panel.add(address, c);
		c.gridy = 2;
		panel.add(statusPE, c);
		c.gridy = 3;
		panel.add(avgRatingPE, c);
		c.gridy = 4;
		panel.add(statusS, c);
		c.gridy = 5;
		panel.add(avgRatingS, c);
		c.gridy = 6;
		panel.add(statusMC, c);
		c.gridy = 7;
		panel.add(avgRatingMC, c);
		c.gridy = 8;
		panel.add(orderHistory, c);
		c.gridy = 9;
		panel.add(logout, c);
		
		
		setSize(600, 600);
		setContentPane(panel);
	}

}
