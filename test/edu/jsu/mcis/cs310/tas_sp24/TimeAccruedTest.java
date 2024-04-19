package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class TimeAccruedTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testMinutesAccruedShift1Weekday() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(3634);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(480, m);

    }

    @Test
    public void testMinutesAccruedShift1WeekdayWithTimeout() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(436);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(0, m);

    }

    @Test
    public void testMinutesAccruedShift1Weekend() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(1087);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(360, m);

    }

    @Test
    public void testMinutesAccruedShift2Weekday() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(4943);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(540, m);

    }
    
    // Added test
    @Test
    public void testAccruedExtra1() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(147);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(555, m);

    }
    
    // Added test
    @Test
    public void testAccruedExtra2() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(3731);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(510, m);

    }
    
    // Added test
    @Test
    public void testAccruedExtra3() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(5742);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(660, m);

    }
    
    // Added test
    @Test
    public void testAccruedExtra4() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */

        Punch p = punchDAO.find(5964);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(600, m);

    }

}
