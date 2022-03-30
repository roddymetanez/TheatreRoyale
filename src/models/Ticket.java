/**
 *
 */
package models;

import java.text.ParseException;
import java.util.ArrayList;

import models.Seat.seatLoc;
import util.DateTimeConverter;
import util.InputReader;

public class Ticket {
	private Performance performance;
	private Patron patron;
	private int customerID;
	private int performanceID;
	private ArrayList<Seat> seatingList;
	private double cost;
	private InputReader inputReader;
	private final double PostFee = 1.00;
	private DateTimeConverter dateTimeConverter;

	public Ticket(Performance performance, Patron patron) {
		this.performance = performance;
		this.patron = patron;
		this.performanceID = performance.getPerfID();
		this.inputReader = new InputReader();
		this.setCustomerID(patron.getID());

		seatingList = new ArrayList<>();
		dateTimeConverter = new DateTimeConverter();

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

	/**
	 * @return the seatingList
	 */
	public ArrayList<Seat> getSeatingList() {
		return seatingList;
	}

	/**
	 * @param seatingList the seatingList to set
	 */
	public boolean setSeatingList() {
		boolean tktsale = false;
		int tmpStallSeats = getPerformance().getStallSeats();
		int tmpCircleSeats = getPerformance().getCircleSeats();
		int tot = tmpCircleSeats + tmpStallSeats;

		System.out.println("===========================================");
		System.out.println("|  Please select the seats you want       |");
		System.out.println("|                                         |");
		System.out.println("|  How many regular and concessionary     |");
		System.out.println("|  tickets would you like? " + tot + " left       |");
		System.out.println("|                                         |");
		System.out.println("|  How many regular tickets?              |");
		System.out.println("|                                         |");
		System.out.println("===========================================");

		int optionReg = inputReader.getNextInt(""); // Prompt the user to enter an option from the above menu

		System.out.println("===========================================");
		System.out.println("|  Please select the seats you want       |");
		System.out.println("|                                         |");
		System.out.println("|  How many regular and concessionary     |");
		System.out.println("|  tickets would you like? " + tot + " left       |");
		System.out.println("|                                         |");
		System.out.println("|  How many concessionary tickets?        |");
		System.out.println("|                                         |");
		System.out.println("===========================================");

		int optionConc = inputReader.getNextInt(""); // Prompt the user to enter an option from the above menu

		tktsale = addSeatsToTicket(optionReg, optionConc);
		return tktsale;
	}

	public boolean addSeatsToTicket(int optionReg, int optionConc) {
		int[] seatTkts = { optionReg, optionConc };
		int tot = optionConc + optionReg;
		if (performance.getStallSeats() < 1 || performance.getCircleSeats() < 1 - tot) {
			System.out.println("There are not enough seats left");
			return false;
		}
		for (int tktype : seatTkts) {
			int y = 0;
			for (int i = 0; i < tktype; i++) {
				Seat seat = new Seat(getPerformance(), seatType(), y);
				seatingList.add(seat);
			}
			y++;
		}
		return true;
	}

	private seatLoc seatType() {
		int seatsRmn = performance.getStallSeats();
		if (seatsRmn > 1) {
			return seatLoc.Stall;
		}
		return seatLoc.Circle;
	}

	public Performance getPerformance() {
		return performance;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	// TODO Dan - is this ok?
	public double calcCost() {
		double ticketCost = 0;
		for (Seat tmpSeat : seatingList) {
			ticketCost = ticketCost + tmpSeat.getSeatCost();
		}
		setCost(ticketCost);
		return ticketCost;

	}

	// TODO Dan - is this ok?
	public double calcCost(ArrayList<Seat> seatingList) {
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

	public boolean checkPostage(Performance performance) throws ParseException {
		return dateTimeConverter.compareDate7dyHence(performance.getStartDateTime());
	}

	public void acceptPostage() {
		double postCharge = 0;
		for (Seat tkt : seatingList) {
			if (!tkt.isConcession()) {
				postCharge = postCharge + PostFee;
			}
		}
		setCost(postCharge + getCost());
	}
}
