package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.DepartmentDAO;
import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DepartmentFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindDepartment1() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d1 = departmentDAO.find(1);

        /* Compare to Expected Values */
        
        assertEquals("#1 (Assembly), Terminal ID: 103", d1.toString());

    }

    @Test
    public void testFindDepartment2() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d2 = departmentDAO.find(6);

        /* Compare to Expected Values */
        
        assertEquals("#6 (Office), Terminal ID: 102", d2.toString());

    }

    @Test
    public void testFindDepartment3() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d3 = departmentDAO.find(8);

        /* Compare to Expected Values */
        
        assertEquals("#8 (Shipping), Terminal ID: 107", d3.toString());

    }
    
    // Added test
    @Test
    public void testFindDepartment4() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d4 = departmentDAO.find(10);

        /* Compare to Expected Values */
        
        assertEquals("#10 (Maintenance), Terminal ID: 104", d4.toString());

    }
    
    // Added test
    @Test
    public void testFindDepartment5() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d5 = departmentDAO.find(3);

        /* Compare to Expected Values */
        
        assertEquals("#3 (Warehouse), Terminal ID: 106", d5.toString());

    }
    
    // Added test
    @Test
    public void testFindDepartment6() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d6 = departmentDAO.find(2);

        /* Compare to Expected Values */
        
        assertEquals("#2 (Cleaning), Terminal ID: 107", d6.toString());

    }

    // Added test
    @Test
    public void testFindDepartment7() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d7 = departmentDAO.find(4);

        /* Compare to Expected Values */
        
        assertEquals("#4 (Grinding), Terminal ID: 104", d7.toString());

    }

    // Added test
    @Test
    public void testFindDepartment8() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d8 = departmentDAO.find(5);

        /* Compare to Expected Values */
        
        assertEquals("#5 (Hafting), Terminal ID: 105", d8.toString());

    }
    
    // Added test
    @Test
    public void testFindDepartment9() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d9 = departmentDAO.find(7);

        /* Compare to Expected Values */
        
        assertEquals("#7 (Press), Terminal ID: 104", d9.toString());

    }
    
    // Added test
    @Test
    public void testFindDepartment10() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d10 = departmentDAO.find(9);

        /* Compare to Expected Values */
        
        assertEquals("#9 (Tool and Die), Terminal ID: 104", d10.toString());

    }

    // Added test
    @Test
    public void testFindInvalidDepartment() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database (Invalid Database)*/
        
        Department d11 = departmentDAO.find(11);

        /* Compare to Expected Value (Null) */
        
        assertNull(d11);

    }
    
}
