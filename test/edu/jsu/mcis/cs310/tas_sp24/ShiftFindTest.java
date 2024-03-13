package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ShiftFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindShiftByID1() {

        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Retrieve Shift Rulesets from Database */
        
        Shift s1 = shiftDAO.find(1);
        Shift s2 = shiftDAO.find(2);
        Shift s3 = shiftDAO.find(3);

        /* Compare to Expected Values */
        
        assertEquals("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)", s1.toString());
        assertEquals("Shift 2: 12:00 - 20:30 (510 minutes); Lunch: 16:30 - 17:00 (30 minutes)", s2.toString());
        assertEquals("Shift 1 Early Lunch: 07:00 - 15:30 (510 minutes); Lunch: 11:30 - 12:00 (30 minutes)", s3.toString());

    }
    
    // Added test
    @Test
    public void testFindShiftByID2() {

        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Retrieve Shift Ruleset from Database */
        
        Shift s1 = shiftDAO.find(4);

        /* Compare to Expected Values */
        
        assertEquals("Shift 3: 22:30 - 07:00 (510 minutes); Lunch: 02:30 - 03:00 (30 minutes)", s1.toString());

    }

    @Test
    public void testFindShiftByBadge1() {

        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create Badge Objects */
        
        Badge b1 = badgeDAO.find("B6902696");
        Badge b2 = badgeDAO.find("76E920D9");
        Badge b3 = badgeDAO.find("4382D92D");

        /* Retrieve Shift Rulesets from Database */
        
        Shift s1 = shiftDAO.find(b1);
        Shift s2 = shiftDAO.find(b2);
        Shift s3 = shiftDAO.find(b3);

        /* Compare to Expected Values */
        
        assertEquals("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)", s1.toString());
        assertEquals("Shift 2: 12:00 - 20:30 (510 minutes); Lunch: 16:30 - 17:00 (30 minutes)", s2.toString());
        assertEquals("Shift 1 Early Lunch: 07:00 - 15:30 (510 minutes); Lunch: 11:30 - 12:00 (30 minutes)", s3.toString());

    }
    
    // Added test
    @Test
    public void testFindShiftByBadge2() {

        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create Badge Objects */
        
        Badge b1 = badgeDAO.find("45E5059F");
        Badge b2 = badgeDAO.find("398B1563");
        Badge b3 = badgeDAO.find("DFDFE648");

        /* Retrieve Shift Rulesets from Database */
        
        Shift s1 = shiftDAO.find(b1);
        Shift s2 = shiftDAO.find(b2);
        Shift s3 = shiftDAO.find(b3);

        /* Compare to Expected Values */
        
        assertEquals("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)", s1.toString());
        assertEquals("Shift 2: 12:00 - 20:30 (510 minutes); Lunch: 16:30 - 17:00 (30 minutes)", s2.toString());
        assertEquals("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)", s3.toString());

    }
    
    // Added test
    @Test
    public void testFindShiftByBadge3() {

        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create Badge Objects */
        
        Badge b1 = badgeDAO.find("DFE4EB13");
        Badge b2 = badgeDAO.find("CF697DE6");
        Badge b3 = badgeDAO.find("FF591F68");

        /* Retrieve Shift Rulesets from Database */
        
        Shift s1 = shiftDAO.find(b1);
        Shift s2 = shiftDAO.find(b2);
        Shift s3 = shiftDAO.find(b3);

        /* Compare to Expected Values */
        
        assertEquals("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)", s1.toString());
        assertEquals("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)", s2.toString());
        assertEquals("Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)", s3.toString());

    }
    
    // Added test
    @Test
    public void testFindInvalidShiftByID() {

        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Retrieve Invalid Shift Ruleset from Database */
        
        Shift s1 = shiftDAO.find(5);

        /* Compare to Expected Values (Null) */
        
        assertNull(s1);

    }
    
}