package Tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.DataAccess;
import util.DBConnector;


/**
 * The test class DataAccessTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
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

    @Test
    public void constructorTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        assertNotNull(db);
        System.out.println(db);

    }
    
    @Test
    public void getShowsTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        ResultSet rs = dataAccess.getShows();
        assertNotNull(rs);
        db.printResult(rs);
    }
    
    @Test
    public void getShowByNameTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        ResultSet rs = dataAccess.getShowByName("Alice");
        assertNotNull(rs);
        db.printResult(rs);
    }
    
    @Test
    public void getShowByDateTest() {
        DataAccess dataAccess = new DataAccess();
        DBConnector db = dataAccess.getDb();
        ResultSet rs = dataAccess.getShowByDate("01-04-22");
        assertNotNull(rs);
        db.printResult(rs);
    }
    
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
