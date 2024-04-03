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
    
    /*public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        
    // iterate through the collection of punches - totaling up the number of minutes between pairs of "clock in" and "clock out" punches
        // minus lunch break deductions
    // time between "clock in" and "time out" pairs should NOT be included in daily total
    // deduction for lunch should be made IF employee worked more than minimum minutes (even if they didn't clock out)
        // deduction should NOT be made if employee didn't work enough minutes
        
    }*/
    
    /*
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {
        int scheduledMinutes = calculateTotalMinutes(shift), actualMinutes = calculateTotalMinutes(punch);
        BigDecimal absenteeism = (scheduledMinutes / actualMinutes) - 100;
        
        return absenteeism;
    }*/
}