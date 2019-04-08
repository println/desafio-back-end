package br.com.stone.manager.employee.domains.role;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.stone.manager.employee.domains.employee.EmployeeRepository;
import br.com.stone.manager.employee.domains.employee.EmployeeResource;
import br.com.stone.manager.employee.support.utils.controller.RestApiController;
import br.com.stone.manager.employee.support.web.rest.util.ResponseUtil;

@RestController
@RestApiController
@Validated
public class RoleResource {
	private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

	private final EmployeeRepository employeeRepository;

	public RoleResource(EmployeeRepository service) {
		this.employeeRepository = service;
	}

	@GetMapping("/roles")
	public ResponseEntity<List<String>> getAllRoles() {
		this.log.debug("REST request to get all Roles");
		Optional<List<String>> roles = Optional.of(this.employeeRepository.findAllRoles());
		return ResponseUtil.wrapOrNotFound(roles);
	}

}
