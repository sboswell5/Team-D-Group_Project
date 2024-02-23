package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;

// Working on this
public class Punch {
    
private final int terminalId; // fix id issue
    private int id;
private final Badge badge;
private final EventType punchType;
private LocalDateTime originalTimestamp;

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

        // Add in event type stuff to identify clock in, clock out, or time out
        
        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append("CLOCK IN: ").append(originalTimestamp);
   
        //"#D2C39273 CLOCK IN: WED 09/05/2018 07:00:07"

        return s.toString();

    }

}
