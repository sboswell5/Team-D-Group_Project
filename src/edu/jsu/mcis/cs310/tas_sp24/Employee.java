package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Employee {

    private final Integer id;
    private final String firstName, middleName, lastName;
    private final LocalDateTime active;
    private final Shift shift;
    private final Badge badge;
    private final Department department;
    private final EmployeeType type;

    public Employee(HashMap<String, Object> employee) {
        
        this.id = (Integer) employee.get("id");
        this.firstName = (String) employee.get("firstName");
        this.middleName = (String) employee.get("middleName");
        this.lastName = (String) employee.get("lastName");
        this.active = (LocalDateTime) employee.get("active");
        this.badge = (Badge) employee.get("badge");
        this.department = (Department) employee.get("department");
        this.shift = (Shift) employee.get("shift");
        this.type = (EmployeeType) employee.get("employeeType");
    }

    // Getters
    public Integer getId() {
        
        return id;
    }
    
    public String getFirstName() {
        
        return firstName;
    }
    
    public String getMiddleName() {
        
        return middleName;
    }
    
    public String getLastName() {
        
        return lastName;
    }
    public LocalDateTime getActive() {
        
        return active;
    }
    
    public Badge getBadge() {
        
        return badge;
    }
    
    public Department getDepartment() {
        
        return department;
    }
    
    public Shift getShift() {
        
        return shift;
    }
    
    public EmployeeType getType() {
        
        return type;
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedActiveDate = active.format(formatter);

        s.append("ID #").append(getId()).append(": ");
        s.append(getLastName()).append(", ").append(getFirstName()).append(' ').append(getMiddleName());
        s.append(" (#").append(badge.getId()).append("), Type: ").append(getType());
        s.append(", Department: ").append(getDepartment().getDescription());
        s.append(", Active: ").append(formattedActiveDate);
        
        return s.toString();
        
    }
}
