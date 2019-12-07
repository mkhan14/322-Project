package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class ManagerHomePage {
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "mahin";
	private static Connection conn = null;
	
	public static void main(String args[]) {
		JFrame managerPage = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		
		JLabel pageName = new JLabel("Manager's Home Page");
		pageName.setFont(new Font("monospaced", Font.PLAIN, 28));
		pageName.setHorizontalAlignment(JLabel.CENTER);
		pageName.setVerticalAlignment(JLabel.CENTER);
		panel.add(pageName);
		
		
		JButton customerBtn = new JButton("View Customer Info");
	    //addBtn.setBounds(100, 10, 500, 25);
		 //addBtn.setPreferredSize(new Dimension(40, 40));
		//addBtn.
	    //addBtn.setBorder(new RoundedBorder(10)); //10 is the radius
	    //addBtn.setForeground(Color.RED);
	   panel.add(customerBtn);
	   JButton employeeBtn = new JButton("View Employee Info");
	   panel.add(employeeBtn);
	   
	   JButton orderBtn = new JButton("View Order Info");
	   panel.add(orderBtn);
	   
	   managerPage.add(panel);
	   managerPage.setSize(800,800);
	   managerPage.setVisible(true);
	   managerPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   JFrame customerInfoPage = new JFrame();
	   JPanel customerInfoPanel = new JPanel();
	   
	   //TextArea item1 = new TextArea("");
	   JLabel item1 = new JLabel("");
		item1.setFont(new Font("monospaced", Font.PLAIN, 14));
		//item1.setHorizontalAlignment(JLabel.CENTER);
		//item1.setVerticalAlignment(JLabel.CENTER);
		customerInfoPanel.add(item1);
		customerInfoPage.add(customerInfoPanel);
		customerInfoPage.setSize(800,800);
		
		//customerInfoPage.setVisible(false);
		customerInfoPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   customerBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  try {
						conn = DriverManager.getConnection(url, user, password);
						managerPage.setVisible(false);
						customerInfoPage.setVisible(true);
						//func();
						
						String query = "SELECT * FROM customers";
						Statement stmt;
						try {
							stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(query);
							while(rs.next()) {
								item1.setText(item1.getText() + "Name: " + rs.getString("name") + "Address: " + rs.getString("address"));
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} 
					catch (SQLException e1) {
						e1.printStackTrace();
					} finally {
						try {
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
			  } 
			} );	
		
		
	   
	   
	}
	
	public static void func() {
		
		
		String query = "SELECT * FROM customers";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Address: " + rs.getString("address"));
				System.out.println(" ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
