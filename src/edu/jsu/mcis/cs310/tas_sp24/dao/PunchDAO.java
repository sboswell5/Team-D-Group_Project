package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import java.time.*;
import java.sql.*;

// Finished find();

public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";

    private final DAOFactory daoFactory;

    PunchDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {

        Punch punch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        int terminalId = rs.getInt("terminalid");
                        String badgeId = rs.getString("badgeid");
                        Badge badge = new BadgeDAO(daoFactory).find(badgeId);
                        LocalDateTime originalTimestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        int eventType = rs.getInt("eventtypeid");
                        EventType punchType = null;
                        
                        switch (eventType) {
                            
                            case 0:
                                
                                punchType = EventType.CLOCK_OUT;
                                break;
                                
                            case 1:
                                
                                punchType = EventType.CLOCK_IN;
                                break;
                                
                            case 2:
                                
                                punchType = EventType.TIME_OUT;
                                break;
                        }
                
                        punch = new Punch(id, terminalId, badge, originalTimestamp, punchType);
                    } 
                }
            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return punch;

    }

}
