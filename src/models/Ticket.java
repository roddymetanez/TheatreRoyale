package models;

public class Ticket {
	
	private int performanceID;
	
	private double ticketPrice;
	
	private boolean purchased;
	
	public Ticket(int performanceID, double ticketPrice, boolean purchased) {
		this.performanceID = performanceID;
		this.ticketPrice = ticketPrice;
		this.purchased = purchased;
	}
	
	
	public int getPerformanceID() {
		return performanceID;
	}

	public void setPerformanceID(int performanceID) {
		this.performanceID = performanceID;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}


}
