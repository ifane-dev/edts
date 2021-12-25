package com.test.project.controller;

import com.test.project.domain.Employee;
import com.test.project.dto.EmployeeDTO;
import com.test.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class HomeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/api/employee")
    public List<EmployeeDTO> list() {
        return employeeService.listAll();
    }

    @GetMapping("/api/employee/{id}")
    public ResponseEntity<Employee> get(@PathVariable Integer id) {
        try {
            Employee employee = employeeService.get(id);
            return new ResponseEntity<Employee>(employee, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/employee")
    public ResponseEntity<Object> add(@RequestBody Employee employee) {
        try {
            final Employee saveEmployee = employeeService.save(employee);
            return ResponseEntity.ok(saveEmployee);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/update/{id}")
    public ResponseEntity<Employee> update(@PathVariable(value = "id") Long employeeId,
                                                   @RequestBody Employee employeeDetails) {
        Employee employee = employeeService.get(employeeId);

        employee.setName(employeeDetails.getName());
        employee.setSalary(employeeDetails.getSalary());
        employee.setGrade(employeeDetails.getGrade());
        final Employee updatedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
}
