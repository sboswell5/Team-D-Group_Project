package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.*;

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
            //punchData.put("adjustmenttype", String.valueOf()); // get value
            punchData.put("originaltimestamp", String.valueOf(dlp.toString()));
            punchData.put("adjustedtimestamp", String.valueOf(dlp.printAdjusted()));

            /* Append HashMap to ArrayList */
            jsonData.add(punchData);
        }
        
        /* Encode into JSON String */
        String json = Jsoner.serialize(jsonData);
        
        /* Return JSON String to caller */
        return json;
       
    }
}