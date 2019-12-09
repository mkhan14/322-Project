package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String root = "root";
	private static final String password = "risa";
	private static Connection conn = null;
	
	private static JFrame frame;
	private static Restaurant restaurant;
	private static JPanel restaurantPage;
	private static Menu menu;
	private static OrderConfirmation confirmation;
	private static Login login;
	private static CustomerAccount myAccount;
	private static User user;

	public static void main(String[] args) {
		try {
			conn = DriverManager.getConnection(url, root, password);
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
		frame.setSize(1100, 850);
	}
	
	public static void goToLogin() {
		if(user == null) {
			login = new Login();
			login.setVisible(true);
		}else if(user.isLoggedIn()) {
			JOptionPane.showMessageDialog(null,"Already logged in.");
		}
	}
	
	public static void goToMyAccount() {
		if(user == null)
			JOptionPane.showMessageDialog(null,"Please login or register.");
		else if(user.getTitle() == User.CUSTOMER && user.isLoggedIn()) {
			myAccount = new CustomerAccount();
			myAccount.setVisible(true);
		}
	}
	
	public static void goToOrderConfirmation(ArrayList<Menu.Item> cart) {
		if(cart.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Your cart is empty.");
		}else {
			confirmation = new OrderConfirmation(cart);
			confirmation.setVisible(true);
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static User getUser() {
		return user;
	}
	public static void setUser(User c) {
		user = c;
	}
	
}
