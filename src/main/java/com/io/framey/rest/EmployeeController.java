package com.io.framey.rest;

import com.io.framey.datamodel.Employee;
import com.io.framey.datamodel.Position;
import com.io.framey.exception.SuperioNotManagerException;
import com.io.framey.services.EmployeeRepository;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employeeManagement")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employee/{id}/position")
    public ResponseEntity<List<Employee>> getEmployeesByPosition(@PathVariable(value = "id") long id) {
        return new ResponseEntity(employeeRepository.getEmployeesByPosition(Position.valueOf(id)),
                HttpStatus.OK);
    }

    @GetMapping("/employee/subordinated")
    public ResponseEntity<List<Employee>> getAllSubordinates() {
        return new ResponseEntity(employeeRepository.getAllSubordinated(),
                HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeesById(@PathVariable(value = "id") Long id) {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity(employee,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Boolean> deleteEmployeesById(@PathVariable(value = "id") Long id) {
        try {
            employeeRepository.deleteEmployeeById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (PSQLException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @DeleteMapping("/employees")
    public ResponseEntity deleteAllEmployees() {
        employeeRepository.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee input) {
        try {
            employeeRepository.putEmployee(input);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (SuperioNotManagerException ex) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateEmployee(@RequestBody Employee input) {
        return new ResponseEntity(employeeRepository.updateEmployee(input), HttpStatus.OK);
    }
}
