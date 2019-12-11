package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManagerPage {

	//private static Manager manager;
	
	final static String[] rest = {"Panda Express", "Sakura", "Masala Cafe"};
	
	public JPanel createPage() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		
		if(Main.getUser().getId() == 0)
			panel.setBackground(new Color(255, 255, 200));
		if(Main.getUser().getId() == 1)
			panel.setBackground(new Color(255, 230, 245));
		if(Main.getUser().getId() == 2)
			panel.setBackground(new Color(240, 255, 220));
		
		JLabel pageName = new JLabel(rest[Main.getUser().getId()] + "'s" + " Manager Page");
		pageName.setFont(new Font("monospaced", Font.PLAIN, 28));
		pageName.setHorizontalAlignment(JLabel.CENTER);
		pageName.setVerticalAlignment(JLabel.CENTER);
		panel.add(pageName);
		
		if(Main.getUser().getId() == 0) {
			ImageIcon icon = new ImageIcon("images/China.png");
			JLabel img = new JLabel(icon);
			panel.add(img);
		}
		if(Main.getUser().getId() == 1) {
			ImageIcon icon = new ImageIcon("images/Japan.png");
			JLabel img = new JLabel(icon);
			panel.add(img);
		}
		if(Main.getUser().getId() == 2) {
			ImageIcon icon = new ImageIcon("images/India.png");
			JLabel img = new JLabel(icon);
			panel.add(img);
		}
		
		
		JButton customerBtn = new JButton("View Customer Info");
		customerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.goToInfo(Info.CI);
			}
			
		});
	   panel.add(customerBtn);
	   
	   
	   JButton employeeBtn = new JButton("View Employee Info");
	   employeeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.goToInfo(Info.EI);
			}
			
		});
	   panel.add(employeeBtn);
	   
	   //JButton orderBtn = new JButton("View Order Info");
	   //panel.add(orderBtn);
	   JButton logoutBtn = new JButton("Logout");
	   logoutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.setUser(null);
				Main.goToRestaurantPage();
			}
			
		});
	   panel.add(logoutBtn);
	   
	   return panel;
	}

}
