package br.com.stone.manager.employee.domains.employee;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.stone.manager.employee.support.web.rest.errors.BadRequestAlertException;

@Service
public class EmployeeService {
	private static final String ENTITY_NAME = "employee";

	private final EmployeeRepository repository;

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public Page<Employee> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public Optional<Employee> findById(Long id) {
		return this.repository.findById(id);
	}

	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

	public Employee create(@Valid Employee employee) {

		Employee entity = new Employee().firstName(employee.getFirstName())
				.lastName(employee.getLastName())
				.age(employee.getAge())
				.role(employee.getRole());

		return this.repository.save(entity);
	}

	public Employee update(@Valid Employee employee) {
		Optional<Employee> result = this.findById(employee.getId());

		if (!result.isPresent()) {
			throw new BadRequestAlertException("Not exists", ENTITY_NAME, "idnull");
		}

		Employee entity = result.get()
				.firstName(employee.getFirstName())
				.lastName(employee.getLastName())
				.age(employee.getAge())
				.role(employee.getRole());

		return this.repository.save(entity);
	}
}
