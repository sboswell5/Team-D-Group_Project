package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.*;

import java.sql.*;

import java.time.LocalDateTime;
import java.util.HashMap;

public class EmployeeDAO {

    private static final String QUERY_FIND = "SELECT * FROM employee WHERE id=? ";
    private static final String QUERY_FIND2 = "SELECT * FROM shift WHERE badgeid=? ";
    private final DAOFactory daoFactory;
    private final ShiftDAO shiftDAO;
    private final DepartmentDAO departmentDAO;
    private final BadgeDAO badgeDAO;
    EmployeeDAO(DAOFactory daoFactory, ShiftDAO shiftDAO, DepartmentDAO departmentDAO, BadgeDAO badgeDAO) {
        this.daoFactory = daoFactory;
        this.departmentDAO = departmentDAO;
        this.shiftDAO = shiftDAO;
        this.badgeDAO = badgeDAO;
    }

    public Employee find(Integer id) {

        Employee employee = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            if(conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                while(rs.next()) {

                    Badge badge = badgeDAO.find(rs.getString("badgeid"));
                    String[] fullName = badge.getDescription().split(",\\s+");
                    String lastName = fullName[0];
                    String middleName = fullName[1];
                    String firstName = fullName[2];

                    LocalDateTime active = LocalDateTime.parse(rs.getString("active"));
                    Department department = departmentDAO.find(rs.getInt("departmentid"));
                    Shift shift = shiftDAO.find(rs.getInt("shiftid"));
                    EmployeeType employeeType = EmployeeType.valueOf(rs.getString("employeetypeid"));
                    employee = new Employee(id, firstName, middleName, lastName, active, badge, department, shift, employeeType);

                }
            }
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        }
        finally {
            try {
                rs.close();
            } catch(SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
        if(ps != null) {
            try {
                ps.close();
            } catch(SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
        return employee;
    }
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

                        employee = find(rs.getInt("employeeid"));
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
