package main;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class Login extends JFrame{

	
	JButton login;
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
	} 
}

