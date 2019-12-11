package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Menu {
	
	final static int PE = 0;
	final static int S = 1;
	final static int MC = 2;
	
	final static String[] rest = {"Panda Express", "Sakura", "Masala Cafe"};
	
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayList<Double> prices = new ArrayList<Double>();
	private ArrayList<Double> ratings = new ArrayList<Double>();
	private static ArrayList<Item> cart = new ArrayList<Item>();
	
	private Connection conn = Main.getConnection();
	
	public JPanel createMenu(int restID) {
		JPanel panel = new JPanel(new BorderLayout());
		if(restID == PE)
			panel.setBackground(new Color(255, 255, 200));
		if(restID == S)
			panel.setBackground(new Color(255, 230, 245));
		if(restID == MC)
			panel.setBackground(new Color(240, 255, 220));
		
		ImageIcon back = new ImageIcon("images/back.png");
		JButton backButton = new JButton(back);
		backButton.setLocation(0, 0);
		backButton.setSize(back.getIconWidth(), back.getIconHeight());
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.goToRestaurantPage();
			}
			
		});

		panel.add(backButton, BorderLayout.NORTH);
		
		JPanel menu = body(restID);
		panel.add(menu, BorderLayout.CENTER);
		
		return panel;
	}
	
	public JPanel body(int restID) {
		JPanel panel = new JPanel(new GridBagLayout());
		Border border = BorderFactory.createEmptyBorder(10,10,10,10);
		GridBagConstraints c = new GridBagConstraints();
		items.clear();
		prices.clear();
		ratings.clear();
		cart.clear();
		
		String query = "SELECT * FROM menu WHERE rest_id = " + restID + ";";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				items.add(rs.getString("item"));
				prices.add(rs.getDouble("price"));
				ratings.add(rs.getDouble("avg_rating"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(restID == PE) {
			panel.setBackground(new Color(255, 255, 200));
			ImageIcon icon = new ImageIcon("images/China.png");
			JLabel img = new JLabel(icon);
			c.gridx = 1; c.gridy = 0;
			panel.add(img,c);
			JLabel label = new JLabel("Panda Express");
			label.setFont(new Font("monospaced", Font.BOLD, 30));
			c.gridx = 2;
			panel.add(label,c);
		}
		if(restID == S) {
			panel.setBackground(new Color(255, 230, 245));
			ImageIcon icon = new ImageIcon("images/Japan.png");
			JLabel img = new JLabel(icon);
			c.gridx = 1; c.gridy = 0;
			panel.add(img,c);
			JLabel label = new JLabel("Sakura");
			label.setFont(new Font("monospaced", Font.BOLD, 30));
			c.gridx = 2;
			panel.add(label,c);
		}
		if(restID == MC) {
			panel.setBackground(new Color(240, 255, 220));
			ImageIcon icon = new ImageIcon("images/India.png");
			JLabel img = new JLabel(icon);
			c.gridx = 1; c.gridy = 0;
			panel.add(img,c);
			JLabel label = new JLabel("Masala Cafe");
			label.setFont(new Font("monospaced", Font.BOLD, 30));
			c.gridx = 2;
			panel.add(label,c);
		}
		for(int i = 0; i < items.size(); i++) {
			JLabel item = new JLabel(items.get(i));	
			item.setBorder(border);
			item.setFont(new Font("monospaced", Font.PLAIN, 20));
			c.gridx = 0; c.gridy = i + 1;
			panel.add(item, c);
			JLabel price = new JLabel("$" + Double.toString(prices.get(i)));
			price.setBorder(border);
			price.setFont(new Font("monospaced", Font.PLAIN, 20));
			c.gridx = 1;
			panel.add(price, c);
			JLabel rate = new JLabel("Rate:" + Double.toString(round(ratings.get(i))));
			rate.setBorder(border);
			rate.setFont(new Font("monospaced", Font.PLAIN, 20));
			c.gridx = 2;
			panel.add(rate, c);
			
			c.gridx = 4;
			JLabel quantity = new JLabel("0");
			panel.add(quantity, c);
			quantity.setFont(new Font("monospaced", Font.PLAIN, 20));
			
			JButton add = new JButton("Add to Cart");
			add.setFont(new Font("monospaced", Font.PLAIN, 20));
			add.setPreferredSize(new Dimension(170,50));
			final int indx = i;
			add.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Item item = new Item(items.get(indx), prices.get(indx));
					cart.add(item);
					quantity.setText(String.valueOf(Integer.parseInt(quantity.getText()) + 1));
				}
				
			});
			c.gridx = 3;
			panel.add(add, c);
			
			
			
			JButton remove = new JButton("Remove");
			remove.setFont(new Font("monospaced", Font.PLAIN, 20));
			remove.setPreferredSize(new Dimension(170,50));
			remove.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String name = items.get(indx);
					for(int i = 0; i < cart.size(); i++) {
						if(cart.get(i).getName().equals(name)) {
							cart.remove(i);
							quantity.setText(String.valueOf(Integer.parseInt(quantity.getText()) - 1));
							break;
						}
					}
				}
				
			});
			c.gridx = 5;
			panel.add(remove, c);
		}
		
		JButton recommendation = new JButton("Recommendations");
		recommendation.setFont(new Font("monospaced", Font.PLAIN, 20));
		recommendation.setPreferredSize(new Dimension(200,60));
		recommendation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showRecommendation(restID);
			}
			
		});
		c.gridx = 1; c.gridy += 1;
		panel.add(recommendation, c);
		
		JButton confirm = new JButton("Confirm Order");
		confirm.setFont(new Font("monospaced", Font.PLAIN, 20));
		confirm.setPreferredSize(new Dimension(200,60));
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.goToRestaurantPage();
				Main.goToOrderConfirmation(cart, restID);
			}
			
		});
		
		c.gridx = 2;
		panel.add(confirm, c);
		
		return panel;
	}
	
	private void showRecommendation(int restID) {
		String recommend = "";
		String query = "SELECT item, avg_rating FROM menu WHERE rest_id = " + restID + " ORDER BY avg_rating DESC LIMIT 3;";
		if(Main.getUser() == null) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					recommend += rs.getString("item") + "        Rate: " + round(rs.getDouble("avg_rating")) + "\n";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, recommend);
		}else if(Main.getUser().getTitle() == User.CUSTOMER) {
			int count = 0;
			String query1 = "SELECT COUNT(*) FROM orders WHERE cust_id = " + Main.getUser().getId() + ";";
			String query2 = "SELECT item, rate FROM orders JOIN orderhistory WHERE orders.order_id = orderhistory.order_id AND "
					+ "rest_id = " + restID + " AND cust_id = " + Main.getUser().getId() + " ORDER BY rate DESC LIMIT 3;";
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query1);
				while(rs.next()) {
					count = rs.getInt("COUNT(*)");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(count >= 3) {
				try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query2);
					while(rs.next()) {
						recommend += rs.getString("item") + "        Rate: " + round(rs.getDouble("rate")) + "\n";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, recommend);
			}else {
				try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()) {
						recommend += rs.getString("item") + "        Rate: " + rs.getDouble("avg_rating") + "\n";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, recommend);
			}
			
		}
	}
	
	private double round(double value) {
		return  Math.floor(value * 100) / 100;
	}
	
	public class Item {

		private String name;
		private double price;
		
		public Item(String name, double price) {
			this.setName(name);
			this.setPrice(price);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

	}

}