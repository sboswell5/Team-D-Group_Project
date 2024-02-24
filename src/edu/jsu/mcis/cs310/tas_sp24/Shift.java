package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.util.HashMap;

public class Shift {
    
    private final String description;
    private final int id, roundInterval, gracePeriod, dockPenalty, lunchThreshold, shiftDuration, lunchDuration;
    private final LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    
    public Shift(HashMap shift) {
        this.id = 0;
        this.description = "";
        this.shiftStart = null;
        this.shiftStop = null;
        this.roundInterval = 0;
        this.gracePeriod = 0;
        this.dockPenalty = 0;
        this.lunchStart = null;
        this.lunchStop = null;
        this.lunchThreshold = 0;
        this.shiftDuration = 0;
        this.lunchDuration = 0;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getId() {
        return this.id;
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
    
    public int getShiftDuration() {
        return this.shiftDuration;
    }
    
    public int getLunchDuration() {
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
    
    //toString override incoming
}
