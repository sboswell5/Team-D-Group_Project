package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.sql.*;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    private static final String QUERY_REPLACE = "REPLACE INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)"; // look into

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

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, employee.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        LocalDate payPeriod = rs.getDate("payperiod").toLocalDate();
                        BigDecimal percentage = rs.getBigDecimal("percentage");
                        
                        absenteeism = new Absenteeism(employee, payPeriod, percentage);
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
        ResultSet rs = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_REPLACE);
                
                ps.setInt(1, absenteeism.getEmployee().getId());
                ps.setDate(2, Date.valueOf(absenteeism.getLocalDate()));
                ps.setBigDecimal(3, absenteeism.getBigDecimal());
                
                ps.executeUpdate();
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
    }
}
