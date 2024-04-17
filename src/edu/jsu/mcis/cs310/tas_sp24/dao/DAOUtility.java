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
        
        // Should iterate through the list (dailypunchlist)
        // Copy the data for each punch into an ArrayList of HashMaps
        // Encode as JSON string
        // Return string to caller
        
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
            punchData.put("originaltimestamp", String.valueOf(dlp.getOriginaltimestamp().format(dlp.formatter).toUpperCase()));
            punchData.put("adjustedtimestamp", String.valueOf(dlp.getAdjustedtimestamp().format(dlp.formatter).toUpperCase()));

            /* Append HashMap to ArrayList */
            jsonData.add(punchData);
        }
        
        /* Encode into JSON String */
        String json = Jsoner.serialize(jsonData);
        
        /* Return JSON String to caller */
        return json; 
    }
    
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift shift) {
        
        /* Create ArrayList Object */
        ArrayList<HashMap<String, String>> PunchListData = new ArrayList<>();
        
        /* Create Json Object */
        JsonObject jsonData = new JsonObject();
        
        /* Calculate Total Minutes & Absenteeism */
        int minutes = calculateTotalMinutes(punchlist, shift);
        BigDecimal percentage = calculateAbsenteeism(punchlist, shift);
        String absenteeism = String.format("%.2f%%", percentage);
        
        for (Punch p : punchlist) {
            /* Create HashMap Object (one for every Punch!) */
            HashMap<String, String> punchData = new HashMap<>();
            
            /* Add Punch Data to HashMap */
            punchData.put("id", String.valueOf(p.getId()));
            punchData.put("badgeid", String.valueOf(p.getBadge().getId()));
            punchData.put("terminalid", String.valueOf(p.getTerminalid()));
            punchData.put("punchtype", String.valueOf(p.getPunchtype()));
            punchData.put("adjustmenttype", String.valueOf(p.getAdjustmenttype()));
            punchData.put("originaltimestamp", String.valueOf(p.getOriginaltimestamp().format(p.formatter).toUpperCase()));
            punchData.put("adjustedtimestamp", String.valueOf(p.getAdjustedtimestamp().format(p.formatter).toUpperCase()));

            /* Append HashMap to ArrayList */
            PunchListData.add(punchData);
        }
        
        jsonData.put("punchlist", PunchListData);
        jsonData.put("totalminutes", minutes);
        jsonData.put("absenteeism", absenteeism);
      
        /* Encode into JSON String */
        String json = Jsoner.serialize(jsonData);
        
        /* Return JSON String to caller */
        return json; 
        
    }

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        LocalTime clockInTime = null;
        long minutesWorked = 0;
        boolean clockedIn = false;
        long lunchDuration = shift.getLunchDuration().toMinutes();

        for (Punch punch : dailypunchlist) {
            switch (punch.getPunchtype()) {
                case CLOCK_IN:
                    clockedIn = true;
                    clockInTime = punch.getAdjustedtimestamp().toLocalTime();
                    continue;

                case CLOCK_OUT:
                    if (clockedIn) {
                        long timeBetween = clockInTime.until(punch.getAdjustedtimestamp().toLocalTime(), ChronoUnit.MINUTES);
                        if (timeBetween >= shift.getLunchThreshold() && !(punch.isWeekend(punch.getAdjustedtimestamp()))) {
                            minutesWorked += timeBetween - lunchDuration;
                        } else {
                            minutesWorked += timeBetween;
                        }
                    }
                    break;
            }
        }
        return (int) minutesWorked;
    }
    
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {
        
        double actualMinutes = calculateTotalMinutes(punchlist, s);
        double scheduledMinutes = 0; 
        
        for (int i = 1; i <= 5; i++) {
            
            scheduledMinutes += (s.getShiftDuration().toMinutes() - s.getLunchDuration().toMinutes());
        }
   
        double absenteeism = ((scheduledMinutes - actualMinutes) / scheduledMinutes) * 100;
        return BigDecimal.valueOf(absenteeism);
    }
}
