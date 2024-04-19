package edu.jsu.mcis.cs310.tas_sp24;

/**
 * Simple enum that tells the different adjustment types for punches.
 * @author Snellen
 */
public enum PunchAdjustmentType {

    NONE("None"),
    SHIFT_START("Shift Start"),
    SHIFT_STOP("Shift Stop"),
    SHIFT_DOCK("Shift Dock"),
    LUNCH_START("Lunch Start"),
    LUNCH_STOP("Lunch Stop"),
    INTERVAL_ROUND("Interval Round");

    /**
     * description - the type of adjustment
     */
    private final String description;

    /**
     * @param d description of the type of adjustment
     */
    private PunchAdjustmentType(String d) {
        description = d;
    }

    /**
     * Overrides the toString() method
     * @return the description of the type of adjustment
     */
    @Override
    public String toString() {
        return description;
    }
}
