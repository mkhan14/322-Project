package main;
//
//<<<<<<< HEAD
//import java.sql.*;
//
//import javax.swing.*;
//
//public class Main {
//	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
//	private static final String user = "root";
//	private static final String password = "mahin";
//	private static Connection conn = null;
//	
//	
//	private static JFrame frame;
//	//private static JFrame frameAgain;
//	private static ManagerPage manager_page;
//	private static JPanel managerPage;
//	private static Info info;
//	private static Login login;
//	private static Manager manager;
//	
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		//Login log;
//		//log = new Login();
//		
//		try {
//			conn = DriverManager.getConnection(url, user, password);
//			//func();
//			//init();
//			goToLogin();
//		} 
//		catch (SQLException e) {
//			e.printStackTrace();
//		} 
//	}
//	
//	public static void func() {
//		String query = "SELECT * FROM customers WHERE username = 'mahin'";
//		Statement stmt;
//		try {
//			stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()) {
//				System.out.println(rs.getString("username"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	//new
//	public static void init() {
//		
//		frame = new JFrame();
//		manager_page = new ManagerPage();
//		info = new Info();
//		managerPage = manager_page.createPage();
//		frame.add(managerPage);
//		frame.setSize(600,850);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
//	
//	public static void goToManagerPage() {
//		frame.getContentPane().removeAll();
//		frame.repaint();
//		frame.revalidate();
//		frame.add(managerPage);
//		frame.setVisible(true);
//		frame.setSize(600, 850);
//	}
//	
//	public static void goToInfo(int infoID) {
//		frame.getContentPane().removeAll();
//		frame.repaint();
//		frame.revalidate();
//		frame.add(info.generateInfo(infoID, manager.getId()));
//		//frame.add(info.generateInfo(infoID, restID));
//		frame.setVisible(true);
//		frame.setSize(1000, 850);
//	}
//	
//	public static void goToLogin() {
//		if(manager == null) {
//			login = new Login();
//			login.setVisible(true);
//		}else if(manager.isLoggedIn()) {
//			JOptionPane.showMessageDialog(null,"Already logged in.");
//		}
//	}
//	
//	public static Connection getConnection() {
//		return conn;
//	}
//	
//	public static Manager getManager() {
//		return manager;
//	}
//	public static void setManager(Manager m) {
//		manager = m;
//	}
//
//=======
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
	
	public static void goToOrderConfirmation(ArrayList<Menu.Item> cart, int id) {
		if(cart.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Your cart is empty.");
		}else {
			confirmation = new OrderConfirmation(cart, id);
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
	
//>>>>>>> refs/remotes/origin/risa
}
