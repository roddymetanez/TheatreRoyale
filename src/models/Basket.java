package models;

import java.util.ArrayList;

public class Basket {

	private ArrayList<Ticket> ticketsInBasket;

	/**
	 * Object containing the tickets as a device of the patron
	 * 
	 */
	public Basket() {
		this.ticketsInBasket = new ArrayList<>();

	}

	public ArrayList<Ticket> getTicketsInBasket() {
		return ticketsInBasket;
	}

	/**
	 * Method to store the tickets, sent from the patron by the method
	 * 
	 * @param ticket
	 */
	public void addToBasket(Ticket ticket) {
		ticketsInBasket.add(ticket);
		// ticket.computeCost(ticketsInBasket);
	}

	/**
	 * Method to remove the tickets from the basket Ticket ID is supplied to the
	 * method and is removed.
	 * 
	 * @param perfID
	 * @return
	 */
	public boolean removeFromBasket(int perfID) {
		for (Ticket ticket : ticketsInBasket) {
			if (ticket.getPerformanceID() == perfID) {
				ticket.markAsSold(false);
				ticketsInBasket.remove(ticket);
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to check the value of the tickets within the basket.
	 * 
	 * @return
	 */
	public double getBasketTotalCost() {
		double total = 0;
		for (Ticket ticket : ticketsInBasket) {
			total += ticket.getCost();
		}
		return total;
	}
}
