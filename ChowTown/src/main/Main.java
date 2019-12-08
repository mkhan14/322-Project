package main;

import java.sql.*;

import javax.swing.*;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "mahin";
	private static Connection conn = null;
	
	
	private static JFrame frame;
	private static Manager manager;
	private static Info info;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Login log;
		//log = new Login();
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			//func();
			init();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void func() {
		String query = "SELECT * FROM customers WHERE username = 'mahin'";
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
	
	//new
	public static void init() {
		
		frame = new JFrame();
		manager = new Manager();
		info = new Info();
		JPanel managerPage = manager.createPage();
		frame.add(managerPage);
		frame.setSize(600,850);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void goToInfo(int infoID) {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.revalidate();
		frame.add(info.generateInfo(infoID));
		frame.setVisible(true);
		frame.setSize(1000, 850);
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	

}
