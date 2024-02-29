package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.ShiftDAO;

import java.time.LocalDateTime;

public class Employee {

    private final Integer id;
    private final String firstName, middleName, lastName;
    private final LocalDateTime active;
    private final Shift shift;
    private final Badge badge;
    private final Department department;

    private EmployeeType type;

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
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", active=" + active +
                ", shift=" + shift +
                ", badge=" + badge +
                ", department=" + department +
                '}';
    }
}
