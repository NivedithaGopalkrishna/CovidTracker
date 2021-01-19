package io.javabrains.covidtracker.models;

public class LocationStats {
    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatestCases(int latestCases) {
        LatestCases = latestCases;
    }

    private String state;

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getLatestCases() {
        return LatestCases;
    }

    private String country;
    private int LatestCases;

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    private int diffFromPrevDay;

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", LatestCases=" + LatestCases +
                '}';
    }
}
