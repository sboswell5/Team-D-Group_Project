package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.DailySchedule;
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

                ps = conn.prepareStatement(QUERY_GET_SHIFT);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        HashMap<String, String> shiftSet = new HashMap<>();
                        
                        shiftSet.put("id", ((Integer) id).toString());
                        shiftSet.put("description", rs.getString("description"));
                        
                        int dailyScheduleId = rs.getInt("dailyscheduleid");
                        DailySchedule dailySchedule = findDailySchedule(dailyScheduleId);
                        //System.out.println(dailySchedule);
                        shift = new Shift(shiftSet, dailySchedule);

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

                ps = conn.prepareStatement(QUERY_GET_EMPLOYEE);
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
    
    private DailySchedule findDailySchedule(int id) {
        
        DailySchedule dailySchedule = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        HashMap<String, String> dailyScheduleSet = new HashMap<>();
                        
                        dailyScheduleSet.put("id", ((Integer) id).toString());
                        
                        LocalTime shiftStart = rs.getTime("shiftstart").toLocalTime();
                        System.out.println(shiftStart);
                        LocalTime shiftStop = rs.getTime("shiftstop").toLocalTime();
                        
                        dailyScheduleSet.put("shiftStart", shiftStart.toString());
                        dailyScheduleSet.put("shiftStop", shiftStop.toString());
                        
                        dailyScheduleSet.put("roundInterval", ((Integer) rs.getInt("roundinterval")).toString());
                        dailyScheduleSet.put("gracePeriod", ((Integer) rs.getInt("graceperiod")).toString());
                        dailyScheduleSet.put("dockPenalty", ((Integer) rs.getInt("dockpenalty")).toString());
                        
                        LocalTime lunchStart = rs.getTime("lunchstart").toLocalTime();
                        LocalTime lunchStop = rs.getTime("lunchstop").toLocalTime();
                        
                        dailyScheduleSet.put("lunchStart", lunchStart.toString());
                        dailyScheduleSet.put("lunchStop", lunchStop.toString());
                        dailyScheduleSet.put("lunchThreshold", ((Integer) rs.getInt("lunchthreshold")).toString());
                        
                        Duration shiftDuration = Duration.between(shiftStart, shiftStop);
                        
                        if (shiftDuration.isNegative()) {
                            
                            LocalTime posDuration = shiftStart.minus(shiftDuration);
                            shiftDuration = Duration.between(posDuration, shiftStart);
                        }
                        
                        Duration lunchDuration = Duration.between(lunchStart, lunchStop);
                        dailyScheduleSet.put("shiftDuration", shiftDuration.toString());
                        dailyScheduleSet.put("lunchDuration", lunchDuration.toString());
                        
                        dailySchedule = new DailySchedule(dailyScheduleSet);
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

        return dailySchedule;
    }
}
