package com.neoproc.autoplanilla.web.rest;

import com.neoproc.autoplanilla.domain.Employee;
import com.neoproc.autoplanilla.repository.EmployeeRepository;
import com.neoproc.autoplanilla.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.neoproc.autoplanilla.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeRepository employeeRepository;

    public EmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employee the employee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employee, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);
        if (employee.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Employee result = employeeRepository.save(employee);
        return ResponseEntity
            .created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employees/:id} : Updates an existing employee.
     *
     * @param id the id of the employee to save.
     * @param employee the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Employee employee
    ) throws URISyntaxException {
        log.debug("REST request to update Employee : {}, {}", id, employee);
        if (employee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Employee result = employeeRepository.save(employee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employee.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employees/:id} : Partial updates given fields of an existing employee, field will ignore if it is null
     *
     * @param id the id of the employee to save.
     * @param employee the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 404 (Not Found)} if the employee is not found,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Employee> partialUpdateEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Employee employee
    ) throws URISyntaxException {
        log.debug("REST request to partial update Employee partially : {}, {}", id, employee);
        if (employee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Employee> result = employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getFirstName() != null) {
                    existingEmployee.setFirstName(employee.getFirstName());
                }
                if (employee.getLastName() != null) {
                    existingEmployee.setLastName(employee.getLastName());
                }
                if (employee.getLastName2() != null) {
                    existingEmployee.setLastName2(employee.getLastName2());
                }
                if (employee.getEmail() != null) {
                    existingEmployee.setEmail(employee.getEmail());
                }
                if (employee.getPhoneNumber() != null) {
                    existingEmployee.setPhoneNumber(employee.getPhoneNumber());
                }
                if (employee.getNationality() != null) {
                    existingEmployee.setNationality(employee.getNationality());
                }
                if (employee.getIdentification() != null) {
                    existingEmployee.setIdentification(employee.getIdentification());
                }
                if (employee.getIdentificationType() != null) {
                    existingEmployee.setIdentificationType(employee.getIdentificationType());
                }
                if (employee.getInsuredNumber() != null) {
                    existingEmployee.setInsuredNumber(employee.getInsuredNumber());
                }
                if (employee.getHireDate() != null) {
                    existingEmployee.setHireDate(employee.getHireDate());
                }
                if (employee.getBirthDate() != null) {
                    existingEmployee.setBirthDate(employee.getBirthDate());
                }
                if (employee.getGender() != null) {
                    existingEmployee.setGender(employee.getGender());
                }
                if (employee.getCivilStatus() != null) {
                    existingEmployee.setCivilStatus(employee.getCivilStatus());
                }
                if (employee.getCountryOfBirth() != null) {
                    existingEmployee.setCountryOfBirth(employee.getCountryOfBirth());
                }
                if (employee.getSalary() != null) {
                    existingEmployee.setSalary(employee.getSalary());
                }
                if (employee.getJornada() != null) {
                    existingEmployee.setJornada(employee.getJornada());
                }
                if (employee.getLanguage() != null) {
                    existingEmployee.setLanguage(employee.getLanguage());
                }
                if (employee.getDaysOfShift() != null) {
                    existingEmployee.setDaysOfShift(employee.getDaysOfShift());
                }
                if (employee.getHoursOfShift() != null) {
                    existingEmployee.setHoursOfShift(employee.getHoursOfShift());
                }
                if (employee.getJobCode() != null) {
                    existingEmployee.setJobCode(employee.getJobCode());
                }
                if (employee.getCostCenter() != null) {
                    existingEmployee.setCostCenter(employee.getCostCenter());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employee.getId().toString())
        );
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Employees");
        if (eagerload) {
            return employeeRepository.findAllWithEagerRelationships();
        } else {
            return employeeRepository.findAll();
        }
    }

    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<Employee> employee = employeeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(employee);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
