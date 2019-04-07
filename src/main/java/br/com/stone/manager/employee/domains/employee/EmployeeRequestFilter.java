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

	public int getNumber() {
		if (Objects.isNull(this.age) && Objects.isNull(this.role)) {
			return 1;
		}

		if (Objects.isNull(this.name) && Objects.isNull(this.role)) {
			return 2;
		}

		if (Objects.isNull(this.name) && Objects.isNull(this.age)) {
			return 3;
		}

		if (Objects.isNull(this.role)) {
			return 4;
		}

		if (Objects.isNull(this.age)) {
			return 5;
		}

		if (Objects.isNull(this.name)) {
			return 6;
		}

		return 7;
	}

	public boolean isEmpty() {
		return Objects.isNull(this.name) && Objects.isNull(this.age) && Objects.isNull(this.role);
	}

}
