package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        //:)
        // test database connectivity; get DAOs 

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
        
        // find badge

        Badge b = badgeDAO.find("C4F37EFF");
        
        //  find department
        for(int i = 1; i<=10 ; i++) {
            Department d = departmentDAO.find(String.valueOf(i));
            System.err.println("Test Department: " + d.toString());
        }
        
        
        // output should be "Test Badge: #C4F37EFF (Welch, Travis C)"
        
        System.err.println("Test Badge: " + b.toString());
        
        // Testing the commit & push! - Madelyn
        // Testing the commit & push! - Kris
        //testing testing!!! 123 -shelby!
    }

}
