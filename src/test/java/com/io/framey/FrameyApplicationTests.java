package com.io.framey;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.framey.datamodel.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.List;

import static com.io.framey.Constants.IT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestCPGonfiguration.class)
@ActiveProfiles({IT})
@AutoConfigureMockMvc
class FrameyApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void controller_should_return_subordinated_employees() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employeeManagement/employee/subordinated"))
                .andExpect(status().isOk())
                .andReturn();

        List<Employee> employees = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Employee>>() {
                }
        );
        assertNotNull(employees);
    }

    @Test
    public void controller_should_return_employees_by_position() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employeeManagement/employee/2/position"))
                .andExpect(status().isOk())
                .andReturn();

        List<Employee> employees = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Employee>>() {
                }
        );
        assertNotNull(employees);
    }

    @Test
    public void controller_should_update_employee() throws Exception {
        Employee updateEmployee = new Employee(3, "Test update", 1, 1, "2021-01-06", "2021-01-09");
        String requestJsonBody = objectMapper.writeValueAsString(updateEmployee);
        MvcResult result = mockMvc.perform(put("/api/employeeManagement/employee")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJsonBody))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(Boolean.parseBoolean(result.getResponse()
                .getContentAsString()));
    }

    @Test
    public void controller_cannot_update_not_existing_employee() throws Exception {
        Employee updateEmployee = new Employee(7, "Test update", 1, 1, "2021-01-06", "2021-01-09");
        String requestJsonBody = objectMapper.writeValueAsString(updateEmployee);
        MvcResult result = mockMvc.perform(put("/api/employeeManagement/employee")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJsonBody))
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(Boolean.parseBoolean(result.getResponse()
                .getContentAsString()));
    }
}
