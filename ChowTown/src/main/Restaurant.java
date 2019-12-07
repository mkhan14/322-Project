package main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Restaurant {

	private GridBagConstraints c = new GridBagConstraints();

	public JPanel createPage() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		
		
		panel.add(addPE());
		panel.add(addS());
		panel.add(addMC());
		
		return panel;
	}
	
	private JPanel addPE() {
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
				Main.goToMenu(Menu.PE);
			}
			
		});
		
		c.gridx = 0;
		c.gridy = 0;
		pandaExpress.add(PELabel, c);
		
		c.gridy = 1;
		pandaExpress.add(PEButton, c);
		
		return pandaExpress;
	}
	
	private JPanel addS() {
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
				Main.goToMenu(Menu.S);
			}
			
		});
		
		c.gridx = 0;
		c.gridy = 0;
		sakura.add(SLabel, c);
		
		c.gridy = 1;
		sakura.add(SButton, c);
		
		return sakura;
	}
	
	private JPanel addMC() {
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
				Main.goToMenu(Menu.MC);
			}
			
		});
		
		c.gridx = 0;
		c.gridy = 0;
		masalaCafe.add(MCLabel, c);
		
		c.gridy = 1;
		masalaCafe.add(MCButton, c);
		
		return masalaCafe;
	}
}
