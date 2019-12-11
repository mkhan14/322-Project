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
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

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

public class cooks {

	private JFrame frmCooks;
	private static final String url = "jdbc:mysql://localhost:3306/chowtown";
	private static final String user = "root";
	private static final String password = "Love9420516@";
	private static Connection conn = null;
	private String[][] menu_val;
	private String del_itm = null;
	private int index = 0;
	private int selectedRow;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cooks window = new cooks();
					window.frmCooks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	ArrayList<prods> pros = new ArrayList<prods>();
	private JTable products;
	private JTable menu_items;
	private JTable order;
		
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
			    			conn = DriverManager.getConnection(url, user, password);
			    			stmt = conn.createStatement();
			    			stmt.executeUpdate(query);
			    		} catch(SQLException f) {
			    			f.printStackTrace();
			    		}finally {
			    			try {
			    				conn.close();
			    			} catch (SQLException e1) {
			    				e1.printStackTrace();
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
		GroupLayout gl_orders = new GroupLayout(orders);
		gl_orders.setHorizontalGroup(
			gl_orders.createParallelGroup(Alignment.LEADING)
				.addComponent(Order_scrollPane, GroupLayout.PREFERRED_SIZE, 762, GroupLayout.PREFERRED_SIZE)
		);
		gl_orders.setVerticalGroup(
			gl_orders.createParallelGroup(Alignment.LEADING)
				.addComponent(Order_scrollPane, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
		);
		
		order = new JTable();
		order.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Accept/Transfer", "Order ID", "Items", "Cook's ID"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		order.getColumnModel().getColumn(0).setPreferredWidth(118);
		order.getColumnModel().getColumn(2).setPreferredWidth(235);
		order.getColumnModel().getColumn(3).setPreferredWidth(97);
		Order_scrollPane.setViewportView(order);
		orders.setLayout(gl_orders);
		
		JPanel cancel_order = new JPanel();
		parent.add(cancel_order, "name_217724233392200");
		
		JPanel myaccount = new JPanel();
		parent.add(myaccount, "name_217765106836000");
		
		
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
//						System.out.println(menu_val[index][0]+" "+menu_val[index][1]);
						index++;
					}
				});
			}
		});
		
		JButton btnLogOut = new JButton("Log out");
		
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
		
		JButton btnCancelledOrders = new JButton("Cancelled Orders");
		
		JButton btnMyAccount = new JButton("My Account");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(99)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSetMenuItems, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnViewSupplies, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
					.addGap(86)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnViewOrders, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
							.addGap(83))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnCancelledOrders)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnMyAccount, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
					.addGap(99))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSetMenuItems, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnViewOrders, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
								.addComponent(btnMyAccount, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnViewSupplies, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
							.addGap(28)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnCancelledOrders, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))))
					.addGap(23))
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
    		String query = "insert into menu values(1, '"+field1.getText().toString()+"', "+Float.parseFloat(field2.getText().toString())+")";
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
    			menu_items.setModel(refresh_menu());
    		}
        } else {
            
        }
	}
	public DefaultTableModel refresh_menu() {
		DefaultTableModel menu_model = new DefaultTableModel(new Object[] {"Restaurant ID", "Item", "Price"},0);
		String query2 = "Select distinct m.rest_id, item, price from menu m, employees e where m.rest_id = e.rest_id";
		Statement stmt;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			while(rs.next()) {
				Object row[] = {rs.getInt("rest_id"), rs.getString("item"), rs.getFloat("price")};
				menu_model.addRow(row);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return menu_model;
	}
	public DefaultTableModel refresh_supply() {
		DefaultTableModel supply_model = new DefaultTableModel(new Object[] {"Product #", "Employee ID", "Product Name", "Price"},0);
		String query = "Select * from supplies";
		Statement stmt;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Object row[] = {rs.getInt("id"), rs.getInt("empl_id"), rs.getString("product_name"), rs.getFloat("price")};
				supply_model.addRow(row);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return supply_model;
	}
	public DefaultTableModel refresh_order() {
		DefaultTableModel order_model = new DefaultTableModel(new Object[] {"Accept/Transfer", "Order ID", "Items", "Cook's ID"},0);
		String query = "select null,h.order_id,item,order_time,cook_id from chowtown.orders o,chowtown.orderhistory h order by order_id;";
		Statement stmt;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Object row[] = {null, rs.getInt("order_id"), rs.getString("item"), rs.getInt("cook_id")};
				order_model.addRow(row);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return order_model;
	}
	public void updateValues() {		
		for(int i = 0; i < index; i++) {
			String query = "update menu set price = "+menu_val[i][1]+" where item = \"" +menu_val[i][0]+"\"";
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
