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
    private LocalDateTime originalTimestamp, adjustedtimestamp;
    private PunchAdjustmentType adjustmenttype;

    // Minimal constructor for Punch objects
    // Added originalTimestamp
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
    
    public void adjust(Shift s){
                       
        //defining our variables: 
        
       LocalTime shiftstart = s.getShiftStart();
       
       LocalTime shiftstop = s.getShiftStop();
       
       LocalTime lunchstart = s.getLunchStart();
       
       LocalTime lunchstop = s.getLunchStop();
       
       int roundInterval = s.getRoundInterval();
       
       int gracePeriod = s.getGracePeriod();
       
       int dockPenalty = s.getDockPenalty();
       //if it is a weekday
       if(isWeekend(originalTimestamp) == false){
           
           LocalDate placeholder = LocalDate.from(originalTimestamp);
           //if we are clocking in for a start of shift
           if(punchType == punchType.CLOCK_IN){
               
               if(originalTimestamp.toLocalTime().isBefore(shiftstart)){
                   
                   adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);
                   
               }
               else if(originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(gracePeriod))){
               
                    adjustedtimestamp = LocalDateTime.of(placeholder, originalTimestamp.toLocalTime().plusMinutes(dockPenalty));
           }
          }
          //if we are clocking out at the end of shift 
          if(punchType == punchType.CLOCK_OUT){
              if(originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(gracePeriod))){
                  adjustedtimestamp = LocalDateTime.of(placeholder, originalTimestamp.toLocalTime().minusMinutes(dockPenalty));
              }
              else if(originalTimestamp.toLocalTime().isAfter(shiftstop)){
                  adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
              }
            //not sure if this is right, how do we handle lunches??
          }
          //if we are clocking out for lunch? what does time out mean :)
          if(punchType == punchType.TIME_OUT){
              if(originalTimestamp.toLocalTime().isBefore(lunchstart) || originalTimestamp.toLocalTime().isAfter(lunchstart)){
                  adjustedtimestamp = LocalDateTime.of(placeholder, lunchstart);
              }
           //same logic for clocking back in after lunch: what is the unique identifier for this? 
          }
       }
       
       
       
       
        
        //get badge
        
        //determine if they are on break or actually clocking out for the day 
        
        //get localDateTime of the shift they're on, figure out how to determine if they need to be penalized or are in the grace period
        
        //result is saved as adjustedTimeStamp of localDateTime type
 
        
        
        
    }
    
    //creating a function to determine if it's the weekend or not: 
    
    public boolean isWeekend(LocalDateTime day){
        
        boolean weekend = false;
        
        if(day.getDayOfWeek().toString().equals("SATURDAY") || day.getDayOfWeek().toString().equals("SUNDAY")){
            
            weekend = true;
            
        }
        
       return weekend; 
        
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
    
    public void setOriginalTimestamp(LocalDateTime originalTimestamp) {
        
        this.originalTimestamp = originalTimestamp;
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
