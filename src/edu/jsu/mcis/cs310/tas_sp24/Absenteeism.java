package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class Absenteeism {

    private final Employee employee;
    private final LocalDate localDate;
    private final BigDecimal bigDecimal;
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public Absenteeism(Employee employee, LocalDate localDate, BigDecimal bigDecimal) {
        
        this.employee = employee;
        this.localDate = localDate;
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
        
        // build string "#28DC3FB8 (Pay Period Starting 09-02-2018): 2.50%"
        
        s.append('#').append(employee.getBadge().getId()).append(' ');
        s.append("(Pay Period Starting ").append(formattedDate).append("): ");
        s.append(getBigDecimal());
        
        //System.out.println(getBigDecimal());

        return s.toString();
    }
}
