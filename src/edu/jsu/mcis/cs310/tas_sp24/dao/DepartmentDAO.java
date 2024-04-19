package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.sql.*;

/**
 * The <code>DepartmentDAO</code> class provides methods to interact with the department database table.
 * It allows for the retrieval of department information based on department ID.
 */
public class DepartmentDAO {
    
    // Query to find department by ID
    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";   
    
    private final DAOFactory daoFactory;

    /**
     * Constructs a new <code>DepartmentDAO</code> instance with the specified DAOFactory.
     * @param daoFactory the DAOFactory to be used by this DepartmentDAO
     */
    DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
     * Finds and retrieves a department from the database based on the provided department ID.
     * @param id the ID of the department to be retrieved
     * @return the Department object retrieved from the database, or null if no department was found
     * @throws DAOException if there is an error accessing the database
     */
    public Department find(int id) {
        Department department = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                boolean hasResults = ps.execute();
                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        String description = rs.getString("description");
                        int terminalid = rs.getInt("terminalid");
                        department = new Department(id, description, terminalid);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            // Close resources
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
    
