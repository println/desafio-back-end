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

	public Page<Employee> findAll(EmployeeRequestFilter filter, Pageable pageable) {
		if (!filter.isEmpty()) {
			return this.findAllByCriteria(filter, pageable);
		}
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

	private Page<Employee> findAllByCriteria(EmployeeRequestFilter filter, Pageable pageable) {
		switch (filter.getNumber()) {
		case 1:
			return this.repository
					.findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(	filter.getName(),
					                                                                      	filter.getName(), pageable);
		case 2:
			return this.repository.findAllByAge(filter.getAge(), pageable);
		case 3:
			return this.repository.findAllByRoleIgnoreCaseContaining(filter.getRole(), pageable);
		case 4:
			return this.repository.findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndAge(filter
			                                                                                                  .getName(), filter.getName(), filter.getAge(), pageable);
		case 5:
			return this.repository
					.findAllByRoleIgnoreCaseContainingAndFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining( filter.getRole(),filter
					                                                                                                  .getName(), filter.getName(), pageable);
		case 6:
			return this.repository.findAllByAgeAndRoleIgnoreCaseContaining(filter.getAge(), filter.getRole(), pageable);
		default:
			return this.repository
					.findAllByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndAgeAndRoleIgnoreCaseContaining(filter
					                                                                                                       .getName(), filter.getName(), filter.getAge(), filter.getRole(), pageable);
		}
	}
}
