/**
 * 
 */
package models;

import java.util.ArrayList;

/**
 * @author Daniel
 *
 */
public class Ticket {
	private int customerID;
	private int performanceID;
  private ArrayList<Seat> seatingList;
	private double cost;
	
	public Ticket(int performanceID, int customerID) {
		this.performanceID = performanceID;
		this.setCustomerID(customerID);
	}

	/**
	 * @return the seatingList
	 */
	public int getPerformanceID() {
		return performanceID;
	}
	
	public void setPerformanceID(int performanceID) {
		this.performanceID = performanceID;
	}

	/**
	 * @return the seatingList
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * @return the seatingList
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the seatingList
	 */
	public ArrayList<Seat> getSeatingList() {
		return seatingList;
	}

	/**
	 * @param seatingList the seatingList to set
	 */
	public void setSeatingList(ArrayList<Seat> seatingList) {
		this.seatingList = seatingList;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
