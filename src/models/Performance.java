package models;

public class Performance {

    private int ID;
    private int showID;

    private Double price;
    private String startDateTime;


    public Performance(int ID, String date, double price) {
    	this.ID = ID;
        this.startDateTime = date;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
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
//    	return new Ticket(this.ID, this.price, this);
//    }
}
