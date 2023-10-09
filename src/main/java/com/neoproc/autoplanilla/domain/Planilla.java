package com.neoproc.autoplanilla.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neoproc.autoplanilla.domain.enumeration.Currency;
import com.neoproc.autoplanilla.domain.enumeration.Estado;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Planilla.
 */
@Entity
@Table(name = "planilla")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Planilla implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "consecutivo_bac")
    private Integer consecutivoBAC;

    @Column(name = "plan_bac")
    private String planBAC;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "notas")
    private String notas;

    @Column(name = "aprobador")
    private String aprobador;

    @Column(name = "notificantes")
    private String notificantes;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda")
    private Currency moneda;

    @Column(name = "vacas_multi")
    private Float vacasMulti;

    @Column(name = "grupo_aprobador")
    private String grupoAprobador;

    @Column(name = "pagador")
    private String pagador;

    @Column(name = "grupo_contabilidad")
    private String grupoContabilidad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "planilla")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "planilla" }, allowSetters = true)
    private Set<Pago> pagos = new HashSet<>();

    @OneToMany(mappedBy = "planilla")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "planilla" }, allowSetters = true)
    private Set<DetallePago> detallePagos = new HashSet<>();

    @OneToMany(mappedBy = "planilla")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "company", "planilla" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_planilla__user",
        joinColumns = @JoinColumn(name = "planilla_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "employees", "planillas", "users" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Planilla id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Planilla tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getConsecutivoBAC() {
        return this.consecutivoBAC;
    }

    public Planilla consecutivoBAC(Integer consecutivoBAC) {
        this.setConsecutivoBAC(consecutivoBAC);
        return this;
    }

    public void setConsecutivoBAC(Integer consecutivoBAC) {
        this.consecutivoBAC = consecutivoBAC;
    }

    public String getPlanBAC() {
        return this.planBAC;
    }

    public Planilla planBAC(String planBAC) {
        this.setPlanBAC(planBAC);
        return this;
    }

    public void setPlanBAC(String planBAC) {
        this.planBAC = planBAC;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Planilla nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNotas() {
        return this.notas;
    }

    public Planilla notas(String notas) {
        this.setNotas(notas);
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getAprobador() {
        return this.aprobador;
    }

    public Planilla aprobador(String aprobador) {
        this.setAprobador(aprobador);
        return this;
    }

    public void setAprobador(String aprobador) {
        this.aprobador = aprobador;
    }

    public String getNotificantes() {
        return this.notificantes;
    }

    public Planilla notificantes(String notificantes) {
        this.setNotificantes(notificantes);
        return this;
    }

    public void setNotificantes(String notificantes) {
        this.notificantes = notificantes;
    }

    public Currency getMoneda() {
        return this.moneda;
    }

    public Planilla moneda(Currency moneda) {
        this.setMoneda(moneda);
        return this;
    }

    public void setMoneda(Currency moneda) {
        this.moneda = moneda;
    }

    public Float getVacasMulti() {
        return this.vacasMulti;
    }

    public Planilla vacasMulti(Float vacasMulti) {
        this.setVacasMulti(vacasMulti);
        return this;
    }

    public void setVacasMulti(Float vacasMulti) {
        this.vacasMulti = vacasMulti;
    }

    public String getGrupoAprobador() {
        return this.grupoAprobador;
    }

    public Planilla grupoAprobador(String grupoAprobador) {
        this.setGrupoAprobador(grupoAprobador);
        return this;
    }

    public void setGrupoAprobador(String grupoAprobador) {
        this.grupoAprobador = grupoAprobador;
    }

    public String getPagador() {
        return this.pagador;
    }

    public Planilla pagador(String pagador) {
        this.setPagador(pagador);
        return this;
    }

    public void setPagador(String pagador) {
        this.pagador = pagador;
    }

    public String getGrupoContabilidad() {
        return this.grupoContabilidad;
    }

    public Planilla grupoContabilidad(String grupoContabilidad) {
        this.setGrupoContabilidad(grupoContabilidad);
        return this;
    }

    public void setGrupoContabilidad(String grupoContabilidad) {
        this.grupoContabilidad = grupoContabilidad;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public Planilla estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<Pago> getPagos() {
        return this.pagos;
    }

    public void setPagos(Set<Pago> pagos) {
        if (this.pagos != null) {
            this.pagos.forEach(i -> i.setPlanilla(null));
        }
        if (pagos != null) {
            pagos.forEach(i -> i.setPlanilla(this));
        }
        this.pagos = pagos;
    }

    public Planilla pagos(Set<Pago> pagos) {
        this.setPagos(pagos);
        return this;
    }

    public Planilla addPago(Pago pago) {
        this.pagos.add(pago);
        pago.setPlanilla(this);
        return this;
    }

    public Planilla removePago(Pago pago) {
        this.pagos.remove(pago);
        pago.setPlanilla(null);
        return this;
    }

    public Set<DetallePago> getDetallePagos() {
        return this.detallePagos;
    }

    public void setDetallePagos(Set<DetallePago> detallePagos) {
        if (this.detallePagos != null) {
            this.detallePagos.forEach(i -> i.setPlanilla(null));
        }
        if (detallePagos != null) {
            detallePagos.forEach(i -> i.setPlanilla(this));
        }
        this.detallePagos = detallePagos;
    }

    public Planilla detallePagos(Set<DetallePago> detallePagos) {
        this.setDetallePagos(detallePagos);
        return this;
    }

    public Planilla addDetallePago(DetallePago detallePago) {
        this.detallePagos.add(detallePago);
        detallePago.setPlanilla(this);
        return this;
    }

    public Planilla removeDetallePago(DetallePago detallePago) {
        this.detallePagos.remove(detallePago);
        detallePago.setPlanilla(null);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setPlanilla(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setPlanilla(this));
        }
        this.employees = employees;
    }

    public Planilla employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Planilla addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setPlanilla(this);
        return this;
    }

    public Planilla removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setPlanilla(null);
        return this;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Planilla users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Planilla addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Planilla removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Planilla company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Planilla)) {
            return false;
        }
        return id != null && id.equals(((Planilla) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Planilla{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", consecutivoBAC=" + getConsecutivoBAC() +
            ", planBAC='" + getPlanBAC() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", notas='" + getNotas() + "'" +
            ", aprobador='" + getAprobador() + "'" +
            ", notificantes='" + getNotificantes() + "'" +
            ", moneda='" + getMoneda() + "'" +
            ", vacasMulti=" + getVacasMulti() +
            ", grupoAprobador='" + getGrupoAprobador() + "'" +
            ", pagador='" + getPagador() + "'" +
            ", grupoContabilidad='" + getGrupoContabilidad() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
