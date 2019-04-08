package br.com.stone.manager.employee.domains.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.com.stone.manager.employee.AbstractResourceJpaTest;
import br.com.stone.manager.employee.TestUtil;

class EmployeeResourceTest extends AbstractResourceJpaTest {

	private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
	private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
	private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_ROLE = "AAAAAAAAAA";
	private static final String UPDATED_ROLE = "BBBBBBBBBB";

	private static final Integer DEFAULT_AGE = 18;
	private static final Integer UPDATED_AGE = 70;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	private Employee employee;

	public static Employee createEntity() {
		Employee employee = new Employee().firstName(DEFAULT_FIRST_NAME)
				.lastName(DEFAULT_LAST_NAME)
				.age(DEFAULT_AGE)
				.role(DEFAULT_ROLE);
		return employee;
	}

	@Override
	public Object createResource() {
		return new EmployeeResource(this.employeeService);
	}

	@BeforeEach
	public void initTest() {
		this.employee = createEntity();
	}

	@Test
	public void createEmployee() throws Exception {
		int databaseSizeBeforeCreate = this.employeeRepository.findAll().size();

		// Create the Employee
		this.restMockMvc
		.perform(post("/api/employees").contentType(TestUtil.APPLICATION_JSON_UTF8)
		         .content(TestUtil.convertObjectToJsonBytes(this.employee)))
		.andExpect(status().isCreated());

		// Validate the Employee in the database
		List<Employee> employees = this.employeeRepository.findAll();
		assertThat(employees).hasSize(databaseSizeBeforeCreate + 1);
		Employee testEmployee = employees.get(employees.size() - 1);
		assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
		assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
		assertThat(testEmployee.getAge()).isEqualTo(DEFAULT_AGE);
		assertThat(testEmployee.getRole()).isEqualTo(DEFAULT_ROLE);
	}

	@Test
	public void getAllEmployees() throws Exception {
		// Initialize the database
		this.employeeRepository.save(this.employee);

		// Get all the employeeList
		this.restMockMvc.perform(get("/api/employees?sort=id,desc"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.[*].id").value(hasItem(this.employee.getId().intValue())))
		.andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
		.andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
		.andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
		.andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));
	}

	@Test
	public void getEmployee() throws Exception {
		// Initialize the database
		this.employeeRepository.save(this.employee);

		// Get the employee
		this.restMockMvc.perform(get("/api/employees/{id}", this.employee.getId()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.id").value(this.employee.getId()))
		.andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
		.andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
		.andExpect(jsonPath("$.age").value(DEFAULT_AGE))
		.andExpect(jsonPath("$.role").value(DEFAULT_ROLE));
	}

	@Test
	public void getNonExistingEmployee() throws Exception {
		// Get the employee
		this.restMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	public void updateEmployee() throws Exception {
		// Initialize the database
		this.employeeRepository.save(this.employee);

		int databaseSizeBeforeUpdate = this.employeeRepository.findAll().size();

		// Update the employee
		Employee updatedEmployee = this.employeeRepository.findById(this.employee.getId()).get();
		updatedEmployee.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).age(UPDATED_AGE).role(UPDATED_ROLE);

		this.restMockMvc
		.perform(put("/api/employees/" + updatedEmployee.getId()).contentType(TestUtil.APPLICATION_JSON_UTF8)
		         .content(TestUtil.convertObjectToJsonBytes(updatedEmployee)))
		.andExpect(status().isOk());

		// Validate the Employee in the database
		List<Employee> employees = this.employeeRepository.findAll();
		assertThat(employees).hasSize(databaseSizeBeforeUpdate);
		Employee testEmployee = employees.get(employees.size() - 1);
		assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
		assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
		assertThat(testEmployee.getAge()).isEqualTo(UPDATED_AGE);
		assertThat(testEmployee.getRole()).isEqualTo(UPDATED_ROLE);
	}

	@Test
	public void deleteEmployee() throws Exception {
		// Initialize the database
		this.employeeRepository.save(this.employee);

		int databaseSizeBeforeDelete = this.employeeRepository.findAll().size();

		// Get the employee
		this.restMockMvc
		.perform(delete("/api/employees/{id}", this.employee.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());

		// Validate the database is empty
		List<Employee> employees = this.employeeRepository.findAll();
		assertThat(employees).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	public void checkFirstNameIsRequired() throws Exception {
		this.employee.setFirstName(null);
		this.checkRequired();
	}

	@Test
	public void checkLastNameIsRequired() throws Exception {
		this.employee.setLastName(null);
		this.checkRequired();
	}

	@Test
	public void checkAgeLessThan14() throws Exception {
		this.employee.setAge(13);
		this.checkRequired();
	}

	@Test
	public void checkAgeGreaterThan100() throws Exception {
		this.employee.setAge(101);
		this.checkRequired();
	}

	@Test
	public void checkRoleIsRequired() throws Exception {
		this.employee.setRole(null);
		this.checkRequired();
	}

	private void checkRequired() throws Exception {
		int databaseSizeBeforeTest = this.employeeRepository.findAll().size();

		this.restMockMvc
		.perform(post("/api/employees").contentType(TestUtil.APPLICATION_JSON_UTF8)
		         .content(TestUtil.convertObjectToJsonBytes(this.employee)))
		.andExpect(status().isBadRequest());

		List<Employee> employees = this.employeeRepository.findAll();
		assertThat(employees).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Employee.class);
		Employee employee1 = new Employee();
		employee1.setId(9000L);
		Employee employee2 = new Employee();
		employee2.setId(employee1.getId());
		assertThat(employee1).isEqualTo(employee2);
		employee2.setId(8000L);
		assertThat(employee1).isNotEqualTo(employee2);
		employee1.setId(null);
		assertThat(employee1).isNotEqualTo(employee2);
	}

}
