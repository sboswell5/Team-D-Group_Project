package edu.jsu.mcis.cs310.tas_sp24;

public enum EventType {

    CLOCK_OUT("CLOCK OUT"),
    CLOCK_IN("CLOCK IN"),
    TIME_OUT("TIME OUT");

    private final String description;

    private EventType(String d) {
        description = d;
    }
   
    @Override
    public String toString() {
        return description;
    }

}
