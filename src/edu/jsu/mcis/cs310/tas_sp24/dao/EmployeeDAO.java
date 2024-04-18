package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.*;

import java.sql.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Data Access Object for the employee section of the database
 * 
 * @author Ryan
 */

public class EmployeeDAO {

    /**
     * Query that finds employee data by employee id
     */
    private static final String QUERY_FIND = "SELECT * FROM employee WHERE id = ?";
    /**
     * Query that finds employee data by badge id
     */
    private static final String QUERY_FIND2 = "SELECT * FROM employee WHERE badgeid = ?";
    
    /**
     * DAO fields
     */
    private final DAOFactory daoFactory;
    private final ShiftDAO shiftDAO;
    private final DepartmentDAO departmentDAO;
    private final BadgeDAO badgeDAO;
    
    /**
     * Creates the EmployeeDAO
     * @param daoFactory passes access to the daoFactory
     * @param shiftDAO passes access to shiftDAO
     * @param departmentDAO passes access to departmentDAO
     * @param badgeDAO passes access to badgeDAO
     */
    EmployeeDAO(DAOFactory daoFactory, ShiftDAO shiftDAO, DepartmentDAO departmentDAO, BadgeDAO badgeDAO) {
        
        this.daoFactory = daoFactory;
        this.departmentDAO = departmentDAO;
        this.shiftDAO = shiftDAO;
        this.badgeDAO = badgeDAO;
    }

    /**
     * Finds the employee data based on the employee id
     * @param id the employee id
     * @return the employee data object
     */
    public Employee find(Integer id) {

        Employee employee = null;
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

                        HashMap<String, Object> employeeParams = new HashMap<>();

                        Badge badge = badgeDAO.find(rs.getString("badgeid"));
                        String[] fullName = badge.getDescription().split(",\\s+");

                        String lastName = fullName[0];
                        String firstName = fullName[1].split(" ")[0];
                        String middleName = fullName[1].split(" ")[1];

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime active = LocalDateTime.parse(rs.getString("active"), dtf);
                        Department department = departmentDAO.find(rs.getInt("departmentid"));
                        Shift shift = shiftDAO.find(rs.getInt("id"));

                        int employeeTypeNum = rs.getInt("employeetypeid");
                        EmployeeType employeeType = null;
                        
                        // simplify
                        switch(employeeTypeNum) {
                            
                            case 0:
                                
                                employeeType = EmployeeType.PART_TIME;
                                break;

                            case 1:
                                
                                employeeType = EmployeeType.FULL_TIME;
                                break;

                            default:
                                
                                throw new IllegalArgumentException("Invalid employeeType id: " + employeeType);
                        }

                        employeeParams.put("id", id);
                        employeeParams.put("firstName", firstName);
                        employeeParams.put("middleName", middleName);
                        employeeParams.put("lastName", lastName);
                        employeeParams.put("active", active);
                        employeeParams.put("badge", badge);
                        employeeParams.put("department", department);
                        employeeParams.put("shift", shift);
                        employeeParams.put("employeeType", employeeType);

                        employee = new Employee(employeeParams);
                    }
                }
            }
            
        } catch(SQLException e) {
            
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
        
        return employee;
    }
    
    /**
     * Finds the employee data based on the employee's badge id
     * @param badge the employee's badge id
     * @return the employee data object
     */
    public Employee find(Badge badge) {

        Employee employee = null;
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

                        employee = find(rs.getInt("id"));
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

        return employee;
    }
}
