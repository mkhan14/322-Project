package main;

public class Customer {
	
	private int id;
	private String name;
	private boolean loggedIn;

	public Customer(int id, String name, boolean loggedIn) {
		this.id = id;
		this.name = name;
		this.loggedIn = loggedIn;
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
