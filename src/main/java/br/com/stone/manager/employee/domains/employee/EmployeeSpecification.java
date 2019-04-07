package br.com.stone.manager.employee.domains.employee;

import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {
	public static Specification<Employee> firstName(String name) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("firstName")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<Employee> lastName(String name) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("lastName")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<Employee> age(Integer age) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("age"), age);
	}

	public static Specification<Employee> role(String role) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("role")), "%" + role + "%");
	}
}
