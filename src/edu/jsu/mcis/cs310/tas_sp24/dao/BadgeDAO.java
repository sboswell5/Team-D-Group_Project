package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;

public class BadgeDAO {

    // Queries
    private static final String QUERY_INSERT = "INSERT INTO badge (id, description) VALUES (?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM badge WHERE id = ?";
    
    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";

    private final DAOFactory daoFactory;

    BadgeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }

    public Badge find(String id) {

        Badge badge = null;

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
                        badge = new Badge(id, description);

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

        return badge;
    }

    public boolean create(Badge badge) {
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean updatedTable = false;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                // add query to top (string)
                ps = conn.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, badge.getDescription());
                ps.setString(2, badge.getId());
               
                int rowsInserted = ps.executeUpdate();

                if (rowsInserted == 1) {
                    updatedTable = true;
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
       
        return updatedTable;
    }

    public boolean delete(String id) {
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean updatedTable = false;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                // add query to top (string)
                ps = conn.prepareStatement(QUERY_DELETE, Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, id);
                
                int rowsInserted = ps.executeUpdate();

                if (rowsInserted == 1) {
                    updatedTable = true;
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
        
        return updatedTable;
    }
}

