package models;

import java.util.ArrayList;

public class Basket {
	
	private ArrayList<Ticket> ticketsInBasket;
	
	public Basket() {
		this.ticketsInBasket = new ArrayList<Ticket>();
		
	}
	
	public ArrayList<Ticket> getTicketsInBasket(){
		return ticketsInBasket;
	}
	
	public void addToBasket(Ticket ticket) {
		ticketsInBasket.add(ticket);
	}
	
	public boolean removeFromBasket(Ticket ticket) {
		return ticketsInBasket.remove(ticket);
	}
	
	public double getBasketTotalCost() {
		double total = 0;
		
		for (Ticket ticket : ticketsInBasket) {
			total += ticket.getCost();
		}
		
		return total;
	}
}
