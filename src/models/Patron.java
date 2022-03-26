package models;

import java.util.ArrayList;

public class Patron {
	private String fName;
	private String lName;
	private String address;
	
	private ArrayList<Ticket> basket;

	public Patron(String fName, String lName, String address) {
		this.fName = fName;
		this.lName = lName;
		this.basket = new ArrayList<Ticket>();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void addToBasket(Ticket ticket) {
		this.basket.add(ticket);
	}
	
	public void removeFromBasket(Ticket ticket) {
		this.basket.remove(ticket);
	}
	
	public void printBasket() {
		this.basket.forEach(ticket -> {
			System.out.println("\n[Performance: " +  ticket.getPerformanceID() + "\nPrice: " + ticket.getTicketPrice() + "]\n");
		});
	}
}
