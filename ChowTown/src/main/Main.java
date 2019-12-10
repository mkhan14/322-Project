package main;

import java.sql.*;

import javax.swing.*;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "mahin";
	private static Connection conn = null;
	
	
	private static JFrame frame;
	//private static JFrame frameAgain;
	private static ManagerPage manager_page;
	private static JPanel managerPage;
	private static Info info;
	private static Login login;
	private static Manager manager;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Login log;
		//log = new Login();
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			//func();
			//init();
			goToLogin();
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
		manager_page = new ManagerPage();
		info = new Info();
		managerPage = manager_page.createPage();
		frame.add(managerPage);
		frame.setSize(600,850);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void goToManagerPage() {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.revalidate();
		frame.add(managerPage);
		frame.setVisible(true);
		frame.setSize(600, 850);
	}
	
	public static void goToInfo(int infoID) {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.revalidate();
		frame.add(info.generateInfo(infoID, manager.getId()));
		//frame.add(info.generateInfo(infoID, restID));
		frame.setVisible(true);
		frame.setSize(1000, 850);
	}
	
	public static void goToLogin() {
		if(manager == null) {
			login = new Login();
			login.setVisible(true);
		}else if(manager.isLoggedIn()) {
			JOptionPane.showMessageDialog(null,"Already logged in.");
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static Manager getManager() {
		return manager;
	}
	public static void setManager(Manager m) {
		manager = m;
	}

}
