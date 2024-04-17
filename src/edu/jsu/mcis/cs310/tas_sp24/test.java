/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp24.dao.DAOFactory;

/**
 *
 * @author artma
 */
public class test {

    
   
    public static void main(String[] args) {
   
        
        // Create a Badge object with a description
        Badge b2 = new Badge("Smith, Daniel Q");
        // The constructor will print the hexadecimal ID to the console
        // Access the ID generated for the badge
        String badgeId = b2.getId();
        // Print the generated ID
        System.out.println("Generated Badge ID: " + badgeId);
        
    }
}
