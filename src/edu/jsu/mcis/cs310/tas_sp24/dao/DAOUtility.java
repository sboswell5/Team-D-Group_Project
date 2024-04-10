package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.*;
import java.math.BigDecimal;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
        
        /* Create ArrayList Object */
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();

        for (Punch dlp : dailypunchlist) {
            /* Create HashMap Object (one for every Punch!) */
            HashMap<String, String> punchData = new HashMap<>();
            
            /* Add Punch Data to HashMap */
            punchData.put("id", String.valueOf(dlp.getId()));
            punchData.put("badgeid", String.valueOf(dlp.getBadge().getId()));
            punchData.put("terminalid", String.valueOf(dlp.getTerminalid()));
            punchData.put("punchtype", String.valueOf(dlp.getPunchtype()));
            punchData.put("adjustmenttype", String.valueOf(dlp.getAdjustmenttype()));
            punchData.put("originaltimestamp", String.valueOf(dlp.formatDate(dlp.getOriginaltimestamp())));
            punchData.put("adjustedtimestamp", String.valueOf(dlp.formatDate(dlp.getAdjustedtimestamp())));

            /* Append HashMap to ArrayList */
            jsonData.add(punchData);
        }
        
        /* Encode into JSON String */
        String json = Jsoner.serialize(jsonData);
        
        /* Return JSON String to caller */
        return json; 
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        
    // iterate through the collection of punches - totaling up the number of minutes between pairs of "clock in" and "clock out" punches
        // minus lunch break deductions

        LocalTime clockInTime = null;
        long minutesWorked = 0;
        boolean clockIn = false;
        long lunchDuration = shift.getLunchDuration().toMinutes();
        Punch previousPunch = null;
       
        for (Punch punch : dailypunchlist) {
            
            switch (punch.getPunchtype()) {
                
                case CLOCK_IN:
                    
                    
                    if (previousPunch != null && previousPunch.getPunchtype() == EventType.TIME_OUT) {
                        
                        break;
                    }
                    
                    clockIn = true;
                    clockInTime = punch.getAdjustedtimestamp().toLocalTime();
                    //continue;
                    break;

                case CLOCK_OUT:
                    
                    if (clockIn) {
                        
                        LocalTime clockOutTime = punch.getAdjustedtimestamp().toLocalTime();
                        minutesWorked += clockInTime.until(clockOutTime, ChronoUnit.MINUTES);
                        //minutesWorked = clockInTime.until(punch.getAdjustedtimestamp().toLocalTime(), ChronoUnit.MINUTES);
                        
                        if (minutesWorked >= shift.getLunchThreshold()) {
                            
                            minutesWorked -= lunchDuration;
                            
                        //} else {
                            
                            //minutesWorked = clockInTime.until(punch.getAdjustedtimestamp().toLocalTime(), ChronoUnit.MINUTES);
                        //}
                        }
                        
                        clockIn = false;
                    }
                    
                    break;
                    
                case TIME_OUT:
                    
                    //System.out.println("Time out");
                    clockIn = false;
                    break;
            }
            
            previousPunch = punch;
        }
        
        return (int) minutesWorked;
        
    // time between "clock in" and "time out" pairs should NOT be included in daily total (if it's a TIME_OUT)

    // deduction for lunch should be made IF employee worked more than minimum minutes (even if they didn't clock out)
        // deduction should NOT be made if employee didn't work enough minutes
        
    }
    
    
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {
        
        // ((scheduledMinutes - workedMinutes) / scheduledMinutes) * 100
        
        //int scheduledMinutes = calculateTotalMinutes(shift), actualMinutes = calculateTotalMinutes(punch);
        //BigDecimal absenteeism = (scheduledMinutes / actualMinutes) - 100;
        
        double scheduledMinutes = s.getShiftDuration().toMinutes();
        double workedMinutes = calculateTotalMinutes(punchlist, s);
        double absenteeism = 0;
        
        absenteeism = ((scheduledMinutes - workedMinutes) / scheduledMinutes) * 100;
        
        return BigDecimal.valueOf(absenteeism);
    }
}
