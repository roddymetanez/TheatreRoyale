package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import data_access.DataAccess;
import models.Patron;
import models.Performance;
import util.InputReader;

public class Theatre {

	public final static int seatsCircle = 80;
	public final static int seatsStalls = 120;
    
    private DataAccess dataAccess;

    private final InputReader inputReader;
    
    private Patron patron;

    public Theatre() {
    	this.dataAccess = new DataAccess();
        this.inputReader = new InputReader();
        this.patron = new Patron();
        
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
            System.out.println("| 4 - View basket                         |");
            System.out.println("| 5 - Exit                                |");
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
                case 4 -> printBasketMenu();
                case 5 -> exit = true;
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
    	ArrayList<Integer> performanceIDsInSearch = new ArrayList<>();
    	ArrayList<Performance> performancsInSearch = new ArrayList<>();
    	try {    			
    		while (rs.next()) {
    			System.out.println("\n[Name: " + rs.getString("show_title") +
    					"\n Description: " + rs.getString("show_description") +
    					"\n Date: " + rs.getString("perf_date") +
    					"\n Genre: " + rs.getString("show_genre") +
    					"\n Language: " + rs.getString("primary_language") +
    					"\n Ticket cost: £" + rs.getString("show_ticketPrice") + 
    					"\n ID: " + rs.getInt("perfID") + "]\n");
    			
    			Performance performance = new Performance(rs.getString("perf_date"));
    			performance.setPrice(rs.getDouble("show_ticketPrice"));
    			performance.setID(rs.getInt("perfID"));
    			
    			performancsInSearch.add(performance);
    			performanceIDsInSearch.add(Integer.valueOf(rs.getString("perfID")));
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
   	
		addToBasket(performancsInSearch);
    	dataAccess.close(); // Close the connection to the database
    	return true;
    }
    
    /**
     * Promts the user to enter their details in order to use the checkout system
     * Registers the customer in the database, as well and initalizing the patron object properties.
     * 
     * ** Customer ID can NOT YET be stored **
     */
    private void registerCustomer() {
    	String fname = inputReader.getNextText("> Enter your first name..");
    	String lname = inputReader.getNextText("> Enter your last name..");
    	String houseNo = inputReader.getNextText("> Enter your house number..");
    	String streetName = inputReader.getNextText("> Enter your street name..");
    	String postalCode = inputReader.getNextText("> Enter your postal code..");
    	
    	try {
    		dataAccess.registerCustomer(fname, lname, houseNo, streetName, postalCode);
    	}catch (SQLException e) {
    		System.out.println("Failed to register.");
    	}
    	
    	
    	patron.setfName(fname);
    	patron.setlName(lname);
    	patron.setAddress_no(houseNo);
    	patron.setAddress_st(streetName);
    	patron.setPost_code(postalCode);
    	
    	// Retrive the information for this customer and store the ID inside the object.
    	
    	// Below cannot be used until users details can be stored
    	
    	//ResultSet rs = dataAccess.getCustomerData(lname);
//    	try {
//    		patron.setID(rs.getInt("customerID"));
//    	} catch(SQLException e) {
//    		e.printStackTrace();
//    	}
    }
    
    /**
     * Adds a performance if it exists from the parameter ArrayList to the users basket 
     * 
     * @param performanceIDs search results from last search
     */
    private void addToBasket(ArrayList<Performance> performanceIDs) {
    	int idSelected = 0;
		try {
			idSelected = inputReader.getNextInt("> Enter the 'Performance ID' to add a performance to your basket, or 'Cancel' to return to the menu\n");
		}catch (NumberFormatException e) {
			System.out.println("Returning to the menu");
			return;
		}
		
		// Ensure the performanceID is within the users search results
		for (Performance performance : performanceIDs) {
			if (performance.getID() == idSelected) {
				patron.addToBasket(performance);
				return;
			}
		} 
    }
    
    /**
     * Prints the Basket Menu, and calls the method the user selects
     * If a customer is NOT registered in the system when "Checking out", they will be promoted to enter their details.
     */
    private void printBasketMenu() {
    	patron.printBasket();
    	
        System.out.println("===========================================");
        System.out.println("| Enter the number to select an option..  |");
        System.out.println("|                                         |");
        System.out.println("| 1 - Remove a ticket from basket         |");
        System.out.println("| 2 - Checkout your basket                |");
        System.out.println("| 3 - Return to main menu                 |");
        System.out.println("| 						                  |");
        System.out.println("===========================================");
        patron.getID();
    	int option = inputReader.getNextInt("");
    	
    	switch (option) {
    	case 1:
    		patron.removeFromBasketByID(option);
    		break;
    	case 2:
    		if (patron.getfName() == null) {
    			registerCustomer();
    		}
    		patron.checkoutBasket(); // Empty method, to be implemented
    		break;
    	}
    }

	public int getSeatsCircle() {
		return seatsCircle;
	}

	public int getSeatsStalls() {
		return seatsStalls;
	}
}
