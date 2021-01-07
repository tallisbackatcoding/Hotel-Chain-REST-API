package com.boots.repository;

import com.boots.entity.Employee;
import com.boots.idclasses.EmployeeIds;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, EmployeeIds> {
    Employee findByEmail(String email);
}
