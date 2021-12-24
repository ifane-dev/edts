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
    public void add(@RequestBody Employee employee) {
        employeeService.save(employee);
    }

    @PutMapping("/api/employee/{id}")
    public ResponseEntity<?> update(@RequestBody Employee employee, @PathVariable Integer id) {
        try {
            Employee existEmployee = employeeService.get(id);
            employeeService.save(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
