package models;

public class Performance {

	private int perfID;
	private int showID;

	private Double price;
	private String startDateTime;

	public Performance(int perfID, String date, double price) {

		this.perfID = perfID;
		this.startDateTime = date;
		this.price = price;
	}

	public int getPerfID() {
		return perfID;
	}

	public void setPerfID(int iD) {
		perfID = iD;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getShowID() {
		return showID;
	}

	public void setShowID(int showID) {
		this.showID = showID;
	}

	/**
	 * Create a ticket for this performance
	 *
	 * @return Ticket created for this show
	 */
//    public Ticket createTicket() {
//    	return new Ticket(this.perfID, this.price, this);
//    }
}
