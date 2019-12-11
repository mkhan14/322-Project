package main;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeliveryGuyPage {
	public JPanel mainpage() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		
		JLabel name = new JLabel("Delivery Person's Page");
		name.setFont(new Font("monospaced", Font.PLAIN, 28));
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setVerticalAlignment(JLabel.CENTER);
		panel.add(name);
		
		JButton bidButton = new JButton("BID");
		bidButton.addActionListener(new ActionListener() {
			@Override
			public void actionPrformed(ActionEvent e) {
				
			}
		})
	}
	
}
