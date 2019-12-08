package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Menu {
	
	final static int PE = 0;
	final static int S = 1;
	final static int MC = 2;
	
	private Connection conn = Main.getConnection();
	private ArrayList<String> cart;
	private double totalPrice;
	
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
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<Double> prices = new ArrayList<Double>();
		
		cart = new ArrayList<String>();
		totalPrice = 0;
		String query = "SELECT * FROM menu WHERE rest_id = " + restID + ";";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				items.add(rs.getString("item"));
				prices.add(rs.getDouble("price"));
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
			JButton add = new JButton("Add to Cart");
			add.setFont(new Font("monospaced", Font.PLAIN, 20));
			add.setPreferredSize(new Dimension(170,50));
			add.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				}
				
			});
			c.gridx = 2;
			panel.add(add, c);
			
			JButton remove = new JButton("Remove");
			remove.setFont(new Font("monospaced", Font.PLAIN, 20));
			remove.setPreferredSize(new Dimension(170,50));
			remove.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				}
				
			});
			c.gridx = 3;
			panel.add(remove, c);
		}
		
		return panel;
	}

}
