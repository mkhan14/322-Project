package main;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
//import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.*;
import java.sql.Statement;

//whats happening
public class Login extends JFrame{

	
private Connection conn = Main.getConnection();
	
	private GridBagConstraints c = new GridBagConstraints();
	private JTextField nameField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel message;
	
	public Login() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel username = new JLabel("Username:");
		username.setFont(new Font("monospaced", Font.PLAIN, 20));
		usernameField = new JTextField(15);
		JLabel password = new JLabel("Password:");
		password.setFont(new Font("monospaced", Font.PLAIN, 20));
		passwordField = new JPasswordField(15);
		
		message = new JLabel();
		message.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		JButton login = new JButton("Login");
		login.setFont(new Font("monospaced", Font.PLAIN, 15));
		login.setPreferredSize(new Dimension(80, 40));
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkLogin();
			}
			
		});
		
		
		
		c.gridx = 0; c.gridy = 0; c.insets = new Insets(10,10,10,10);
		panel.add(username, c);
		c.gridx = 1;
		panel.add(usernameField, c);
		c.gridx = 0; c.gridy = 1;
		panel.add(password, c);
		c.gridx = 1;
		panel.add(passwordField, c);
		c.gridx = 0; c.gridy = 2;
		panel.add(login, c);
		//c.gridx = 1;
		//panel.add(register, c);
		c.gridx = 0; c.gridy = 4;
		panel.add(message, c);
		
		setSize(400, 300);
		setContentPane(panel);
	}
	
	private void checkLogin() {
		String username = usernameField.getText();
		String password = String.valueOf(passwordField.getPassword());
		String query = "SELECT COUNT(*), rest_id, name FROM managers WHERE username = '" + username + "' AND password = '" + password + "';";
		int count = 0;
		int id = -1;
		String name = "";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				count = rs.getInt("COUNT(*)");
				id = rs.getInt("rest_id");
				name = rs.getString("name");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(count > 0) {
			Main.setManager(new Manager(id, name, true));
			dispose();
			Main.init();
		}else {
			message.setText("Account does not exist.");
		}
	}
	
}

