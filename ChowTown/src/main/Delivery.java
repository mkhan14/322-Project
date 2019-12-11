package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Delivery extends JFrame{
	private Connection conn = Main.getConnection();
	private GridBagConstraints c = new GridBagConstraints();
	private User delivery;
	private JTextField amount;

	public Delivery() {
		createPage();
	}
	
	private void createPage() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel headers = new JPanel();
		
		JButton myAccount = new JButton("My Account");
		myAccount.setPreferredSize(new Dimension(150, 40));
		myAccount.setFont(new Font("monospaced", Font.PLAIN, 20));
		myAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goToMyAccount();
			}
			
		});
		
		JButton logout = new JButton("Logout");
		logout.setPreferredSize(new Dimension(100, 40));
		logout.setFont(new Font("monospaced", Font.PLAIN, 20));
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.setUser(null);
				Main.goToRestaurantPage();
				dispose();
			}
			
		});
		
		headers.add(myAccount);
		headers.add(logout);
		panel.add(headers, BorderLayout.NORTH);
		
		JPanel footers = new JPanel(new GridLayout(6, 1));
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		
		JLabel traffic = new JLabel("Today's Traffic:");
		traffic.setFont(new Font("monospaced", Font.BOLD, 30));
		traffic.setBorder(border);
		footers.add(traffic);
		JLabel toArea0 = new JLabel("The Traffic to A: " + FindRoute.dijkstra(0)[1]);
		toArea0.setFont(new Font("monospaced", Font.PLAIN, 20));
		toArea0.setBorder(border);
		footers.add(toArea0);
		JLabel toArea1 = new JLabel("The Traffic to B: "+ FindRoute.dijkstra(0)[2]);
		toArea1.setFont(new Font("monospaced", Font.PLAIN, 20));
		toArea1.setBorder(border);
		footers.add(toArea1);
		JLabel toArea2 = new JLabel("The Traffic to C: "+ FindRoute.dijkstra(0)[3]);
		toArea2.setFont(new Font("monospaced", Font.PLAIN, 20));
		toArea2.setBorder(border);
		footers.add(toArea2);
		
		JLabel bidLabel = new JLabel ("Please enter a bid amount as a positive integer.");
		bidLabel.setFont(new Font("monospaced", Font.BOLD, 20));
		footers.add(bidLabel);
		
		amount = new JTextField();
		amount.setPreferredSize(new Dimension(30,30));
		footers.add(amount);
		panel.add(footers, BorderLayout.SOUTH);
		
		delivery = Main.getUser();
		int restID = -1;
		JPanel orders = new JPanel(new GridBagLayout());
		c.insets = new Insets(10,10,10,10);
		String query = "SELECT rest_id FROM employees WHERE id = " + delivery.getId() + ";";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				restID = rs.getInt("rest_id");
			}
			query = "SELECT order_id, cust_id, name, address FROM orders JOIN customers WHERE cook_id IS NOT NULL AND deli_id IS NULL AND rest_id = " + restID 
					+ " AND cust_id = id;";
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				int orderID = rs.getInt("order_id");
				int custID = rs.getInt("cust_id");
				String name = rs.getString("name");
				String address = User.AREAS[rs.getInt("address")];
				
				JLabel order = new JLabel("Order ID: " + orderID);
				order.setFont(new Font("monospaced", Font.PLAIN, 20));
				c.gridx = 0; c.gridy += 1;
				orders.add(order, c);
				JLabel customer = new JLabel("Customer: " +  name);
				customer.setFont(new Font("monospaced", Font.PLAIN, 20));
				c.gridx = 1;
				orders.add(customer, c);
				JLabel custAddress = new JLabel("Address: " + address);
				custAddress.setFont(new Font("monospaced", Font.PLAIN, 20));
				c.gridx = 2;
				orders.add(custAddress, c);
				JButton bid = new JButton("Bid");
				bid.setPreferredSize(new Dimension(100, 40));
				bid.setFont(new Font("monospaced", Font.PLAIN, 20));
				final int id = restID;
				bid.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						bidding(orderID, custID, id);
					}
					
				});
				c.gridx = 3;
				orders.add(bid, c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel.add(orders, BorderLayout.CENTER);
		setSize(750, 800);
		setContentPane(panel);
		setVisible(true);
	}
	
	private void bidding(int orderID, int custID, int restID) {
		if(checkAmountInput()) {
			boolean chosen = true;
			int bidAmount = Integer.parseInt(amount.getText());
			for(int i = 0; i < 5; i++) {
				if(bidAmount > (int)(70.0 * Math.random()) + 1) {
					chosen = false;
					break;
				}
			}
			if(chosen) {
				String query = "UPDATE orders SET deli_id = " + delivery.getId() + " WHERE order_id = " + orderID + ";";
				try {
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				goToRatePage(orderID, custID, restID);
			}else {
				JOptionPane.showMessageDialog(null, "You have lost the bid.");
				String query = "SELECT id FROM employees WHERE job_title = 1 AND id != " + delivery.getId() + " AND rest_id = "
				+ restID + " ORDER BY RAND() LIMIT 1;";
				int id = -1;
				try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()) {
						id = rs.getInt("id");					
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				double rate = (4.0 * Math.random()) + 1.0;
				updateCustomerRatings(orderID, custID, id, restID, rate);
				getContentPane().removeAll();
				repaint();
				revalidate();
				createPage();
			}
		}
	}
	
	private void goToRatePage(int orderID, int custID, int restID) {
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
				createPage();
			}
			
		});
		panel.add(backButton, BorderLayout.NORTH);
		
		JPanel body = new JPanel(new GridBagLayout());
		c.gridx = 0;
		JLabel chosenLabel = new JLabel("Your bid was chosen!");
		chosenLabel.setFont(new Font("monospaced", Font.BOLD, 30));
		c.insets = new Insets(10,10,10,10);
		body.add(chosenLabel, c);
		JLabel cust = new JLabel("Please rate the customer from 1-5: ");
		cust.setFont(new Font("monospaced", Font.PLAIN, 20));
		JTextField custRate = new JTextField();
		custRate.setPreferredSize(new Dimension(30,30));
		c.gridx = 0; c.gridy += 1;
		body.add(cust, c);
		c.gridx = 1;
		body.add(custRate, c);
		JButton confirmRate = new JButton("Rate");
		confirmRate.setFont(new Font("monospaced", Font.PLAIN, 20));
		confirmRate.setPreferredSize(new Dimension(150, 40));
		confirmRate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkRateInput(custRate)) {
					updateCustomerRatings(orderID, custID, delivery.getId(), restID, Double.parseDouble(custRate.getText()));
					getContentPane().removeAll();
					repaint();
					revalidate();
					createPage();
				}
				
			}
			
		});
		c.gridy += 1;
		body.add(confirmRate, c);	
		
		panel.add(body, BorderLayout.CENTER);
		
		setSize(700, 400);
		setContentPane(panel);
		setVisible(true);
	}
	
	private void updateCustomerRatings(int orderID, int custID, int deliID, int restID, double rate) {
		String query = "UPDATE orders SET deli_id = " + deliID + " WHERE order_id = " + orderID + ";";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			
			double avg = 0;
			query = "SELECT avg_rating, num_rated FROM customerratings WHERE cust_id = " + custID + " AND rest_id = "
					+ restID + ";";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				avg = ((rs.getDouble("avg_rating") * rs.getInt("num_rated")) + rate) / (rs.getInt("num_rated") + 1);
			}
			String query1 = "UPDATE customerratings SET avg_rating = " + avg + " WHERE cust_id = " + custID + " AND rest_id = "
					+ restID + ";";
			String query2 = "UPDATE customerratings SET num_rated = num_rated + 1 WHERE cust_id = " + custID + " AND rest_id = "
					+ restID + ";";
			stmt.executeUpdate(query1);
			stmt.executeUpdate(query2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void goToMyAccount() {
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
				createPage();
			}
			
		});
		panel.add(backButton, BorderLayout.NORTH);
		
		int restID = -1;
		double sal = 0;
		double rate = 0;
		int warn = 0;
		String query = "SELECT rest_id, salary, avg_rating, warning FROM employees WHERE id = " + delivery.getId() + ";";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				restID = rs.getInt("rest_id");
				sal = rs.getDouble("salary");
				rate = rs.getDouble("avg_rating");
				warn = rs.getInt("warning");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		JPanel body = new JPanel(new GridBagLayout());
		JLabel name = new JLabel("Name: " + delivery.getName());
		name.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel work = new JLabel("Working for: " + Menu.rest[restID]);
		work.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel job = new JLabel("Working as: " + User.TITLES[delivery.getTitle()]);
		job.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel salary = new JLabel("Salary: $" +  sal);
		salary.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel rating = new JLabel("Rate: " + round(rate));
		rating.setFont(new Font("monospaced", Font.PLAIN, 20));
		JLabel warning = new JLabel("Warning: " + warn);
		warning.setFont(new Font("monospaced", Font.PLAIN, 20));
		
		c.gridx = 0; c.gridy = 0; c.insets = new Insets(10,10,10,10);
		body.add(name, c);
		c.gridy = 1;
		body.add(work, c);
		c.gridy = 2;
		body.add(job, c);
		c.gridy = 3;
		body.add(salary, c);
		c.gridy = 4;
		body.add(rating, c);
		c.gridy = 5;
		body.add(warning, c);
		
		panel.add(body, BorderLayout.CENTER);
		setSize(600, 600);
		setContentPane(panel);
	}
	
	private boolean checkAmountInput() {
		boolean checkInputs = true;
		try{
			int r = Integer.parseInt(amount.getText());
			if(r < 1) {
				JOptionPane.showMessageDialog(null, "Please enter a positive integer value.");
				checkInputs = false;
			}
		}catch(Exception e){
			if(checkInputs)
				JOptionPane.showMessageDialog(null, "Please enter a positive integer value.");
			checkInputs = false;
		}

		return checkInputs;
	}
	
	private boolean checkRateInput(JTextField custRate) {
		boolean checkInputs = true;
		try{
			double r = Double.parseDouble(custRate.getText());
			if(r > 5 || r < 1) {
				JOptionPane.showMessageDialog(null, "Please enter integer or decimal values between 1-5.");
				checkInputs = false;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Please enter integer or decimal values.");
			checkInputs = false;
		}
		return checkInputs;
	}
	
	private double round(double value) {
		return  Math.floor(value * 100) / 100;
	}

}