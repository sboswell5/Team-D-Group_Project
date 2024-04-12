package edu.jsu.mcis.cs310.tas_sp24;
import java.util.zip.CRC32;

public class Badge {

    private final String id, description;

    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }
    
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
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }
}
