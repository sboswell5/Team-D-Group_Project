package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;

// Finished printOriginal();
// Added default case to switch

public class Punch {
    
private final int terminalId;
private int id;
private final Badge badge;
private final EventType punchType;
private LocalDateTime originalTimestamp, adjustedTimestamp;
private PunchAdjustmentType adjustmentType;

    // Minimal constructor for Punch objects
    public Punch(int terminalId, Badge badge, EventType punchType) {

        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
    }
    
    // Constructor for existing punches
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.punchType = punchType; 
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
    
    public String printOriginal() {

        StringBuilder s = new StringBuilder();
        
        // Get the time in the correct format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String formattedDate = originalTimestamp.format(formatter);
        
        // Capitalize the abbreviated day
        formattedDate = formattedDate.substring(0, 3).toUpperCase() + formattedDate.substring(3);
        
        switch (punchType) {
            
            case CLOCK_OUT:
                
                s.append('#').append(badge.getId()).append(' ');
                s.append(EventType.CLOCK_OUT).append(": ").append(formattedDate);
                break;
                
            case CLOCK_IN:
                
                s.append('#').append(badge.getId()).append(' ');
                s.append(EventType.CLOCK_IN).append(": ").append(formattedDate);
                break;
                
            case TIME_OUT:
                
                s.append('#').append(badge.getId()).append(' ');
                s.append(EventType.TIME_OUT).append(": ").append(formattedDate);
                break;
                
            default:
                
                // Default test - look at later?
                throw new IllegalArgumentException("Unexpected punch type: " + punchType);
        }
       
        return s.toString();
    }
    
    public String printAdjusted() {
        
        return ":)"; //to be implemented later
    }
    
    @Override
    public String toString() {
        
        return printOriginal();
    }
}
