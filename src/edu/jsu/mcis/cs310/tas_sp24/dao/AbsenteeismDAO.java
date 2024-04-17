package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.math.BigDecimal;
import java.sql.*;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    //private static final String QUERY_REPLACE = "REPLACE INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)"; // look into
    
    // possibly do delete if match and then insert if not exist
    //private static final String QUERY_DELETE = "DELETE FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    //private static final String QUERY_INSERT = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)";

    //private static final String QUERY_UPDATE = "UPDATE absenteeism SET percentage = ? WHERE employeeid = ? AND payperiod = ?";
    
    // Try this one?
    private static final String QUERY_UPDATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE percentage = ?";
    
    private final DAOFactory daoFactory;

    AbsenteeismDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;
    }

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

                /*ps = conn.prepareStatement(QUERY_REPLACE);
                
                ps.setInt(1, absenteeism.getEmployee().getId());
                ps.setDate(2, Date.valueOf(absenteeism.getLocalDate()));
                ps.setBigDecimal(3, absenteeism.getBigDecimal());
                
                ps.executeUpdate();*/
                
                /*Absenteeism existingAbsenteeism = find(absenteeism.getEmployee(), absenteeism.getLocalDate());
                
                if (existingAbsenteeism != null) {
                    
                    BigDecimal newPercentage = absenteeism.getBigDecimal();
                    LocalDate payPeriodStart = absenteeism.getLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                    
                    ps = conn.prepareStatement(QUERY_UPDATE);
                    
                    ps.setBigDecimal(1, newPercentage);
                    ps.setInt(2, absenteeism.getEmployee().getId());
                    ps.setDate(3, Date.valueOf(payPeriodStart));
                    
                    ps.executeUpdate();
                
                } else {
                    
                    ps = conn.prepareStatement(QUERY_INSERT);
                    LocalDate payPeriodStart = absenteeism.getLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                
                    ps.setInt(1, absenteeism.getEmployee().getId());
                    ps.setDate(2, Date.valueOf(payPeriodStart));
                    ps.setBigDecimal(3, absenteeism.getBigDecimal());
                    
                    ps.executeUpdate();
                }
            }*/
                
                /*PreparedStatement dps = conn.prepareStatement(QUERY_DELETE);
                PreparedStatement ips = conn.prepareStatement(QUERY_INSERT);
                
                LocalDate payPeriodStart = absenteeism.getLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                
                dps.setInt(1, absenteeism.getEmployee().getId());
                dps.setDate(2, Date.valueOf(payPeriodStart));
                
                int deletedRows = dps.executeUpdate();
            
                if (deletedRows == 0) {
                  
                    ips.setInt(1, absenteeism.getEmployee().getId());
                    ips.setDate(2, Date.valueOf(payPeriodStart));
                    ips.setBigDecimal(3, absenteeism.getBigDecimal());
                
                    ips.executeUpdate();
                }
            }*/
                
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
