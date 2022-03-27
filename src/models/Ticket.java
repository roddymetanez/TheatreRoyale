package models;

public class Ticket {
	private int customerID;
	private int performanceID;
	private double cost;
	

	public Ticket(int performanceID, int customerID) {
		this.performanceID = performanceID;
		this.setCustomerID(customerID);
	}

	public int getPerformanceID() {
		return performanceID;
	}
	
	public void setPerformanceID(int performanceID) {
		this.performanceID = performanceID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
