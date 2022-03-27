package Tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Patron;
import models.Performance;
import models.Seat;
import models.Seat.seatLoc;
import models.Ticket;
import util.DateTimeConverter;

class UserInteractionTesting {

	private static String nowNow;
	private static Performance testPerformance;
	private static Seat testSeat;
	private static ArrayList<Seat> testSeats;
	private static Ticket testTicket;
	private static DateTimeConverter dtConv;

	@BeforeEach
	void setUp() throws Exception {
		dtConv = new DateTimeConverter();
		nowNow = LocalDateTime.now().toString();
		testPerformance = new Performance(999, nowNow, 1);
		Patron albert = new Patron();
		testPerformance.setPrice(19.99);
		testSeats = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			testSeat = new Seat(testPerformance, seatLoc.Stall, 0);
			testSeats.add(testSeat);
		}
		System.out.println("perf before assignment " + testPerformance.getPrice());
		testTicket = new Ticket(testPerformance, albert);
		testTicket.setSeatingList(testSeats);
		System.out.println("perf after assignment " + testPerformance.getPrice());
		System.out.println("ticket after " + testTicket.getCost());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	private void stagingForTest() {
//		dtConv = new DateTimeConverter();
//		nowNow = LocalDateTime.now().toString();
//		testPerformance = new Performance(999, nowNow, 1);
//		Patron albert = new Patron();
//		testSeat = new Seat(testPerformance, seatLoc.Stall, 0);
//		testPerformance.setPrice(19.99);
//		testSeats = new ArrayList<>();
//		for (int i = 0; i < 3; i++) {
//			testSeats.add(testSeat);
//		}
//		testTicket = new Ticket(testPerformance, albert);
//		testTicket.setSeatingList(testSeats);
//	}

	@Test
	public void seatTest() {
		String nowNow = LocalDateTime.now().toString();
		Performance performance = new Performance(0, nowNow, 1);
		Seat testSeat = new Seat(performance, seatLoc.Stall, 0);
		assertNotNull(testSeat);
	}

	@Test
	public void ticketTest() {

		assertEquals(59.97, testTicket.getCost());
	}

	@Test
	public void timeTestFromPerformance() throws ParseException {
		SimpleDateFormat tmpDate = dtConv.stringToSqLDate(testPerformance.getStartDateTime());
		String tmpStr = tmpDate.toString();
		assertEquals("'2025-01-01  23:59:99'", tmpStr);

	}

	@Test
	public void postageTest() {
		testPerformance.setStartDateTime(nowNow);

		assertEquals(0, testTicket.getPostage());

	}
}
