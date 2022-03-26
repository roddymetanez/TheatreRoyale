package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

import data_access.DataAccess;
import util.InputReader;

public class Theatre {

	public final static int seatsCircle = 80;
	public final static int seatsStalls = 120;
    
    private DataAccess dataAccess;

    private final InputReader inputReader;

    public Theatre() {
    	this.dataAccess = new DataAccess();
        this.inputReader = new InputReader();
        
        displayInterface();
    }

    private void displayInterface() {
        int option;
        boolean exit = false;
        do {
        	
        	// Interface displayed to the user through the console window
            System.out.println("===========================================");
            System.out.println("| Enter the number to select an option..  |");
            System.out.println("|                                         |");
            System.out.println("| 1 - Browse all available shows          |");
            System.out.println("| 2 - Find show by title                  |");
            System.out.println("| 3 - Find shows by date                  |");
            System.out.println("| 4 - Exit                                |");
            System.out.println("|                                         |");
            System.out.println("===========================================");
            System.out.print("\n> ");
            
            // Ensure a digit is provided
            try {
                option = inputReader.getNextInt("");
            } catch (InputMismatchException e) {
                System.out.println("Error: You must enter a digit");
                return;
            }
            
            // Switch the option and call the correct method, or exit.
            dataAccess = new DataAccess(); // Reopen the database connection
            switch (option) {
                case 1 -> browseShows();
                case 2 -> findShowByName();
                case 3 -> findShowsByDate();
                case 4 -> exit = true;
            }
        } while (!exit);

        System.out.println("Closing..");
        System.exit(0); // Exit the program
    }


    /**
     * Call the procedure to retrieve all currently scheduled shows, and pass them to the printResults
     * method to print them to the console.
     */
    private void browseShows() {
    	
    	ResultSet rs = dataAccess.getShows();
    	printResults(rs);
    }

    /**
     * Call the procedure to retrieve all scheduled shows by name, and pass them to the printResults
     * method to print them to the console.
     */
    private void findShowByName() {
    	
    	String name = inputReader.getNextText("\nEnter the show name:\n> ");
    	ResultSet rs = dataAccess.getShowByName(name);

    	printResults(rs);
    }

    /**
     * Call the procedure to retrieve all scheduled shows on a specific date, and pass them to the printResults
     * method to print them to the console.
     */
    private void findShowsByDate() {
    	
    	String date = inputReader.getNextText("\nEnter the date of which you'd like to see shows for [dd-MM-yy]\n> ");
        ResultSet rs = dataAccess.getShowByDate(date.toString());
        
    	printResults(rs);
    }

    /**
     * Print out the results of the provided ResultSet
     * 
     * @param rs ResultSet to print
     */
    private boolean printResults(ResultSet rs) {
    	// Iterate through all elements within the ResultSet and print them to the console. 
    	try {    			
    		while (rs.next()) {
    			System.out.println("\n[Name: " + rs.getString("Name") + "\n Description: " + rs.getString("Blurb") + "\n Date: " + rs.getString("Date") + "]\n");
    		}
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	dataAccess.close(); // Close the connection to the database
    	return true;
    }

	public int getSeatsCircle() {
		return seatsCircle;
	}

	public int getSeatsStalls() {
		return seatsStalls;
	}
}
