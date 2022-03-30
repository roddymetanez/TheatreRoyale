package models;

import java.text.ParseException;
import java.util.ArrayList;

import util.InputReader;

public class Patron {

	private int id;

	private String fName;
	private String lName;
	private String address_no;
	private String address_st;
	private String post_code;
	private Ticket ticket;

	private double balance;

	private final InputReader inputReader;
	private Basket usersBasket;

	public Patron() {
		// Constructor
		this.inputReader = new InputReader();
		this.usersBasket = new Basket();
		this.balance = 125.00; // TODO, why this value? All about the benjies...
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getAddress_no() {
		return address_no;
	}

	public void setAddress_no(String address_no) {
		this.address_no = address_no;
	}

	public String getAddress_st() {
		return address_st;
	}

	public void setAddress_st(String address_st) {
		this.address_st = address_st;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
  
	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the usersBasket
	 */
	public Basket getBasket() {
		return usersBasket;
	}

	/**
	 * @param usersBasket the usersBasket to set
	 */
	public void setBasket(Basket usersBasket) {
		this.usersBasket = usersBasket;
	}

	// Method needs to be updated to ensure A:
	// Ticket is available for purchase/not
	// sold out, and
	// B: Check the performanceID is valid.
	public boolean holdForBasket(Performance performance) {
		selectForBasket(ticket = createTicket(performance)); // Empty method
		boolean tktSale = ticket.setSeatingList();
		if (tktSale) {
			ticket.calcCost();
			try {
				if (ticket.checkPostage(performance)) {
					String postAccept = inputReader.getNextText("\nWould you like postage for your tickets? [Y = Yes, N = No]");
					if (postAccept.equalsIgnoreCase("y")) {
						ticket.acceptPostage();
					}
					String ticketAccept = inputReader.getNextText("\nComplete sale?  [Y = Yes, N = No]");
					if (ticketAccept.equalsIgnoreCase("y")) {
						System.out.println("\nSuccessfully added performance [" + performance.getPerfID() + "] to your basket\n");
						acceptTicketToBasket(ticket);
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else {
			System.out.println("\nPlease try again as seats are limited for that performance");
			return false;
		}
	// Method needs to be updated to ensure A: Ticket is available for purchase/not
	// sold out, and B: Check the performanceID is valid.
	public void addToBasket(Performance performance) {
		usersBasket.addToBasket(createTicket(performance));
		System.out.println("\nSuccessfully added performance [" + performance.getPerfID() + "] to your basket\n");
	}

	/**
	 * Creates a ticket for the passed performance
	 *
	 * @param performance to create a ticket for
	 * @return ticket created
	 */
	private Ticket createTicket(Performance performance) {
		ticket = new Ticket(performance, this);
		ticket.setCost(performance.getPrice());
		return ticket;
	}

	/**
	 * Removes a ticket from the users basket by ID if it exists
	 *
	 * @param id
	 * @return
	 */
	public void removeFromBasketByID(int perfID) {
 		if (usersBasket.removeFromBasket(perfID)) {
 			System.out.println("Ticket has been removed from your basket.");
 		} else {
 			System.out.println("Ticket could not be removed, or does not exist in your basket.");
		}
	}

	/**
	 * Method to be updated
	 */
	public void checkoutBasket(boolean menu) {
		if (menu) {
			double bsktPrice = usersBasket.getBasketTotalCost();
			printBasket();
			System.out.println("===========================================");
			System.out.println("|                                         |");
			System.out.println("|                                         |");
			System.out.println("|                                         |");
			System.out.println("|                                         |");
			System.out.println("|                                         |");
			System.out.println("|                                         |");
			System.out.println("===========================================");
		}
	}

	// Method to be updated
	/**
	 * Prints on the size of the users basket, as well as the contents
	 */
	public void printBasket() {
		ArrayList<Ticket> inBasket = usersBasket.getTicketsInBasket();
		System.out.println("\nBasket size: " + inBasket.size());
		System.out.println("Basket contents:\n ");

		if (inBasket.isEmpty()) {
			System.out.println("Your basket is empty..\n");
			return;
		}
		inBasket.forEach(ticket -> {
			System.out.println("Performance ID: " + ticket.getPerformanceID() + "\t Show Title: "
					+ ticket.getPerformance().getTitle() + "\t Show Time: " + ticket.getPerformance().getStartDateTime()
					+ "\t Price: " + ticket.getPerformance().getPrice());
		});
	}

	/**
	 * Method to select the ticket and its seats
	 * 
	 * @param testTicket
	 */
	public void selectForBasket(Ticket ticket) {
	}

	public void acceptTicketToBasket(Ticket ticket) {
		usersBasket.addToBasket(ticket);
	}

}
