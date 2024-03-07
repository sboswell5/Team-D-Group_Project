package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import java.time.*;
import java.sql.*;
import java.util.ArrayList;

// Finished find();
// Added default case to switch
// Added create();
// Added insertPunch(); to go along with create();
// create() good now :)

public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    //private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND LIKE timestamp = '?%'";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND timestamp = ?";

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
                                
                            // Default test - look at later?
                            default:
                
                                throw new IllegalArgumentException("Unexpected punch type: " + eventType);
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
    
    public int create(Punch punch) {
        
        int punchTerminalId = punch.getTerminalid();
        
        // If terminal ID is 0, automatically authorize new punch
        if (punchTerminalId == 0) {
            
            return insertPunch(punch);
            
        } else {
            
            // Retrieve employee associated with a given badge
            EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
            Employee employee = employeeDAO.find(punch.getBadge());
            
            // If employee exists, check if new terminal ID matches the designated clock terminal of the employee's department
            if (employee != null) {
                
                Department employeeDepartment = employee.getDepartment();
                int employeeTerminalId = employeeDepartment.getTerminalid();
                
                if (punchTerminalId == employeeTerminalId) {
                    
                    // Insert the punch if IDs match
                    return insertPunch(punch);
                }
            }
        }
        
        // Return 0 if the punch fails authorization check or if an error occured during the insertion process
        return 0;
   
    }
    
    private int insertPunch(Punch punch) {
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement("INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                
                ps.setInt(1, punch.getTerminalid());
                ps.setString(2, punch.getBadge().getId());
                ps.setTimestamp(3, Timestamp.valueOf(punch.getOriginaltimestamp().withNano(0)));
                ps.setInt(4, punch.getPunchtype().ordinal());
                
                int rowsInserted = ps.executeUpdate();

                if (rowsInserted > 0) {

                    rs = ps.getGeneratedKeys();

                    while (rs.next()) {

                        // Return ID of new punch
                        return rs.getInt(1);
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
        
        return 0;
    }
    
    public ArrayList<Punch> list(Badge badge, LocalDate localDate) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Punch> punchList = new ArrayList<>();
        Punch punch = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());
                ps.setString(2, localDate.toString());
                
                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        
                        int id = rs.getInt("id");
                        int terminalId = rs.getInt("terminalid");
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
                                
                            // Default test - look at later?
                            default:
                
                                throw new IllegalArgumentException("Unexpected punch type: " + eventType);
                        }
                
                        punch = new Punch(id, terminalId, badge, originalTimestamp, punchType);
                        
                        if (punchList.isEmpty()) {
                            
                            punchList.add(punch);
                        }
                        
                        for (int i = 0; i < punchList.size(); i++) {
                            
                            if ((punchList.get(i)).getOriginaltimestamp().isAfter(originalTimestamp)) {
                                
                                punchList.add(i, punch);
                                
                            }
                        }
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
        
        return punchList;
    }
}
        
        