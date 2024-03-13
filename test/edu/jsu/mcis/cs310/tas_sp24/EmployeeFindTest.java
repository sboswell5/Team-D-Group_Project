package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EmployeeFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindEmployee1() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e1 = employeeDAO.find(14);

        /* Compare to Expected Values */
        
        assertEquals("ID #14: Donaldson, Kathleen C (#229324A4), Type: Full-Time, Department: Press, Active: 02/02/2017", e1.toString());

    }
    
    @Test
    public void testFindEmployee2() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("ADD650A8");
        Employee e2 = employeeDAO.find(b);

        /* Compare to Expected Values */

        assertEquals("ID #82: Taylor, Jennifer T (#ADD650A8), Type: Full-Time, Department: Office, Active: 02/13/2016", e2.toString());

    }
    
    @Test
    public void testFindEmployee3() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e3 = employeeDAO.find(127);

        /* Compare to Expected Values */
        
        assertEquals("ID #127: Elliott, Nancy L (#EC531DE6), Type: Temporary / Part-Time, Department: Shipping, Active: 09/22/2015", e3.toString());

    }
    
    @Test
    public void testFindEmployee4() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("C1E4758D");
        Employee e4 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #93: Leist, Rodney J (#C1E4758D), Type: Temporary / Part-Time, Department: Warehouse, Active: 10/09/2015", e4.toString());

    }
    
    // Added test
    @Test
    public void testFindEmployee5() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e5 = employeeDAO.find(80);

        /* Compare to Expected Values */
        
        assertEquals("ID #80: Snively, Georgine R (#AB8204A4), Type: Full-Time, Department: Grinding, Active: 01/28/2017", e5.toString());
    
    }
    
    // Added test
    @Test
    public void testFindEmployee6() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("021890C0");
        Employee e6 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #1: Chapell, George R (#021890C0), Type: Temporary / Part-Time, Department: Assembly, Active: 04/02/2016", e6.toString());
     
    } 
    
    // Added test
    @Test
    public void testFindEmployee7() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e7 = employeeDAO.find(25);

        /* Compare to Expected Values */
        
        assertEquals("ID #25: Smith, Patrick R (#3282F212), Type: Full-Time, Department: Maintenance, Active: 10/21/2015", e7.toString());
     
    }
    
    // Added test
    @Test
    public void testFindEmployee8() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("9186E711");
        Employee e8 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #67: Adams, Cruz C (#9186E711), Type: Temporary / Part-Time, Department: Cleaning, Active: 01/17/2016", e8.toString());
     
    }
    
    // Added test
    @Test
    public void testFindEmployee9() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e9 = employeeDAO.find(63);

        /* Compare to Expected Values */
        
        assertEquals("ID #63: McGruder, Patricia W (#8D6362AD), Type: Temporary / Part-Time, Department: Hafting, Active: 03/19/2016", e9.toString());
   
    }
    
    // Added test
    @Test
    public void testFindEmployee10() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("07901755");
        Employee e10 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #2: Terrell, Kenneth R (#07901755), Type: Full-Time, Department: Tool and Die, Active: 05/29/2016", e10.toString());

    }
    
    // Added test
    @Test
    public void testFindInvalidEmployee() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by invalid id) */

        Employee e11 = employeeDAO.find(10000);

        /* Compare to Expected Value (Null) */
        
        assertNull(e11);

    }
    
}
