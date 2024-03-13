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
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

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
       
      if(originalTimestamp.toLocalTime() == shiftstart){
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
           
           //System.out.println("First if statement");
       }
       else if(originalTimestamp.toLocalTime() == lunchstart){
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
       else if(originalTimestamp.toLocalTime() == lunchstop){
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
       else if(originalTimestamp.toLocalTime() == shiftstop){
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
              //if it is a weekday
       if(!isWeekend(originalTimestamp)){
           
           LocalDate placeholder = LocalDate.from(originalTimestamp);
           
           // =================  CLOCK IN ========================
           if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isBefore(shiftstart.minusMinutes(roundInterval))) {
               adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);
           }
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isBefore(shiftstart.plusMinutes(gracePeriod))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_START;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);
           }
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(gracePeriod))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
               adjustedtimestamp = LocalDateTime.of(placeholder,shiftstart.plusMinutes(dockPenalty));
           }
           
           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isBefore(lunchstart)) {
               adjustmenttype = PunchAdjustmentType.LUNCH_START;
               adjustedtimestamp = LocalDateTime.of(placeholder, lunchstart);
           }
           
           // ================== LUNCH ===========================
           if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isBefore(lunchstart)) {
               adjustmenttype = PunchAdjustmentType.LUNCH_START;
               adjustedtimestamp = LocalDateTime.of(placeholder, lunchstart);
           }
           
           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isBefore(lunchstop)) {
               adjustmenttype = PunchAdjustmentType.LUNCH_START;
               adjustedtimestamp = LocalDateTime.of(placeholder, lunchstart);
           }
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isAfter(lunchstart)) {
               adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
               adjustedtimestamp = LocalDateTime.of(placeholder, lunchstop);
           }
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isBefore(lunchstop)) {
               adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
               adjustedtimestamp = LocalDateTime.of(placeholder, lunchstop);
           }
           
           // ================== CLOCK OUT ========================
           if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(roundInterval))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
           }
           
           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isAfter(shiftstop.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(gracePeriod))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop.minusMinutes(dockPenalty));
           }
           
           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isAfter(shiftstop.minusMinutes(gracePeriod))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
           }
           
           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isAfter(shiftstop.plusMinutes(roundInterval))) {
               adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
           }
           
           
           
           
           
           
           
           
           
           /*
           
           //if we are clocking in for a start of shift
           if(punchType == EventType.CLOCK_IN) {
               
               if(originalTimestamp.toLocalTime().isBefore(LocalTime.from(shiftstart.minusMinutes(roundInterval)))){
                   adjustedtimestamp = LocalDateTime.of(placeholder, originalTimestamp.toLocalTime().plusMinutes(shiftstart.minusMinutes(roundInterval).getMinute()));
               }
               else if(originalTimestamp.toLocalTime().isBefore(shiftstart)) {
                   
                   adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);
                   
                   adjustedtimestamp.withSecond(0).withNano(0);
                   
                   adjustmenttype = PunchAdjustmentType.SHIFT_START;
               }
               else if(originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(gracePeriod))) {
               
                   adjustedtimestamp = LocalDateTime.of(placeholder, originalTimestamp.toLocalTime().plusMinutes(dockPenalty));
                   
                   adjustedtimestamp.withSecond(0).withNano(0);
                   
                   adjustmenttype = PunchAdjustmentType.SHIFT_START;
                }
          }
          //Lunch Break
          if(punchType == EventType.CLOCK_OUT && adjustedtimestamp.toLocalTime() == lunchstart) {
              if(originalTimestamp.toLocalTime().isBefore(lunchstart) || originalTimestamp.toLocalTime().isAfter(lunchstart)){
                  
                  
                  adjustedtimestamp = LocalDateTime.of(placeholder, lunchstart);
                  
                  adjustedtimestamp.withSecond(0).withNano(0);
                  
                   adjustmenttype = PunchAdjustmentType.LUNCH_START;
              }
          
          //if(originalTimestamp.toLocalTime() == lunchstop){
              
              if(originalTimestamp.toLocalTime().isBefore(lunchstop) || originalTimestamp.toLocalTime().isAfter(lunchstop)){
                  
                  adjustedtimestamp = LocalDateTime.of(placeholder, lunchstop);
                  
                  adjustedtimestamp.withSecond(0).withNano(0);
                  
                  adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
                  
              }
          //}
          //if we are clocking out at the end of shift 
          if(punchType == EventType.CLOCK_OUT){
              if(originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(gracePeriod))){
                  
                  adjustedtimestamp = LocalDateTime.of(placeholder, originalTimestamp.toLocalTime().minusMinutes(dockPenalty));
                  
                  adjustedtimestamp.withSecond(0).withNano(0);
                  
                  adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
              }
              else if(originalTimestamp.toLocalTime().isAfter(LocalTime.from(shiftstart.minusMinutes(roundInterval)))){
                  
                  adjustedtimestamp = LocalDateTime.of(placeholder, originalTimestamp.toLocalTime().minusMinutes(shiftstop.plusMinutes(roundInterval).getMinute()));
                  
              }
              else if(originalTimestamp.toLocalTime().isAfter(shiftstop)){
                  
                  adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
                  
                  adjustedtimestamp.withSecond(0).withNano(0);
                                    
                  adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
              }
          }
          }
*/
          
          }
       
       System.out.println(adjustedtimestamp);
              
       
        
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
        System.out.println("#" + badge.getId() + " " + punchType + ": " + adjustedtimestamp.format(formatter).toUpperCase() + " " + adjustmenttype);
        return "#" + badge.getId() + " " + punchType + ": " + adjustedtimestamp.format(formatter).toUpperCase() +  " " +adjustmenttype;
                
    }
    
    @Override
    public String toString() {
        
        return printOriginal();
    }
}
