package com.io.framey.services;

import com.io.framey.datamodel.Employee;
import com.io.framey.datamodel.Position;
import org.postgresql.util.PSQLException;

import java.util.List;

public interface EmployeeRepository {

    int putEmployee(Employee employee);

    boolean deleteEmployeeById(Long id) throws PSQLException;

    boolean updateEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    List<Employee> getAllSubordinated();

    List<Employee> getEmployeesByPosition(Position position);

    void deleteAll();

}
