package Tests;

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
		testPerformance = new Performance(99990999, nowNow, 1);
		Patron albert = new Patron();
		testPerformance.getPrice();
		// testPerformance.setPrice(19.99);
		testSeats = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			testSeat = new Seat(testPerformance, seatLoc.Stall, 0);
			testSeats.add(testSeat);
		}
		System.out.println("perf before assignment " + testPerformance.getPrice());
		testTicket = new Ticket(testPerformance, albert);
		testTicket.setSeatingList(testSeats);
		testTicket.ReComputeCost();
		System.out.println("perf after assignment " + testPerformance.getPrice());
		System.out.println("ticket after " + testTicket.getCost());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

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

//	@Test
////	public void timeTestFromPerformance() throws ParseException {
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//
//	sdf.parse(testPerformance.getStartDateTime());
//
////		String tmpStr = sdf .format("YYYY-MM-DD HH:MM:DD").toString();
//	assertEquals("'2025-01-01  23:59:99'", sdf.toString());
//
//	}
//
//	String strDate = "2011-12-31 00:00:00";
//
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//	java.util.Date date = sdf.parse(strDate);
//	java.sql.Date sqlDate = new Date(date.getTime());System.out.println("String converted to java.sql.Date :"+sqlDate);
//
//	@Test
//	public void postageTest() {
//		testPerformance.setStartDateTime(nowNow);
//
//		assertEquals(0, testTicket.getPostage());
//
//	}
}
