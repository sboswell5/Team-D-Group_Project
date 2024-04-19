package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        
        // test database connectivity; get DAOs 

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        
        // Find badge
        Badge b = badgeDAO.find("C4F37EFF");
        
        System.err.println("Test Badge: " + b.toString());
        
        // output should be "Test Badge: #C4F37EFF (Welch, Travis C)"
        
        // Find department
        for(int i = 1; i <= 10 ; i++) {
            Department d = departmentDAO.find(i);
            System.err.println("Test Department: " + d.toString());
        }
        
        Department d1 = departmentDAO.find(8);
        System.err.println("Test Department: " + d1.toString());
        
        
        // Find punch
        Punch p = punchDAO.find(236);
        System.err.println("Test Punch: " + p.toString());
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 27);
        
        System.err.println(ts);
    }
}
