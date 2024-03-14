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

    //parameter map of objects, cast to native type
    // class of parameters (or hashmap)
    /*
    public Employee(Integer id, String firstName, String middleName, String lastName, LocalDateTime active, Badge badge, Department department, Shift shift, EmployeeType type) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;
        this.type = type;
    } */

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

    // getters
    public Integer getId() {
        return this.id;
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

        s.append("ID #").append(id).append(": ");
        s.append(lastName).append(", ").append(firstName).append(' ').append(middleName);
        s.append(" (#").append(badge.getId()).append("), Type: ").append(type);
        s.append(", Department: ").append(getDepartment().getDescription());
        s.append(", Active: ").append(formattedActiveDate);
        
        return s.toString();
    
    }

}
