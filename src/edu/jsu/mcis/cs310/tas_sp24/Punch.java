package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;

/* Working on this
// CURRENT ISSUE:
// EXPECTED: #D2C39273 CLOCK IN: [WED 09/05/2018 ]07:00:07
// GETTING: #D2C39273 CLOCK IN: [2018-09-05T]07:00:07 ... need to figure out
// could be either Punch.java or PunchDAO.java issue?
*/

public class Punch {
    
private final int terminalId;
private int id;
private final Badge badge;
private final EventType punchType;
private LocalDateTime originalTimestamp, adjustedTimestamp;
private PunchAdjustmentType adjustmentType;

    public Punch(int terminalId, Badge badge, EventType punchType) {

        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
    }
    
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.punchType = punchType; 
    }

    public int getTerminalId() {
        
        return terminalId;
    }
    
    public int getId() {
        
        return id;
    }

    public Badge getBadge() {
        
        return badge;
    }
    
    public EventType getPunchType() {
        
        return punchType;
    }
    
    public LocalDateTime OriginalTimestamp() {
        
        return originalTimestamp;
    }
    
    public String printOriginal() {

        StringBuilder s = new StringBuilder();

        switch (punchType) {
            
            case CLOCK_OUT:
                
                s.append('#').append(badge.getId()).append(' ');
                s.append(EventType.CLOCK_OUT).append(": ").append(originalTimestamp);
                break;
                
            case CLOCK_IN:
                
                s.append('#').append(badge.getId()).append(' ');
                s.append(EventType.CLOCK_IN).append(": ").append(originalTimestamp);
                break;
                
            case TIME_OUT:
                
                s.append('#').append(badge.getId()).append(' ');
                s.append(EventType.TIME_OUT).append(": ").append(originalTimestamp);
                break;
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
