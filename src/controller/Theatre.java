package controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import models.Show;
import util.InputReader;

public class Theatre {

	public final static int seatsCircle = 80;
	public final static int seatsStalls = 120;
	
    private final HashMap<String, Show> scheduledShows;

    private final InputReader inputReader;

    public Theatre() {
        this.scheduledShows = new HashMap<>();
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
            
            try {
                option = inputReader.getNextInt("");
            } catch (InputMismatchException e) {
                System.out.println("Error: You must enter a digit");
                return;
            }
            
            // Switch the option and call the correct method, or exit.
            switch (option) {
                case 1 -> browseShows();
                case 2 -> findShowByTitle();
                case 3 -> findShowsByDate();
                case 4 -> exit = true;
            }
        } while (!exit);

        System.out.println("Closing..");
        System.exit(0); // Exit the program
    }


    /**
     * Print out all available shows
     */
    private void browseShows() {
        if (showsIsEmpty()) {
            System.out.println("There are no shows currently scheduled");
            return;
        }
        
        scheduledShows.forEach((title, show) -> System.out.println(show));

    }

    /**
     * Locate a show by the title
     */
    private void findShowByTitle() {
        if (showsIsEmpty()) {
            System.out.println("There are no shows currently scheduled");
            return;
        }
        // Locate a specific show by the title
        Show show = null;

        while (show == null) {
            String title = inputReader.getNextText("Enter the show title\n");
            show = scheduledShows.get(title);
        }
        System.out.print(show);
    }

    /**
     * Find all shows that have performances on a specific date
     */
    private void findShowsByDate() {
        if (showsIsEmpty()) {
            System.out.println("There are no shows currently scheduled");
            return;
        }
        // Locate a specific show by the date it begins (Display all that apply)
        LocalDate date;
        try {
        	// Attempt to parse the provided date into a LocalDate data type
            date = LocalDate.parse(inputReader.getNextText("\nEnter the date of which you'd like to see shows for [yyyy-MM-dd]\n"));
        } catch (DateTimeParseException e) {
            System.out.println("Error: You must enter the date in the correct format. [yyyy-MM-dd] - [2021-04-01]");
            return;
        }
        ArrayList<Show> showsByDate = new ArrayList<>();

        // Add all shows that have performances on the date to the list to print out
        scheduledShows.forEach((title, show) -> {
            if (!show.findPerformancesByDate(date).isEmpty()) {
                showsByDate.add(show);
            }
        });
        
        // Print out all shows found with performances on the date
        printShows(showsByDate);
    }

    /**
     * Print out all shows within the provided arraylist.
     * 
     * @param shows to print to console
     */
    private void printShows(ArrayList<Show> shows) {
        if (!shows.isEmpty()) {
            System.out.println("\nAvailable shows");

            shows.forEach(System.out::println);
        }
    }

    private boolean showsIsEmpty() {
        return this.scheduledShows.isEmpty();
    }

	public int getSeatsCircle() {
		return seatsCircle;
	}

	public int getSeatsStalls() {
		return seatsStalls;
	}
}
