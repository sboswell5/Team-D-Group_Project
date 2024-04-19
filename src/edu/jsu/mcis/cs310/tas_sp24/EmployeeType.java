package edu.jsu.mcis.cs310.tas_sp24;

/**
 * Simple enum that shows if an employee is full-time or part-time
 * @author Ryan
 */

public enum EmployeeType {

    /**
     * Two employee types, full or part time
     * description - employeeType
     */
    PART_TIME("Temporary / Part-Time"),
    FULL_TIME("Full-Time");
    private final String description;

    /**
     * Establishes the EmployeeType enum
     * @param d passes the employeeType
     */
    private EmployeeType(String d) {
        description = d;
    }

    /**
     * Ovverrides the toString method
     * @return employeeType
     */
    @Override
    public String toString() {
        return description;
    }
    
}
