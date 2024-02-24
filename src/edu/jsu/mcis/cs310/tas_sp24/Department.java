package edu.jsu.mcis.cs310.tas_sp24;

public class Department {

    // Create fields.
    private final String id, terminalid, description;
    
    public Department(String id, String description, String terminalid1) {
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }
    
    public String getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getTerminalid() {
        return terminalid;
    }
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');
        s.append('(').append(terminalid).append(')');

        return s.toString();
    
    }
    
}
