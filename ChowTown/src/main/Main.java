package main;

import java.sql.*;

public class Main {
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "mahin";
	private static Connection conn = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Login log;
		//log = new Login();
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			func();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

}
