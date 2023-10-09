package com.neoproc.autoplanilla.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neoproc.autoplanilla.domain.enumeration.Estado;
import com.neoproc.autoplanilla.domain.enumeration.TipoCedulaCompany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_identificacion", nullable = false)
    private TipoCedulaCompany tipoIdentificacion;

    @NotNull
    @Column(name = "identificacion", nullable = false, unique = true)
    private String identificacion;

    @NotNull
    @Column(name = "correo_electronico", nullable = false)
    private String correoElectronico;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "canton")
    private String canton;

    @Column(name = "distrito")
    private String distrito;

    @Column(name = "barrio")
    private String barrio;

    @Column(name = "sennas")
    private String sennas;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "company", "planilla" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pagos", "detallePagos", "employees", "users", "company" }, allowSetters = true)
    private Set<Planilla> planillas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_company__user",
        joinColumns = @JoinColumn(name = "company_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Company nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCedulaCompany getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public Company tipoIdentificacion(TipoCedulaCompany tipoIdentificacion) {
        this.setTipoIdentificacion(tipoIdentificacion);
        return this;
    }

    public void setTipoIdentificacion(TipoCedulaCompany tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return this.identificacion;
    }

    public Company identificacion(String identificacion) {
        this.setIdentificacion(identificacion);
        return this;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public Company correoElectronico(String correoElectronico) {
        this.setCorreoElectronico(correoElectronico);
        return this;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreComercial() {
        return this.nombreComercial;
    }

    public Company nombreComercial(String nombreComercial) {
        this.setNombreComercial(nombreComercial);
        return this;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Integer getTelefono() {
        return this.telefono;
    }

    public Company telefono(Integer telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Company provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return this.canton;
    }

    public Company canton(String canton) {
        this.setCanton(canton);
        return this;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return this.distrito;
    }

    public Company distrito(String distrito) {
        this.setDistrito(distrito);
        return this;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getBarrio() {
        return this.barrio;
    }

    public Company barrio(String barrio) {
        this.setBarrio(barrio);
        return this;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getSennas() {
        return this.sennas;
    }

    public Company sennas(String sennas) {
        this.setSennas(sennas);
        return this;
    }

    public void setSennas(String sennas) {
        this.sennas = sennas;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public Company estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getWebsite() {
        return this.website;
    }

    public Company website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setCompany(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setCompany(this));
        }
        this.employees = employees;
    }

    public Company employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Company addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setCompany(this);
        return this;
    }

    public Company removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setCompany(null);
        return this;
    }

    public Set<Planilla> getPlanillas() {
        return this.planillas;
    }

    public void setPlanillas(Set<Planilla> planillas) {
        if (this.planillas != null) {
            this.planillas.forEach(i -> i.setCompany(null));
        }
        if (planillas != null) {
            planillas.forEach(i -> i.setCompany(this));
        }
        this.planillas = planillas;
    }

    public Company planillas(Set<Planilla> planillas) {
        this.setPlanillas(planillas);
        return this;
    }

    public Company addPlanilla(Planilla planilla) {
        this.planillas.add(planilla);
        planilla.setCompany(this);
        return this;
    }

    public Company removePlanilla(Planilla planilla) {
        this.planillas.remove(planilla);
        planilla.setCompany(null);
        return this;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Company users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Company addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Company removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", nombreComercial='" + getNombreComercial() + "'" +
            ", telefono=" + getTelefono() +
            ", provincia='" + getProvincia() + "'" +
            ", canton='" + getCanton() + "'" +
            ", distrito='" + getDistrito() + "'" +
            ", barrio='" + getBarrio() + "'" +
            ", sennas='" + getSennas() + "'" +
            ", estado='" + getEstado() + "'" +
            ", website='" + getWebsite() + "'" +
            "}";
    }
}
