package models;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Show {

    private ArrayList<Performance> performances;

    private String title;
    private String type;
    private String description;

    public Show(String type) {
        this.performances = new ArrayList<>();
        this.type = type;
    }

    /**
     * Retrieve all performances within this show
     *
     * @return ArrayList<Performance>
     */
    public ArrayList<Performance> getPerformances() {
        return this.performances;
    }

    public void addPerformance(String performanceDate, int performanceDuration) {
        performances.add(new Performance(performanceDate, performanceDuration));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieve all performances on a specific date
     *
     * @param date
     * @return
     */
    public ArrayList<Performance> findPerformancesByDate(LocalDate date) {
        ArrayList<Performance> performancesOnDate = new ArrayList<>();

        /* Convert the LocalDateTime variable to LocalDate and compare it against the LocalDate provided.
         * If the dates match, add it to the arraylist ready to be returned.
         */
        performances.forEach(performance -> {
            if (performance.getStartDateTime().toLocalDate().equals(date)) {
                performancesOnDate.add(performance);
            }
        });
        return performancesOnDate;
    }

    @Override
    public String toString() {
        return "Show{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", totalPerformances='" + performances.size() + '\'' +
                '}' + "\n";
    }
}
