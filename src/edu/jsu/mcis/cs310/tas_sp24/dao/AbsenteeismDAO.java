package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Data Access Object for the absenteeism section of the database.
 * @author Madelyn
 */
public class AbsenteeismDAO {

    /**
     * query to find absenteeism data from an employeeid and payperiod
     */
    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    
    /**
     * query to insert data into the absenteeism table in the database if there is no duplicates; percentage duplicates are updated with the parameters passed
     */
    private static final String QUERY_UPDATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE percentage = ?";
    
    /**
     * The DAOFactory instance used by this class.
     */
    private final DAOFactory daoFactory;

    /**
     * Constructs a new AbsenteeismDAO in the DAOFactory
     * @param daoFactory the DAOFactory instance used
     */
    AbsenteeismDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;
    }

    /**
     * Finds the absenteeism data for the specified employee and date.
     * @param employee the employee to search for absenteeism records
     * @param localDate the date to search for absenteeism records
     * @return the Absenteeism object with the records from the database
     */
    public Absenteeism find(Employee employee, LocalDate localDate) {

        Absenteeism absenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Adjust the time to go to the start of the pay period
                LocalDate payPeriodStart = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, employee.getId());
                ps.setDate(2, Date.valueOf(payPeriodStart));
              
                boolean hasresults = ps.execute();
                
                if (hasresults) {

                    rs = ps.getResultSet();
              
                    while (rs.next()) {
                        
                        LocalDate payPeriod = rs.getDate("payperiod").toLocalDate();
                        BigDecimal percentage = rs.getBigDecimal("percentage");
                     
                        absenteeism = new Absenteeism(employee, payPeriod, percentage);
                        System.out.println(absenteeism);
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

        return absenteeism;
    }
    
    /**
     * Creates or updates an absenteeism record in the database.
     * @param absenteeism the Absenteeism object representing the data to be created or added
     */
    public void create(Absenteeism absenteeism) {
        
        PreparedStatement ps = null;
  
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_UPDATE);
                LocalDate payPeriodStart = absenteeism.getLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            
                ps.setInt(1, absenteeism.getEmployee().getId());
                ps.setDate(2, Date.valueOf(payPeriodStart));
                ps.setBigDecimal(3, absenteeism.getBigDecimal());
                ps.setBigDecimal(4, absenteeism.getBigDecimal());
                
                ps.executeUpdate();
            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (ps != null) {
                
                try {
                    
                    ps.close();
                    
                } catch (SQLException e) {
                    
                    throw new DAOException(e.getMessage());
                }
            }
        }
    }
}
