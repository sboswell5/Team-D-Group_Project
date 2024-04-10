package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Punch {
    
    private final int terminalId;
    private int id;
    private final Badge badge;
    private final EventType punchType;
    private LocalDateTime originalTimestamp, adjustedtimestamp;
    private PunchAdjustmentType adjustmenttype;
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

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
        
        adjustedtimestamp = originalTimestamp;
        
        //defining our variables:

        LocalTime shiftstart = s.getShiftStart();

        LocalTime shiftstop = s.getShiftStop();

        LocalTime lunchstart = s.getLunchStart();

        LocalTime lunchstop = s.getLunchStop();

        int roundInterval = s.getRoundInterval();

        int gracePeriod = s.getGracePeriod();

        int dockPenalty = s.getDockPenalty();

        if (originalTimestamp.toLocalTime().withSecond(0).equals(shiftstart)) {

            adjustedtimestamp = originalTimestamp.withSecond(0).withNano(0);
            adjustmenttype = PunchAdjustmentType.NONE;
        }

       else if(originalTimestamp.toLocalTime().withSecond(0).equals(lunchstart)) {
           adjustedtimestamp = originalTimestamp.withSecond(0).withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
       else if(originalTimestamp.toLocalTime().withSecond(0).equals(lunchstop)) {
           
           adjustedtimestamp = originalTimestamp.withSecond(0).withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }
       else if(originalTimestamp.toLocalTime().withSecond(0).equals(shiftstop)) {
           
           adjustedtimestamp = originalTimestamp.withSecond(0).withNano(0);
           adjustmenttype = PunchAdjustmentType.NONE;
       }

        //System.out.println(originalTimestamp);

        if (!isWeekend(originalTimestamp)) {
            LocalDate placeholder = LocalDate.from(originalTimestamp);

            // =================  CLOCK IN ========================
            if (punchType == EventType.CLOCK_IN) {

                if (originalTimestamp.toLocalTime().isAfter(shiftstart.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftstart)) {
                    adjustmenttype = PunchAdjustmentType.SHIFT_START;
                    adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);

                } else if (originalTimestamp.toLocalTime().isBefore(shiftstart.minusMinutes(roundInterval))) {
                    adjustedtimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval));

                } else if (originalTimestamp.toLocalTime().isBefore(shiftstart.plusMinutes(gracePeriod))) {
                    adjustmenttype = PunchAdjustmentType.SHIFT_START;
                    adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart);

                } else if (originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(gracePeriod)) && originalTimestamp.toLocalTime().isBefore(lunchstart)) {
                    adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                    adjustedtimestamp = LocalDateTime.of(placeholder, shiftstart.plusMinutes(dockPenalty));
                }
            }

            // ================== LUNCH ===========================
            if (punchType == EventType.CLOCK_OUT) {

                if (originalTimestamp.toLocalTime().isBefore(lunchstart)) {
                    adjustmenttype = PunchAdjustmentType.LUNCH_START;
                    adjustedtimestamp = LocalDateTime.of(placeholder, lunchstart).withSecond(0).withNano(0);
                }

                else if(originalTimestamp.toLocalTime().isAfter(lunchstart)) {    
                    adjustmenttype = PunchAdjustmentType.LUNCH_START;
                    adjustedtimestamp = LocalDateTime.of(placeholder, lunchstart).withSecond(0).withNano(0);
                }
            }
            
            if (punchType == EventType.CLOCK_IN) {
                
                if (originalTimestamp.toLocalTime().isAfter(lunchstop)) {
                    adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
                    adjustedtimestamp = LocalDateTime.of(placeholder, lunchstop).withSecond(0).withNano(0);
                }
            
                else if(originalTimestamp.toLocalTime().isBefore(lunchstop) && originalTimestamp.toLocalTime().isAfter(shiftstart.plusMinutes(roundInterval)) ){
                    adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
                    adjustedtimestamp = LocalDateTime.of(placeholder, lunchstop).withSecond(0).withNano(0);
                }
            }

            // ================== CLOCK OUT ========================
            if (punchType == EventType.CLOCK_OUT) {

                if (originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isAfter(lunchstop)) {
                    adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
                    adjustedtimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);

                } else if ((originalTimestamp.toLocalTime().isAfter(shiftstop.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(gracePeriod))) ||
                        (originalTimestamp.toLocalTime().equals(shiftstop.minusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isBefore(shiftstop.minusMinutes(gracePeriod)))) {
                    adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                    adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop.minusMinutes(dockPenalty)).withSecond(0).withNano(0);

                } else if (originalTimestamp.toLocalTime().isAfter(shiftstop.minusMinutes(gracePeriod)) && originalTimestamp.toLocalTime().isBefore(shiftstop)) {
                    adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
                    adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop).withSecond(0).withNano(0);

                } else if (originalTimestamp.toLocalTime().isAfter(shiftstop) && originalTimestamp.toLocalTime().isBefore(shiftstop.plusMinutes(roundInterval))) {
                    adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
                    adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop).withSecond(0).withNano(0);

                } else if (originalTimestamp.toLocalTime().isBefore(shiftstop.plusMinutes(roundInterval)) && originalTimestamp.toLocalTime().isAfter(lunchstop)) {
                    adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
                    adjustedtimestamp = LocalDateTime.of(placeholder, shiftstop).withSecond(0).withNano(0);

                } else if (originalTimestamp.toLocalTime().isAfter(shiftstop.plusMinutes(roundInterval))) {
                    adjustedtimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);
                }
                System.out.println(adjustedtimestamp);
            }
        
            
        } else {
            LocalDate placeholder = LocalDate.from(originalTimestamp);
            adjustedtimestamp = LocalDateTime.of(placeholder, roundOutsideInterval(originalTimestamp.toLocalTime(), roundInterval)).withSecond(0).withNano(0);
        }
    }
    
    //creating a function to determine if it's the weekend or not:
    public boolean isWeekend(LocalDateTime ldt){
        
        DayOfWeek day = ldt.getDayOfWeek();
                
        boolean weekend = false;

        if (day.getDayOfWeek().toString().equals("SATURDAY") || day.getDayOfWeek().toString().equals("SUNDAY")){

            weekend = true;
        }

       return weekend;
        
    }

    // can be 2 lines of code after mod

    public LocalTime roundOutsideInterval(LocalTime originalTimestamp, int roundInterval) {
        if(originalTimestamp.getMinute() % roundInterval == 0) { 
            this.adjustmenttype = PunchAdjustmentType.NONE; 
            originalTimestamp.withSecond(0).withNano(0);
        }
        else if((originalTimestamp.getMinute() + (originalTimestamp.getSecond() * .01)) % roundInterval > (roundInterval / 2)) {
            this.adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
            originalTimestamp = this.originalTimestamp.toLocalTime().plusMinutes(roundInterval - (originalTimestamp.getMinute() % roundInterval)).withSecond(0).withNano(0);
        }

        else if(originalTimestamp.getMinute() % roundInterval < (roundInterval / 2)) {
            this.adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
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
        
        return adjustedtimestamp;
    }

    public PunchAdjustmentType getAdjustmenttype() {
        return adjustmenttype;
    }
    
    public void setOriginalTimestamp(LocalDateTime originalTimestamp) {
        
        this.originalTimestamp = originalTimestamp;
    }
    
    public String formatDate(LocalDateTime ldt) {
        // Get the time in the correct format
        String formattedDate = ldt.format(formatter);
        
        // Capitalize the abbreviated day
        formattedDate = formattedDate.substring(0, 3).toUpperCase() + formattedDate.substring(3);
        
        return formattedDate;
    }
    
    public String printOriginal() {

        StringBuilder s = new StringBuilder();
        String fd = formatDate(originalTimestamp);
        
        // Format Information in String Builder
        s.append('#').append(badge.getId()).append(' ');
        s.append(punchType).append(": ").append(fd);
        
        return s.toString();
    }
    
    public String printAdjusted() {
        
        StringBuilder s = new StringBuilder();
        
        s.append('#').append(badge.getId()).append(' ');
        s.append(punchType).append(": ").append(formatDate(adjustedtimestamp));
        s.append(" (").append(adjustmenttype).append(")");
        
        return s.toString();
    }
    
    @Override
    public String toString() {
        
        return printOriginal();
    }
}
