package br.com.stone.manager.employee.domains.employee;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query(value = "SELECT DISTINCT role FROM Employee e WHERE LOWER(role) LIKE CONCAT('%',LOWER(:query),'%') ORDER BY role ASC")
	List<String> searchRoles(@Param("query") String contains);

	@Query(value = "SELECT DISTINCT role FROM Employee ORDER BY role ASC")
	List<String> findAllRoles();

	Page<Employee> findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String firstName,
			String lastName, Pageable pageable);

	Page<Employee> findAllByAge(Integer age, Pageable pageable);

	Page<Employee> findAllByRoleIgnoreCaseContaining(String role, Pageable pageable);

	Page<Employee> findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndAge(String firstName,
			String lastName, Integer age, Pageable pageable);

	Page<Employee> findAllByRoleIgnoreCaseContainingAndFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(
			String role, String firstName, String lastName, Pageable pageable);

	Page<Employee> findAllByAgeAndRoleIgnoreCaseContaining(Integer age, String role, Pageable pageable);

	Page<Employee> findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndAgeAndRoleIgnoreCaseContaining(
			String firstName, String lastName, Integer age, String role, Pageable pageable);

}
