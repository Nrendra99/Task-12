package org.user.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.user.app.model.Employee;
import org.user.app.repository.EmployeeRepository;

/**
 * Service implementation for managing {@link Employee} entities.
 * Provides business logic and interacts with the {@link EmployeeRepository}.
 */
@Service
public class ServiceImplementation implements EmpService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Adds a new employee to the system.
     * 
     * @param employee The employee to be added.
     * @return The added employee with a generated ID.
     */
    @Override
    public Employee addEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    /**
     * Retrieves all employees from the system.
     * 
     * @return A list of all employees.
     */
    @Override
    public List<Employee> getEmployess() {
        return this.employeeRepository.findAll();
    }

    /**
     * Retrieves employees filtered by their department.
     * 
     * @param department The department to filter employees by.
     * @return A list of employees belonging to the specified department.
     */
    @Override
    public List<Employee> getEmployeesByDep(String department) {
        return this.employeeRepository.findByDepartment(department); 
    }

    /**
     * Retrieves an employee by their ID.
     * 
     * @param id The ID of the employee to retrieve.
     * @return An Optional containing the employee if found, or empty if not.
     */
    @Override
    public Optional<Employee> getEmpById(String id) {
        return this.employeeRepository.findById(id);
    }

    /**
     * Updates an existing employee's details.
     * 
     * @param employee The employee with updated details.
     * @return The updated employee.
     */
    @Override
    public Employee updateEmployee(Employee employee) {
        // Ensure the employee exists before updating
        if (this.employeeRepository.existsById(employee.getId())) {
            return this.employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found with ID: " + employee.getId());
        }
    }

    /**
     * Deletes an employee from the system by their ID.
     * 
     * @param id The ID of the employee to delete.
     */
    @Override
    public void deleteEmpById(String id) {
        this.employeeRepository.deleteById(id);
    }
}