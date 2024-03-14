package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.*;

import java.sql.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class EmployeeDAO {

    private static final String QUERY_FIND = "SELECT * FROM employee WHERE id=? ";
    private static final String QUERY_FIND2 = "SELECT * FROM employee WHERE badgeid=? ";
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

                if(hasresults) {

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
