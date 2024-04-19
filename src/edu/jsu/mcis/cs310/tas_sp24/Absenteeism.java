package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDate;
import java.math.BigDecimal;

/**
 *
 * @author Madelyn
 */

public class Absenteeism {

    private final Employee employee;
    private final LocalDate localDate;
    private final BigDecimal bigDecimal;

    /**
     *
     * @param employee
     * @param localDate
     * @param bigDecimal
     */
    public Absenteeism(Employee employee, LocalDate localDate, BigDecimal bigDecimal) {
        this.employee = employee;
        this.localDate = localDate;
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
     * @return bigDecimal
     */
    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        
        // build string "#28DC3FB8 (Pay Period Starting 09-02-2018): 2.50%"
        
        s.append('#').append(employee.getBadge()).append(' ');
        s.append("(Pay Period Starting ").append(getLocalDate()).append("): ");
        s.append(getBigDecimal());

        return s.toString();

    }

}
