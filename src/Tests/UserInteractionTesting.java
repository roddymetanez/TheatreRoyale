package Tests;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Performance;
import models.Seat;
import models.Seat.seatLoc;
import models.Ticket;

class UserInteractionTesting {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void seatTest() {
		String nowNow = LocalDateTime.now().toString();
		Performance performance = new Performance(nowNow, 1);
		Seat testSeat = new Seat(performance, seatLoc.Stall);
		assertNotNull(testSeat);

	}

	@Test
	public void TicketTest() {
		Ticket testTicket = new Ticket(999, 999);
		testTicket.addSeats(Seat testSeats);
			
		
		}
}
