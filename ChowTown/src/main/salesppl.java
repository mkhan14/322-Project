package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;

public class salesppl {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					salesppl window = new salesppl();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public salesppl() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 673, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel menu = new JPanel();
		
		JPanel parent = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menu, GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
				.addComponent(parent, GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(menu, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(parent, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
		);
		
		JPanel Supplies = new JPanel();
		
		JPanel MyAccount = new JPanel();
		GroupLayout gl_parent = new GroupLayout(parent);
		gl_parent.setHorizontalGroup(
			gl_parent.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_parent.createSequentialGroup()
					.addComponent(MyAccount, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Supplies, GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE))
		);
		gl_parent.setVerticalGroup(
			gl_parent.createParallelGroup(Alignment.LEADING)
				.addComponent(Supplies, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
				.addComponent(MyAccount, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
		);
		parent.setLayout(gl_parent);
		
		JButton btnVieweditSupplies = new JButton("View/Edit Supplies");
		btnVieweditSupplies.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnMyAccount = new JButton("My Account");
		btnMyAccount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout gl_menu = new GroupLayout(menu);
		gl_menu.setHorizontalGroup(
			gl_menu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menu.createSequentialGroup()
					.addGap(68)
					.addComponent(btnVieweditSupplies)
					.addGap(58)
					.addComponent(btnMyAccount)
					.addGap(71)
					.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(68, Short.MAX_VALUE))
		);
		gl_menu.setVerticalGroup(
			gl_menu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menu.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_menu.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnMyAccount)
						.addComponent(btnLogOut, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
					.addGap(7))
				.addGroup(gl_menu.createSequentialGroup()
					.addGap(27)
					.addComponent(btnVieweditSupplies)
					.addContainerGap(27, Short.MAX_VALUE))
		);
		menu.setLayout(gl_menu);
		frame.getContentPane().setLayout(groupLayout);
	}
}
