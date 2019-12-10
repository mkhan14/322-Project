package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OrderConfirmation extends JFrame{
	private Connection conn = Main.getConnection();
	private JTextArea textArea;
	private Hashtable<String, Integer> order;
    private double totalPrice = 0;

	public OrderConfirmation(ArrayList<Menu.Item> cart, int restID) {
		JPanel panel = new JPanel(new GridBagLayout());
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(700, 600));
        textArea.setEditable(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 20));
        totalPrice = 0;
        order = new Hashtable<String, Integer>();
        
        int orderID = createOrder(restID);
        textArea.append("Your order ID is: " + orderID + "\n");
        for(Menu.Item i : cart) {
        	if(Main.getUser() != null)
        		addItemsToOrder(i, orderID);
        	if(order.containsKey(i.getName()))
        		order.replace(i.getName(),  order.get(i.getName()) + 1);
        	else
        		order.put(i.getName(), 1);
        	totalPrice += i.getPrice();
        }
        
        order.forEach((name, quantity) -> {
        	String str = name + " x " + quantity;
        	textArea.append(str + "\n");
        });
        
        if(Main.getUser() != null)
        	getDiscounts(Main.getUser(), restID);
        
        textArea.append("\nTotal Price: $" + Double.toString(Math.ceil(totalPrice)) + "\n");
        textArea.append("\nOrder has been confirmed!!\n");
        textArea.append("If you have registered,\n");
        textArea.append("please go to your order history to see if your order has been approved.\n");
        panel.add(textArea);
        
        
		setSize(1000,600);
		setContentPane(panel);
	}
	
	public void getDiscounts(User customer, int restID) {
		String query = "SELECT status FROM customerratings WHERE cust_id = " + customer.getId() + " AND rest_id = " + restID + ";";
		int status = -1;
		String item = "";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				status = rs.getInt("status");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(status == User.REGISTERED) {
			totalPrice *= 0.85;
			textArea.append("\nYou got a 15% discount!\n");
		}
		if(status == User.VIP) {
			totalPrice *= 0.75;
			query = "SELECT item FROM menu WHERE rest_id = " + restID + " ORDER BY avg_rating DESC LIMIT 1;";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					item = rs.getString("item");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			textArea.append("\nYou got a 25% discount!\n");
			textArea.append("You got a free " + item +"\n");
		}
	}
	
	public int createOrder(int restID) {
		int orderID = -1;
		if(Main.getUser() != null) {
			String query = "SELECT MAX(order_id) FROM orders;";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					orderID = rs.getInt("MAX(order_id)")+1;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			query = "INSERT INTO orders (order_id, cust_id, rest_id) VALUES (" + orderID + ", "
							+ Main.getUser().getId() + ", " + restID + ");";
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return orderID;
	}
	
	public void addItemsToOrder(Menu.Item item, int orderID) {
		String query = "INSERT INTO orderhistory (order_id, item) VALUES (" + orderID + ", '" + item.getName() + "');";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

}
