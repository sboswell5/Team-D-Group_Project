package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Department;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author artma
 */
public class DepartmentDAO {
       
    DepartmentDAO() {
        
    }
    public void getDepartmentDAO(){
        
    }
    public Department find(String id) {
        Department dep = new Department("id","description","terminalid");
        return dep;
    }
}
