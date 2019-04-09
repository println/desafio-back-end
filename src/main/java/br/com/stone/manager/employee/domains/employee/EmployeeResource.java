package br.com.stone.manager.employee.domains.employee;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.stone.manager.employee.support.utils.controller.RestApiController;
import br.com.stone.manager.employee.support.web.rest.util.HeaderUtil;
import br.com.stone.manager.employee.support.web.rest.util.PaginationUtil;
import br.com.stone.manager.employee.support.web.rest.util.ResponseUtil;

@RestController
@RestApiController
public class EmployeeResource {

	private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

	@Value("${spring.data.web.pageable.one-indexed-parameters}")
	private boolean oneIndexedParameters;

	private static final String ENTITY_NAME = "employee";

	private final EmployeeService service;

	public EmployeeResource(EmployeeService service) {
		this.service = service;
	}

	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
		this.log.debug("REST request to save Employee : {}", employee);
		Employee result = this.service.create(employee);
		return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee)
			throws URISyntaxException {
		this.log.debug("REST request to update Employee : {}", employee);
		Employee result = this.service.update(id, employee);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer age,
			@RequestParam(required = false) String role,
			Pageable pageable) {
		this.log.debug("REST request to get a page of Employees");
		Page<Employee> page = this.service.findAll(new EmployeeRequestFilter(name, age, role), pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(	page, "/api/employees",
		                                                                   	this.oneIndexedParameters);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/employees/count")
	public ResponseEntity<Long> countEmployees() {
		this.log.debug("REST request to get a page of Employees");
		Long count = this.service.count();
		return ResponseEntity.ok().body(count);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
		this.log.debug("REST request to get Employee : {}", id);
		Optional<Employee> optional = this.service.findById(id);
		return ResponseUtil.wrapOrNotFound(optional);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		this.log.debug("REST request to delete Employee : {}", id);
		this.service.deleteById(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
