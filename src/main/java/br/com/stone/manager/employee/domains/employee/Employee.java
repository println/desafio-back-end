package br.com.stone.manager.employee.domains.employee;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.stone.manager.employee.support.utils.model.AuditableModel;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends AuditableModel<Employee> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
	@SequenceGenerator(name="employee_id_seq", sequenceName = "EMPLOYEE_SEQ",  allocationSize = 1)
	private Long id;

	@NotNull(message = "First name cannot be null")
	@Column(name = "first_name")
	private String firstName;

	@NotNull(message = "Last name cannot be null")
	@Column(name = "last_name")
	private String lastName;

	@NotNull
	@Min(value = 14, message = "The age should be greater than or equals 14")
	@Max(value = 100, message = "The age should be less than or equals 100")
	@Column(name = "age")
	private int age;

	@NotNull
	@Column(name = "role")
	private String role;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public Employee firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Employee lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return this.age;
	}

	public Employee age(int age) {
		this.age = age;
		return this;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRole() {
		return this.role;
	}

	public Employee role(String role) {
		this.role = role;
		return this;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Employee employee = (Employee) o;
		if (employee.getId() == null || this.getId() == null) {
			return false;
		}
		return Objects.equals(this.getId(), employee.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [id=");
		builder.append(this.id);
		builder.append(", firstName=");
		builder.append(this.firstName);
		builder.append(", lastName=");
		builder.append(this.lastName);
		builder.append(", age=");
		builder.append(this.age);
		builder.append(", role=");
		builder.append(this.role);
		builder.append("]");
		return builder.toString();
	}



}
