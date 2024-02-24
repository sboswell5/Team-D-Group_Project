package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.sql.*;

public class DepartmentDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";   
    
    private final DAOFactory daoFactory;
    
    DepartmentDAO(DAOFactory daoFactory) {
        
        this.daoFactory = daoFactory;
        
    }
    
    public Department find(String id) {
        
        Department department = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        String description = rs.getString("description");
                        String terminalid = rs.getString("terminalid");
                        department = new Department(id, description, terminalid);

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
        return department;
    }
}
    
