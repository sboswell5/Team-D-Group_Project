package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.util.HashMap;

/**
 * A method that rule set data for a day. Originally found in Shift.java
 * 
 * @author Kris
 */

public class DailySchedule {
    
    /**
     * roundInterval - The interval rounded by in the event a punch is very late
     * gracePeriod - The safety time where no penalties are made
     * dockPenalty - The amount of time docked in the event of a punch made too late
     * lunchThreshold - The amount of time that determines whether a shift requires a lunch
     * shiftDuration - The time between the start and stop of a shift
     * lunchDuration - The time between the start and stop of a lunch
     * shiftStart - The start time of a shift
     * shiftStop - The stop time of a shift
     * lunchStart - The start time of a lunch
     * lunchStop - The stop time of a lunch
     */
    private final Integer roundInterval, gracePeriod, dockPenalty, lunchThreshold;
    private final Duration shiftDuration, lunchDuration;
    private final LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    
    /**
     * A constructor holding the information of a shift rule set
     * 
     * @param schedule Passes the shift rule set into the constructor via HashMap
     */
    public DailySchedule(HashMap<String, String> schedule) {
        this.shiftStart = LocalTime.parse(schedule.get("shiftStart"));
        this.shiftStop = LocalTime.parse(schedule.get("shiftStop"));
        this.roundInterval = Integer.valueOf(schedule.get("roundInterval"));
        this.gracePeriod = Integer.valueOf(schedule.get("gracePeriod"));
        this.dockPenalty = Integer.valueOf(schedule.get("dockPenalty"));
        this.lunchStart = LocalTime.parse(schedule.get("lunchStart"));
        this.lunchStop = LocalTime.parse(schedule.get("lunchStop"));
        this.lunchThreshold = Integer.valueOf(schedule.get("lunchThreshold"));
        this.shiftDuration = Duration.parse(schedule.get("shiftDuration"));
        this.lunchDuration = Duration.parse(schedule.get("lunchDuration"));
    }
    
    /**
     * @return roundInterval
     */
    public int getRoundInterval() {
        return this.roundInterval;
    }
    
    /**
     * @return gracePeriod
     */
    public int getGracePeriod() {
        return this.gracePeriod;
    }
    
    /**
     * @return dockPenalty
     */
    public int getDockPenalty() {
        return this.dockPenalty;
    }
    
    /**
     * @return lunchThreshold
     */
    public int getLunchThreshold() {
        return this.lunchThreshold;
    }
    
    /**
     * @return shiftDuration
     */
    public Duration getShiftDuration() {
        return this.shiftDuration;
    }
    
    /**
     * @return lunchDuration
     */
    public Duration getLunchDuration() {
        return this.lunchDuration;
    }
    
    /**
     * @return shiftStart
     */
    public LocalTime getShiftStart() {
        return this.shiftStart;
    }
    
    /**
     * @return shiftStop
     */
    public LocalTime getShiftStop() {
        return this.shiftStop;
    }
    
    /**
     * @return lunchStart
     */
    public LocalTime getLunchStart() {
        return this.lunchStart;
    }
    
    /**
     * @return lunchStop
     */
    public LocalTime getLunchStop() {
        return this.lunchStop;
    }
}