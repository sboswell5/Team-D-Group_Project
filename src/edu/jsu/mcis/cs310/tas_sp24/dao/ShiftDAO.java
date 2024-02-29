package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.sql.*;
import java.time.*;
import java.util.HashMap;

public class ShiftDAO {

    private static final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND2 = "SELECT * FROM employee WHERE badgeid = ?";

    private final DAOFactory daoFactory;

    ShiftDAO(DAOFactory daoFactory) { 

        this.daoFactory = daoFactory;

    }

    public Shift find(int id) {

        Shift shift = null;

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
                        HashMap<String, String> shiftSet = new HashMap<>();
                        
                        shiftSet.put("id", ((Integer) id).toString());
                        shiftSet.put("description", rs.getString("description"));
                        
                        LocalTime shiftStart = rs.getTime("shiftstart").toLocalTime();
                        LocalTime shiftStop = rs.getTime("shiftstop").toLocalTime();
                        
                        shiftSet.put("shiftStart", shiftStart.toString());
                        shiftSet.put("shiftStop", shiftStop.toString());
                        
                        shiftSet.put("roundInterval", ((Integer) rs.getInt("roundinterval")).toString());
                        shiftSet.put("gracePeriod", ((Integer) rs.getInt("graceperiod")).toString());
                        shiftSet.put("dockPenalty", ((Integer) rs.getInt("dockpenalty")).toString());
                        
                        LocalTime lunchStart = rs.getTime("lunchstart").toLocalTime();
                        LocalTime lunchStop = rs.getTime("lunchstop").toLocalTime();
                        
                        shiftSet.put("lunchStart", lunchStart.toString());
                        shiftSet.put("lunchStop", lunchStop.toString());
                        shiftSet.put("lunchThreshold", ((Integer) rs.getInt("lunchthreshold")).toString());
                        
                        Duration shiftDuration = Duration.between(shiftStart, shiftStop);
                        if(shiftDuration.isNegative()) {
                            LocalTime posDuration = shiftStart.minus(shiftDuration);
                            shiftDuration = Duration.between(posDuration, shiftStart);
                        }
                        
                        Duration lunchDuration = Duration.between(lunchStart, lunchStop);
                        shiftSet.put("shiftDuration", shiftDuration.toString());
                        shiftSet.put("lunchDuration", lunchDuration.toString());
                        
                        shift = new Shift(shiftSet);
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

        return shift;

    }
    
    public Shift find(Badge badge) {

        Shift shift = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND2);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        shift = find(rs.getInt("shiftid"));
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

        return shift;

    }
}
