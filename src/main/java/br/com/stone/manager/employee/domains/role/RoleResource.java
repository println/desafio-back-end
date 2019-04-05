package br.com.stone.manager.employee.domains.role;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.stone.manager.employee.domains.employee.EmployeeRepository;
import br.com.stone.manager.employee.domains.employee.EmployeeResource;
import br.com.stone.manager.employee.support.utils.controller.RestApiController;
import br.com.stone.manager.employee.support.web.rest.util.ResponseUtil;

@RestController
@RestApiController
public class RoleResource {
	private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

	private final EmployeeRepository employeeRepository;

	public RoleResource(EmployeeRepository service) {
		this.employeeRepository = service;
	}

	@GetMapping("/roles")
	public ResponseEntity<List<String>> queryRoles(@RequestParam(required = false, defaultValue = "") String contains) {
		this.log.debug("REST request to get a page of Employees");
		Optional<List<String>> roles = Optional.of(this.employeeRepository.findRolesByQuery(contains));
		return ResponseUtil.wrapOrNotFound(roles);
	}

}
