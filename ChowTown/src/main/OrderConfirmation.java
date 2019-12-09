package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OrderConfirmation extends JFrame{

	public OrderConfirmation(ArrayList<Menu.Item> cart) {
		JPanel panel = new JPanel(new GridBagLayout());
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(700, 300));
        textArea.setEditable(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 20));
        double totalPrice = 0;
        
        Hashtable<String, Integer> order = new Hashtable<String, Integer>();
        for(Menu.Item i : cart) {
        	if(order.containsKey(i.getName()))
        		order.replace(i.getName(),  order.get(i.getName()) + 1);
        	else
        		order.put(i.getName(), 1);
        	totalPrice += i.getPrice();
        }
        
        order.forEach((name, quantity) -> {
        	String str = name + " x " + quantity;
        	textArea.append(str + "\n");
        });
        textArea.append("\nTotal Price: " + Double.toString(totalPrice) + "\n");
        textArea.append("\nOrder has been confirmed!!\n");
        textArea.append("Please go to your order history to see if your order has been approved.\n");
        textArea.append("You have a 10min grace period to cancel your order.\n");
        panel.add(textArea);
        
		setSize(700,600);
		setContentPane(panel);
	}
	
	public void addOrderToDatabase() {
		//on what condition is it approved?
	}

}
