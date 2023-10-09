package com.neoproc.autoplanilla.web.rest;

import static com.neoproc.autoplanilla.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neoproc.autoplanilla.IntegrationTest;
import com.neoproc.autoplanilla.domain.Employee;
import com.neoproc.autoplanilla.domain.enumeration.Gender;
import com.neoproc.autoplanilla.domain.enumeration.IdentificationType;
import com.neoproc.autoplanilla.domain.enumeration.JornadaType;
import com.neoproc.autoplanilla.domain.enumeration.Language;
import com.neoproc.autoplanilla.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    private static final IdentificationType DEFAULT_IDENTIFICATION_TYPE = IdentificationType.CN;
    private static final IdentificationType UPDATED_IDENTIFICATION_TYPE = IdentificationType.CR;

    private static final String DEFAULT_INSURED_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INSURED_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_CIVIL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CIVIL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_BIRTH = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALARY = new BigDecimal(0);
    private static final BigDecimal UPDATED_SALARY = new BigDecimal(1);

    private static final JornadaType DEFAULT_JORNADA = JornadaType.TiempoCompleto;
    private static final JornadaType UPDATED_JORNADA = JornadaType.MedioTiempo;

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    private static final Integer DEFAULT_DAYS_OF_SHIFT = 0;
    private static final Integer UPDATED_DAYS_OF_SHIFT = 1;

    private static final Integer DEFAULT_HOURS_OF_SHIFT = 0;
    private static final Integer UPDATED_HOURS_OF_SHIFT = 1;

    private static final Integer DEFAULT_JOB_CODE = 1;
    private static final Integer UPDATED_JOB_CODE = 2;

    private static final String DEFAULT_COST_CENTER = "AAAAAAAAAA";
    private static final String UPDATED_COST_CENTER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .lastName2(DEFAULT_LAST_NAME_2)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .nationality(DEFAULT_NATIONALITY)
            .identification(DEFAULT_IDENTIFICATION)
            .identificationType(DEFAULT_IDENTIFICATION_TYPE)
            .insuredNumber(DEFAULT_INSURED_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .gender(DEFAULT_GENDER)
            .civilStatus(DEFAULT_CIVIL_STATUS)
            .countryOfBirth(DEFAULT_COUNTRY_OF_BIRTH)
            .salary(DEFAULT_SALARY)
            .jornada(DEFAULT_JORNADA)
            .language(DEFAULT_LANGUAGE)
            .daysOfShift(DEFAULT_DAYS_OF_SHIFT)
            .hoursOfShift(DEFAULT_HOURS_OF_SHIFT)
            .jobCode(DEFAULT_JOB_CODE)
            .costCenter(DEFAULT_COST_CENTER);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .identification(UPDATED_IDENTIFICATION)
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .insuredNumber(UPDATED_INSURED_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .civilStatus(UPDATED_CIVIL_STATUS)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .salary(UPDATED_SALARY)
            .jornada(UPDATED_JORNADA)
            .language(UPDATED_LANGUAGE)
            .daysOfShift(UPDATED_DAYS_OF_SHIFT)
            .hoursOfShift(UPDATED_HOURS_OF_SHIFT)
            .jobCode(UPDATED_JOB_CODE)
            .costCenter(UPDATED_COST_CENTER);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getLastName2()).isEqualTo(DEFAULT_LAST_NAME_2);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testEmployee.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testEmployee.getIdentificationType()).isEqualTo(DEFAULT_IDENTIFICATION_TYPE);
        assertThat(testEmployee.getInsuredNumber()).isEqualTo(DEFAULT_INSURED_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testEmployee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployee.getCivilStatus()).isEqualTo(DEFAULT_CIVIL_STATUS);
        assertThat(testEmployee.getCountryOfBirth()).isEqualTo(DEFAULT_COUNTRY_OF_BIRTH);
        assertThat(testEmployee.getSalary()).isEqualByComparingTo(DEFAULT_SALARY);
        assertThat(testEmployee.getJornada()).isEqualTo(DEFAULT_JORNADA);
        assertThat(testEmployee.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testEmployee.getDaysOfShift()).isEqualTo(DEFAULT_DAYS_OF_SHIFT);
        assertThat(testEmployee.getHoursOfShift()).isEqualTo(DEFAULT_HOURS_OF_SHIFT);
        assertThat(testEmployee.getJobCode()).isEqualTo(DEFAULT_JOB_CODE);
        assertThat(testEmployee.getCostCenter()).isEqualTo(DEFAULT_COST_CENTER);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFirstName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setLastName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmail(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setNationality(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setIdentification(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentificationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setIdentificationType(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHireDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setHireDate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setSalary(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJornadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setJornada(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].lastName2").value(hasItem(DEFAULT_LAST_NAME_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].identificationType").value(hasItem(DEFAULT_IDENTIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].insuredNumber").value(hasItem(DEFAULT_INSURED_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].civilStatus").value(hasItem(DEFAULT_CIVIL_STATUS)))
            .andExpect(jsonPath("$.[*].countryOfBirth").value(hasItem(DEFAULT_COUNTRY_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(sameNumber(DEFAULT_SALARY))))
            .andExpect(jsonPath("$.[*].jornada").value(hasItem(DEFAULT_JORNADA.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].daysOfShift").value(hasItem(DEFAULT_DAYS_OF_SHIFT)))
            .andExpect(jsonPath("$.[*].hoursOfShift").value(hasItem(DEFAULT_HOURS_OF_SHIFT)))
            .andExpect(jsonPath("$.[*].jobCode").value(hasItem(DEFAULT_JOB_CODE)))
            .andExpect(jsonPath("$.[*].costCenter").value(hasItem(DEFAULT_COST_CENTER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeesWithEagerRelationshipsIsEnabled() throws Exception {
        when(employeeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(employeeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(employeeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(employeeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.lastName2").value(DEFAULT_LAST_NAME_2))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION))
            .andExpect(jsonPath("$.identificationType").value(DEFAULT_IDENTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.insuredNumber").value(DEFAULT_INSURED_NUMBER))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.civilStatus").value(DEFAULT_CIVIL_STATUS))
            .andExpect(jsonPath("$.countryOfBirth").value(DEFAULT_COUNTRY_OF_BIRTH))
            .andExpect(jsonPath("$.salary").value(sameNumber(DEFAULT_SALARY)))
            .andExpect(jsonPath("$.jornada").value(DEFAULT_JORNADA.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.daysOfShift").value(DEFAULT_DAYS_OF_SHIFT))
            .andExpect(jsonPath("$.hoursOfShift").value(DEFAULT_HOURS_OF_SHIFT))
            .andExpect(jsonPath("$.jobCode").value(DEFAULT_JOB_CODE))
            .andExpect(jsonPath("$.costCenter").value(DEFAULT_COST_CENTER));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .identification(UPDATED_IDENTIFICATION)
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .insuredNumber(UPDATED_INSURED_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .civilStatus(UPDATED_CIVIL_STATUS)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .salary(UPDATED_SALARY)
            .jornada(UPDATED_JORNADA)
            .language(UPDATED_LANGUAGE)
            .daysOfShift(UPDATED_DAYS_OF_SHIFT)
            .hoursOfShift(UPDATED_HOURS_OF_SHIFT)
            .jobCode(UPDATED_JOB_CODE)
            .costCenter(UPDATED_COST_CENTER);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getLastName2()).isEqualTo(UPDATED_LAST_NAME_2);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testEmployee.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testEmployee.getIdentificationType()).isEqualTo(UPDATED_IDENTIFICATION_TYPE);
        assertThat(testEmployee.getInsuredNumber()).isEqualTo(UPDATED_INSURED_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployee.getCivilStatus()).isEqualTo(UPDATED_CIVIL_STATUS);
        assertThat(testEmployee.getCountryOfBirth()).isEqualTo(UPDATED_COUNTRY_OF_BIRTH);
        assertThat(testEmployee.getSalary()).isEqualByComparingTo(UPDATED_SALARY);
        assertThat(testEmployee.getJornada()).isEqualTo(UPDATED_JORNADA);
        assertThat(testEmployee.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testEmployee.getDaysOfShift()).isEqualTo(UPDATED_DAYS_OF_SHIFT);
        assertThat(testEmployee.getHoursOfShift()).isEqualTo(UPDATED_HOURS_OF_SHIFT);
        assertThat(testEmployee.getJobCode()).isEqualTo(UPDATED_JOB_CODE);
        assertThat(testEmployee.getCostCenter()).isEqualTo(UPDATED_COST_CENTER);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .identification(UPDATED_IDENTIFICATION)
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .insuredNumber(UPDATED_INSURED_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getLastName2()).isEqualTo(UPDATED_LAST_NAME_2);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testEmployee.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testEmployee.getIdentificationType()).isEqualTo(UPDATED_IDENTIFICATION_TYPE);
        assertThat(testEmployee.getInsuredNumber()).isEqualTo(UPDATED_INSURED_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployee.getCivilStatus()).isEqualTo(DEFAULT_CIVIL_STATUS);
        assertThat(testEmployee.getCountryOfBirth()).isEqualTo(UPDATED_COUNTRY_OF_BIRTH);
        assertThat(testEmployee.getSalary()).isEqualByComparingTo(DEFAULT_SALARY);
        assertThat(testEmployee.getJornada()).isEqualTo(DEFAULT_JORNADA);
        assertThat(testEmployee.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testEmployee.getDaysOfShift()).isEqualTo(DEFAULT_DAYS_OF_SHIFT);
        assertThat(testEmployee.getHoursOfShift()).isEqualTo(DEFAULT_HOURS_OF_SHIFT);
        assertThat(testEmployee.getJobCode()).isEqualTo(DEFAULT_JOB_CODE);
        assertThat(testEmployee.getCostCenter()).isEqualTo(DEFAULT_COST_CENTER);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .identification(UPDATED_IDENTIFICATION)
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .insuredNumber(UPDATED_INSURED_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .civilStatus(UPDATED_CIVIL_STATUS)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .salary(UPDATED_SALARY)
            .jornada(UPDATED_JORNADA)
            .language(UPDATED_LANGUAGE)
            .daysOfShift(UPDATED_DAYS_OF_SHIFT)
            .hoursOfShift(UPDATED_HOURS_OF_SHIFT)
            .jobCode(UPDATED_JOB_CODE)
            .costCenter(UPDATED_COST_CENTER);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getLastName2()).isEqualTo(UPDATED_LAST_NAME_2);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testEmployee.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testEmployee.getIdentificationType()).isEqualTo(UPDATED_IDENTIFICATION_TYPE);
        assertThat(testEmployee.getInsuredNumber()).isEqualTo(UPDATED_INSURED_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployee.getCivilStatus()).isEqualTo(UPDATED_CIVIL_STATUS);
        assertThat(testEmployee.getCountryOfBirth()).isEqualTo(UPDATED_COUNTRY_OF_BIRTH);
        assertThat(testEmployee.getSalary()).isEqualByComparingTo(UPDATED_SALARY);
        assertThat(testEmployee.getJornada()).isEqualTo(UPDATED_JORNADA);
        assertThat(testEmployee.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testEmployee.getDaysOfShift()).isEqualTo(UPDATED_DAYS_OF_SHIFT);
        assertThat(testEmployee.getHoursOfShift()).isEqualTo(UPDATED_HOURS_OF_SHIFT);
        assertThat(testEmployee.getJobCode()).isEqualTo(UPDATED_JOB_CODE);
        assertThat(testEmployee.getCostCenter()).isEqualTo(UPDATED_COST_CENTER);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
