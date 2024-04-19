package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import java.time.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Data Access Object for the punch (event) section of the database.
 * @author Madelyn
 * @author Kris
 */
public class PunchDAO {
    
    /**
     * query to insert a punch (event) into the database
     */
    private static final String QUERY_INSERT = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";
    
    /**
     * query to find punch data from a punch id
     */
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    
    /**
     * query to list punch data for a specific badge on a given day
     */
    private static final String QUERY_LIST = "Select *, Date(`timestamp`) AS originaldate FROM `event` WHERE badgeid = ? HAVING originaldate = ? Order BY `timestamp`";
    
    /**
     * query to list punch data for closing punch pairs for a specific badge on a given day
     */
    private static final String QUERY_CLOSE_LIST = "Select *, Date(`timestamp`) AS originaldate FROM `event` WHERE badgeid = ? HAVING originaldate = ? Order BY `timestamp` LIMIT 1";
    
    /**
     * The DAOFactory instance used by this class.
     */
    private final DAOFactory daoFactory;

    /**
     * Constructs a new PunchDAO in the DAOFactory
     * @param daoFactory the DAOFactory instance used
     */
    PunchDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;
    }

    /**
     * Finds the punch data for the given punch id.
     * @param id the id of a given punch
     * @return the Punch object corresponding to the id
     * @author Madelyn
     */
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
                        EventType punchType = EventType.values()[eventType];
                
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
    
    /**
     * Creates a new punch entry in the database and returns a result based on if it was successful or not.
     * @param punch the Punch object to be created
     * @return an integer indicating the result of the insert: 0 if the punch fails the authorization or if there was an error; 1 if the punch was inserted successfully
     * @author Madelyn
     */
    public int create(Punch punch) {
        
        int result = 0;
        int punchTerminalId = punch.getTerminalid();
        
        // If terminal ID is 0, automatically authorize new punch
        if (punchTerminalId == 0) {
            
            result = insertPunch(punch);
            
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
                    result = insertPunch(punch);
                }
            }
        }
        
        // Return 0 if the punch fails authorization check or if an error occured during the insertion process
        return result;
    }
    
    /**
     * Inserts a new punch into the database and returns the id of that punch
     * @param punch the Punch object to be inserted
     * @return the id of the inserted punch
     * @author Madelyn
     */
    private int insertPunch(Punch punch) {
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
                
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
    
    /**
     * Retrieves a list of punches for a specific badge and day.
     * @param badge the badge for which punches will be retrieved
     * @param localDate the date for punches to be retrieved
     * @return an ArrayList of Punch objects
     * @author Madelyn
     * @author Kris
     */
    public ArrayList<Punch> list(Badge badge, LocalDate localDate) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Punch> punchList = new ArrayList<>();
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                String ld = localDate.toString();
                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());
                ps.setString(2, ld);
                
                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        
                        int id = rs.getInt("id");
                        int terminalId = rs.getInt("terminalid");
                        LocalDateTime originalTimestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        int eventType = rs.getInt("eventtypeid");
                        EventType punchType = EventType.values()[eventType];
                        
                        Punch punch = new Punch(id, terminalId, badge, originalTimestamp, punchType);
                        
                        punchList.add(punch);
                    }
            
                    while (punchList.size() % 2 != 0) {
                        
                        localDate = localDate.plusDays(1);
                        punchList.add(closeClockInPair(badge, localDate));
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
    
    /**
     * Retrieves a punch that closes a clock-in pair that happens the day after a time-out or clock-out punch.
     * @param badge the badge for which punches will be retrieved
     * @param localDate the date for punches to be retrieved
     * @return a punch that closes a clock-in pair
     * @author Kris
     */
    public Punch closeClockInPair(Badge badge, LocalDate localDate) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Punch punch = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                String ld = localDate.toString();
                ps = conn.prepareStatement(QUERY_CLOSE_LIST); //maybe use query just in og method
                ps.setString(1, badge.getId());
                ps.setString(2, ld);
                
                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        
                        int id = rs.getInt("id");
                        int terminalId = rs.getInt("terminalid");
                        LocalDateTime originalTimestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        int eventType = rs.getInt("eventtypeid");
                        EventType punchType = EventType.values()[eventType];
                      
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
    
    /**
     * Retrieves a list of punches for a specified range of dates.
     * @param badge the badge for which punches will be retrieved
     * @param begin the start date of the range
     * @param end the end date of the range
     * @return an ArrayList of punch Objects
     * @author Madelyn
     */
    public ArrayList<Punch> list(Badge badge, LocalDate begin, LocalDate end) {
        
        ArrayList<Punch> rangedPunchList = new ArrayList<>();
        
        // Check if begin date is after end date.
        if (begin.isAfter(end)){
            
            // return empty list
            return rangedPunchList;
        }
        
        // Set the current date to the date given as a parameter
        LocalDate date = begin;
        
        // Iterate through the dates until it reaches the end date (as given in the parameter)
        while (!date.isAfter(end)) {
            
            // Call first list() method to get a single day punch
            ArrayList<Punch> dayList = list(badge, date);
            
            // Add the single day punches to the rangedPunchList
            rangedPunchList.addAll(dayList);
            
            // Move to next day
            date = date.plusDays(1);
        }
        
        int j = 1;
        ArrayList<Integer> removeList = new ArrayList<>();
        
        for (Punch p : rangedPunchList) {
            
            if (j < rangedPunchList.size()) {
                
                if (p.getId() == rangedPunchList.get(j).getId()) {
                    
                    removeList.add(rangedPunchList.indexOf(p));
                }
            }
            
            j++;
        }
        
        for (int index : removeList) {
            
            rangedPunchList.remove(rangedPunchList.get(index));
        }
        
        return rangedPunchList;
    }
}

        