package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Info {
	
	final static int CI = 0;
	final static int EI = 1;
	//final static int OI = 2;
	final static int LO = 2;
	
	final static String[] statString = {"BLACKLISTED", "REGISTERED ", "VIP"};
	final static String[] jobString = {"COOK", "DELIVERY PERSON", "SALESPERSON"};
	
	private Connection conn = Main.getConnection();
	/*private ArrayList<String> customers = new ArrayList<String>();
	private ArrayList<Integer> addresses = new ArrayList<Integer>();
	private ArrayList<Integer> cust_status = new ArrayList<Integer>();
	private ArrayList<Double> cust_avg_ratings = new ArrayList<Double>();
	private ArrayList<Integer> cust_ids = new ArrayList<Integer>();*/
	
	private ArrayList<String> customers;
	private ArrayList<Integer> addresses;
	private ArrayList<Integer> cust_status;
	private ArrayList<Double> cust_avg_ratings;
	private ArrayList<Integer> cust_ids;
	private ArrayList<Integer> numRateds;
	
	private ArrayList<String> employees;
	private ArrayList<Integer> jobTitles;
	private ArrayList<Double> salaries;
	private ArrayList<Double> empl_avg_ratings;
	private ArrayList<Double> lThree;
	private ArrayList<Integer> empl_ids;
	private ArrayList<Integer> num_warnings;
	private ArrayList<Integer> emplnumRateds;
	
	private static Manager manager;
	

	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel generateInfo(int infoID, int restID) {
		customers = new ArrayList<String>();
		addresses = new ArrayList<Integer>();
		cust_status = new ArrayList<Integer>();
		cust_avg_ratings = new ArrayList<Double>();
		cust_ids = new ArrayList<Integer>();
		numRateds = new ArrayList<Integer>();
		
		
		employees = new ArrayList<String>();
		jobTitles = new ArrayList<Integer>();
		salaries = new ArrayList<Double>();
		empl_avg_ratings = new ArrayList<Double>();
		lThree = new ArrayList<Double>();
		empl_ids = new ArrayList<Integer>();
		num_warnings = new ArrayList<Integer>();
		emplnumRateds = new ArrayList<Integer>();
		
		String query = "SELECT name,address,avg_rating,status,id,num_rated FROM customers JOIN customerratings WHERE rest_id = " + restID + " AND cust_id = id";
		String query2 = "SELECT name,job_title,salary,avg_rating,last_three,id,warning,num_rated FROM employees WHERE rest_id = " + restID;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				customers.add(rs.getString("name"));
				addresses.add(rs.getInt("address"));
				cust_avg_ratings.add(rs.getDouble("avg_rating"));
				cust_status.add(rs.getInt("status"));
				cust_ids.add(rs.getInt("id"));
				numRateds.add(rs.getInt("num_rated"));
			}
			
			ResultSet rs2 = stmt.executeQuery(query2);
			while(rs2.next()) {
				employees.add(rs2.getString("name"));
				jobTitles.add(rs2.getInt("job_title"));
				salaries.add(rs2.getDouble("salary"));
				empl_avg_ratings.add(rs2.getDouble("avg_rating"));
				lThree.add(rs2.getDouble("last_three"));
				empl_ids.add(rs2.getInt("id"));
				num_warnings.add(rs2.getInt("warning"));
				emplnumRateds.add(rs2.getInt("num_rated"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//for testing purposes
		for(int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i)); 
		}
		
		
		
		JPanel panel = new JPanel();
		
		if(infoID == CI) {
			panel.setLayout(new GridLayout(8,1));
			
			if(Main.getManager().getId() == 0)
				panel.setBackground(new Color(255, 255, 200));
			if(Main.getManager().getId() == 1)
				panel.setBackground(new Color(255, 230, 245));
			if(Main.getManager().getId() == 2)
				panel.setBackground(new Color(240, 255, 220));
			
			JComboBox custList = new JComboBox(customers.toArray());
			//custList.setSelectedIndex(0);
			panel.add(custList);
			
			JLabel addr = new JLabel("");
			panel.add(addr);
			
			JLabel acr = new JLabel("");
			panel.add(acr);
			
			JLabel stat = new JLabel("");
			panel.add(stat);
			
			JButton bListBtn = new JButton("Blacklist This Customer");
			panel.add(bListBtn);
			bListBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(cust_avg_ratings.get(custList.getSelectedIndex()) == 1 && cust_status.get(custList.getSelectedIndex()) != 0) {
						
						String updateStat = "UPDATE customerratings SET status = '0' WHERE avg_rating = 1 AND rest_id = " + restID + " AND cust_id = " + cust_ids.get(custList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(updateStat);
							Main.goToInfo(Info.CI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else {
						System.out.println("no blacklist occured");
					}
					
				
				}
			});
			
			JButton prom_dem_Btn = new JButton("Promote/Demote This Customer");
			panel.add(prom_dem_Btn);
			prom_dem_Btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(cust_avg_ratings.get(custList.getSelectedIndex()) > 4 && cust_status.get(custList.getSelectedIndex()) == 1 && numRateds.get(custList.getSelectedIndex()) > 3) {
						
						String updateStat = "UPDATE customerratings SET status = '2' WHERE avg_rating > 4 AND num_rated > 3 AND status = 1 AND rest_id = " + restID + " AND cust_id = " + cust_ids.get(custList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(updateStat);
							Main.goToInfo(Info.CI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else if(cust_avg_ratings.get(custList.getSelectedIndex()) < 2 && (custList.getSelectedIndex()) > 1 && cust_status.get(custList.getSelectedIndex()) == 1 && numRateds.get(custList.getSelectedIndex()) > 3){
						String updateStat = "UPDATE customerratings SET status = '-1' WHERE avg_rating < 2 AND avg_rating > 1 AND num_rated > 3 AND status = 1 AND rest_id = " + restID + " AND cust_id = " + cust_ids.get(custList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(updateStat);
							Main.goToInfo(Info.CI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else {
						System.out.println("no promotion/demotion occured");
					}
					
				
				}
			});
			
			JButton backBtn = new JButton("Back");
			panel.add(backBtn);
			backBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Main.goToManagerPage();
				}
				
			});
			
			
			custList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					addr.setFont(new Font("monospaced", Font.PLAIN, 20));
					
					addr.setText("Address: Area "+ addresses.get(custList.getSelectedIndex()).toString());
					
					acr.setFont(new Font("monospaced", Font.PLAIN, 20));
					
					acr.setText("Average Rating: "+ cust_avg_ratings.get(custList.getSelectedIndex()).toString());
					
					stat.setFont(new Font("monospaced", Font.PLAIN, 20));
					if(cust_status.get(custList.getSelectedIndex()) == 0) {
						stat.setText("Status: " + statString[0]);
					}else if(cust_status.get(custList.getSelectedIndex()) == 1) {
						stat.setText("Status: " + statString[1]);
					}else if(cust_status.get(custList.getSelectedIndex()) == 2) {
						stat.setText("Status: " + statString[2]);
					}else {
						stat.setText("Status: VISITOR");
					}
					
					
				
				}
			});
		}
		
		if(infoID == EI) {
			panel.setLayout(new GridLayout(12,1));
			
			if(Main.getManager().getId() == 0)
				panel.setBackground(new Color(255, 255, 200));
			if(Main.getManager().getId() == 1)
				panel.setBackground(new Color(255, 230, 245));
			if(Main.getManager().getId() == 2)
				panel.setBackground(new Color(240, 255, 220));
			
			JComboBox emplList = new JComboBox(employees.toArray());
			panel.add(emplList);
			
			JLabel jtitle = new JLabel("");
			panel.add(jtitle);
			
			
			JLabel sal = new JLabel("");
			panel.add(sal);
			
			JLabel empl_av_rate = new JLabel("");
			panel.add(empl_av_rate);
			
			JLabel lastThreeLabel = new JLabel("");
			panel.add(lastThreeLabel);
			
			JLabel warnLabel = new JLabel("");
			panel.add(warnLabel);
			
			JLabel emplnumratedLabel = new JLabel("");
			panel.add(emplnumratedLabel);
			
			JButton warnBtn = new JButton("Warn Employee");
			panel.add(warnBtn);
			warnBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						//String warnEmpl = "UPDATE employees SET warning = " + (num_warnings.get(emplList.getSelectedIndex()) + 1) + " WHERE rest_id = " + restID + " AND id = " + empl_ids.get(emplList.getSelectedIndex());
						if(emplnumRateds.get(emplList.getSelectedIndex()) > 3 && empl_avg_ratings.get(emplList.getSelectedIndex()) < 2) {
							String warnEmpl = "UPDATE employees SET warning = " + (num_warnings.get(emplList.getSelectedIndex()) + 1) + " WHERE rest_id = " + restID + " AND id = " + empl_ids.get(emplList.getSelectedIndex());
							try {
								Statement stmt = conn.createStatement();
								stmt.executeUpdate(warnEmpl);
								Main.goToInfo(Info.EI);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						
				
			}
			});
			
			JButton fireBtn = new JButton("Fire Employee");
			panel.add(fireBtn);
			fireBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(num_warnings.get(emplList.getSelectedIndex()) > 3 && jobTitles.get(emplList.getSelectedIndex()) != 2) {
						String fireEmpl = "DELETE FROM employees WHERE warning > 3 AND job_title <> 2 AND rest_id = " + restID + " AND id = " + empl_ids.get(emplList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(fireEmpl);
							Main.goToInfo(Info.EI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else if(num_warnings.get(emplList.getSelectedIndex()) == 3 && jobTitles.get(emplList.getSelectedIndex()) == 2){
						String fireEmpl = "DELETE FROM employees WHERE warning > 3 AND job_title = 2 AND rest_id = " + restID + " AND id = " + empl_ids.get(emplList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(fireEmpl);
							Main.goToInfo(Info.EI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else {
						System.out.println("No firing occurred");
					}
				
			}
			});
			
			
			JButton increase_sal_Btn = new JButton("Increase Salary of Employee");
			panel.add(increase_sal_Btn);
			increase_sal_Btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						String raiseEmpl = "UPDATE employees SET salary = " + (salaries.get(emplList.getSelectedIndex()) * 1.10) + " WHERE rest_id = " + restID + " AND id = " + empl_ids.get(emplList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(raiseEmpl);
							Main.goToInfo(Info.EI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				
			}
			});
			
			JButton decrease_sal_Btn = new JButton("Decrease Salary of Employee");
			panel.add(decrease_sal_Btn);
			decrease_sal_Btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						String cutEmpl = "UPDATE employees SET salary = " + (salaries.get(emplList.getSelectedIndex()) * .90) + " WHERE rest_id = " + restID + " AND id = " + empl_ids.get(emplList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(cutEmpl);
							Main.goToInfo(Info.EI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				
			}
			});
			
			JButton backBtn = new JButton("Back");
			panel.add(backBtn);
			backBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Main.goToManagerPage();
				}
				
			});
			
			
			
			
			
			
			emplList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					jtitle.setFont(new Font("monospaced", Font.PLAIN, 20));
					
					if(jobTitles.get(emplList.getSelectedIndex()) == 0) {
						jtitle.setText("Job Title: " + jobString[0]);
					}else if(jobTitles.get(emplList.getSelectedIndex()) == 1) {
						jtitle.setText("Job Title: " + jobString[1]);
					}else if(jobTitles.get(emplList.getSelectedIndex()) == 2) {
						jtitle.setText("Job Title: " + jobString[2]);
					}
					
					sal.setFont(new Font("monospaced", Font.PLAIN, 20));
					sal.setText("Salary: "+ salaries.get(emplList.getSelectedIndex()).toString());
					
					empl_av_rate.setFont(new Font("monospaced", Font.PLAIN, 20));
					empl_av_rate.setText("Average Rating: "+ empl_avg_ratings.get(emplList.getSelectedIndex()).toString());
					
					lastThreeLabel.setFont(new Font("monospaced", Font.PLAIN, 20));
					lastThreeLabel.setText("Last Three Ratings Average: "+ lThree.get(emplList.getSelectedIndex()).toString());
					
					warnLabel.setFont(new Font("monospaced", Font.PLAIN, 20));
					warnLabel.setText("Warnings: "+ num_warnings.get(emplList.getSelectedIndex()).toString());
					
					emplnumratedLabel.setFont(new Font("monospaced", Font.PLAIN, 20));
					emplnumratedLabel.setText("Number of times rated: "+ emplnumRateds.get(emplList.getSelectedIndex()).toString());
				
				}
			});
		}
		
		//if(infoID == LO){
			
		//}
		
		return panel;
	}
	

}
