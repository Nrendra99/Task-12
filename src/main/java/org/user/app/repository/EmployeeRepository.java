package org.user.app.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.user.app.model.Employee;

/**
 * Repository interface for performing CRUD operations on {@link Employee} entities.
 * Extends MongoRepository to provide default methods for basic CRUD operations.
 */
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    /**
     * Finds a list of employees by their department.
     * 
     * @param department The department to filter employees by.
     * @return A list of employees belonging to the specified department.
     */ 
    public List<Employee> findByDepartment(String department);
    
    /**
     * Finds a list of employees by their location.
     * 
     * @param location The location to filter employees by.
     * @return A list of employees belonging to the specified location.
     */ 
    public List<Employee> findByLocation(String location);
}