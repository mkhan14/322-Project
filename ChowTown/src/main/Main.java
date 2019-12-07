package main;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "risa";
	private static Connection conn = null;
	
	private static JFrame frame;
	private static Restaurant restaurant;
	private static Menu menu;
	private static int chosenMenu = -1;

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
		JPanel restaurantPage = restaurant.createPage();
		frame.add(restaurantPage);
    	frame.setSize(600, 850);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void goToRestaurantPage() {
		
	}
	
	public static void goToMenu(int restID) {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.revalidate();
		frame.add(menu.createMenu(restID));
		frame.setVisible(true);
		frame.setSize(1000, 850);
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static void setMenu(int m) {
		chosenMenu = m;
	}
	
	public static void someFunction() {
		String query = "SELECT * FROM customers";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println(rs.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
