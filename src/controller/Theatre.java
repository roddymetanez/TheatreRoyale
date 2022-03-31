package controller;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import data_access.DataAccess;
import models.Patron;
import models.Performance;
import models.Seat;
import models.Seat.seatLoc;
import models.Ticket;
import util.DateTimeConverter;
import util.InputReader;

public class Theatre {

	public final static int seatsCircle = 80;
	public final static int seatsStalls = 120;

	private DataAccess dataAccess;
	private ArrayList<Performance> performancesInSearch = new ArrayList<>(); // All performances found in the users search
	private boolean testMode = false; // Used to indicate if the application is being run with JUnit for testing

	private final InputReader inputReader;

	private Patron patron; // The patron that is currently using the application

	private static final DecimalFormat twoDigitsDoubles = new DecimalFormat("#.##"); // Format the currency to 2 decimal places

	/**
	 * Theatre constructor Initializes global variables and calls displayInterface()
	 * to print out the main menu
	 * 
	 * @param testMode indicates if this application is being ran with JUnit for testing purposes
	 */
	public Theatre(boolean testMode) {
		this.testMode = testMode;
		this.dataAccess = new DataAccess();
		this.inputReader = new InputReader();
		this.patron = new Patron();

		twoDigitsDoubles.setRoundingMode(RoundingMode.FLOOR);

		if (!testMode) {
			displayInterface();
		}
	}

	/**
	 * Print out the main menu interface and prompt the user to enter an option from
	 * within the menu. The corresponding method will be called from the option the
	 * user entered, or the application will exit.
	 *
	 * This menu is the initial display for this application, and will be available
	 * until the application is closed or exited with the menu option.
	 */
	private void displayInterface() {
		int option = 0;
		boolean exit = false; // false indicates the application should continue running, false stops it.
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
			}
			catch (InputMismatchException e) {
				System.out.println("Error: You must enter a digit");
				return;
			}
			dataAccess = new DataAccess(); // Reopen the database connection
			
			// Switch the option and call the correct method, or exit.
			switch (option) {
			case 1 -> browseShows();
			case 2 -> findShowByName();
			case 3 -> findShowsByDate();
			case 4 -> printBasketMenu();
			case 5 -> exit = true;
			}
		}
		while (!exit);

		System.out.println("Closing..");
		System.exit(0); // Exit the program
	}

	/**
	 * Call the procedure to retrieve all currently scheduled shows, and pass them
	 * to the printResults method to print them to the console.
	 */
	private void browseShows() {
		ResultSet rs = dataAccess.getShows(); // Returns a result set of all shows
		printResults(rs);
	}

	/**
	 * Call the procedure to retrieve all scheduled shows by name, and pass them to
	 * the printResults method to print them to the console.
	 */
	private void findShowByName() {
		String name = inputReader.getNextText("\nEnter the show name:\n> "); // Name to be used to search for shows
		
		ResultSet rs = dataAccess.getShowByName(name); // Returns a result set of all shows with a specific name
		printResults(rs);
	}
	
	/**
	 * Call the procedure to retrieve all scheduled shows by name, and pass them to
	 * the printResults method to print them to the console.
	 * 
	 * Used for testing purposes only and not part of the actual application
	 * 
	 * @param name to be used to search for shows
	 */
	public void findShowByName_Test(String name) {
		ResultSet rs = dataAccess.getShowByName(name); // Returns a result set of all shows with a specific name
		printResults(rs);
	}

	/**
	 * Call the procedure to retrieve all scheduled shows on a specific date, and
	 * pass them to the printResults method to print them to the console.
	 */
	private void findShowsByDate() {

		DateTimeConverter findByDate = new DateTimeConverter(); 
		try {
			// Attempt to convert the users 'date' into the format used in the database
			String date = findByDate.stringToDate(inputReader.getNextText("\nEnter the date of which you'd like to see shows for \t [dd-MM-yy]\n> "));
			ResultSet rs = dataAccess.getShowByDate(date); 
			printResults(rs);
		}
		catch (ParseException improperlyFormattedDate) {
			// Date could not be parsed
			improperlyFormattedDate.printStackTrace();
		}
	}
	
	/**
	 * Call the procedure to retrieve all scheduled shows on a specific date, and
	 * pass them to the printResults method to print them to the console.
	 * 
	 * Used for testing purposes only and not part of the actual application
	 *
	 * @param testDate
	 */
	public void findShowsByDate_Test(String testDate) {

		DateTimeConverter findByDate = new DateTimeConverter();
		try {
			// Attempt to convert the users 'date' into the format used in the database
			testDate = findByDate.stringToDate(testDate);
			ResultSet rs = dataAccess.getShowByDate(testDate);
			printResults(rs);
		}
		catch (ParseException improperlyFormattedDate) {
			// Date could not be parsed
			improperlyFormattedDate.printStackTrace();
		}

	}

	/**
	 * Utility testing method to get individual performances of shows by performanceID
	 *
	 * @param performanceID to be searched
	 */
	public void getShowByPerformanceID(int testPerfID) {
		ResultSet rs = dataAccess.getShowByPerfID(testPerfID);
		printResults(rs);

	}

	/**
	 * Print out the results of the provided ResultSet in as a formatted string
	 *
	 * Create and store each result as a performance and initialize the object
	 * variables.
	 *
	 * Once all results have been printed, call addToBasket() to prompt the user to
	 * add to basket, or return to the main menu
	 *
	 * @param rs ResultSet to print
	 */
    private void printResults(ResultSet rs) {
        try {
            if (!rs.next()) {
                System.out.println("No show of that record");
                return;
            }
            do {
            	// Print out a readable format for the show including all details
                System.out.println("\n[Name: " + rs.getString("show_title") + "\n [Description: "
                        + rs.getString("show_description") + "]" + "\n [Date: " + rs.getString("perf_date") + "]"
                        + "\n [Genre: " + rs.getString("show_genre") + "\t Language: "
                        + rs.getString("primary_language") + "\t Ticket cost: £" + rs.getString("show_ticketPrice")
                        + "]" + "\n [ID: " + rs.getInt("perfID") + "]\n");

                // Create a performance object and initialize variables
                Performance performance = new Performance(rs.getInt("perfID"), rs.getString("perf_date"),
                        rs.getDouble("show_ticketPrice"), rs.getString("show_title"), rs.getInt("seats_circle"),
                        rs.getInt("seats_stall"));
                performancesInSearch.add(performance);
            }
            while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!testMode) {
            if (selectForBasket(performancesInSearch)) {
                dataAccess.close(); // Close the connection to the database
                return;
            }
        }
        dataAccess.close(); // Close the connection to the database
    }

	/**
	 * Prompts the user to enter their details in order to use the checkout system
	 * Registers the customer in the database, as well and initalizing the patron
	 * object properties.
	 *
	 */
	private boolean registerCustomer() {
		// Prompt user to enter their details
		String fname = inputReader.getNextText("> Enter your first name..");
		String lname = inputReader.getNextText("> Enter your last name..");
		String houseNo = inputReader.getNextText("> Enter your house number..");
		String streetName = inputReader.getNextText("> Enter your street name..");
		String postalCode = inputReader.getNextText("> Enter your postal code..");

		// Attempts to store the details in the database
		int CID = -1;
		CID = dataAccess.registerCustomer(fname, lname, houseNo, streetName, postalCode);
		if (CID < 0) {
			// The customer could NOT be registered, return to the main menu - The customer
			// MUST be registered to continue here.
			System.out.println("Failed to register.");
			return false;
		}

		// Initialize patron variables with the values entered above
		patron.setfName(fname);
		patron.setlName(lname);
		patron.setAddress_no(houseNo);
		patron.setAddress_st(streetName);
		patron.setPost_code(postalCode);

		// Retrieve the information for this customer and store the ID inside the
		// object.
		// Below cannot be used until users details can be stored

		ResultSet rs = dataAccess.getCustomerData(CID);
		try {
			if (rs.next()) {
				patron.setID(rs.getInt("customerID"));
			}
		}
		catch (SQLException e) {
			System.out.println("Failed to register.");
			registerCustomer();
			return false;
		}
		// Successfully registered, welcome aboard!
		System.out.println("Welcome to the Royale " + fname + "\n");
		return true;
	}

	/**
	 * Prompts the user to enter a performanceID to add a performance to their
	 * basket, or return to the main menu. Adds a performance if it exists from the
	 * search results ArrayList to the users basket
	 *
	 * @param performanceIDs search results from last search
	 * @return
	 */
	private boolean selectForBasket(ArrayList<Performance> performanceIDs) {
		int idSelected = 0;
		try {
			// ID to be selected from the performanceIDs ArrayLisy
			idSelected = Integer.valueOf(inputReader.getNextInt(
					"> Enter the 'Performance ID' to add a performance to your basket, or 'Cancel' to return to the menu\n"));
		}
		catch (NumberFormatException e) {
			// User did not enter a digit, return to menu
			System.out.println("Returning to the menu");
			return false; 
		}

		// Ensure the performanceID is within the users search results
		for (Performance performance : performanceIDs) {
			if (performance.getPerfID() == idSelected) {
				// ID user entered is equal to this performance, add it to basket and return
				return patron.holdForBasket(performance);
			}
		}
		
		// ID user entered did not equal any of the performanceIDs within their search results
		System.out.println("There is no performance with this ID in your search results..");
		
		// Recall this method to prompt the user to enter another ID or return to the menu
		selectForBasket(performanceIDs); 
		
		return false;
	}

	/**
	 * Calls the printBasket() method to print out all tickets within a users basket
	 * to the screen
	 *
	 * Prints the Basket Menu, and calls the method the user selects If a customer
	 * is NOT registered in the system when "Checking out", they will be promoted to
	 * enter their details.
	 */
	private void printBasketMenu() {
		
		// Prints out all tickets within a users basket
		printBasket(); 

		System.out.println("===========================================");
		System.out.println("| Enter the number to select an option..  |");
		System.out.println("|                                         |");
		System.out.println("| 1 - Remove a ticket from basket         |");
		System.out.println("| 2 - Checkout your basket                |");
		System.out.println("| 3 - Return to main menu                 |");
		System.out.println("|                                         |");
		System.out.println("===========================================");

		int option = inputReader.getNextInt(""); // Prompt the user to enter an option from the above menu
		switch (option) {
		case 1:
			// Removes a ticket from the users basket
			patron.removeFromBasketByID(
					inputReader.getNextInt("Enter the 'Performance ID' to remove a performance from your basket\n"));
			break;

		case 2:
			if (patron.getfName() == null) { // Check if the current user has already entered their details
				registerCustomer(); // Prompt user to enter their details
			}
			checkoutBasket();
			break;
		}
	}

	/**
	 * Prints the checkout menu to the console and waits for user input.
	 * 
	 * Requests a users payment details upon them entering option 1.
	 */
	public void checkoutBasket() {
		int option = 0;
		if (!testMode) {
			printBasket();
			System.out.println("===========================================");
			System.out.println("| Enter the number to select an option..  |");
			System.out.println("|                                         |");
			System.out.println("| 1. Complete purchase                    |");
			System.out.println("| 2. Return to main menu                  |");
			System.out.println("|                                         |");
			System.out.println("===========================================");
			if (!testMode) {
				option = inputReader.getNextInt(""); // Prompt the user to enter an option from the above menu
			}
			else {
				// Test mode is enabled, option 1 is explicitly called.
				option = 1; 
			}
			switch (option) {
				case 1:
					if (patron.getBasket().getTicketsInBasket().isEmpty()) {
						// User does not have any tickets, prompt them to add them to basket & return.
						System.out.println("You don't not have any tickets to purchase, try adding some to your basket.");
						return;
					}
					if (paymentForTickets(!testMode)) {
						// Used to check if a user has entered a valid 16 digit number
						boolean validCCDetails = requestPaymentDetails();
						while (!validCCDetails) {
							// Request a valid 16 digit number until it is provided
							validCCDetails = requestPaymentDetails();
						}
						updateSeats(testMode, patron.getBasket().getTicketsInBasket());
						createPrintedTicket();
						displayInterface();
					}
					break;
			}
		}
	}

	/**
	 * Request the user to enter a number and return true if the length is 16
	 * 
	 * @return true if user enters a 16 digit number
	 */
	private boolean requestPaymentDetails() {
		String ccLongNum = inputReader.getNextText("Enter the 16 digit number from the front of your card");
		return ccLongNum.length() == 16;
	}

	/**
	 * Print out the TheatreReceipt with the with the tickets purchased
	 * then clear the basket of any tickets.
	 */
	private void createPrintedTicket() {
		System.out.println("\n[Theatre Receipt]\n");
		printBasket();
		System.out.println("\nPurchase has been completed!\n");

		patron.getBasket().clearBasket();
	}

	private boolean paymentForTickets(boolean testMode) {
		return testMode;
	}

	/**
	 * Update the available seats in the database
	 * 
	 * @param testMode indicates if this is being ran as a test
	 * @param tktListOf tickets
	 */
	private void updateSeats(boolean testMode, ArrayList<Ticket> tktListOf) {
		int stallSeats = 0;
		int circleSeats = 0;
		for (Ticket tkt : tktListOf) {
			for (Seat tmpSeat : tkt.getSeatingList()) {
				if (seatLoc.Stall == tmpSeat.getSeatLoc())
					stallSeats++;
				else {
					circleSeats++;
				}
			}
			if (!dataAccess.updateAvailableSeats(tkt.getPerformanceID(), stallSeats, circleSeats)) {
				System.out.println("error in seat reservation");
			}
		}
	}

	/**
	 * Prints out the size of the users basket, as well as the contents and total.
	 */
	public void printBasket() {
		ArrayList<Ticket> inBasket = patron.getBasket().getTicketsInBasket();
		System.out.println("\nBasket size: " + inBasket.size());
		System.out.println("Basket contents:\n ");

		if (inBasket.isEmpty()) {
			System.out.println("Your basket is empty..\n");
			return;
		}
		inBasket.forEach(ticket -> {
			Double tmpTktCost = ticket.getCost();
			tmpTktCost.doubleValue();
			int fullPrcTkt = ticket.getFullPrcTkt();
			int fullCncTkt = ticket.getCncPrcTkt();

			System.out.println("\n[Performance ID: " + ticket.getPerformanceID() + "\t Show Title: "
					+ ticket.getPerformance().getTitle() + "\t Show Time: " + ticket.getPerformance().getStartDateTime()
					+ "\n Tickets: " + fullPrcTkt + " x Full price tickets\t " + fullCncTkt + " x Concessionary tickets"
					+ "\t Total: " + (twoDigitsDoubles.format(tmpTktCost)) + "]");
		});
	}

	public int getSeatsCircle() {
		return seatsCircle;
	}

	public int getSeatsStalls() {
		return seatsStalls;
	}

	public ArrayList<Performance> getPerformancesInSearch() {
		return performancesInSearch;
	}

	public void setPerformancesInSearch(ArrayList<Performance> performancesInSearch) {
		this.performancesInSearch = performancesInSearch;
	}

	/**
	 * gets the patron for the purposes of basket filling
	 *
	 * @return the Patron
	 */
	public Patron getPatron() {
		return patron;
	}
}
