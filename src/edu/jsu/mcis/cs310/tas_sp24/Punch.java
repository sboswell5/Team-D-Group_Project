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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

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
       
       if(originalTimestamp.toLocalTime().withSecond(0).equals(shiftstart)) {
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
       else if(originalTimestamp.toLocalTime().withSecond(0).equals(lunchstart)) {
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
       else if(originalTimestamp.toLocalTime().withSecond(0).equals(lunchstop)) {
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
       else if(originalTimestamp.toLocalTime().withSecond(0).equals(shiftstop)) {
           
           adjustedtimestamp = originalTimestamp.withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
              //if it is a weekday
       if(!isWeekend(originalTimestamp)){
           
           LocalDate placeholder = LocalDate.from(originalTimestamp);
           
           // =================  CLOCK IN ========================
           /*
           if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isBefore(shiftstart.minusMinutes(roundInterval))) {
               adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);
           }*/

           if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isAfter(shiftstart.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftstart)) {
               adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);
           }

           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isBefore(shiftstart.minusMinutes(roundInterval))) {
               //adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
                adjustedtimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval));
           }
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isBefore(shiftstart.plusMinutes(gracePeriod))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_START;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);
           }
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(gracePeriod)) && originalTimestamp.toLocalTime().isBefore(lunchstart)) {
               adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
               adjustedtimestamp = LocalDateTime.of(placeholder,shiftstart.plusMinutes(dockPenalty));
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
           
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isAfter(lunchstart) && originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(roundInterval))) {
               adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
               adjustedtimestamp = LocalDateTime.of(placeholder, lunchstop);
           }
           
           
           else if(punchType == EventType.CLOCK_IN && originalTimestamp.toLocalTime().isBefore(lunchstop) && originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(roundInterval))) {
               adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
               adjustedtimestamp = LocalDateTime.of(placeholder, lunchstop);
           }
           
           // ================== CLOCK OUT ========================
           if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isAfter(lunchstop)) {
               adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop.minusMinutes(dockPenalty));
           }

           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isAfter(shiftstop.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(gracePeriod))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop.minusMinutes(dockPenalty));
           }
           
           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isAfter(shiftstop.minusMinutes(gracePeriod)) && originalTimestamp.toLocalTime().isBefore(shiftstop)) {
               adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
           }
           else if(punchType == EventType.CLOCK_OUT  && originalTimestamp.toLocalTime().isAfter(shiftstop) && originalTimestamp.toLocalTime().isBefore(shiftstop.plusMinutes(roundInterval))) {
               adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
           }
           
           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isBefore(shiftstop.plusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isAfter(shiftstop)) {
               adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
               adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop);
           }

           else if(punchType == EventType.CLOCK_OUT && originalTimestamp.toLocalTime().isAfter(shiftstop.plusMinutes(roundInterval))) {
               //adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
               adjustedtimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval));
           }
          
          }

    }

    //creating a function to determine if it's the weekend or not:
    public boolean isWeekend(LocalDateTime day){
        
        boolean weekend = false;

        if(day.getDayOfWeek().toString().equals("SATURDAY") || day.getDayOfWeek().toString().equals("SUNDAY")){

            weekend = true;

        }

       return weekend;
        
    }

    // can be 2 lines of code after mod
    public  LocalTime roundOutsideInterval(LocalTime originalTimestamp, int roundInterval) {

        int OGminute = originalTimestamp.getMinute();
        LocalTime roundedTime = null;
        int remain = OGminute % roundInterval;

        if(remain == 0) {
            this.adjustmenttype = PunchAdjustmentType.NONE;
            roundedTime = originalTimestamp;
        }
        else if(remain <= 7) {
            this.adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
            roundedTime = originalTimestamp.minusMinutes(remain);
        }
        else if(remain >= 8) {
            this.adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
            roundedTime = originalTimestamp.plusMinutes(remain);
        }

        return roundedTime;
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
        s.append(punchType).append(": ").append(adjustedtimestamp.format(formatter).toUpperCase());
        s.append(" (").append(adjustmenttype).append(")");
        
        //System.out.println("#" + badge.getId() + " " + punchType + ": " + adjustedtimestamp.format(formatter).toUpperCase() + " " + adjustmenttype);
        //return "#" + badge.getId() + " " + punchType + ": " + adjustedtimestamp.format(formatter).toUpperCase() + " " + "(" + adjustmenttype + ")";
         
        return s.toString();
    }
    
    @Override
    public String toString() {
        
        return printOriginal();
    }
}
