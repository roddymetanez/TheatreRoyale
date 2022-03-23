package src.data_access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import src.data_access.DBConnector;
import src.util.Parser;

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
   
    public void registerCustomer(String fname, String lname, String add_no, String add_st, String post_code) {
        String query = "{call " + REGISTER_CUSTOMER + "(?, ?, ?, ?, ?)}";
        ArrayList<String> params = new ArrayList<String>();
        params.add(fname);
        params.add(lname);
        params.add(add_no);
        params.add(add_st);
        params.add(post_code);
        db.callNString(query, params);  
    }
   
    public ResultSet getCustomerData(String lname) {
        String query = "{call " + GET_CUSTOMER_DATA + "(?)}";
        ArrayList<String> params = new ArrayList<String>();
        params.add(lname);
        return db.callNString(query, params);
    }
       // ResultSet rs = dataAccess.getCustomerData("Scott");
    /**
     * close will close the connection to the current database
     */
    public void close() {
        db.close();
    }
}