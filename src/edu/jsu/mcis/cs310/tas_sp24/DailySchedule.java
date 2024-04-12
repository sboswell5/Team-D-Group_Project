package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.util.HashMap;

public class DailySchedule {
    
    private final Integer roundInterval, gracePeriod, dockPenalty, lunchThreshold;
    private final Duration shiftDuration, lunchDuration;
    private final LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    
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
    
    public int getRoundInterval() {
        return this.roundInterval;
    }
    
    public int getGracePeriod() {
        return this.gracePeriod;
    }
    
    public int getDockPenalty() {
        return this.dockPenalty;
    }
    
    public int getLunchThreshold() {
        return this.lunchThreshold;
    }
    
    public Duration getShiftDuration() {
        return this.shiftDuration;
    }
    
    public Duration getLunchDuration() {
        return this.lunchDuration;
    }
    
    public LocalTime getShiftStart() {
        return this.shiftStart;
    }
    
    public LocalTime getShiftStop() {
        return this.shiftStop;
    }
    
    public LocalTime getLunchStart() {
        return this.lunchStart;
    }
    
    public LocalTime getLunchStop() {
        return this.lunchStop;
    }
}