package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindPunches1() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */
        
        Punch p1 = punchDAO.find(3433);
        Punch p2 = punchDAO.find(3325);
        Punch p3 = punchDAO.find(1963);

        /* Compare to Expected Values */
        
        assertEquals("#D2C39273 CLOCK IN: WED 09/05/2018 07:00:07", p1.printOriginal());
        assertEquals("#DFD9BB5C CLOCK IN: TUE 09/04/2018 08:00:00", p2.printOriginal());
        assertEquals("#99F0C0FA CLOCK IN: SAT 08/18/2018 06:00:00", p3.printOriginal());

    }

    @Test
    public void testFindPunches2() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */

        Punch p4 = punchDAO.find(5702);
        Punch p5 = punchDAO.find(4976);
        Punch p6 = punchDAO.find(2193);

        /* Compare to Expected Values */

        assertEquals("#0FFA272B CLOCK OUT: MON 09/24/2018 17:30:04", p4.printOriginal());
        assertEquals("#FCE87D9F CLOCK OUT: TUE 09/18/2018 17:34:00", p5.printOriginal());
        assertEquals("#FCE87D9F CLOCK OUT: MON 08/20/2018 17:30:00", p6.printOriginal());

    }
    
    @Test
    public void testFindPunches3() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */

        Punch p7 = punchDAO.find(954);
        Punch p8 = punchDAO.find(258);
        Punch p9 = punchDAO.find(717);

        /* Compare to Expected Values */

        assertEquals("#618072EA TIME OUT: FRI 08/10/2018 00:12:35", p7.printOriginal());
        assertEquals("#0886BF12 TIME OUT: THU 08/02/2018 06:06:38", p8.printOriginal());
        assertEquals("#67637925 TIME OUT: TUE 08/07/2018 23:12:34", p9.printOriginal());

    }
    
    // Added test
    @Test
    public void testFindPunch4() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        
        Punch p10 = punchDAO.find(1059);
        Punch p11 = punchDAO.find(6374);
        Punch p12 = punchDAO.find(262);
        
        assertEquals("#DFDFE648 CLOCK OUT: FRI 08/10/2018 15:33:37", p10.printOriginal());
        assertEquals("#CEC9A3DA CLOCK IN: SAT 09/29/2018 05:59:04", p11.printOriginal());
        assertEquals("#58EB7EA1 TIME OUT: THU 08/02/2018 06:06:38", p12.printOriginal());
    }
    
    // Added test
    @Test
    public void testFindPunch5() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        
        Punch p13 = punchDAO.find(378);
        Punch p14 = punchDAO.find(731);
        Punch p15 = punchDAO.find(1080);
        
        assertEquals("#4382D92D CLOCK OUT: THU 08/02/2018 16:42:22", p13.printOriginal());
        assertEquals("#58EB7EA1 CLOCK IN: WED 08/08/2018 06:50:52", p14.printOriginal());
        assertEquals("#76E920D9 TIME OUT: SAT 08/11/2018 00:12:35", p15.printOriginal());
    }
    
}
