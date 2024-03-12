package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchRangeListFindTest {
    
    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }
    
    @Test
    public void testFindPunchList1() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 17);
        LocalDate te = LocalDate.of(2018, Month.SEPTEMBER, 24);

        Badge b = badgeDAO.find("67637925");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts, te);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(4716));
        p2.add(punchDAO.find(4811));
        p2.add(punchDAO.find(4813));
        p2.add(punchDAO.find(4847));
        p2.add(punchDAO.find(4884));
        p2.add(punchDAO.find(4949));
        p2.add(punchDAO.find(5018));
        p2.add(punchDAO.find(5132));
        p2.add(punchDAO.find(5177));
        p2.add(punchDAO.find(5267));
        p2.add(punchDAO.find(5324));
        p2.add(punchDAO.find(5392));
        p2.add(punchDAO.find(5461));
        p2.add(punchDAO.find(5526));
        p2.add(punchDAO.find(5598));
        p2.add(punchDAO.find(5649));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        System.out.println(s2.toString());
        System.out.println(s1.toString());
        assertEquals(s2.toString(), s1.toString());

    }

    @Test
    public void testFindPunchList2() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.SEPTEMBER, 27);
        LocalDate te = LocalDate.of(2018, Month.SEPTEMBER, 30);

        Badge b = badgeDAO.find("87176FD7");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts, te);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        
        p2.add(punchDAO.find(6089));
        p2.add(punchDAO.find(6112));
        p2.add(punchDAO.find(6118));
        p2.add(punchDAO.find(6129));
        p2.add(punchDAO.find(6241));
        p2.add(punchDAO.find(6299));
        p2.add(punchDAO.find(6380));
        p2.add(punchDAO.find(6431));
        

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }

    @Test
    public void testFindPunchRangeList3() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts = LocalDate.of(2018, Month.AUGUST, 1);
        LocalDate te = LocalDate.of(2018, Month.AUGUST, 5);

        Badge b = badgeDAO.find("58EB7EA1");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts, te);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        
        p2.add(punchDAO.find(166));
        p2.add(punchDAO.find(262));
        p2.add(punchDAO.find(271));
        p2.add(punchDAO.find(342));
        p2.add(punchDAO.find(398));
        p2.add(punchDAO.find(454));
        

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        System.out.println(s2.toString());
        System.out.println(s1.toString());
        assertEquals(s2.toString(), s1.toString());

    }
}
