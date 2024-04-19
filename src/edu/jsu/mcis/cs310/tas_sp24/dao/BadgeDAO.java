package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;

/**
 * The <code>BadgeDAO</code> class provides methods to interact with the badge database table.
 * It allows for the creation, deletion, and retrieval of badge information.
 */
public class BadgeDAO {

    // Queries
    private static final String QUERY_INSERT = "INSERT INTO badge (id, description) VALUES (?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM badge WHERE id = ?";
    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";

    private final DAOFactory daoFactory;

    /**
     * Constructs a new <code>BadgeDAO</code> instance with the specified DAOFactory.
     * @param daoFactory the DAOFactory to be used by this BadgeDAO
     */
    BadgeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Finds and retrieves a badge from the database based on the provided badge ID.
     * @param id the ID of the badge to be retrieved
     * @return the Badge object retrieved from the database, or null if no badge was found
     * @throws DAOException if there is an error accessing the database
     */
    public Badge find(String id) {
        Badge badge = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, id);
                boolean hasResults = ps.execute();
                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        String description = rs.getString("description");
                        badge = new Badge(id, description);
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
        return badge;
    }

    /**
     * Creates a new badge in the database with the provided badge information.
     * @param badge the Badge object containing the information of the badge to be created
     * @return true if the badge was successfully created, false otherwise
     * @throws DAOException if there is an error accessing the database
     */
    public boolean create(Badge badge) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean updatedTable = false;
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, badge.getId());
                ps.setString(2, badge.getDescription());
                int rowsInserted = ps.executeUpdate();
                if (rowsInserted == 1) {
                    updatedTable = true;
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
        return updatedTable;
    }

    /**
     * Deletes the badge with the specified ID from the database.
     * @param id the ID of the badge to be deleted
     * @return true if the badge was successfully deleted, false otherwise
     * @throws DAOException if there is an error accessing the database
     */
    public boolean delete(String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean updatedTable = false;
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_DELETE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, id);
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted == 1) {
                    updatedTable = true;
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
        return updatedTable;
    }
}

