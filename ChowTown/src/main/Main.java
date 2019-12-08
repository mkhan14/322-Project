package main;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "risa";
	private static Connection conn = null;
	
	private static JFrame frame;
	private static Restaurant restaurant;
	private static JPanel restaurantPage;
	private static Menu menu;
	private static Login login;
	private static MyAccount myAccount;
	private static Customer customer;

	public static void main(String[] args) {
		try {
			conn = DriverManager.getConnection(url, user, password);
			init();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public static void init() {
		frame = new JFrame();
		restaurant = new Restaurant();
		menu = new Menu();
		restaurantPage = restaurant.createPage();
		frame.add(restaurantPage);
    	frame.setSize(600, 850);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void goToRestaurantPage() {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.revalidate();
		frame.add(restaurantPage);
		frame.setVisible(true);
		frame.setSize(600, 850);
	}
	
	public static void goToMenu(int restID) {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.revalidate();
		frame.add(menu.createMenu(restID));
		frame.setVisible(true);
		frame.setSize(1000, 850);
	}
	
	public static void goToLogin() {
		if(customer == null) {
			login = new Login();
			login.setVisible(true);
		}else if(customer.isLoggedIn()) {
			JOptionPane.showMessageDialog(null,"Already logged in.");
		}
	}
	
	public static void goToMyAccount() {
		if(customer == null)
			JOptionPane.showMessageDialog(null,"Please login or register.");
		else if(customer.isLoggedIn()) {
			myAccount = new MyAccount();
			myAccount.setVisible(true);
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static Customer getCustomer() {
		return customer;
	}
	public static void setCustomer(Customer c) {
		customer = c;
	}
	
}
