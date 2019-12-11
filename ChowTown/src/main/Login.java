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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JComboBox registerAs;
	private JComboBox area;
	private JComboBox rest;
	
	public Login() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		registerAs = new JComboBox(User.TITLES);
		registerAs.setPreferredSize(new Dimension(150, 60));
		registerAs.setFont(new Font("monospaced", Font.PLAIN, 15));
		
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
		panel.add(registerAs, c);
		c.gridy = 1;
		panel.add(username, c);
		c.gridx = 1;
		panel.add(usernameField, c);
		c.gridx = 0; c.gridy = 2;
		panel.add(password, c);
		c.gridx = 1;
		panel.add(passwordField, c);
		c.gridx = 0; c.gridy = 3;
		panel.add(login, c);
		c.gridx = 1;
		panel.add(register, c);
		c.gridx = 0; c.gridy = 4;
		panel.add(message, c);
		
		setSize(500, 400);
		setContentPane(panel);
	}
	
	private void checkLogin() {
		int title = registerAs.getSelectedIndex();
		String username = usernameField.getText();
		String password = String.valueOf(passwordField.getPassword());
		String query = "";
		if(title == User.CUSTOMER)
			query = "SELECT COUNT(*), id, name FROM customers WHERE username = '" + username + "' AND password = '" + password + "';";
		else if(title == User.MANAGER){
			query = "SELECT COUNT(*), rest_id, name FROM managers WHERE username = '" + username + "' AND password = '" + password + "';";
		}else{
			query = "SELECT COUNT(*), id, name FROM employees WHERE username = '" + username + "' AND password = '" + password + "';";
		}
		int count = 0;
		int id = -1;
		int rest_id = -1;
		String name = "";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				count = rs.getInt("COUNT(*)");
				if(title == User.MANAGER)
					rest_id = rs.getInt("rest_id");
				else
					id = rs.getInt("id");
				name = rs.getString("name");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(count > 0) {
			if(title == User.CUSTOMER)
				Main.setUser(new User(User.CUSTOMER, id, name, true));
			if(title == User.COOK) {
				Main.setUser(new User(User.COOK, id, name, true));
				Main.initCook();
				Main.getFrame().dispose();
			}
			if(title == User.DELIVERY) {
				Main.setUser(new User(User.DELIVERY, id, name, true));
				Main.goToDeliveryPage();
				Main.getFrame().dispose();
			}
			if(title == User.SALES) {
				Main.setUser(new User(User.SALES, id, name, true));
				Main.goToSalesPage();
				Main.getFrame().dispose();
			}
			if(title == User.MANAGER) {
				Main.setUser(new User(User.MANAGER, rest_id, name, true));
				Main.initManager();
				Main.getFrame().dispose();
			}
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
		
		registerAs = new JComboBox(User.TITLES1);
		registerAs.setPreferredSize(new Dimension(150, 60));
		registerAs.setFont(new Font("monospaced", Font.PLAIN, 15));
		
		rest = new JComboBox(Menu.rest);
		rest.setPreferredSize(new Dimension(200, 60));
		rest.setFont(new Font("monospaced", Font.PLAIN, 15));
		
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
		area = new JComboBox(User.AREAS);
		area.setPreferredSize(new Dimension(150, 60));
		area.setFont(new Font("monospaced", Font.PLAIN, 15));
		message = new JLabel();
		message.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		JButton register = new JButton("Register");
		register.setFont(new Font("monospaced", Font.PLAIN, 15));
		register.setPreferredSize(new Dimension(100, 40));
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int title = registerAs.getSelectedIndex();
				int restID = rest.getSelectedIndex();
				int id = -1;
				String name = nameField.getText();
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				int address = area.getSelectedIndex();
				
				if(title > -1 && !name.isEmpty() && !password.isEmpty() && address > -1) {
					String query = "";
					String query1 = "";
					String query2 = "";
					String query3 = "";
					String query4 = "";
					String query5 = "";
					String query6 = "";
					if(title == User.CUSTOMER) {
						query = "SELECT COUNT(*) from customers WHERE username = '" + username + "';";
						query1 = "SELECT MAX(id) FROM customers;";
					}else {
						query = "SELECT COUNT(*) from employees WHERE username = '" + username + "';";
						query1 = "SELECT MAX(id) FROM employees;";
					}
					
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
						try {
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(query1);
							while(rs.next()) {
								id = rs.getInt("MAX(id)") + 1;
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						try {
							Statement stmt = conn.createStatement();
							if(title == User.CUSTOMER) {
								query2 = "INSERT INTO customers VALUES(" + id + ", '" + username + "', '" + password + "', '" + name + "', " + address + ");";
								query3 = "INSERT INTO customerratings VALUES (" + id + ", " + Menu.PE + ", 5, 1, 1);"; 
								query4 = "INSERT INTO customerratings VALUES (" + id + ", " + Menu.S + ", 5, 1, 1);"; 
								query5 = "INSERT INTO customerratings VALUES (" + id + ", " + Menu.MC + ", 5, 1, 1);"; 
								stmt.executeUpdate(query2);
								stmt.executeUpdate(query3);
								stmt.executeUpdate(query4);
								stmt.executeUpdate(query5);
								Main.setUser(new User(User.CUSTOMER, id, name, true));
							}else{
								query6 = "INSERT INTO employees VALUES(" + id + ", " + restID + ", '" + username + "', '" + password + 
										"', '" + name + "', " + title + ", 15, 5, 1, 5);";
								stmt.executeUpdate(query6);
							}
							if(title == User.COOK) {
								Main.setUser(new User(User.COOK, id, name, true));
								Main.initCook();
								Main.getFrame().dispose();
							}
							if(title == User.DELIVERY) {
								Main.setUser(new User(User.DELIVERY, id, name, true));
								Main.goToDeliveryPage();
								Main.getFrame().dispose();
							}
							if(title == User.SALES) {
								Main.setUser(new User(User.SALES, id, name, true));
								Main.goToSalesPage();
								Main.getFrame().dispose();
							}
							dispose();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}else {
					JOptionPane.showMessageDialog(null,"Please fill in all areas.");
				}
				
			}
			
		});
		
		c.gridx = 0; c.gridy = 0;
		panel.add(registerAs, c);
		c.gridx = 1;
		panel.add(rest, c);
		c.gridx = 0; c.gridy = 1;
		panel.add(name, c);
		c.gridx = 1;
		panel.add(nameField, c);
		c.gridx = 0; c.gridy = 2;
		panel.add(username, c);
		c.gridx = 1;
		panel.add(usernameField, c);
		c.gridx = 0; c.gridy = 3;
		panel.add(password, c);
		c.gridx = 1;
		panel.add(passwordField, c);
		c.gridx = 0; c.gridy = 4;
		panel.add(address, c);
		c.gridx = 1;
		panel.add(area, c);
		c.gridx = 0; c.gridy = 5;
		panel.add(register, c);
		c.gridx = 1;
		panel.add(message, c);
		
		setSize(500, 400);
		setContentPane(panel);
		setVisible(true);
	}
}