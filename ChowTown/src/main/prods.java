package main;

public class prods {
	private int p_num, emp_id;
	private String p_name;
	private float price;
	
	public prods(int p_num, int emp_id, String p_name, float price) {
		this.p_num = p_num;
		this.emp_id = emp_id;
		this.p_name = p_name;
		this.price = price;
	}
	public int getpnum(){
		return p_num;
	}
	public int getempid() {
		return emp_id;
	}
	public String getpname() {
		return p_name;
	}
	public float getprice() {
		return price;
	}
}

