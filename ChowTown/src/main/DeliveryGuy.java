package main;

public class DeliveryGuy {
	private int id;
	private String name;
	private boolean loggedOn;
	
	public DeliveryGuy(int id, String name, Boolean loggedOn) {
		this.id = id;
		this.name = name;
		this.loggedOn = loggedOn;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Boolean getIsLoggedOn() {
		return loggedOn;
	}
}
