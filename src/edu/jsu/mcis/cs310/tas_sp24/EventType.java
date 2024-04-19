package edu.jsu.mcis.cs310.tas_sp24;

/**
 * Simple enum that tells the different types of punches.
 * @author Snellen
 */
public enum EventType {

    CLOCK_OUT("CLOCK OUT"),
    CLOCK_IN("CLOCK IN"),
    TIME_OUT("TIME OUT");

    /**
     * description - the type of punch
     */
    private final String description;

    /**
     * @param d description of the type of punch
     */
    private EventType(String d) {
        description = d;
    }
   
    /**
     * Overrides the toString() method
     * @return the description of the type of punch
     */
    @Override
    public String toString() {
        return description;
    }

}
