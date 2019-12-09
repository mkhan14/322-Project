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
	final static int OI = 2;
	
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
		
		//String query = "SELECT name,address FROM customers JOIN customerratings WHERE rest_id = " + restID + " AND cust_id = id";
		String query = "SELECT name,address,avg_rating,status,id,num_rated FROM customers JOIN customerratings WHERE rest_id = " + restID + " AND cust_id = id";
		//String query1 = "SELECT avg_rating FROM customerratings JOIN customers WHERE rest_id = " + restID + " AND cust_id = id";
		//String query2 = "SELECT * FROM employees";
		String query2 = "SELECT name,job_title,salary,avg_rating,last_three FROM employees WHERE rest_id = " + restID;
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			//ResultSet rs1 = stmt.executeQuery(query1);
			while(rs.next()) {
				customers.add(rs.getString("name"));
				addresses.add(rs.getInt("address"));
				cust_avg_ratings.add(rs.getDouble("avg_rating"));
				cust_status.add(rs.getInt("status"));
				cust_ids.add(rs.getInt("id"));
				numRateds.add(rs.getInt("num_rated"));
			}
			
			//Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt.executeQuery(query2);
			while(rs2.next()) {
				employees.add(rs2.getString("name"));
				jobTitles.add(rs2.getInt("job_title"));
				salaries.add(rs2.getDouble("salary"));
				empl_avg_ratings.add(rs2.getDouble("avg_rating"));
				lThree.add(rs2.getDouble("last_three"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*try {
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			while(rs2.next()) {
				employees.add(rs2.getString("name"));
				//prices.add(rs.getDouble("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		for(int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i)); 
		}
		
		
		
		//JPanel panel = new JPanel(new GridBagLayout());
		//Border border = BorderFactory.createEmptyBorder(20,20,20,20);
		//GridBagConstraints c = new GridBagConstraints();
		JPanel panel = new JPanel();
		
		if(infoID == CI) {
			panel.setLayout(new GridLayout(7,1));
			
			
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
						//alter row of customerrating where rating is 1 so that the status becomes 0
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 (this would be so that any customer with avg rating of 1 can be blacklisted)
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 AND cust_id = customers.id (idk if this will work)
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 AND cust_id = cust_ids.get(custList.getSelectedIndex());
						//then run goToInfo
						
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
						//alter row of customerrating where rating is 1 so that the status becomes 0
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 (this would be so that any customer with avg rating of 1 can be blacklisted)
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 AND cust_id = customers.id (idk if this will work)
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 AND cust_id = cust_ids.get(custList.getSelectedIndex());
						//then run goToInfo
						
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
			
			
			custList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//for(int i = 0; i < addresses.size(); i++) {
					//	System.out.println(addresses.get(i)); 
					//}
					
					
					
					
					//addr.setBorder(border);
					addr.setFont(new Font("monospaced", Font.PLAIN, 20));
					//c.gridx = 0; c.gridy = 1;
					//panel.remove(addr);
					addr.setText("Address: Area "+ addresses.get(custList.getSelectedIndex()).toString());
					
					acr.setFont(new Font("monospaced", Font.PLAIN, 20));
					//c.gridx = 0; c.gridy = 1;
					//panel.remove(addr);
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
			panel.setLayout(new GridLayout(5,1));
			
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
			
			/*JButton update_salary_Btn = new JButton("Update Salary of This Employee");
			panel.add(update_salary_Btn);
			update_salary_Btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(cust_avg_ratings.get(custList.getSelectedIndex()) > 4 && cust_status.get(custList.getSelectedIndex()) == 1 && numRateds.get(custList.getSelectedIndex()) > 3) {
						//alter row of customerrating where rating is 1 so that the status becomes 0
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 (this would be so that any customer with avg rating of 1 can be blacklisted)
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 AND cust_id = customers.id (idk if this will work)
						//UPDATE customerratings SET status = '0' WHERE avg_rating = 1 AND cust_id = cust_ids.get(custList.getSelectedIndex());
						//then run goToInfo
						
						String updateStat = "UPDATE customerratings SET status = '2' WHERE avg_rating > 4 AND num_rated > 3 AND status = 1 AND rest_id = " + restID + " AND cust_id = " + cust_ids.get(custList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(updateStat);
							Main.goToInfo(Info.EI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else if(cust_avg_ratings.get(custList.getSelectedIndex()) < 2 && (custList.getSelectedIndex()) > 1 && cust_status.get(custList.getSelectedIndex()) == 1 && numRateds.get(custList.getSelectedIndex()) > 3){
						String updateStat = "UPDATE customerratings SET status = '-1' WHERE avg_rating < 2 AND avg_rating > 1 AND num_rated > 3 AND status = 1 AND rest_id = " + restID + " AND cust_id = " + cust_ids.get(custList.getSelectedIndex());
						try {
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(updateStat);
							Main.goToInfo(Info.EI);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else {
						System.out.println("no promotion/demotion occured");
					}
					
				
				}
			});*/
			
			
			emplList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//for(int i = 0; i < addresses.size(); i++) {
					//	System.out.println(addresses.get(i)); 
					//}
					
					
					
					
					//addr.setBorder(border);
					jtitle.setFont(new Font("monospaced", Font.PLAIN, 20));
					//c.gridx = 0; c.gridy = 1;
					//panel.remove(addr);
					//jtitle.setText("Job title: "+ jobTitles.get(emplList.getSelectedIndex()).toString());
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
				
				}
			});
		}
		
		//if(infoID == OI){
			
		//}
		
		return panel;
	}
	
	//public static int convertStatusToString(int cs) {
	//	
	//}

}
