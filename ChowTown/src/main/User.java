package main;

public class User {

	final static int COOK = 0;
	final static int DELIVERY = 1;
	final static int SALES = 2;
	final static int CUSTOMER = 3;
	final static String[] titles = {"Cook", "Delivery", "Salesperson", "Customer"};
	
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
