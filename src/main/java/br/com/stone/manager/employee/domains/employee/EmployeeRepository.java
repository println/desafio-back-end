package br.com.stone.manager.employee.domains.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
	@Query(value = "SELECT DISTINCT role FROM Employee ORDER BY role ASC")
	List<String> findAllRoles();
}
