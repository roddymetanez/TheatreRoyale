package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data_access.DataAccess;
import data_access.DBConnector;


/**
 * The test class DataAccessTest.
 */
public class DataAccessTest
{
    /**
     * Default constructor for test class DataAccessTest
     */
    public DataAccessTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    /**
     * constructorTest tests that the connection has been correctly constructed
     */
    @Test
    public void constructorTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        assertNotNull(db);
        System.out.println(db);

    }
    
    /**
     * getShowsTest tests that a table of show information is returned from the database query (a visual
     * test from the terminal window)
     */
    @Test
    public void getShowsTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        ResultSet rs = dataAccess.getShows();
        assertNotNull(rs);
        db.printResult(rs);
    }
    
    /**
     * getShowByNameTest tests that show information is returned from a database query
     * by show name (a visual test from the terminal window) 
     */
    @Test
    public void getShowByNameTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        ResultSet rs = dataAccess.getShowByName("Aladdin");
        assertNotNull(rs);
        db.printResult(rs);
    }
    
    /**
     * getShowByDate Test tests that show information is returned from a database query 
     * by date (a visual test from the terminal window ) 
     */
    @Test
    public void getShowByDateTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        ResultSet rs = dataAccess.getShowByDate("2022-04-29 19:30:00");
        assertNotNull(rs);
        db.printResult(rs);
    }
    
    /**
     * buyTicketTest tests that customer data is successfully stored in the database
     * by storing a set of customer data and then retrieving it

     * createTicketTest tests that ticket bookings are successfully stored in the database
     * by storing a booking and then rettrieving it
     */
    @Test
    public void buyTicketTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        int CID = -1;
        CID = dataAccess.registerCustomer("Zoe", "Scott", "3", "Saturn Way", "CV37 7NE");
        assertNotSame(-1, CID);
        db.close();
        db.connect();
        ResultSet rs = dataAccess.getCustomerData(CID);
        assertNotNull(rs);
        db.printResult(rs);

        dataAccess.createTicket(1,1,2,3,true);
        db.close();
        db.connect();
        rs = dataAccess.getTicket(1);
        assertNotNull(rs);
        db.printResult(rs);
    }
    
    /**
     * updateAvailableSeatTest updates the number of seats for a performance and then 
     * tests that the remaining number of seats is correct
     */
    @Test 
    public void updateAvailableSeatTest() {
    DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        int perfId = 1000;
        ResultSet rs = dataAccess.getAvailableSeats(perfId);
        assertNotNull(rs);
        int aStall = -1;
        int aCircle = -1;
        ResultSet rs1 = null;
        try
        {
            assertTrue(rs.next());
            aCircle = rs.getInt("seats_circle");
            aStall = rs.getInt("seats_stall");
            db.close();
            db.connect();
            boolean success = dataAccess.updateAvailableSeats(perfId, aStall, aCircle);
            assertTrue(success);
            db.close();
            db.connect();
            rs1 = dataAccess.getAvailableSeats(perfId);
            //if (rs1 != null) db.printResult(rs1);
            assertNotNull(rs1);
            assertTrue(rs1.next());
            assertEquals(rs1.getInt("seats_circle"),0);
            assertEquals(rs1.getInt("seats_stall"),0);
            db.printResult(rs1);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            if (rs != null) db.printResult(rs);
            if (rs1 != null) db.printResult(rs1);
        }
    }
    /**
     * closeTest shows that the current database connection will be closed, by closing the connection and 
     * testing to see if it is still available
     */
    @Test
    public void closeTest() {
        DataAccess dataAccess = new DataAccess();
        dataAccess.close();
        Connection conn = dataAccess.getDb().getConn();
        try {
            assertTrue(conn.isClosed());
        } catch (SQLException sqle) {
            System.err.println(sqle.toString());
        }
    }
}
