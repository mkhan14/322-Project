package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Restaurant {
	private Connection conn = Main.getConnection();
	private GridBagConstraints c = new GridBagConstraints();
	
	public JPanel createPage() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(headers(), BorderLayout.NORTH);
		
		JPanel rest = new JPanel();
		rest.setLayout(new GridLayout(3,1));
		rest.add(pandaExpress());
		rest.add(sakura());
		rest.add(masalaCafe());
		panel.add(rest, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel headers() {
		JPanel headers = new JPanel();
		
		JButton myAccount = new JButton("My Account");
		myAccount.setPreferredSize(new Dimension(150, 40));
		myAccount.setFont(new Font("monospaced", Font.PLAIN, 20));
		myAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.goToMyAccount();
			}
			
		});
		
		JButton login = new JButton("Login");
		login.setPreferredSize(new Dimension(100, 40));
		login.setFont(new Font("monospaced", Font.PLAIN, 20));
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.goToLogin();
			}
			
		});
		
		headers.add(myAccount);
		headers.add(login);
		
		return headers;
	}
	
	private JPanel pandaExpress() {
		JPanel pandaExpress = new JPanel();
		pandaExpress.setLayout(new GridBagLayout());
		
		JLabel PELabel = new JLabel("Panda Express");
		PELabel.setFont(new Font("monospaced", Font.PLAIN, 30));
	
		ImageIcon PEImage = new ImageIcon("images/China.png");
		JButton PEButton = new JButton(PEImage);
		PEButton.setSize(400, 400);
		PEButton.setContentAreaFilled(false);
		PEButton.setBorderPainted(false);
		PEButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isBlacklisted(Menu.PE))
					Main.goToMenu(Menu.PE);
				else
					JOptionPane.showMessageDialog(null,"You have been blacklisted.");
			}
			
		});
		
		c.gridx = 0;
		c.gridy = 0;
		pandaExpress.add(PELabel, c);
		
		c.gridy = 1;
		pandaExpress.add(PEButton, c);
		
		return pandaExpress;
	}
	
	private JPanel sakura() {
		JPanel sakura = new JPanel();
		sakura.setLayout(new GridBagLayout());
		
		JLabel SLabel = new JLabel("Sakura");
		SLabel.setFont(new Font("monospaced", Font.PLAIN, 30));
		
		ImageIcon SImage = new ImageIcon("images/Japan.png");
		JButton SButton = new JButton(SImage);
		SButton.setSize(SImage.getIconWidth(), SImage.getIconHeight());
		SButton.setContentAreaFilled(false);
		SButton.setBorderPainted(false);
		SButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isBlacklisted(Menu.S))
					Main.goToMenu(Menu.S);
				else
					JOptionPane.showMessageDialog(null,"You have been blacklisted.");
			}
			
		});
		
		c.gridx = 0;
		c.gridy = 0;
		sakura.add(SLabel, c);
		
		c.gridy = 1;
		sakura.add(SButton, c);
		
		return sakura;
	}
	
	private JPanel masalaCafe() {
		JPanel masalaCafe = new JPanel();
		masalaCafe.setLayout(new GridBagLayout());
		
		JLabel MCLabel = new JLabel("Masala Cafe");
		MCLabel.setFont(new Font("monospaced", Font.PLAIN, 30));
		
		ImageIcon MCImage = new ImageIcon("images/India.png");
		JButton MCButton = new JButton(MCImage);
		MCButton.setSize(MCImage.getIconWidth(), MCImage.getIconHeight());
		MCButton.setContentAreaFilled(false);
		MCButton.setBorderPainted(false);
		MCButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isBlacklisted(Menu.MC))
					Main.goToMenu(Menu.MC);
				else
					JOptionPane.showMessageDialog(null,"You have been blacklisted.");
			}
			
		});
		
		c.gridx = 0;
		c.gridy = 0;
		masalaCafe.add(MCLabel, c);
		
		c.gridy = 1;
		masalaCafe.add(MCButton, c);
		
		return masalaCafe;
	}
	
	private boolean isBlacklisted(int restID) {
		int status = getStatus(restID);
		if(status == User.BLACKLIST)
			return true;
		return false;
	}
	
	public int getStatus(int restID) {
		User user = Main.getUser();
		int status = -1;
		if(user != null && user.getTitle() == User.CUSTOMER) {
			int id = user.getId();
			String query = "SELECT status FROM customerratings WHERE cust_id = " + id + " AND rest_id = " + restID + ";";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					status = rs.getInt("status");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return status;
		}
		return status;
	}
}
