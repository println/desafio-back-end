package br.com.stone.manager.employee.domains.employee;

import java.util.Objects;

public class EmployeeRequestFilter {
	private String name;
	private Integer age;
	private String role;

	public EmployeeRequestFilter(String name, Integer age, String role) {
		this.name = name;
		this.age = age;
		this.role = role;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean hasName() {
		return Objects.nonNull(this.name);
	}

	public boolean hasAge() {
		return Objects.nonNull(this.age);
	}

	public boolean hasRole() {
		return Objects.nonNull(this.role);
	}

}
