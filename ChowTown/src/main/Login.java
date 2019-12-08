package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
		
		JButton register = new JButton("Register");
		register.setFont(new Font("monospaced", Font.PLAIN, 15));
		register.setPreferredSize(new Dimension(100, 40));
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goToRegister();
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
		c.gridx = 1;
		panel.add(register, c);
		c.gridx = 0; c.gridy = 4;
		panel.add(message, c);
		
		setSize(400, 300);
		setContentPane(panel);
	}
	
	private void checkLogin() {
		String username = usernameField.getText();
		String password = String.valueOf(passwordField.getPassword());
		String query = "SELECT COUNT(*), id, name, address FROM customers WHERE username = '" + username + "' AND password = '" + password + "';";
		int count = 0;
		int id = -1;
		String name = "";
		int address = -1;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				count = rs.getInt("COUNT(*)");
				id = rs.getInt("id");
				name = rs.getString("name");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(count > 0) {
			Main.setCustomer(new Customer(id, name, true));
			dispose();
		}else {
			message.setText("Account does not exist.");
		}
	}
	
	private void goToRegister() {
		getContentPane().removeAll();
		repaint();
		revalidate();
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel name = new JLabel("Name:");
		name.setFont(new Font("monospaced", Font.PLAIN, 20));
		nameField = new JTextField(15);
		JLabel username = new JLabel("Username:");
		username.setFont(new Font("monospaced", Font.PLAIN, 20));
		usernameField = new JTextField(15);
		JLabel password = new JLabel("Password:");
		password.setFont(new Font("monospaced", Font.PLAIN, 20));
		passwordField = new JPasswordField(15);
		JLabel address = new JLabel("Address:");
		address.setFont(new Font("monospaced", Font.PLAIN, 20));
		
		message = new JLabel();
		message.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		JButton register = new JButton("Register");
		register.setFont(new Font("monospaced", Font.PLAIN, 15));
		register.setPreferredSize(new Dimension(100, 40));
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int id = -1;
				String name = nameField.getText();
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				int address = -1;
				String query = "SELECT COUNT(*) from customers WHERE username = '" + username + "';";
				int count = 0;
				try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()) {
						count = rs.getInt("COUNT(*)");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if(count > 0) {
					message.setText("Account already exists.");
				}else {
					query = "SELECT MAX(id) FROM customers;";
					try {
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while(rs.next()) {
							id = rs.getInt("MAX(id)") + 1;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					query = "INSERT INTO customers VALUES(" + id + ", '" + username + "', '" + password + "', '" + name + "', " + address + ");";
					String query1 = "INSERT INTO customerratings VALUES (" + id + ", " + Menu.PE + ", 5, 1, 1);"; 
					String query2 = "INSERT INTO customerratings VALUES (" + id + ", " + Menu.S + ", 5, 1, 1);"; 
					String query3 = "INSERT INTO customerratings VALUES (" + id + ", " + Menu.MC + ", 5, 1, 1);"; 
					try {
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(query);
						stmt.executeUpdate(query1);
						stmt.executeUpdate(query2);
						stmt.executeUpdate(query3);
						Main.setCustomer(new Customer(id, name, true));
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				
			}
			
		});
		
		c.gridx = 0; c.gridy = 0;
		panel.add(name, c);
		c.gridx = 1;
		panel.add(nameField, c);
		c.gridx = 0; c.gridy = 1;
		panel.add(username, c);
		c.gridx = 1;
		panel.add(usernameField, c);
		c.gridx = 0; c.gridy = 2;
		panel.add(password, c);
		c.gridx = 1;
		panel.add(passwordField, c);
		c.gridx = 0; c.gridy = 3;
		panel.add(address, c);
		c.gridx = 0; c.gridy = 4;
		panel.add(register, c);
		c.gridx = 1;
		panel.add(message, c);
		
		setSize(400, 300);
		setContentPane(panel);
		setVisible(true);
	}
}
