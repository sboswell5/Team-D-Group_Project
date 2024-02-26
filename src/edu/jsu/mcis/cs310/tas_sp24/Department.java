package edu.jsu.mcis.cs310.tas_sp24;

public class Department {

    private final int id;
    private String terminalid, description;
    
    public Department(int id, String description, String terminalid) {
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }
    
    public int getId() {
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
        s.append(", Terminal ID: ").append(terminalid);
        
        return s.toString();
    
    }
    
}
