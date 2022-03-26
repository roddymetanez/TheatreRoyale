/**
 * 
 */
package models;

import java.util.ArrayList;

/**
 * @author clayton
 *
 */
public class Ticket {
	private int customerID;
	private int performanceID;
	private ArrayList<Seat> seatingList;

	public Ticket(int performanceID, int customerID) {
		this.performanceID = performanceID;
		this.setCustomerID(customerID);
	}

	/**
	 * @return the Performace
	 */
	public int getPerformanceID() {
		return performanceID;
	}

	/**
	 * @return the seatingList
	 */
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

}