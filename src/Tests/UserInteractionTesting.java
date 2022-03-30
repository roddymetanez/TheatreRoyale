package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Theatre;
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

	@BeforeEach
	void setUp() throws Exception {
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
		Seat testSeat = new Seat(testPerformance, seatLoc.Stall, 0);
		assertNotNull(testSeat);
	}

	@Test
	public void ticketPriceTest() {
		assertEquals(159.92, testTicket.getCost());
	}

	@Test
	public void testPerformanceStartDateTime() {
		String sdf = testPerformance.getStartDateTime();
		assertEquals("2025-01-01 23:59:59", sdf.toString());

	}

	@Test
	public void getPerformanceByDate() {
		performancesInSearch.clear();
		testTheatre.findShowsByDate_Test("01-01-25");
		performancesInSearch = testTheatre.getPerformancesInSearch();
		System.out.println("number: " + performancesInSearch.size());
		testPerformance = performancesInSearch.get(0);
		assertEquals("Hamlet in the original Klingon", testPerformance.getTitle());
	}

	@Test
	public void addTicketsToBasketTest() throws ParseException {
		// get the patron
		Patron albert = testTheatre.getPatron();
		// get the performance
		testTheatre.getShowByPerformanceID(99990999);
		// get the price on the ticket
		testTicket.calcCost();
		// say no to postage
		testTicket.checkPostage(testPerformance);
		testTicket.acceptPostage();
		// process basket
		albert.acceptTicketToBasket(testTicket);
		// end the test
		double tmpTotal = albert.getBasket().getBasketTotalCost();

		assertEquals(167.92, tmpTotal);

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
		assertEquals(167.92, testTicket.getCost());

	}
}
