/**
 *
 */
package models;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import data_access.DataAccess;
import models.Seat.seatLoc;
import util.DateTimeConverter;
import util.InputReader;

public class Ticket {
	private int customerID;
	private int performanceID;
	private int concessionaryPriceTicket;
	private int fullPriceTicket;
	private double cost;
	private final double PostFee = 1.00;
	private boolean ticketMarkAsBuy;

	private ArrayList<Seat> seatingList;

	private Performance performance;
	private Patron patron;
	private InputReader inputReader;
	private DateTimeConverter dateTimeConverter;
	private DataAccess dataAccess;
	private DecimalFormat twoDigitsDoubles = new DecimalFormat("#.##");

	public Ticket(Performance performance, Patron patron) {
		this.performance = performance;
		this.patron = patron;
		this.performanceID = performance.getPerfID();
		this.inputReader = new InputReader();
		this.setCustomerID(patron.getID());
		ticketMarkAsBuy = false; // tickets are unsold on generation

		seatingList = new ArrayList<>();
		dateTimeConverter = new DateTimeConverter();

		twoDigitsDoubles.setRoundingMode(RoundingMode.FLOOR);

		this.dataAccess = new DataAccess();
		this.inputReader = new InputReader();

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
	 * Method to set the number and type of tickets sold
	 * 
	 * @param seatingList the seatingList within the ticket to be set
	 */
	public boolean chooseNumberSeats() {

		getSeatsForPerformance(performance);

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
		System.out.println("|  tickets would you like? " + (tot - optionReg) + " left       |");
		System.out.println("|                                         |");
		System.out.println("|  How many concessionary tickets?        |");
		System.out.println("|                                         |");
		System.out.println("===========================================");

		int optionConc = inputReader.getNextInt(""); // Prompt the user to enter an option from the above menu

		tktsale = addSeatsToTicket(optionReg, optionConc);
		return tktsale;
	}

	/**
	 * Call the procedure to retrieve all scheduled shows by name, and pass them to
	 * the printResults method to print them to the console.
	 */
	private void getSeatsForPerformance(Performance performance) {
		ResultSet rs = dataAccess.getAvailableSeats(performance.getPerfID());
		try {
			performance.setStallSeats(rs.getInt("seats_stall"));
			performance.setCircleSeats(rs.getInt("seats_circle"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method, separated out from chooseNumber seats for testing purposes. itterates
	 * through seats requested, creating seats as part of ticket
	 * 
	 * @param optionReg,  integer
	 * @param optionConc, integer
	 * @return
	 */
	public boolean addSeatsToTicket(int optionReg, int optionConc) {
		int[] seatTkts = { optionReg, optionConc };
		int tot = optionConc + optionReg;
		boolean seatConcession = false;
		if ((performance.getStallSeats() + performance.getCircleSeats()) < tot) {
			System.out.println("There are not enough seats left");
			return false;
		}
		for (int tktype : seatTkts) {
			for (int i = 0; i < tktype; i++) {
				Seat seat = new Seat(getPerformance(), seatType(), seatConcession);
				seatingList.add(seat);
			}
			seatConcession = !seatConcession;
		}
		return true;
	}

	/**
	 * Method, separated out from chooseNumberSeats() for testing purposes.
	 * Itterates through enum. NOTE, decision taken that we do not differentiate
	 * stall and circle seats
	 */
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
		concessionaryPriceTicket = 0;
		fullPriceTicket = 0;
		double ticketCost = 0;
		for (Seat tmpSeat : seatingList) {
			ticketCost = ticketCost + tmpSeat.getSeatCost();
			if (tmpSeat.isConcession()) {
				concessionaryPriceTicket++;
			}
			else {
				fullPriceTicket++;
			}

		}
		setCost(ticketCost);
		return ticketCost;
	}

	// TODO Dan - is this ok?
	public double calcCost(ArrayList<Seat> seatingList) {
		concessionaryPriceTicket = 0;
		fullPriceTicket = 0;
		double ticketCost = 0;
		for (Seat tmpSeat : seatingList) {
			ticketCost = ticketCost + tmpSeat.getSeatCost();
			if (tmpSeat.isConcession()) {
				concessionaryPriceTicket++;
			}
			else {
				fullPriceTicket++;
			}
		}
		setCost(ticketCost);
		return ticketCost;
	}

	/**
	 * Method to check date within the 7 day timing for tickets This method will be
	 * called from the ticket sales methods
	 * 
	 * @param performance, the performance details of the show
	 * @return
	 * @throws ParseException
	 */
	public boolean checkPostage(Performance performance) throws ParseException {
		return dateTimeConverter.compareDate7dyHence(performance.getStartDateTime());
	}

	/**
	 * Method to add posting costs to the ticket. Postage added on setting of seat
	 * being sold is concessionary or not
	 * 
	 */
	public void acceptPostage() {
		double postCharge = 0;
		for (Seat tkt : seatingList) {
			if (!tkt.isConcession()) {
				postCharge = postCharge + PostFee;
			}
		}
		setCost(postCharge + getCost());
	}

	public int getFullPrcTkt() {
		return fullPriceTicket;
	}

	public int getCncPrcTkt() {
		return concessionaryPriceTicket;
	}

	public void markAsSold() {
		ticketMarkAsBuy = !ticketMarkAsBuy;
	}

	public void markAsSold(boolean b) {
		ticketMarkAsBuy = b;

	}
}
