package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.util.HashMap;

public class Shift {
    
    private final String description;
    private final Integer id, roundInterval, gracePeriod, dockPenalty, lunchThreshold;
    private final Duration shiftDuration, lunchDuration;
    private final LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    
    public Shift(HashMap<String, String> shift) {
        this.id = Integer.valueOf(shift.get("id"));
        this.description = shift.get("description");
        this.shiftStart = LocalTime.parse(shift.get("shiftStart"));
        this.shiftStop = LocalTime.parse(shift.get("shiftStop"));
        this.roundInterval = Integer.valueOf(shift.get("roundInterval"));
        this.gracePeriod = Integer.valueOf(shift.get("gracePeriod"));
        this.dockPenalty = Integer.valueOf(shift.get("dockPenalty"));
        this.lunchStart = LocalTime.parse(shift.get("lunchStart"));
        this.lunchStop = LocalTime.parse(shift.get("lunchStop"));
        this.lunchThreshold = Integer.valueOf(shift.get("lunchThreshold"));
        this.shiftDuration = Duration.parse(shift.get("shiftDuration"));
        this.lunchDuration = Duration.parse(shift.get("lunchDuration"));
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
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append(description).append(": ").append(shiftStart).append(" - ").append(shiftStop).append(" (");
        s.append(shiftDuration.toMinutes()).append(" minutes); Lunch: ").append(lunchStart).append(" - ").append(lunchStop);
        s.append(" (").append(lunchDuration.toMinutes()).append(" minutes)");

        return s.toString();
    }
}
