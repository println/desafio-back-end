package br.com.stone.manager.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.validation.Validator;

import br.com.stone.manager.employee.support.web.rest.errors.ExceptionTranslator;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public abstract class AbstractResourceJpaTest {

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private Validator validator;

	protected MockMvc restMockMvc;

	public abstract Object createResource();

	protected MockMvc createMockMvcWithEagerRelationships(Object resource) {
		return this.getMvcBuilder(resource).build();
	}

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.restMockMvc = this.getMvcBuilder(this.createResource()).setValidator(this.validator).build();
	}

	private StandaloneMockMvcBuilder getMvcBuilder(Object resource) {
		return MockMvcBuilders.standaloneSetup(resource)
				.setCustomArgumentResolvers(this.pageableArgumentResolver)
				.setControllerAdvice(this.exceptionTranslator)
				.setConversionService(TestUtil.createFormattingConversionService())
				.setMessageConverters(this.jacksonMessageConverter);
	}

}