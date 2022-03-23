package src.data_access;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.data_access.DataAccess;
import src.data_access.DBConnector;


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
     * storeCustomerData tests that customer data is successfully stored in the database
     * by storing a set of customer data and then retrieving it
     */
    @Test
    public void registerCustomerTest(){
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        dataAccess.registerCustomer("Zoe", "Scott", "3", "Saturn Way", "CV37 7NE");
        ResultSet rs = dataAccess.getCustomerData("Scott");
        assertNotNull(rs);
        db.printResult(rs);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
