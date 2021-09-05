package com.io.framey.services;

import com.io.framey.common.IdGenerator;
import com.io.framey.datamodel.Employee;
import com.io.framey.datamodel.Position;
import com.io.framey.exception.SuperioNotManagerException;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.LinkedList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    public EmployeeRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.idGenerator = idGenerator;
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final IdGenerator idGenerator;

    private static final String INSERT_QUERY = "insert into employee " +
            "(id, name, positionID, superior, start_date, end_date)" +
            "values (:id, :name, :positionID, :superior, :start_date, :end_date)";
    private static final String DELETE_ALL_EMPLOYEES_QUERY = "delete from employee";
    private static final String DELETE_EMPLOYEES_BY_ID_QUERY = "delete from employee where id = :id";
    private static final String SELECT_EMPLOYEES_BY_ID_QUERY = "select * from employee where id = :id";
    private static final String UPDATE_EMPLOYEE_BY_PARAMS = "update employee set name = :name," +
            " positionID = :positionID," +
            " superior = :superior," +
            " end_date = :end_date" +
            " where id = :id";

    private static final String SELECT_EMPLOYEES_BY_POSITION = "select * from employee where positionID = :positionID";

    private static final String SELECT_SUBORDINATED_EMPLOYEES =
            "select * from employee where superior in (select id from employee where positionID = :positionID)";


    private static final String EMPLOYEE_IS_NOT_SUPERIO = "Superio id: %s is not manager";

    @Override
    public int putEmployee(Employee employee) {
        Employee manager = superioCheck(employee);
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", idGenerator.generateId())
                .addValue("name", employee.getName())
                .addValue("positionID", employee.getPosition())
                .addValue("superior", manager == null ? null : employee.getSuperior())
                .addValue("start_date", employee.getStartDate())
                .addValue("end_date", employee.getEndDate());
        return jdbcTemplate.update(INSERT_QUERY, paramSource);
    }

    private Employee superioCheck(Employee employee) {
        Employee manager = getEmployeeById(employee.getSuperior());
        if (manager != null && !Position.valueOf(manager.getPosition()).equals(Position.MANAGER)) {
            String message = String.format(EMPLOYEE_IS_NOT_SUPERIO, employee.getSuperior());
            System.out.println(message);
            throw new SuperioNotManagerException(message);
        }
        return manager;
    }

    @Override
    public boolean deleteEmployeeById(Long id) throws PSQLException {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.update(DELETE_EMPLOYEES_BY_ID_QUERY, paramSource) > 0;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        Employee manager = superioCheck(employee);
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", employee.getId())
                .addValue("name", employee.getName())
                .addValue("positionID", employee.getPosition())
                .addValue("superior", manager == null ? null : employee.getSuperior())
                .addValue("end_date", employee.getEndDate());
        return jdbcTemplate.update(UPDATE_EMPLOYEE_BY_PARAMS, paramSource) > 0;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(SELECT_EMPLOYEES_BY_ID_QUERY, paramSource, rs -> {
            Employee employee = null;
            if (rs.next()) {
                employee = new Employee(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("positionID"),
                        rs.getLong("superior"),
                        rs.getDate("start_date").toString(),
                        rs.getDate("end_date").toString());
            }
            return employee;
        });

    }

    @Override
    public List<Employee> getAllSubordinated() {
        return getEmployeeList(SELECT_SUBORDINATED_EMPLOYEES, Position.MANAGER);
    }

    @Override
    public List<Employee> getEmployeesByPosition(Position position) {
        return getEmployeeList(SELECT_EMPLOYEES_BY_POSITION, position);
    }

    private List<Employee> getEmployeeList(String query, Position position) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("positionID", position.getPositionValueId());
        return jdbcTemplate.query(query, paramSource, rs -> {
            List<Employee> lst = new LinkedList<>();
            while (rs.next()) {
                lst.add(new Employee(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("positionID"),
                        rs.getLong("superior"),
                        rs.getDate("start_date").toString(),
                        rs.getDate("end_date").toString()));
            }
            return lst;
        });
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.getJdbcTemplate().execute(DELETE_ALL_EMPLOYEES_QUERY);
    }
}
