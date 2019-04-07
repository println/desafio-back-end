package br.com.stone.manager.employee.domains.employee;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.stone.manager.employee.support.web.rest.errors.BadRequestAlertException;

@Service
public class EmployeeService {
	private static final String ENTITY_NAME = "employee";

	private final EmployeeRepository repository;

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public Page<Employee> findAll(EmployeeRequestFilter filter, Pageable pageable) {
		Specification<Employee> specification = Specification.where(null);
		if (filter.hasName()) {
			specification = specification.and(
			                                  EmployeeSpecification.firstName(filter.getName())
			                                  .or(EmployeeSpecification.lastName(filter.getName())));
		}
		if (filter.hasAge()) {
			specification = specification.and(EmployeeSpecification.age(filter.getAge()));
		}
		if (filter.hasRole()) {
			specification = specification.and(EmployeeSpecification.role(filter.getRole()));
		}
		return this.repository.findAll(specification, pageable);
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

	public Employee update(Long id, @Valid Employee employee) {
		Optional<Employee> result = this.findById(id);

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
