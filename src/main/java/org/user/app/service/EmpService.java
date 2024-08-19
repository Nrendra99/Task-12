package org.user.app.service;

import java.util.List;
import java.util.Optional;

import org.user.app.model.Employee;

/**
 * Service interface for managing {@link Employee} entities.
 * Provides methods for basic CRUD operations and additional querying capabilities.
 */
public interface EmpService {
 
    /**
     * Adds a new employee to the system.
     * 
     * @param employee The employee to be added.
     * @return The added employee with generated ID.
     */
    public Employee addEmployee(Employee employee);

    /**
     * Retrieves all employees from the system.
     * 
     * @return A list of all employees.
     */
    public List<Employee> getEmployess();

    /**
     * Retrieves employees by their department.
     * 
     * @param department The department to filter employees by.
     * @return A list of employees belonging to the specified department.
     */
    public List<Employee> getEmployeesByDep(String department);
    
    /**
     * Retrieves employees by their department.
     * 
     * @param location The location to filter employees by.
     * @return A list of employees belonging to the specified location.
     */
    public List<Employee> getEmployeesByLocation(String location);
    
    /**
     * Retrieves an employee by their ID.
     * 
     * @param id The ID of the employee to retrieve.
     * @return An Optional containing the employee if found, or empty if not.
     */
    public Optional<Employee> getEmpById(String id);

    /**
     * Updates an existing employee's details.
     * 
     * @param employee The employee with updated details.
     * @return The updated employee.
     */
    public Employee updateEmployee(Employee employee);

    /**
     * Deletes an employee from the system by their ID.
     * 
     * @param id The ID of the employee to delete.
     */
    public void deleteEmpById(String id);
}