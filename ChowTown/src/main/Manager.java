package main;

public class Manager {
	private int rest_id;
	private String name;
	private boolean loggedIn;

	public Manager(int rest_id, String name, boolean loggedIn) {
		this.rest_id = rest_id;
		this.name = name;
		this.loggedIn = loggedIn;
	}

	public int getId() {
		return rest_id;
	}
	
	public String getName() {
		return name;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
}

