package com.neoproc.autoplanilla.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neoproc.autoplanilla.domain.enumeration.Gender;
import com.neoproc.autoplanilla.domain.enumeration.IdentificationType;
import com.neoproc.autoplanilla.domain.enumeration.JornadaType;
import com.neoproc.autoplanilla.domain.enumeration.Language;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "last_name_2")
    private String lastName2;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "identification", nullable = false)
    private String identification;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identification_type", nullable = false)
    private IdentificationType identificationType;

    @Column(name = "insured_number")
    private String insuredNumber;

    @NotNull
    @Column(name = "hire_date", nullable = false)
    private Instant hireDate;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "civil_status")
    private String civilStatus;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "salary", precision = 21, scale = 2, nullable = false)
    private BigDecimal salary;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jornada", nullable = false)
    private JornadaType jornada;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Min(value = 0)
    @Column(name = "days_of_shift")
    private Integer daysOfShift;

    @Min(value = 0)
    @Column(name = "hours_of_shift")
    private Integer hoursOfShift;

    @Column(name = "job_code")
    private Integer jobCode;

    @Column(name = "cost_center")
    private String costCenter;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees", "planillas", "users" }, allowSetters = true)
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pagos", "detallePagos", "employees", "users", "company" }, allowSetters = true)
    private Planilla planilla;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName2() {
        return this.lastName2;
    }

    public Employee lastName2(String lastName2) {
        this.setLastName2(lastName2);
        return this;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Employee nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdentification() {
        return this.identification;
    }

    public Employee identification(String identification) {
        this.setIdentification(identification);
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public IdentificationType getIdentificationType() {
        return this.identificationType;
    }

    public Employee identificationType(IdentificationType identificationType) {
        this.setIdentificationType(identificationType);
        return this;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getInsuredNumber() {
        return this.insuredNumber;
    }

    public Employee insuredNumber(String insuredNumber) {
        this.setInsuredNumber(insuredNumber);
        return this;
    }

    public void setInsuredNumber(String insuredNumber) {
        this.insuredNumber = insuredNumber;
    }

    public Instant getHireDate() {
        return this.hireDate;
    }

    public Employee hireDate(Instant hireDate) {
        this.setHireDate(hireDate);
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Instant getBirthDate() {
        return this.birthDate;
    }

    public Employee birthDate(Instant birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Employee gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCivilStatus() {
        return this.civilStatus;
    }

    public Employee civilStatus(String civilStatus) {
        this.setCivilStatus(civilStatus);
        return this;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getCountryOfBirth() {
        return this.countryOfBirth;
    }

    public Employee countryOfBirth(String countryOfBirth) {
        this.setCountryOfBirth(countryOfBirth);
        return this;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public Employee salary(BigDecimal salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public JornadaType getJornada() {
        return this.jornada;
    }

    public Employee jornada(JornadaType jornada) {
        this.setJornada(jornada);
        return this;
    }

    public void setJornada(JornadaType jornada) {
        this.jornada = jornada;
    }

    public Language getLanguage() {
        return this.language;
    }

    public Employee language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getDaysOfShift() {
        return this.daysOfShift;
    }

    public Employee daysOfShift(Integer daysOfShift) {
        this.setDaysOfShift(daysOfShift);
        return this;
    }

    public void setDaysOfShift(Integer daysOfShift) {
        this.daysOfShift = daysOfShift;
    }

    public Integer getHoursOfShift() {
        return this.hoursOfShift;
    }

    public Employee hoursOfShift(Integer hoursOfShift) {
        this.setHoursOfShift(hoursOfShift);
        return this;
    }

    public void setHoursOfShift(Integer hoursOfShift) {
        this.hoursOfShift = hoursOfShift;
    }

    public Integer getJobCode() {
        return this.jobCode;
    }

    public Employee jobCode(Integer jobCode) {
        this.setJobCode(jobCode);
        return this;
    }

    public void setJobCode(Integer jobCode) {
        this.jobCode = jobCode;
    }

    public String getCostCenter() {
        return this.costCenter;
    }

    public Employee costCenter(String costCenter) {
        this.setCostCenter(costCenter);
        return this;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee company(Company company) {
        this.setCompany(company);
        return this;
    }

    public Planilla getPlanilla() {
        return this.planilla;
    }

    public void setPlanilla(Planilla planilla) {
        this.planilla = planilla;
    }

    public Employee planilla(Planilla planilla) {
        this.setPlanilla(planilla);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", lastName2='" + getLastName2() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", identification='" + getIdentification() + "'" +
            ", identificationType='" + getIdentificationType() + "'" +
            ", insuredNumber='" + getInsuredNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", civilStatus='" + getCivilStatus() + "'" +
            ", countryOfBirth='" + getCountryOfBirth() + "'" +
            ", salary=" + getSalary() +
            ", jornada='" + getJornada() + "'" +
            ", language='" + getLanguage() + "'" +
            ", daysOfShift=" + getDaysOfShift() +
            ", hoursOfShift=" + getHoursOfShift() +
            ", jobCode=" + getJobCode() +
            ", costCenter='" + getCostCenter() + "'" +
            "}";
    }
}
