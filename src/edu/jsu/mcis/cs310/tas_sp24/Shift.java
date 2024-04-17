package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.util.HashMap;

public class Shift {
    
    private final String description;
    private final Integer id;
    private final DailySchedule defaultschedule;
    
    public Shift(HashMap<String, String> shift, DailySchedule dailySchedule) {
        this.id = Integer.valueOf(shift.get("id"));
        this.description = shift.get("description");
        this.defaultschedule = dailySchedule;
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
