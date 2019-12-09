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


public class Login extends JFrame{

	
	/*JButton login;
	JPanel loginpanel;
	JTextField userField;
	JTextField passField;
	JLabel username;
	JLabel password;


	public Login(){
		super("Login to ChowTown");

		login = new JButton("Login");
		loginpanel = new JPanel();
		userField = new JTextField(15);
		passField = new JPasswordField(15);
		username = new JLabel("Username:");
		password = new JLabel("Password:");

		setSize(300,200);
		setLocation(500,280);
		loginpanel.setLayout (null); 


		userField.setBounds(90,30,150,20);
		passField.setBounds(90,65,150,20);
		login.setBounds(110,100,80,20);
		username.setBounds(20,28,80,20);
		password.setBounds(20,63,800,20);

		loginpanel.add(login);
		loginpanel.add(userField);
		loginpanel.add(passField);
		loginpanel.add(username);
		loginpanel.add(password);

		getContentPane().add(loginpanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Writer writer = null;
		File check = new File("userCred.txt");
		if(check.exists()){

		}else{
			try{
				File textFile = new File("userCred.txt");
				writer = new BufferedWriter(new FileWriter(textFile));
			}catch(IOException e){
				e.printStackTrace();
			}
		}




		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File("userCred.txt");
					Scanner scan = new Scanner(file);
					String line = null;
					FileWriter filewrite = new FileWriter(file, true);

					String usertxt = " ";
					String passtxt = " ";
          			String fileuname = userField.getText();
          			String filepaswd = passField.getText();


          			if(fileuname.equals("") && filepaswd.equals("")){
          				JOptionPane.showMessageDialog(null,"Please insert Username and Password");
          			}
          			boolean c = false;
          			while (scan.hasNext()) {
          				usertxt = scan.nextLine();
          				passtxt = scan.nextLine();
						if(fileuname.equals(usertxt) && filepaswd.equals(passtxt)) {
						    c = true;    
						    JOptionPane.showMessageDialog(null,"Welcome to ChowTown!");
						     setVisible(false);
						     break;
						  }
						
						break;
          			}
          			if (c == false) {
          				JOptionPane.showMessageDialog(null,"Wrong Username / Password");
          				userField.setText("");
          				passField.setText("");
          			}
          			} 
					catch (IOException d) {
					d.printStackTrace();
				}

			}
		});
	} */
	
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
		//int address = -1;
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

