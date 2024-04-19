package edu.jsu.mcis.cs310.tas_sp24;

/**
 * The <code>Department</code> class represents a department in the TAS system.
 * It encapsulates information about the department, including its ID, description, and terminal ID.
 */
public class Department {

    private final int id, terminalid;
    private final String description;
    
    /**
     * Constructs a new <code>Department</code> instance with the specified ID, description, and terminal ID.
     * @param id the ID of the department
     * @param description the description of the department
     * @param terminalid the terminal ID of the department
     */
    public Department(int id, String description, int terminalid) {
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }
    
    /**
     * Returns the ID of the department.
     * @return the ID of the department
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the description of the department.
     * @return the description of the department
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns the terminal ID of the department.
     * @return the terminal ID of the department
     */
    public int getTerminalid() {
        return terminalid;
    }
    
    /**
     * Returns a string representation of the department.
     * The string representation includes the department ID, description, and terminal ID.
     * @return a string representation of the department
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');
        s.append(", Terminal ID: ").append(terminalid);
        return s.toString();
    }
    
}

