package Tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Performance;
import models.Seat;
import models.Seat.seatLoc;
import models.Ticket;

class UserInteractionTesting {

	private String nowNow;
	private Performance testPerformance;
	private Seat testSeat;
	ArrayList<Seat> testSeats;
	private Ticket testTicket;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	private void stagingForTest() {
		nowNow = LocalDateTime.now().toString();
		testPerformance = new Performance(999, nowNow, 1);
		testSeat = new Seat(testPerformance, seatLoc.Stall, 0);
		testPerformance.setPrice(19.99);
		testSeats = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			testSeats.add(testSeat);
		}
		testTicket = new Ticket(999, 999);
		testTicket.setSeatingList(testSeats);
	}

	@Test
	public void seatTest() {
		String nowNow = LocalDateTime.now().toString();
		Performance performance = new Performance(0, nowNow, 1);
		Seat testSeat = new Seat(performance, seatLoc.Stall, 0);
		assertNotNull(testSeat);
	}

	@Test
	public void TicketTest() {
		stagingForTest();

		assertEquals(59.97, testTicket.getCost());
	}

	@Test
	public void postageTest() {
		stagingForTest();
		testPerformance.setStartDateTime(nowNow);

		assertEquals(0, testTicket.getPostage());

	}
}
