package edu.jsu.mcis.cs310.tas_sp24;
import java.util.zip.CRC32;

/**
 * Badge class that holds the information from the badge section of the database
 */

public class Badge {
    
    /**
     * id - the unique id of this badge
     * description - the name of the owner of the badge
     */
    private final String id, description;

    /**
     * Constructor for the badge
     * @param id passes id into constructor
     * @param description passes description into constructor
     */
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }
    
    /**
     * Unique constructor that creates the badge id for a new badge object
     * @param description passes description into constructor
     */
    public Badge(String description){
        this.description = description;
        CRC32 crc32 = new CRC32();
        crc32.update(description.getBytes());
        long checksum = crc32.getValue();
        // Convert the checksum to a hexadecimal string
        String hexString = Long.toHexString(checksum).toUpperCase();
        // Ensure that the ID is exactly eight characters long
        while (hexString.length() < 8) {
            hexString = "0" + hexString;
        }
        this.id = hexString;
        
        System.out.println(hexString);
    }
    
    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Overrides the toString method
     * @return the badge fields formatted to accommodate a test
     */
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }
}
