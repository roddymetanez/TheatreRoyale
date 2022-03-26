/**
 * 
 */
package models;

/**
 * @author clayton
 *
 */
public class Seat {
	private Performance performance;
	private seatLoc seatLoc;

	public enum seatLoc {
		Circle, Stall
	};

	public Seat(Performance perf, seatLoc seatType) {
		setPerformance(perf);
		setSeat(seatType);
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	public void setSeat(seatLoc seatType) {
		seatLoc = seatType;
	}

}
