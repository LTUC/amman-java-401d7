package com.LTUCD7.salmonCookies2.repositories;

import com.LTUCD7.salmonCookies2.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
