package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Theatre;
import data_access.DataAccess;
import models.Patron;
import models.Performance;
import models.Seat;
import models.Seat.seatLoc;
import models.Ticket;
import util.DateTimeConverter;

class UserInteractionTesting {

	private String nowNow;
	private Performance testPerformance;
	private Seat testSeat;
	private ArrayList<Seat> testSeats;
	private Ticket testTicket;
	private DateTimeConverter dtConv;
	private Theatre testTheatre;
	private Patron albert;
	private ArrayList<Performance> performancesInSearch;
	private DataAccess dataAccess;

	@BeforeEach
	void setUp() throws Exception {
		this.dataAccess = new DataAccess();
		// create theatre testing mode set to true
		testTheatre = new Theatre(true);
		// time converter
		dtConv = new DateTimeConverter();
		nowNow = LocalDateTime.now().toString();
		testTheatre.getShowByPerformanceID(99990999);
		performancesInSearch = testTheatre.getPerformancesInSearch();
		testPerformance = performancesInSearch.get(0);
		albert = new Patron();
		testPerformance.getPrice();
		testTicket = new Ticket(testPerformance, albert);
		testTicket.addSeatsToTicket(5, 3);
		testTicket.calcCost();
	}

	@AfterEach
	void tearDown() throws Exception {
		performancesInSearch.clear();
	}

	@Test
	public void seatTest() {
		String nowNow = LocalDateTime.now().toString();
		Seat testSeat = new Seat(testPerformance, seatLoc.Stall, false);
		assertNotNull(testSeat);
	}

	@Test
	public void ticketPriceTest() {
		assertEquals(144.9275, testTicket.getCost());
	}

	@Test
	public void testPerformanceStartDateTime() {
		String sdf = testPerformance.getStartDateTime();
		assertEquals("2025-01-01 23:59:59", sdf.toString());

	}

	@Test
	public void testTooManyTickets() {
		testTicket.addSeatsToTicket(5000, 0);
		testTicket.calcCost();
		assertEquals(144.9275, testTicket.getCost());
	}

	@Test
	public void getPerformanceByDate() {
		performancesInSearch.clear();
		testTheatre = new Theatre(true);
		testTheatre.findShowsByDate_Test("01-01-25");
		performancesInSearch = testTheatre.getPerformancesInSearch();
		System.out.println("number: " + performancesInSearch.size());
		testPerformance = performancesInSearch.get(0);
		assertEquals("Hamlet in the original Klingon", testPerformance.getTitle());
	}
	
	@Test
	public void getPerformanceByNameTest() {
		testTheatre = new Theatre(true);
		testTheatre.findShowByName_Test("Aladdin");
		
		performancesInSearch = testTheatre.getPerformancesInSearch();
		assertEquals("Aladdin", performancesInSearch.get(0).getTitle());
	}

	@Test
	public void addTicketsToBasketTest() throws ParseException {
		// get the patron
		Patron albert = testTheatre.getPatron();
		// get the performance
		testTheatre.getShowByPerformanceID(99990999);
		// get the price on the ticket
		testTicket.addSeatsToTicket(5, 3);
		testTicket.calcCost(); // Calculates and sets ticket cost
		// say no to postage
		testTicket.checkPostage(testPerformance);
		testTicket.acceptPostage();
		// process basket
		albert.acceptTicketToBasket(testTicket);
		// end the test
		double tmpTotal = albert.getBasket().getBasketTotalCost();

		assertEquals(149.9275, tmpTotal);
	}

	@Test
	public void subtractTicketsFromBasket() {
		// // remove tickets
		albert.getBasket().removeFromBasket(99990999);
		double tmpTotal = albert.getBasket().getBasketTotalCost();
		assertEquals(0, tmpTotal);
		// end the test
	}

	@Test
	public void postageTest() {
		testPerformance.setStartDateTime("9999-12-31 23:59:59");
		testTicket.acceptPostage();
		assertEquals(149.9275, testTicket.getCost());

	}

	@Test
	public void retrieveSeatsFromDb() {
		int seatsCircle = testPerformance.getCircleSeats();
		int seatsStall = testPerformance.getStallSeats();
		testTicket.addSeatsToTicket(5, 3);
		ResultSet rs = dataAccess.getAvailableSeats(99990999);
		try {
			testPerformance.setStallSeats(rs.getInt("seats_stall"));
			testPerformance.setCircleSeats(rs.getInt("seats_circle"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			assertEquals(200, (seatsCircle + seatsStall));
		}
	}
}
