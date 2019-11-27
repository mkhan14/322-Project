package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Restaurant {
	public static void main(String args[]) {
		JFrame restaurantPage = new JFrame();;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,3));
		
		JPanel restaurant1 = new JPanel(new BorderLayout(1,1));
		JLabel ch = new JLabel("Chinese Restaurant");
		ch.setFont(new Font("monospaced", Font.PLAIN, 20));
		ch.setHorizontalAlignment(JLabel.CENTER);
		ch.setVerticalAlignment(JLabel.CENTER);
		ImageIcon icon1 = new ImageIcon("images/China.png");
		JLabel img1 = new JLabel(icon1);
		restaurant1.add(ch, BorderLayout.PAGE_START);
		restaurant1.add(img1, BorderLayout.CENTER);
		panel.add(restaurant1);
		
		JPanel restaurant2 = new JPanel();
		restaurant2.setLayout(new BorderLayout());
		JLabel jp = new JLabel("Japanese Restaurant");
		jp.setFont(new Font("monospaced", Font.PLAIN, 20));
		jp.setHorizontalAlignment(JLabel.CENTER);
		jp.setVerticalAlignment(JLabel.CENTER);
		ImageIcon icon2 = new ImageIcon("images/Japan.png");
		JLabel img2 = new JLabel(icon2);
		restaurant2.add(jp, BorderLayout.PAGE_START);
		restaurant2.add(img2, BorderLayout.CENTER);
		panel.add(restaurant2);
		
		JPanel restaurant3 = new JPanel(new BorderLayout());
		JLabel in = new JLabel("Indian Restaurant");
		in.setFont(new Font("monospaced", Font.PLAIN, 20));
		in.setHorizontalAlignment(JLabel.CENTER);
		in.setVerticalAlignment(JLabel.CENTER);
		ImageIcon icon3 = new ImageIcon("images/India.png");
		JLabel img3 = new JLabel(icon3);
		restaurant3.add(in, BorderLayout.PAGE_START);
		restaurant3.add(img3, BorderLayout.CENTER);
		panel.add(restaurant3);
		
		JPanel restaurant4 = new JPanel(new BorderLayout());
		JLabel ko = new JLabel("Korean Restaurant");
		ko.setFont(new Font("monospaced", Font.PLAIN, 20));
		ko.setHorizontalAlignment(JLabel.CENTER);
		ko.setVerticalAlignment(JLabel.CENTER);
		ImageIcon icon4 = new ImageIcon("images/SouthKorea.png");
		JLabel img4 = new JLabel(icon4);
		restaurant4.add(ko, BorderLayout.PAGE_START);
		restaurant4.add(img4, BorderLayout.CENTER);
		panel.add(restaurant4);
		
		JPanel restaurant5 = new JPanel(new BorderLayout());
		JLabel vi = new JLabel("Vietnamese Restaurant");
		vi.setFont(new Font("monospaced", Font.PLAIN, 20));
		vi.setHorizontalAlignment(JLabel.CENTER);
		vi.setVerticalAlignment(JLabel.CENTER);
		ImageIcon icon5 = new ImageIcon("images/Vietnam.png");
		JLabel img5 = new JLabel(icon5);
		restaurant5.add(vi, BorderLayout.PAGE_START);
		restaurant5.add(img5, BorderLayout.CENTER);
		panel.add(restaurant5);
		
		restaurantPage.add(panel);
    	restaurantPage.setSize(800, 800);
        restaurantPage.setVisible(true);
        restaurantPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
