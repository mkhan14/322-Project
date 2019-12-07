package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayList<Double> prices = new ArrayList<Double>();
	private ArrayList<String> cart;
	private double totalPrice;
	
	public JPanel createMenu(int restID) {
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
		
		JPanel panel = new JPanel(new GridBagLayout());
		Border border = BorderFactory.createEmptyBorder(20,20,20,20);
		GridBagConstraints c = new GridBagConstraints();
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
			
			JButton remove = new JButton("Remove from Cart");
			remove.setFont(new Font("monospaced", Font.PLAIN, 20));
			remove.setPreferredSize(new Dimension(210,50));
			remove.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				}
				
			});
			c.gridx = 3;
			panel.add(remove, c);
		}
		
		return panel;
//		JFrame menuPage = new JFrame();
//		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout(20,2));
//		
//		JLabel restaurantName = new JLabel("Chinese Restaurant");
//		restaurantName.setFont(new Font("monospaced", Font.PLAIN, 28));
//		restaurantName.setHorizontalAlignment(JLabel.CENTER);
//		restaurantName.setVerticalAlignment(JLabel.CENTER);
//		panel.add(restaurantName);
//		
//		JLabel empty = new JLabel (" ");
//		panel.add(empty);
//		
//		JLabel houseSpecials = new JLabel("House Specials");
//		houseSpecials.setFont(new Font("monospaced", Font.PLAIN, 20));
//		houseSpecials.setForeground(Color.RED);
//		houseSpecials.setHorizontalAlignment(JLabel.CENTER);
//		houseSpecials.setVerticalAlignment(JLabel.CENTER);
//		panel.add(houseSpecials);
//		
//		JLabel empty1 = new JLabel (" ");
//		panel.add(empty1);
//		
//		JLabel item1 = new JLabel("Fried Chicken Wings(4) ・・・・・・・・・・・・ $5.25");
//		item1.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item1.setHorizontalAlignment(JLabel.CENTER);
//		item1.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item1);
//		
//		JButton add = new JButton("add");
//		panel.add(add);
//		
//		JLabel item2 = new JLabel("Fried Jumbo Shrimp(5) ・・・・・・・・・・・・ $5.50");
//		item2.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item2.setHorizontalAlignment(JLabel.CENTER);
//		item2.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item2);
//		
//		JButton add1 = new JButton("add");
//		panel.add(add1);
//		
//		JLabel item3 = new JLabel("Fried Spare Ribs ・・・・・・・・・・・・・・・ $5.25");
//		item3.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item3.setHorizontalAlignment(JLabel.CENTER);
//		item3.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item3);
//		
//		JButton add2 = new JButton("add");
//		panel.add(add2);
//		
//		JLabel item4 = new JLabel("Boneless Chicken ・・・・・・・・・・・・・・・ $5.00");
//		item4.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item4.setHorizontalAlignment(JLabel.CENTER);
//		item4.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item4);
//		
//		JButton add3 = new JButton("add");
//		panel.add(add3);
//		
//		JLabel item5 = new JLabel("Chicken Nuggets(10) ・・・・・・・・・・・・・ $5.00");
//		item5.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item5.setHorizontalAlignment(JLabel.CENTER);
//		item5.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item5);
//		
//		JButton add4 = new JButton("add");
//		panel.add(add4);
//		
//		JLabel item6 = new JLabel("Boneless Spare Ribs ・・・・・・・・・・・・・ $5.75");
//		item6.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item6.setHorizontalAlignment(JLabel.CENTER);
//		item6.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item6);
//		
//		JButton add5 = new JButton("add");
//		panel.add(add5);
//		
//		JLabel item7 = new JLabel("Honey Chicken Wings ・・・・・・・・・・・・・ $5.50");
//		item7.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item7.setHorizontalAlignment(JLabel.CENTER);
//		item7.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item7);
//		
//		JButton add6 = new JButton("add");
//		panel.add(add6);
//		
//		JLabel appetizers = new JLabel("Appetizers");
//		appetizers.setFont(new Font("monospaced", Font.PLAIN, 20));
//		appetizers.setForeground(Color.RED);
//		appetizers.setHorizontalAlignment(JLabel.CENTER);
//		appetizers.setVerticalAlignment(JLabel.CENTER);
//		panel.add(appetizers);
//		
//		JLabel empty2 = new JLabel (" ");
//		panel.add(empty2);
//		
//		JLabel item8 = new JLabel("Egg Roll(1) ・・・・・・・・・・・・・・・・・・ $1.50");
//		item8.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item8.setHorizontalAlignment(JLabel.CENTER);
//		item8.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item8);	
//		
//		JButton add7 = new JButton("add");
//		panel.add(add7);
//		
//		JLabel item9 = new JLabel("Spring Roll(3) ・・・・・・・・・・・・・・・・ $3.00");
//		item9.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item9.setHorizontalAlignment(JLabel.CENTER);
//		item9.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item9);
//		
//		JButton add8 = new JButton("add");
//		panel.add(add8);
//		
//		JLabel item10 = new JLabel("Shrimp Egg Roll(1) ・・・・・・・・・・・・・・ $3.00");
//		item10.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item10.setHorizontalAlignment(JLabel.CENTER);
//		item10.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item10);
//		
//		JButton add9 = new JButton("add");
//		panel.add(add9);
//		
//		JLabel item11 = new JLabel("Fried Wonton(10) ・・・・・・・・・・・・・・・ $4.50");
//		item11.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item11.setHorizontalAlignment(JLabel.CENTER);
//		item11.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item11);
//		
//		JButton add10 = new JButton("add");
//		panel.add(add10);
//		
//		JLabel item12 = new JLabel("Barbecued Spare Ribs ・・・・・・・・・・・・・ $6.75");
//		item12.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item12.setHorizontalAlignment(JLabel.CENTER);
//		item12.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item12);
//		
//		JButton add11 = new JButton("add");
//		panel.add(add11);
//		
//		JLabel item13 = new JLabel("Fried Dumplings(8) ・・・・・・・・・・・・・・ $6.25");
//		item13.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item13.setHorizontalAlignment(JLabel.CENTER);
//		item13.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item13);
//		
//		JButton add12 = new JButton("add");
//		panel.add(add12);
//		
//		JLabel item14 = new JLabel("Steamed Dumplings(8) ・・・・・・・・・・・・・ $6.25");
//		item14.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item14.setHorizontalAlignment(JLabel.CENTER);
//		item14.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item14);
//		
//		JButton add13 = new JButton("add");
//		panel.add(add13);
//		
//		JLabel item15 = new JLabel("French Fries ・・・・・・・・・・・・・・・・・ $2.50");
//		item15.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item15.setHorizontalAlignment(JLabel.CENTER);
//		item15.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item15);
//		
//		JButton add14 = new JButton("add");
//		panel.add(add14);
//		
//		JLabel item16 = new JLabel("Onion Rings(10) ・・・・・・・・・・・・・・・・ $2.50");
//		item16.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item16.setHorizontalAlignment(JLabel.CENTER);
//		item16.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item16);
//		
//		JButton add15 = new JButton("add");
//		panel.add(add15);
//		
//		JLabel item17 = new JLabel("Green Plantain ・・・・・・・・・・・・・・・・ $3.00");
//		item17.setFont(new Font("monospaced", Font.PLAIN, 14));
//		item17.setHorizontalAlignment(JLabel.CENTER);
//		item17.setVerticalAlignment(JLabel.CENTER);
//		panel.add(item17);
//		
//		JButton add16 = new JButton("add");
//		panel.add(add16);
//		
//		menuPage.add(panel);
//    	menuPage.setSize(800, 800);
//        menuPage.setVisible(true);
//        menuPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
