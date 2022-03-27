package data_access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import data_access.DBConnector;
import util.Parser;

/**
 * DataAccess
 */
public class DataAccess {
    private DBConnector db;
    private final String GET_CUSTOMER_DATA = "getCustomerData";
    private final String GET_SHOWS = "getShows";
    private final String GET_SHOW_BY_DATE = "getShowByDate";
    private final String GET_SHOW_BY_NAME = "getShowByName";
    private final String REGISTER_CUSTOMER = "registerCustomer";
    private final String GET_AVAILABLE_SEATS = "getAvailableSeats";
    private final String UPDATE_AVAILABLE_SEATS = "updateAvailableSeats";
    private final String CREATE_TICKET = "createTicket";
    private final String GET_TICKET = "getTicket";

    /**
     * DataAccess instantiates a new Database connectionand connects to that database
     */
    public DataAccess() {
        db = new DBConnector();
        // connect to the database
        db.connect();        
    }
    
    /**
     * getDb gets the connected database handle
     * @return DBConnector - the current database handle
     */
    public DBConnector getDb() {
        return db;
    }
    
    /**
     * getShows runs the database query to provide a table of all current
     * information on shows
     */
    public ResultSet getShows() {
        String query = "{call " + GET_SHOWS + "()}";

        ArrayList<String> params = new ArrayList<String>();
        return db.callNString(query, params);
    }

    
    /**
     * getShowByName runs the database query to provide all  
     * information on shows on a particular show
     * @param name String - the name of show information is requested for
     * @return ResultSet - the information for the requested show
     */
    public ResultSet getShowByName(String name) {
        String query = "{call " + GET_SHOW_BY_NAME + "(?)}";
        ArrayList<String> params = new ArrayList<String>();
        params.add(name);
        return db.callNString(query, params);
    }
    
    
    /**
     * getShowByDate runs the database query to provide all 
     * information on shows on a particular date
     * @param date String - the date information is requested for
     * @return ResultSet - the information for the requested date
     */
    public ResultSet getShowByDate(String date) {
        String query = "{call " + GET_SHOW_BY_DATE + "(?)}";
        ArrayList<String> params = new ArrayList<String>();
        params.add(date);
        return db.callNString(query, params);
    }
   
    
    /**
     * registerCustomer will add a new Customer to the database
     * @params fname,lname,add_no,add_st,post_code all the Customer data that we wish to store
     * There is provision within th database to store addition information if that is required 
     * in future versions
     * @return int the customerID for the customer added
     */
    public int registerCustomer(String fname, String lname, String add_no, String add_st, String post_code) {
        String query = "{call " + REGISTER_CUSTOMER + "(?, ?, ?, ?, ?)}";
        ArrayList<String> params = new ArrayList<String>();
        params.add(fname);
        params.add(lname);
        params.add(add_no);
        params.add(add_st);
        params.add(post_code);
        ResultSet rs = db.callNString(query, params);
        int retVal = -1;
        try {
            if (rs.next())
                retVal = rs.getInt("customerID");
        } catch (SQLException sqle) {
            //System.err.println(sqle.toString());
            sqle.printStackTrace();
        }
//        db.close();
//        db.connect();
        return retVal;
    }
   /**
    * getCustomerData gets all the data stored for a specified Customer
    * @param CID integer the CustomerID
    * @return ResultSet of all the Customer Data
    */
    public ResultSet getCustomerData(int CID) {
        String query = "{call " + GET_CUSTOMER_DATA + "(?)}";
        ArrayList<Integer> params = new ArrayList<Integer>();
        params.add(CID);
        return db.callNInt(query, params);
    }
     
    
    /**
     * buyTicket records a ticket purchase in the database
     * Register the Customer buying the ticket 
     * Create a new entry in the ticket table
     * @param fname,lname,add_no,add_st,post_code String -  The name, address and phone of the Customer
     * @param perfId, stall,circle,post int - The details of the tickets purchased
     * @ return bookingMade Boolean - an indication of whether the booking has succeeded
     */
    public Boolean buyTicket(String fname, String lname, String add_no, String add_st, String post_code, 
                                int perfId, int stall,int circle, boolean post) {
        //ResultSet rs = getCustomerData(lname);
        int custId = -1;
        //if (rs == null) {
        custId = registerCustomer(fname,lname,add_no,add_st, post_code);
        //}
        boolean bookingMade = updateAvailableSeats(perfId,stall,circle);
        if (bookingMade) { createTicket(custId, perfId,stall, circle,post); }
        return bookingMade;
    }    
    
                                
    /**
     * createTicket will create a record if the customer having made a booking
     * @param custID int the customer who made the booking
     * @param perfId int the performance the booking has been made for
     * @param stall int the number of stall seats booked
     * @param circle int the number of circle seats booked
     * @param post boolean indicates whether the tickets are to be posted
    */
    public void createTicket(int custID, int perfID, int stall,int circle, boolean post) {
        int newPost = 0;
        // converting the boolean to an integer for MySQL 0 is false
        if (post) newPost = 1; 
         String query = "{call " + CREATE_TICKET + "(?, ?, ?, ?, ?)}";
        ArrayList<Integer> params = new ArrayList<Integer>();
        params.add(custID);
        params.add(perfID);
        params.add(stall);
        params.add(circle);
        params.add(newPost);
        db.callNInt(query, params);      
    }
    
    /**
     * getTicket will get all th stored tickets for a specified customer
     * @param custID int - the customerID (from the Customer Database Table) 
     * @return ResultSet - all the tickets that customer has purchased
     */
    public ResultSet getTicket(int custID) {
         String query = "{call " + GET_TICKET + "(?)}";
        ArrayList<Integer> params = new ArrayList<Integer>();
        params.add(custID);
        return db.callNInt(query, params);         
    }
    /**
     * updateAvailableSeats will check that seats are available and will record that any seats
     * booked are no longer available
     * @param perfId int the performance the seats are relevant to
     * @param circle intthe number of circle seats
     * @param stall in the number of stall seats
     * @return boolean an indication that there are not enough seats for that booking
     */
    
    public boolean updateAvailableSeats(int perfId, int stall, int circle) {
        // Check the seats are available
        ResultSet rs = getAvailableSeats(perfId);
        int newStall = 0;
        int newCircle = 0;
        boolean bookingMade = false;
        try {
            if (rs.next()) {
                newStall = rs.getInt("seats_stall") - stall;
                newCircle = rs.getInt("seats_circle") - circle;
            }
        } catch (SQLException sqle) {
            //System.err.println(sqle.toString());
            if (rs != null) db.printResult(rs);
            sqle.printStackTrace();

        }

        if ((newStall >= 0)&&(newCircle >=0))
        // Update the available seats for the performance
        {   String query = "{call " + UPDATE_AVAILABLE_SEATS + "(?,?,?)}";
            ArrayList<Integer> params = new ArrayList<Integer>();
            params.add(perfId);
            params.add(newStall);
            params.add(newCircle);
            db.callNInt(query, params);
            bookingMade = true;
        }
        db.close();
        db.connect();
        return bookingMade;
    }
    
    /** 
     * getAvailableSeats will retrieve all avaible seats for a given performance
     * @param perfID int - the performance required
     * @return ResultSet -all the stored information for that performance , which includes the 
     * fields seats_stall and seats_cricle
     */
    public ResultSet getAvailableSeats(int perfId) {
        String query = "{call " + GET_AVAILABLE_SEATS + "(?)}";
        ArrayList<Integer> params = new ArrayList<Integer>();
        params.add(perfId);
        return db.callNInt(query, params);
    }
    
    
    /**
     * close will close the connection to the current database
     */
    public void close() {
        db.close();
    }
}