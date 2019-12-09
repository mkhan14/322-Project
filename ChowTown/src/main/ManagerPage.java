package main;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManagerPage {

	private static Manager manager;
	
	public JPanel createPage() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		
		JLabel pageName = new JLabel("Manager's Home Page");
		pageName.setFont(new Font("monospaced", Font.PLAIN, 28));
		pageName.setHorizontalAlignment(JLabel.CENTER);
		pageName.setVerticalAlignment(JLabel.CENTER);
		panel.add(pageName);
		
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
	   
	   JButton orderBtn = new JButton("View Order Info");
	   panel.add(orderBtn);
	   
	   return panel;
	}

}
