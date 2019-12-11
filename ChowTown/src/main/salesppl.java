package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class salesppl {

	private JFrame frame;
	private JTable supply;
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "Love9420516@";
	private static Connection conn = null;
	private int selectedRow;
	private String[][] menu_val;
	private int index = 0;
	
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
		parent.setLayout(new CardLayout(0, 0));
		
		JPanel MyAccount = new JPanel();
		parent.add(MyAccount, "name_313985598329700");
		parent.add(Supplies, "name_313985649504000");
		
		JScrollPane supply_scrollPane = new JScrollPane();
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField field1 = new JTextField("");
		        JTextField field2 = new JTextField("");
		        JPanel panel = new JPanel(new GridLayout(0, 1));
		        panel.add(new JLabel("Product name:"));
		        panel.add(field1);
		        panel.add(new JLabel("Price:"));
		        panel.add(field2);
		        int result = JOptionPane.showConfirmDialog(null, panel, "Add Supply Products",
		            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		        	int id = supply.getRowCount()+1;
		    		String query = "insert into supplies values(" + id + ", 0, '" + field1.getText().toString()+"', "+Float.parseFloat(field2.getText().toString())+")";
		    		Statement stmt;
		    		try {
		    			conn = DriverManager.getConnection(url, user, password);
		    			stmt = conn.createStatement();
		    			stmt.executeUpdate(query);    			
		    		} catch(SQLException e) {
		    			e.printStackTrace();
		    		}finally {
		    			try {
		    				conn.close();
		    			} catch (SQLException e1) {
		    				e1.printStackTrace();
		    			}
		    			supply.setModel(refresh_supply());
		    		}
		        } else {
		            
		        }
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateValues();
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout gl_Supplies = new GroupLayout(Supplies);
		gl_Supplies.setHorizontalGroup(
			gl_Supplies.createParallelGroup(Alignment.LEADING)
				.addComponent(supply_scrollPane, GroupLayout.PREFERRED_SIZE, 656, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_Supplies.createSequentialGroup()
					.addGap(205)
					.addComponent(btnAdd)
					.addGap(81)
					.addComponent(btnSave))
		);
		gl_Supplies.setVerticalGroup(
			gl_Supplies.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Supplies.createSequentialGroup()
					.addGap(2)
					.addComponent(supply_scrollPane, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_Supplies.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSave)
						.addComponent(btnAdd))
					.addContainerGap())
		);
		
		supply = new JTable() {
			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 2 ? true:false;
			}
		};
		supply.setFont(new Font("Tahoma", Font.PLAIN, 16));
		supply.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Product Name", "Price"
			}
		));
		supply.getColumnModel().getColumn(0).setPreferredWidth(50);
		supply.getColumnModel().getColumn(1).setPreferredWidth(360);
		supply.getColumnModel().getColumn(2).setPreferredWidth(150);
		supply_scrollPane.setViewportView(supply);
		Supplies.setLayout(gl_Supplies);
		
		JButton btnVieweditSupplies = new JButton("View/Edit Supplies");
		btnVieweditSupplies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.removeAll();
				supply.setModel(refresh_supply());
				parent.add(Supplies);
				parent.repaint();
				parent.revalidate();
			}
		});
		btnVieweditSupplies.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout gl_menu = new GroupLayout(menu);
		gl_menu.setHorizontalGroup(
			gl_menu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menu.createSequentialGroup()
					.addGap(102)
					.addComponent(btnVieweditSupplies, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
					.addGap(151)
					.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(69, Short.MAX_VALUE))
		);
		gl_menu.setVerticalGroup(
			gl_menu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menu.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_menu.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLogOut, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
						.addComponent(btnVieweditSupplies, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
					.addGap(7))
		);
		menu.setLayout(gl_menu);
		frame.getContentPane().setLayout(groupLayout);
	}
	public DefaultTableModel refresh_supply() {
		DefaultTableModel supply_model = new DefaultTableModel(new Object[] {"ID", "Product Name", "Price"},0);
		String query = "Select * from supplies";
		Statement stmt;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Object row[] = {rs.getInt("id"), rs.getString("product_name"), rs.getFloat("price")};
				supply_model.addRow(row);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return supply_model;
	}
	public void updateValues() {
		for(int i = 0; i < supply.getRowCount(); i++) {
			String query = "update supplies set price = " + supply.getValueAt(i,2)+" where id = " + supply.getValueAt(i,0);
    		Statement stmt;
    		try {
    			conn = DriverManager.getConnection(url, user, password);
    			stmt = conn.createStatement();
    			stmt.executeUpdate(query);
    		} catch(SQLException e) {
    			e.printStackTrace();
    		}finally {
    			try {
    				conn.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
    		}
		}
		supply.setModel(refresh_supply());
	}
}
