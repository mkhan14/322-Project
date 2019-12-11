package main;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;

public class cooks {
	private Connection conn = Main.getConnection();
	private JFrame frmCooks;
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private String[][] menu_val;
	private String del_itm = null;
	private int index = 0, ck_id = Main.getUser().getId();
	private int selectedRow;
		
	private JTable products;
	private JTable menu_items;
	private JTable order;
	private JTextField txtName;
	private JTextField txtTitle;
	private JTextField txtID;
	private JTextField txtRestaurant;
	private JTextField txtSalary;
	private JTextField txtRating;
		
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public cooks() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCooks = new JFrame();
		frmCooks.setTitle("Cooks");
		frmCooks.setBounds(100, 100, 780, 490);
		frmCooks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCooks.setVisible(true);

		JButton acceptBtn = new JButton("Accept");
		acceptBtn.setEnabled(false);
		
		JPanel panel = new JPanel();
		
		JPanel parent = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmCooks.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(parent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(parent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		parent.setLayout(new CardLayout(0, 0));
		
		JPanel blank = new JPanel();
		parent.add(blank, "name_149072434615400");
		
		JPanel supplies = new JPanel();
		parent.add(supplies, "name_133233477037700");
		
		JScrollPane Supplies_scrollPane = new JScrollPane();
		GroupLayout gl_supplies = new GroupLayout(supplies);
		gl_supplies.setHorizontalGroup(
			gl_supplies.createParallelGroup(Alignment.LEADING)
				.addComponent(Supplies_scrollPane, GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
		);
		gl_supplies.setVerticalGroup(
			gl_supplies.createParallelGroup(Alignment.LEADING)
				.addComponent(Supplies_scrollPane, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
		);
		
		products = new JTable() {
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		products.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		products.setRowSelectionAllowed(false);
		products.setCellSelectionEnabled(true);
		products.setFillsViewportHeight(true);
		products.getTableHeader().setReorderingAllowed(false);
//		parent.add(products);
		products.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product #", "Employee ID", "Product Name", "Price"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Integer.class, String.class, Float.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			
		});
		products.getColumnModel().getColumn(1).setPreferredWidth(100);
		products.getColumnModel().getColumn(2).setPreferredWidth(97);
		Supplies_scrollPane.setViewportView(products);
		supplies.setLayout(gl_supplies);
		
		JPanel menu = new JPanel();
		parent.add(menu, "name_133233487312999");
		
		JScrollPane Menu_scrollPane = new JScrollPane();
		
		menu_items = new JTable() {
			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 2 ? true:false;
			}
		};
		menu_items.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedRow = menu_items.getSelectedRow();
				del_itm = (String) menu_items.getValueAt(selectedRow,1);
			}
		});
		
		menu_items.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Restaurant ID", "Product Name", "Price"
			}
		));
		menu_items.getColumnModel().getColumn(0).setPreferredWidth(100);
		menu_items.getColumnModel().getColumn(1).setPreferredWidth(200);
		menu_items.getColumnModel().getColumn(2).setPreferredWidth(100);
		menu_items.setFillsViewportHeight(true);
		menu_items.getTableHeader().setReorderingAllowed(false);
		
		Menu_scrollPane.setViewportView(menu_items);
		
		JButton additem = new JButton("Add New Item");
		additem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addmenuitem();
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateValues();
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (del_itm != null)
					if(JOptionPane.showConfirmDialog(null, "Confirm delete item \""+del_itm+"\"?", "Delete Menu Item", JOptionPane.CANCEL_OPTION) == JOptionPane.YES_OPTION) {
						String query = "delete from menu where item = \"" + del_itm + "\"";
			    		Statement stmt;
			    		try {
			    			stmt = conn.createStatement();
			    			stmt.executeUpdate(query);
			    		} catch(SQLException f) {
			    			f.printStackTrace();
			    		}
			    		menu_items.setModel(refresh_menu());
			    		menu_items.getModel().addTableModelListener(
			    				new TableModelListener()
			    				{
			    					public void tableChanged(TableModelEvent e) {
			    						int selectedRowIndex;
			    						selectedRowIndex = menu_items.getSelectedRow();
			    						menu_val[index][0] = menu_items.getValueAt(selectedRowIndex, 1).toString();
			    						menu_val[index][1] = menu_items.getValueAt(selectedRowIndex, 2).toString();
			    						index++;
			    					}
			    				});
			    		del_itm = null;
					}else;
				else
					JOptionPane.showMessageDialog(null, "Please select an item.", "Warning", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		GroupLayout gl_menu = new GroupLayout(menu);
		gl_menu.setHorizontalGroup(
			gl_menu.createParallelGroup(Alignment.LEADING)
				.addComponent(Menu_scrollPane, GroupLayout.PREFERRED_SIZE, 762, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_menu.createSequentialGroup()
					.addGap(97)
					.addComponent(additem, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addGap(99)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addGap(97))
		);
		gl_menu.setVerticalGroup(
			gl_menu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menu.createSequentialGroup()
					.addComponent(Menu_scrollPane, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_menu.createParallelGroup(Alignment.BASELINE)
						.addComponent(additem, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addComponent(btnSave)
						.addComponent(btnDelete))
					.addContainerGap())
		);
		menu.setLayout(gl_menu);
		
		JPanel orders = new JPanel(); 
		parent.add(orders, "name_217698308502700");
		
		JScrollPane Order_scrollPane = new JScrollPane();
		
		order = new JTable(){
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		order.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedRow = order.getSelectedRow();
				if( order.getValueAt(selectedRow, 2) == null) {
					acceptBtn.setEnabled(true);
				}else {
					acceptBtn.setEnabled(false);
				}
			}
		});
		order.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Order ID", "Items", "Cook's ID"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		order.getColumnModel().getColumn(1).setPreferredWidth(250);
		order.getColumnModel().getColumn(2).setPreferredWidth(100);
		Order_scrollPane.setViewportView(order);
		
		acceptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				System.out.println(order.getValueAt(selectedRow, 2));
				String query = "update orders set cook_id = "+ck_id+" where order_id = "+order.getValueAt(selectedRow, 0);
	    		Statement stmt;
	    		try {
	    			stmt = conn.createStatement();
	    			stmt.executeUpdate(query);    			
	    		} catch(SQLException f) {
	    			f.printStackTrace();
	    		}
			}
		});
		GroupLayout gl_orders = new GroupLayout(orders);
		gl_orders.setHorizontalGroup(
			gl_orders.createParallelGroup(Alignment.LEADING)
				.addComponent(Order_scrollPane, GroupLayout.PREFERRED_SIZE, 762, GroupLayout.PREFERRED_SIZE)
				.addGroup(Alignment.TRAILING, gl_orders.createSequentialGroup()
					.addContainerGap(348, Short.MAX_VALUE)
					.addComponent(acceptBtn)
					.addGap(343))
		);
		gl_orders.setVerticalGroup(
			gl_orders.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_orders.createSequentialGroup()
					.addComponent(Order_scrollPane, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(acceptBtn)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		orders.setLayout(gl_orders);
		
		JPanel myaccount = new JPanel();
		parent.add(myaccount, "name_217765106836000");
		
		JLabel lblMyaccount = new JLabel("My Account");
		lblMyaccount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblName = new JLabel("Name :");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtName.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title :");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtTitle = new JTextField();
		txtTitle.setEditable(false);
		txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtTitle.setColumns(10);
		
		JLabel lblID = new JLabel("ID :");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtID.setColumns(10);
		
		JLabel lblRestaurant = new JLabel("Restaurant :");
		lblRestaurant.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtRestaurant = new JTextField();
		txtRestaurant.setEditable(false);
		txtRestaurant.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtRestaurant.setColumns(10);
		
		JLabel lblSalary = new JLabel("Salary :");
		lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtSalary = new JTextField();
		txtSalary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSalary.setEditable(false);
		txtSalary.setColumns(10);
		
		JLabel lblRating = new JLabel("Rating :");
		lblRating.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtRating = new JTextField();
		txtRating.setEditable(false);
		txtRating.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtRating.setColumns(10);
		
		JButton btnSave_1 = new JButton("Save");
		btnSave_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] value = myAccount();
				if(txtName.getText().compareTo(value[0]) == 0) {
					;
				}else {
					String query = "update employees set name = \""+txtName.getText()+"\" where id = " + txtID.getText();
		    		Statement stmt;
		    		try {
		    			stmt = conn.createStatement();
		    			stmt.executeUpdate(query);
		    		} catch(SQLException f) {
		    			f.printStackTrace();
		    		}
		    		String[] update = myAccount();
		    		txtName.setText(update[0]);
					JOptionPane.showMessageDialog(null,"Information Updated!", "Update", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnSave_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GroupLayout gl_myaccount = new GroupLayout(myaccount);
		gl_myaccount.setHorizontalGroup(
			gl_myaccount.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_myaccount.createSequentialGroup()
					.addGroup(gl_myaccount.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_myaccount.createSequentialGroup()
							.addGap(351)
							.addComponent(btnSave_1))
						.addGroup(gl_myaccount.createSequentialGroup()
							.addGap(135)
							.addGroup(gl_myaccount.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_myaccount.createSequentialGroup()
									.addGroup(gl_myaccount.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblID)
										.addComponent(lblName)
										.addComponent(lblSalary))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_myaccount.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_myaccount.createSequentialGroup()
											.addComponent(txtSalary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(lblRating))
										.addGroup(gl_myaccount.createSequentialGroup()
											.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(81)
											.addComponent(lblTitle))
										.addGroup(gl_myaccount.createSequentialGroup()
											.addComponent(txtID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(lblRestaurant)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_myaccount.createParallelGroup(Alignment.LEADING)
										.addComponent(txtRating, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtRestaurant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_myaccount.createSequentialGroup()
									.addGap(200)
									.addComponent(lblMyaccount)))))
					.addContainerGap(135, Short.MAX_VALUE))
		);
		gl_myaccount.setVerticalGroup(
			gl_myaccount.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_myaccount.createSequentialGroup()
					.addGap(16)
					.addComponent(lblMyaccount)
					.addGap(25)
					.addGroup(gl_myaccount.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName)
						.addComponent(lblTitle))
					.addGap(41)
					.addGroup(gl_myaccount.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblID)
						.addComponent(txtID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRestaurant)
						.addComponent(txtRestaurant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(41)
					.addGroup(gl_myaccount.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSalary)
						.addComponent(txtSalary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRating)
						.addComponent(txtRating, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addComponent(btnSave_1)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		myaccount.setLayout(gl_myaccount);
		
		
		JButton btnViewSupplies = new JButton("View Supplies");
		btnViewSupplies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.removeAll();
				products.setModel(refresh_supply());
				parent.add(supplies);
				parent.repaint();
				parent.revalidate();
			}
		});
		
		JButton btnSetMenuItems = new JButton("Set Menu Items");
		btnSetMenuItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.removeAll();
				menu_items.setModel(refresh_menu());
				parent.add(menu);
				parent.repaint();
				parent.revalidate();
				menu_val = new String[menu_items.getRowCount()][2];
				menu_items.getModel().addTableModelListener(
				new TableModelListener()
				{
					public void tableChanged(TableModelEvent e) {
						int selectedRowIndex;
						selectedRowIndex = menu_items.getSelectedRow();
						menu_val[index][0] = menu_items.getValueAt(selectedRowIndex, 1).toString();
						menu_val[index][1] = menu_items.getValueAt(selectedRowIndex, 2).toString();
						index++;
					}
				});
			}
		});
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.setUser(null);
				Main.goToRestaurantPage();
				frmCooks.dispose();
			}
		});
		
		JButton btnViewOrders = new JButton("View Orders");
		btnViewOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.removeAll();
				order.setModel(refresh_order());
				
				parent.add(orders);
				parent.repaint();
				parent.revalidate();
			}
		});
		
		JButton btnMyAccount = new JButton("My Account");
		btnMyAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] values = myAccount();
				parent.removeAll();		
				txtName.setText(values[0]);
				txtTitle.setText(values[1]);
				txtID.setText(values[2]);
				switch(Integer.parseInt(values[3])) {
					case 0:
						txtRestaurant.setText("Panda Express");
						break;
					case 1:
						txtRestaurant.setText("Sakura");
						break;
					case 2:
						txtRestaurant.setText("Masala Cafe");
						break;
					default:
						txtRestaurant.setText("Unknown");
				}
				txtSalary.setText(values[4]);
				txtRating.setText(values[5]);
				parent.add(myaccount);
				parent.repaint();
				parent.revalidate();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(94)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSetMenuItems, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnViewSupplies, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
					.addGap(86)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnMyAccount, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
						.addComponent(btnViewOrders, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
					.addGap(79)
					.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
					.addGap(93))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 1, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(btnSetMenuItems, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnViewOrders, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
									.addComponent(btnViewSupplies, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
								.addGap(28)
								.addComponent(btnMyAccount, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))))
					.addGap(21))
		);
		panel.setLayout(gl_panel);
		frmCooks.getContentPane().setLayout(groupLayout);
	}
//	public void tableChanged(TableModelEvent e) {
//		int row = e.getFirstRow();
//		int column = e.getColumn();
//		Object data = menu_items.getValueAt(row, column);
//		System.out.println(data);
//	}
	private void addmenuitem() { 
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Item name:"));
        panel.add(field1);
        panel.add(new JLabel("Price:"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Menu Item",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
    		String query = "insert into menu values(1, '"+field1.getText().toString()+"', "+Float.parseFloat(field2.getText().toString())+", 5, 1)";
    		Statement stmt;
    		try {
    			stmt = conn.createStatement();
    			stmt.executeUpdate(query);    			
    		} catch(SQLException e) {
    			e.printStackTrace();
    		}
    		menu_items.setModel(refresh_menu());
        }
	}
	public String[] myAccount() {
		String[] result = new String[6];
		String query = "select name, job_title, id, rest_id, salary, avg_rating from employees where id = " + ck_id;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String[] value = {rs.getString("name"), rs.getString("job_title"), rs.getString("id"), rs.getString("rest_id"), rs.getString("salary"), rs.getString("avg_rating")};
				result = value;
			}			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public DefaultTableModel refresh_menu() {
		DefaultTableModel menu_model = new DefaultTableModel(new Object[] {"Restaurant ID", "Item", "Price"},0);
		String query2 = "Select distinct m.rest_id, item, price from menu m, employees e where m.rest_id = e.rest_id";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			while(rs.next()) {
				Object row[] = {rs.getInt("rest_id"), rs.getString("item"), rs.getFloat("price")};
				menu_model.addRow(row);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return menu_model;
	}
	public DefaultTableModel refresh_supply() {
		DefaultTableModel supply_model = new DefaultTableModel(new Object[] {"Product #", "Employee ID", "Product Name", "Price"},0);
		String query = "Select * from supplies";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Object row[] = {rs.getInt("id"), rs.getInt("empl_id"), rs.getString("product_name"), rs.getFloat("price")};
				supply_model.addRow(row);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return supply_model;
	}
	public DefaultTableModel refresh_order() {
		DefaultTableModel order_model = new DefaultTableModel(new Object[] {"Order ID", "Items", "Cook's ID"},0);
		String query = "select h.order_id,item,cook_id from chowtown.orders o,chowtown.orderhistory h order by order_id;";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Object row[] = {rs.getInt("order_id"), rs.getString("item"), rs.getString("cook_id")};
				order_model.addRow(row);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return order_model;
	}
	public void updateValues() {		
		for(int i = 0; i < index; i++) {
			String query = "update menu set price = "+menu_val[i][1]+" where item = \"" +menu_val[i][0]+"\"";
    		Statement stmt;
    		try {
    			stmt = conn.createStatement();
    			stmt.executeUpdate(query);
    		} catch(SQLException e) {
    			e.printStackTrace();
    		}
		}
		menu_items.setModel(refresh_menu());
		menu_items.getModel().addTableModelListener(
				new TableModelListener()
				{
					public void tableChanged(TableModelEvent e) {
						int selectedRowIndex;
						selectedRowIndex = menu_items.getSelectedRow();
						menu_val[index][0] = menu_items.getValueAt(selectedRowIndex, 1).toString();
						menu_val[index][1] = menu_items.getValueAt(selectedRowIndex, 2).toString();
						index++;
					}
				});
		index = 0;
	}
}
