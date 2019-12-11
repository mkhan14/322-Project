package main;

public class User {

	final static int COOK = 0;
	final static int DELIVERY = 1;
	final static int SALES = 2;
	final static int CUSTOMER = 3;
	final static int MANAGER = 4;
	final static String[] TITLES = {"Cook", "Delivery", "Salesperson", "Customer", "Manager"};
	final static String[] TITLES1 = {"Cook", "Delivery", "Salesperson", "Customer"};
	
	//user is seen as a visitor if there are no user object created. user == null 
	final static int BLACKLIST = 0;
	final static int REGISTERED = 1;
	final static int VIP = 2;
	final static String[] STATUS = {"BLACKLISTED", "REGISTERED ", "VIP"};
	
	final static int AREA0 = 0;
	final static int AREA1 = 1;
	final static int AREA2 = 2;
	final static String[] AREAS = {"AREA0", "AREA1", "AREA2"};

	
	private int title;
	private int id;
	private String name;
	private boolean loggedIn;

	public User(int title, int id, String name, boolean loggedIn) {
		this.title = title;
		this.id = id;
		this.name = name;
		this.loggedIn = loggedIn;
	}

	public int getTitle() {
		return title;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

}