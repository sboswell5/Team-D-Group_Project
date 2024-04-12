package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.util.HashMap;

public class Shift {
    
    //private final String description;
    //private final Integer id, roundInterval, gracePeriod, dockPenalty, lunchThreshold;
    //private final Duration shiftDuration, lunchDuration;
    //private final LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    //private final DailySchedule defaultschedule;
    
    private final String description;
    private final Integer id;
    private final DailySchedule defaultschedule;
    
    /*public Shift(HashMap<String, String> shift) {
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
    }*/
    
    public Shift(HashMap<String, String> shift) {
        this.id = Integer.valueOf(shift.get("id"));
        this.description = shift.get("description");
        this.defaultschedule = new DailySchedule(shift);
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getId() {
        return this.id;
    }
    
    public DailySchedule getDefaultSchedule() {
        return this.defaultschedule;
    }
    
    public int getRoundInterval() {
        return this.defaultschedule.getRoundInterval();
    }
    
    public int getGracePeriod() {
        return this.defaultschedule.getGracePeriod();
    }
    
    public int getDockPenalty() {
        return this.defaultschedule.getDockPenalty();
    }
    
    public int getLunchThreshold() {
        return this.defaultschedule.getLunchThreshold();
    }
    
    public Duration getShiftDuration() {
        return this.defaultschedule.getShiftDuration();
    }
    
    public Duration getLunchDuration() {
        return this.defaultschedule.getLunchDuration();
    }
    
    public LocalTime getShiftStart() {
        return this.defaultschedule.getShiftStart();
    }
    
    public LocalTime getShiftStop() {
        return this.defaultschedule.getShiftStop();
    }
    
    public LocalTime getLunchStart() {
        return this.defaultschedule.getLunchStart();
    }
    
    public LocalTime getLunchStop() {
        return this.defaultschedule.getLunchStop();
    }
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append(description).append(": ").append(getShiftStart()).append(" - ").append(getShiftStop()).append(" (");
        s.append(getShiftDuration().toMinutes()).append(" minutes); Lunch: ").append(getLunchStart()).append(" - ").append(getLunchStop());
        s.append(" (").append(getLunchDuration().toMinutes()).append(" minutes)");

        return s.toString();
    }
}
