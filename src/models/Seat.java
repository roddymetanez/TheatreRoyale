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
	private boolean concession;
	private final double concessionDiscount = 0.25;
	private double seatCost;

	public enum seatLoc {
		Circle, Stall
	}

	public Seat(Performance perf, seatLoc seatType, double concession) {
		setPerformance(perf);
		setSeat(seatType);
		calcCost();
	}

	private void calcCost() {
		if (concession) {
			setSeatCost(performance.getPrice() * (1 - concessionDiscount));
		}
		else {
			setSeatCost(performance.getPrice());
		}
	}

	public double getSeatCost() {
		return seatCost;
	}

	public void setSeatCost(double seatCost) {
		this.seatCost = seatCost;
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
