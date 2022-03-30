package models;

public class Performance {

	private int perfID;
	private int showID;
	private String title;
	private Double price;
	private String startDateTime;
	private int stallSeats;
	private int circleSeats;

	public Performance(int perfID, String date, double price, String title, int stallSeats, int circleSeats) {

		this.perfID = perfID;
		this.startDateTime = date;
		this.price = price;
		this.setTitle(title);
		this.stallSeats = stallSeats;
		this.circleSeats = circleSeats;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the stallSeats
	 */
	public int getStallSeats() {
		return stallSeats;
	}

	/**
	 * @param stallSeats the stallSeats to set
	 */
	public void setStallSeats(int stallSeats) {
		this.stallSeats = stallSeats;
	}

	/**
	 * @return the circleSeats
	 */
	public int getCircleSeats() {
		return circleSeats;
	}

	/**
	 * @param circleSeats the circleSeats to set
	 */
	public void setCircleSeats(int circleSeats) {
		this.circleSeats = circleSeats;
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
