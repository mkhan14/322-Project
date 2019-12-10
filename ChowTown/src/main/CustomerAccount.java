package main;

import java.awt.BorderLayout;
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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomerAccount extends JFrame{
	private Connection conn = Main.getConnection();
	private GridBagConstraints c = new GridBagConstraints();
	private User customer;
	
	public CustomerAccount() {
		customer = Main.getUser();
		int addrsID = -1;
		String statPE = "";
		double ratingPE = 0;
		String statS = "";
		double ratingS = 0;
		String statMC = "";
		double ratingMC = 0;

		String query = "SELECT address, rest_id, avg_rating, status FROM customers JOIN customerratings WHERE id = " + customer.getId() 
				+ " AND cust_id = " + customer.getId() + ";";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				addrsID = rs.getInt("address");
				if(rs.getInt("rest_id") == Menu.PE) {
					statPE = User.STATUS[rs.getInt("status")];
					ratingPE = rs.getDouble("avg_rating");
				}
				if(rs.getInt("rest_id") == Menu.S) {
					statS = User.STATUS[rs.getInt("status")];
					ratingS = rs.getDouble("avg_rating");
				}
				if(rs.getInt("rest_id") == Menu.MC) {
					statMC = User.STATUS[rs.getInt("status")];
					ratingMC = rs.getDouble("avg_rating");
				}
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JPanel panel = new JPanel(new GridBagLayout());
		JLabel name = new JLabel("Name: " + customer.getName());
		name.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel address = new JLabel("Address: "+ User.AREAS[addrsID]);
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
				showOrderHistory();
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
	
	private void showOrderHistory() {
		getContentPane().removeAll();
		repaint();
		revalidate();

		JPanel panel = new JPanel(new BorderLayout());
		ImageIcon back = new ImageIcon("images/back.png");
		JButton backButton = new JButton(back);
		backButton.setLocation(0, 0);
		backButton.setSize(back.getIconWidth(), back.getIconHeight());
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.goToMyAccount();
			}
			
		});
		panel.add(backButton, BorderLayout.NORTH);
		
		JPanel history = new JPanel(new GridBagLayout());
		c.gridy = -1; c.insets = new Insets(10,10,10,10);
		
		String query = "SELECT * FROM orders WHERE cust_id = " + customer.getId();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int id = rs.getInt("order_id");
				String restName = Menu.rest[rs.getInt("rest_id")];
				Object cookID = rs.getObject("cook_id");
				Object deliID = rs.getObject("deli_id");
				
				JLabel orderID = new JLabel("Order ID: " + id);
				orderID.setFont(new Font("monospaced", Font.PLAIN, 15));
				c.gridx = 0; c.gridy += 1;
				history.add(orderID, c);
				
				JLabel rest = new JLabel("Restaurant: " + restName);
				rest.setFont(new Font("monospaced", Font.PLAIN, 15));
				c.gridx = 1;
				history.add(rest, c);
				
				JLabel approval = new JLabel();
				approval.setFont(new Font("monospaced", Font.PLAIN, 15));
				if(cookID == null || deliID == null)
					approval.setText("NOT COMPLETED");
				else
					approval.setText("COMPLETED");
				c.gridx = 2;
				history.add(approval, c);
				
				JButton cancel = new JButton("Cancel");
				cancel.setFont(new Font("monospaced", Font.PLAIN, 15));
				cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(cookID == null || deliID == null) {
							deleteOrder(id);
							repaint();
						}else {
							JOptionPane.showMessageDialog(null, "Your order has been made already and cannot be canceled.");
						}
					}
					
				});

				c.gridx = 3;
				history.add(cancel, c);
				
				JButton rate = new JButton("Rate");
				rate.setFont(new Font("monospaced", Font.PLAIN, 15));
				rate.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(cookID == null || deliID == null) {
							JOptionPane.showMessageDialog(null, "Your order has not been made yet.");
						}else {
							goToRateScreen(id, (Integer) cookID, (Integer)deliID);
						}
					}
					
				});
				c.gridx = 4;
				history.add(rate, c);
			}

			panel.add(history, BorderLayout.CENTER);
			
			setSize(1000, 600);
			setContentPane(panel);
			setVisible(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void goToRateScreen(int orderID, int cookID, int deliID) {
		getContentPane().removeAll();
		repaint();
		revalidate();

		JPanel panel = new JPanel(new BorderLayout());
		
		ImageIcon back = new ImageIcon("images/back.png");
		JButton backButton = new JButton(back);
		backButton.setLocation(0, 0);
		backButton.setSize(back.getIconWidth(), back.getIconHeight());
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showOrderHistory();
			}
			
		});
		
		panel.add(backButton, BorderLayout.NORTH);
		
		JPanel history = new JPanel(new GridBagLayout());
		JLabel deli = new JLabel("Please rate the delivery person from 1-5: ");
		deli.setFont(new Font("monospaced", Font.PLAIN, 15));
		JTextField deliRate = new JTextField();
		deliRate.setPreferredSize(new Dimension(30,30));
		c.gridx = 0; c.gridy = 0; c.insets = new Insets(10,10,10,10);
		history.add(deli, c);
		c.gridx = 1;
		history.add(deliRate, c);
		
		
		ArrayList<JTextField> inputs = new ArrayList<JTextField>();
		String query = "SELECT item FROM orderhistory WHERE order_id = " + orderID + ";";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				c.gridx = 0; c.gridy += 1;
				JLabel item = new JLabel("Please rate " + rs.getString("item") + " from 1-5: ");
				item.setFont(new Font("monospaced", Font.PLAIN, 15));
				JTextField itemRate = new JTextField();
				inputs.add(itemRate);
				itemRate.setPreferredSize(new Dimension(30,30));
				history.add(item, c);
				c.gridx = 1;
				history.add(itemRate,c );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JButton confirmRate = new JButton("Rate");
		confirmRate.setFont(new Font("monospaced", Font.PLAIN, 15));
		confirmRate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkInputs(inputs, deliRate)) {
					double r = Double.parseDouble(deliRate.getText());
					String query = "UPDATE orders SET deli_rate = " + r +" WHERE order_id = " + orderID + ";";
					try {
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(query);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					for(JTextField text: inputs) {
						r = Double.parseDouble(text.getText());
						query = "UPDATE orderhistory SET rate = " + r +" WHERE rate IS NULL AND order_id = " + orderID + " LIMIT 1;";
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(query);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
	
				}
				
			}
			
		});
		
		c.gridy += 1;
		history.add(confirmRate, c);	
		
		panel.add(history, BorderLayout.CENTER);
		setSize(600, 700);
		setContentPane(panel);
		setVisible(true);
	}
	
	private boolean checkInputs(ArrayList<JTextField> inputs, JTextField deliRate) {
		boolean checkInputs = true;
		for(JTextField text: inputs) {
			try{
				double r = Double.parseDouble(text.getText());
				if(r > 5 || r < 1) {
					JOptionPane.showMessageDialog(null, "Please enter integer or decimal values between 1-5.");
					checkInputs = false;
					break;
				}
				System.out.println("!");
			}catch(Exception e){
				if(checkInputs)
					JOptionPane.showMessageDialog(null, "Please enter integer or decimal values.");
				checkInputs = false;
			}
		}
		if(checkInputs) {
			try{
				double r = Double.parseDouble(deliRate.getText());
				if(r > 5 || r < 1) {
					JOptionPane.showMessageDialog(null, "Please enter integer or decimal values between 1-5.");
					checkInputs = false;
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Please enter integer or decimal values.");
				checkInputs = false;
			}
		}
		
		return checkInputs;
	}
	
	//update rate menu items and the cook and delivery guy
	
	private void deleteOrder(int orderID) {
		String query = "DELETE FROM orders WHERE order_id = " + orderID + ";";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
