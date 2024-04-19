package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Punch class that handles the creation, adjusting, and printing of punches.
 * @author Madelyn
 * @author Ryan
 * @author Shelby
 */
public class Punch {
    
    /**
     * terminalId - the id of the terminal the punch is done at
     * id - the id of the punch itself
     * badge - the unique number assigned to each employee
     * punchType - the type of punch
     * originalTimestamp - the timestamp of the punch before it is adjusted
     * adjustedTimestamp - the timestamp of the punch after it is adjusted
     * adjustmentType - the type of adjustment
     * formatter - the format that the date and time should be in
     * @author Madelyn
     */
    private final int terminalId;
    private int id;
    private final Badge badge;
    private final EventType punchType;
    private LocalDateTime originalTimestamp, adjustedTimestamp;
    private PunchAdjustmentType adjustmentType;
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

    /**
     * Constructor to make a new punch.
     * @param terminalId passes the terminal id into the constructor
     * @param badge passes the badge into the constructor
     * @param punchType passes the punchType into the constructor
     * originalTimestamp - initializes the timestamp for a punch
     */
    public Punch(int terminalId, Badge badge, EventType punchType) {

        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTimestamp = LocalDateTime.now();
    }
    
    /**
     * Constructor for existing punches.
     * @param id passes the punch id into the constructor
     * @param terminalId passes the terminal id into the constructor
     * @param badge passes the badge into the constructor
     * @param originalTimestamp passes the original timestamp into the constructor
     * @param punchType passes the punch type into the constructor
     */
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.punchType = punchType; 
    }
    
    /**
     * Adjusts a punch to a certain time based on specific rules.
     * @param s the specific shift of an employee that will make a punch
     * @author Ryan
     * @author Shelby
     */
    public void adjust(Shift s) {
        
        adjustedTimestamp = originalTimestamp;
        
        // Defining the variables & populating them
        LocalTime shiftStart = s.getShiftStart();
        LocalTime shiftStop = s.getShiftStop();
        LocalTime lunchStart = s.getLunchStart();
        LocalTime lunchStop = s.getLunchStop();
        int roundInterval = s.getRoundInterval();
        int gracePeriod = s.getGracePeriod();
        int dockPenalty = s.getDockPenalty();
        
        if (!isWeekend(originalTimestamp)) {
            
            LocalDate placeholder = LocalDate.from(originalTimestamp);
           
            // ================= LOGIC FOR CLOCK IN ========================
            if (punchType == EventType.CLOCK_IN) {
                
                // ----------------- SHIFT START ---------------------------
                // If the punch is exactly at the start of the shift
                if (originalTimestamp.toLocalTime().withSecond(0).equals(shiftStart)) {
                    
                    adjustedTimestamp = originalTimestamp.withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.NONE;
                    
                // If the punch is before the shift but within the round interval beforehand 
                } else if (originalTimestamp.toLocalTime().isAfter(shiftStart.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftStart) ||
                          (originalTimestamp.toLocalTime().equals(shiftStart.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftStart))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, shiftStart).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.SHIFT_START;
                    
                // If the punch is before the shift but before the round interval beforehand
                } else if (originalTimestamp.toLocalTime().isBefore(shiftStart.minusMinutes(roundInterval))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);

                // If the punch is within the grace period
                } else if (originalTimestamp.toLocalTime().isBefore(shiftStart.plusMinutes(gracePeriod)) ||
                          (originalTimestamp.toLocalTime().equals(shiftStart.plusMinutes(gracePeriod)))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, shiftStart).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.SHIFT_START;
                    
                // If the punch is within the dock penalty range
                } else if (originalTimestamp.toLocalTime().isAfter(shiftStart.plusMinutes(gracePeriod)) && (originalTimestamp.toLocalTime().isBefore(shiftStart.plusMinutes(dockPenalty))) ||
                          (originalTimestamp.toLocalTime().equals(shiftStart.plusMinutes(gracePeriod)) && originalTimestamp.toLocalTime().isBefore(shiftStart.plusMinutes(dockPenalty)))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, shiftStart.plusMinutes(dockPenalty)).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
                    
                // If the punch is after the dock penalty range and before lunch
                } else if (originalTimestamp.toLocalTime().isAfter(shiftStart.plusMinutes(dockPenalty)) && originalTimestamp.toLocalTime().isBefore(lunchStart)) {
                  
                    adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);
                  
                // ----------------- LUNCH ---------------------------
                // If the punch is exactly at the end of lunch
                } else if (originalTimestamp.toLocalTime().withSecond(0).equals(lunchStop)) {
           
                    adjustedTimestamp = originalTimestamp.withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.NONE;
                    
                // If the punch is before the end of lunch and after the start of lunch
                } else if (originalTimestamp.toLocalTime().isBefore(lunchStop) && (originalTimestamp.toLocalTime().isAfter(lunchStart))){
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval));
                    adjustmentType = PunchAdjustmentType.LUNCH_STOP;
                
                // If there are any punches not covered above
                } else {
                
                    adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);
                }
            }

            // ================== LOGIC FOR CLOCK OUT ========================
            if (punchType == EventType.CLOCK_OUT) {
                
                // ----------------- LUNCH -----------------------------------
                // If the punch is exactly at the start of lunch
                if (originalTimestamp.toLocalTime().withSecond(0).equals(lunchStart)) {
           
                    adjustedTimestamp = originalTimestamp.withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.NONE;
                    
                // If the punch is after the start of lunch and before the end of lunch
                } else if (originalTimestamp.toLocalTime().isAfter(lunchStart) && (originalTimestamp.toLocalTime().isBefore(lunchStop))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, lunchStart).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.LUNCH_START;
                
                // ----------------- SHIFT END -------------------------------
                // If the punch is exactly at the end of the shift
                } else if (originalTimestamp.toLocalTime().withSecond(0).equals(shiftStop)) {
           
                    adjustedTimestamp = originalTimestamp.withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.NONE;
                    
                // If the punch is after the shift but within the round interval afterwards
                } else if (originalTimestamp.toLocalTime().isAfter(shiftStop) && originalTimestamp.toLocalTime().isBefore(shiftStop.plusMinutes(roundInterval)) ||
                          (originalTimestamp.toLocalTime().equals(shiftStop.plusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isAfter(shiftStop))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, shiftStop).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.SHIFT_STOP;
                    
                // If the punch is after the shift but after the round interval afterwards
                } else if (originalTimestamp.toLocalTime().isAfter(shiftStop.plusMinutes(roundInterval))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);

                // If the punch is within the grace period
                } else if (originalTimestamp.toLocalTime().isAfter(shiftStop.minusMinutes(gracePeriod)) ||
                          (originalTimestamp.toLocalTime().equals(shiftStop.minusMinutes(gracePeriod)))) {
                    
                    adjustedTimestamp = LocalDateTime.of(placeholder, shiftStop).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.SHIFT_STOP;
                    
                // If the punch is within the dock penalty range
                } else if ((originalTimestamp.toLocalTime().isAfter(shiftStop.minusMinutes(dockPenalty)) && originalTimestamp.toLocalTime().isBefore(shiftStop.minusMinutes(gracePeriod))) ||
                          (originalTimestamp.toLocalTime().equals(shiftStop.minusMinutes(dockPenalty)) && originalTimestamp.toLocalTime().isBefore(shiftStop.minusMinutes(gracePeriod)))) {
                   
                    adjustedTimestamp = LocalDateTime.of(placeholder, shiftStop.minusMinutes(dockPenalty)).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
                   
                // If the punch is before the dock penalty range and after lunch
                } else if (originalTimestamp.toLocalTime().isBefore(shiftStop.minusMinutes(dockPenalty)) && originalTimestamp.toLocalTime().isAfter(lunchStop)) {
                 
                    adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);
                    adjustmentType = PunchAdjustmentType.INTERVAL_ROUND;
                
                // If there are any punches not covered above
                } else {
                   
                    adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);
                }
            }
            
        // For weekend punches
        } else {
            
            LocalDate placeholder = LocalDate.from(originalTimestamp);
            adjustedTimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);
        }
    }
    
    /**
     * Simple method to check if the day of the week is a weekend.
     * @param day the day of the week to be checked
     * @return true is the day is a weekend and false if the day is a weekday
     * @author Shelby
     */
    public boolean isWeekend(LocalDateTime day){
        
        boolean weekend = false;

        if (day.getDayOfWeek().toString().equals("SATURDAY") || day.getDayOfWeek().toString().equals("SUNDAY")){

            weekend = true;
        }

       return weekend;
    }

    /**
     * Method to round punches to the nearest interval as specified by the database.
     * @param originalTimestamp the timestamp to be adjusted 
     * @param roundInterval the interval specified by the database that punches should be round to
     * @return the adjusted timestamp 
     * @author Ryan
     */
    public LocalTime roundOutsideInterval(LocalTime originalTimestamp, int roundInterval) {
        
        if (originalTimestamp.getMinute() % roundInterval == 0) { 
            
            this.adjustmentType = PunchAdjustmentType.NONE; 
            originalTimestamp.withSecond(0).withNano(0);
        }
        
        else if ((originalTimestamp.getMinute() + (originalTimestamp.getSecond() * .01)) % roundInterval > (roundInterval / 2)) {
            
            this.adjustmentType = PunchAdjustmentType.INTERVAL_ROUND;
            originalTimestamp = this.originalTimestamp.toLocalTime().plusMinutes(roundInterval - (originalTimestamp.getMinute() % roundInterval)).withSecond(0).withNano(0);
        }

        else if (originalTimestamp.getMinute() % roundInterval < (roundInterval / 2)) {
            
            this.adjustmentType = PunchAdjustmentType.INTERVAL_ROUND;
            originalTimestamp = this.originalTimestamp.toLocalTime().minusMinutes(originalTimestamp.getMinute() % roundInterval).withSecond(0).withNano(0);
        }
        
        return originalTimestamp;
    }
    
    /**
     * @return terminalId
     */
    public int getTerminalid() {
        
        return terminalId;
    }
    
    /**
     * @return id
     */
    public int getId() {
        
        return id;
    }

    /**
     * @return badge
     */
    public Badge getBadge() {
        
        return badge;
    }
    
    /**
     * @return punchType
     */
    public EventType getPunchtype() {
        
        return punchType;
    }
    
    /**
     * @return originalTimestamp
     */
    public LocalDateTime getOriginaltimestamp() {
        
        return originalTimestamp;
    }
    
    /**
     * @return adjustedTimestamp
     */
    public LocalDateTime getAdjustedtimestamp() {
        
        return adjustedTimestamp;
    }

    /**
     * @return adjustmentType
     */
    public PunchAdjustmentType getAdjustmenttype() {
        
        return adjustmentType;
    }
    
    /**
     * Sets the originalTimestamp.
     * @param originalTimestamp the original timestamp of a punch
     */
    public void setOriginalTimestamp(LocalDateTime originalTimestamp) {
        
        this.originalTimestamp = originalTimestamp;
    }
    
    /**
     * @return the original timestamp of a punch, its punch type, and the badge of the person who made it in a formatted string to satisfy tests
     */
    public String printOriginal() {

        StringBuilder s = new StringBuilder();
        
        // Get the time in the correct format
        String formattedDate = originalTimestamp.format(formatter);
        
        // Capitalize the abbreviated day
        formattedDate = formattedDate.substring(0, 3).toUpperCase() + formattedDate.substring(3);
        
        // Format Information in String Builder
        s.append('#').append(badge.getId()).append(' ');
        s.append(punchType).append(": ").append(formattedDate);
        
        return s.toString();
    }
    
    /**
     * @return the adjusted timestamp of a punch, its punch type, the adjustment type, and the badge of the person who made it in a formatted string to satisfy tests
     */
    public String printAdjusted() {
        
        StringBuilder s = new StringBuilder();
        
        s.append('#').append(badge.getId()).append(' ');
        s.append(punchType).append(": ").append(adjustedTimestamp.format(formatter).toUpperCase());
        s.append(" (").append(adjustmentType).append(")");
        
        return s.toString();
    }
    
    /**
     * Overrides the toString() method
     * @return the formatted original timestamp 
     */
    @Override
    public String toString() {
        
        return printOriginal();
    }
}