package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Absenteeism {

    private final Employee employee;
    private final LocalDate localDate;
    private final BigDecimal bigDecimal;
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public Absenteeism(Employee employee, LocalDate localDate, BigDecimal bigDecimal) {
        
        this.employee = employee;
        this.localDate = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        this.bigDecimal = bigDecimal;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
    
    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        
        // Get the correct format
        String formattedDate = localDate.format(formatter);
        
        s.append('#').append(employee.getBadge().getId()).append(' ');
        s.append("(Pay Period Starting ").append(formattedDate).append("): ");
        s.append(String.format("%.2f%%", getBigDecimal()));
        
        return s.toString();
    }
}
