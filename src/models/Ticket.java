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
	private Performance performance;
	private Patron patron;
	private int customerID;
	private int performanceID;
	private ArrayList<Seat> seatingList;
	private double cost;

	public Ticket(Performance performance, Patron patron) {
		this.performance = performance;
		this.patron = patron;
		this.performanceID = performance.getPerfID();
		this.setCustomerID(patron.getID());
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

	// TODO Dan - is this ok?
	public double ReComputeCost() {
		double ticketCost = 0;
		for (Seat tmpSeat : seatingList) {
			ticketCost = ticketCost + tmpSeat.getSeatCost();
		}
		setCost(ticketCost);
		return ticketCost;

	}

	// TODO Dan - is this ok?
	public double computeCost(ArrayList<Seat> seatingList) {
		double ticketCost = 0;
		for (Seat tmpSeat : seatingList) {
			ticketCost = ticketCost + tmpSeat.getSeatCost();
		}
		setCost(ticketCost);
		return ticketCost;

	}

//	public Integer getPostage() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public double getPostage() {
		performance.getStartDateTime();
		int postageCost = 0;
		String tmpDate = performance.getStartDateTime();
		// if (LocalDate. < LocalDate.) {}
		return 0;
	}
}
