package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Punch {
    
    private final int terminalId;
    private int id;
    private final Badge badge;
    private final EventType punchType;
    private LocalDateTime originalTimestamp, adjustedTimestamp;
    private PunchAdjustmentType adjustmentType;
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

    // Constructor to make a punch
    public Punch(int terminalId, Badge badge, EventType punchType) {

        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTimestamp = LocalDateTime.now();
    }
    
    // Constructor for existing punches
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.punchType = punchType; 
    }
    
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
    
    // Checks to see if the day is a weekend
    public boolean isWeekend(LocalDateTime day){
        
        boolean weekend = false;

        if (day.getDayOfWeek().toString().equals("SATURDAY") || day.getDayOfWeek().toString().equals("SUNDAY")){

            weekend = true;
        }

       return weekend;
    }

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
    
    public int getTerminalid() {
        
        return terminalId;
    }
    
    public int getId() {
        
        return id;
    }

    public Badge getBadge() {
        
        return badge;
    }
    
    public EventType getPunchtype() {
        
        return punchType;
    }
    
    public LocalDateTime getOriginaltimestamp() {
        
        return originalTimestamp;
    }
    
    public LocalDateTime getAdjustedtimestamp() {
        
        return adjustedTimestamp;
    }

    public PunchAdjustmentType getAdjustmenttype() {
        
        return adjustmentType;
    }
    
    public void setOriginalTimestamp(LocalDateTime originalTimestamp) {
        
        this.originalTimestamp = originalTimestamp;
    }
    
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
    
    public String printAdjusted() {
        
        StringBuilder s = new StringBuilder();
        
        s.append('#').append(badge.getId()).append(' ');
        s.append(punchType).append(": ").append(adjustedTimestamp.format(formatter).toUpperCase());
        s.append(" (").append(adjustmentType).append(")");
        
        return s.toString();
    }
    
    @Override
    public String toString() {
        
        return printOriginal();
    }
}