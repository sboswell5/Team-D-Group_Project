package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 *
 * @author Madelyn
 */

 * Absenteeism class that is used to represent an employee's absenteeism record for a specific pay period.
 * @author Madelyn
 */
public class Absenteeism {

    /**
     * employee - the employee that absenteeism will be calculated for
     * localDate - the day of the week
     * bigDecimal - the type of number used for absenteeism percentages
     * formatter - the format that the date should be in
     */
    private final Employee employee;
    private final LocalDate localDate;
    private final BigDecimal bigDecimal;
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    /**
     *
     * @param employee
     * @param localDate
     * @param bigDecimal
     * Constructor for absenteeism
     * @param employee passes the employee into the constructor
     * @param localDate passes the localDate (set to Sunday) into the constructor
     * @param bigDecimal passes the bigDecimal into the constructor
     */
    public Absenteeism(Employee employee, LocalDate localDate, BigDecimal bigDecimal) {
        
        this.employee = employee;
        this.localDate = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        this.bigDecimal = bigDecimal;
    }

    /**
     *
     * @return employee
     */
    public Employee getEmployee() {
        
        return employee;
    }

    /**
     *
     * @return localDate
     */
    public LocalDate getLocalDate() {
        
        return localDate;
    }

    /**
     *
    
    /**
     * @return bigDecimal
     */
    public BigDecimal getBigDecimal() {
        
        return bigDecimal;
    }

    /**
     * Overrides the toString() method
     * @return the absenteeism of an employee (with their badge number) in a formatted string to satisfy tests
     */
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
