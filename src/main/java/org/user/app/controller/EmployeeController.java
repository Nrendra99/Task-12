package org.user.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.user.app.exceptions.EmployeeNotFoundException;
import org.user.app.model.Employee;
import org.user.app.service.ServiceImplementation;

import jakarta.validation.Valid; 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller for handling employee-related web requests.
 */
@Controller
public class EmployeeController {

    @Autowired
    private ServiceImplementation serviceImplementation;

    /**
     * Handles the request to display the employee homepage with a list of all employees.
     * 
     * @param model Model object to hold data for the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/home")
    @Operation(summary = "List all employees", description = "Retrieves a list of all employees.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees")
    public String empList(Model model) {
        model.addAttribute("employees", this.serviceImplementation.getEmployess());
        return "Employee Homepage"; // View name for displaying employee list
    }

    /**
     * Displays the form for adding a new employee.
     * 
     * @param model Model object to hold data for the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/addEmployee")
    @Operation(summary = "Show add employee form", description = "Displays the form for adding a new employee.")
    public String addEmpForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "AddEmployee"; // View name for displaying the employee addition form
    }

    /**
     * Handles the form submission for adding a new employee.
     * 
     * @param employee The employee data from the form, validated.
     * @param result The result of validation, containing any validation errors.
     * @param model Model object to hold data for the view.
     * @return The name of the view template to render, or redirect to another page.
     */
    @PostMapping("/process")
    @Operation(summary = "Add a new employee", description = "Processes the form to add a new employee.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee added successfully"),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    public String addEmpProcess(@Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Collect all validation error messages
            StringBuilder errorMsg = new StringBuilder("Validation errors: ");
            result.getAllErrors().forEach(error -> {
                errorMsg.append(error.getDefaultMessage()).append("; ");
            });
            
            // Add the error message to the model
            model.addAttribute("message", errorMsg.toString());

            // Return the form view with validation error messages
            return "AddEmployee";
        }

        // If there are no validation errors, add the employee and show a success message
        this.serviceImplementation.addEmployee(employee);
        model.addAttribute("message", "Employee added successfully!");
        model.addAttribute("employee", new Employee()); // Reset the form fields by providing a new Employee object

        // Return the form view to show the success message
        return "AddEmployee";
    }
    
    @GetMapping("/employee/{id}")
    @Operation(summary = "View employee details", description = "Retrieves and displays details of a specific employee.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved employee details"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public String viewEmp(@PathVariable String id, Model model) {
        model.addAttribute("employee", this.serviceImplementation.getEmpById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found")));
        return "Employee"; // View name for displaying employee details
    }
    
    /**
     * Displays the form for updating an existing employee.
     * 
     * @param id The ID of the employee to update.
     * @param model Model object to hold data for the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/update/{id}")
    @Operation(summary = "Show update form", description = "Displays the form for updating an existing employee.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employee for update")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Employee employee = this.serviceImplementation.getEmpById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));
        model.addAttribute("employee", employee);
        return "Update Employee Form"; // View name for displaying the update form
    }

    /**
     * Handles the form submission for updating an existing employee.
     * 
     * @param id The ID of the employee to update.
     * @param employeeDetails The updated employee data from the form.
     * @return Redirects to the view page for the updated employee.
     */
    @PostMapping("/update/{id}")
    @Operation(summary = "Update an employee", description = "Processes the form to update an existing employee.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public String updateEmployee(@PathVariable String id, @ModelAttribute("employee") Employee employeeDetails) {
        Employee existingEmployee = this.serviceImplementation.getEmpById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setPhoneNo(employeeDetails.getPhoneNo());
        existingEmployee.setDepartment(employeeDetails.getDepartment());
        existingEmployee.setSalary(employeeDetails.getSalary());
        existingEmployee.setRole(employeeDetails.getRole());

        this.serviceImplementation.updateEmployee(existingEmployee);

        return "redirect:/employee/{id}"; // Redirect to the view page after updating
    }

    /**
     * Handles the request to delete an employee by ID.
     * 
     * @param id The ID of the employee to delete.
     * @return Redirects to the homepage after deletion.
     */
    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete an employee", description = "Deletes an employee by ID.")
    @ApiResponse(responseCode = "200", description = "Employee deleted successfully")
    public String deleteEmp(@PathVariable String id) {
        this.serviceImplementation.deleteEmpById(id);
        return "redirect:/home"; // Redirect to the homepage after deletion
    }

    /**
     * Displays employees filtered by department.
     * 
     * @param department The department to filter employees by.
     * @param model Model object to hold data for the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/view/{department}")
    @Operation(summary = "View employees by department", description = "Retrieves and displays employees filtered by department.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employees by department")
    public String viewEmpByDep(@PathVariable String department, Model model) {
        model.addAttribute("employees", this.serviceImplementation.getEmployeesByDep(department));
        return "EmployeeByDep"; // View name for displaying employees by department
    }
}