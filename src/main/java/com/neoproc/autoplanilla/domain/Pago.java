package com.neoproc.autoplanilla.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neoproc.autoplanilla.domain.enumeration.EstadoPago;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pago.
 */
@Entity
@Table(name = "pago")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userID;

    @NotNull
    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "sequence_bank")
    private Integer sequenceBank;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "salario_bruto", precision = 21, scale = 2, nullable = false)
    private BigDecimal salarioBruto;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "adicionales", precision = 21, scale = 2, nullable = false)
    private BigDecimal adicionales;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "deducciones", precision = 21, scale = 2, nullable = false)
    private BigDecimal deducciones;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "otrasdeducciones", precision = 21, scale = 2, nullable = false)
    private BigDecimal otrasdeducciones;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "social_security", precision = 21, scale = 2, nullable = false)
    private BigDecimal socialSecurity;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "renta", precision = 21, scale = 2, nullable = false)
    private BigDecimal renta;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "pago_neto", precision = 21, scale = 2, nullable = false)
    private BigDecimal pagoNeto;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "adicionalesnodeducibles", precision = 21, scale = 2, nullable = false)
    private BigDecimal adicionalesnodeducibles;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EstadoPago status;

    @Column(name = "process_id")
    private String processID;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pagos", "detallePagos", "employees", "users", "company" }, allowSetters = true)
    private Planilla planilla;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pago id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return this.userID;
    }

    public Pago userID(String userID) {
        this.setUserID(userID);
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCompany() {
        return this.company;
    }

    public Pago company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getSequenceBank() {
        return this.sequenceBank;
    }

    public Pago sequenceBank(Integer sequenceBank) {
        this.setSequenceBank(sequenceBank);
        return this;
    }

    public void setSequenceBank(Integer sequenceBank) {
        this.sequenceBank = sequenceBank;
    }

    public BigDecimal getSalarioBruto() {
        return this.salarioBruto;
    }

    public Pago salarioBruto(BigDecimal salarioBruto) {
        this.setSalarioBruto(salarioBruto);
        return this;
    }

    public void setSalarioBruto(BigDecimal salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public BigDecimal getAdicionales() {
        return this.adicionales;
    }

    public Pago adicionales(BigDecimal adicionales) {
        this.setAdicionales(adicionales);
        return this;
    }

    public void setAdicionales(BigDecimal adicionales) {
        this.adicionales = adicionales;
    }

    public BigDecimal getDeducciones() {
        return this.deducciones;
    }

    public Pago deducciones(BigDecimal deducciones) {
        this.setDeducciones(deducciones);
        return this;
    }

    public void setDeducciones(BigDecimal deducciones) {
        this.deducciones = deducciones;
    }

    public BigDecimal getOtrasdeducciones() {
        return this.otrasdeducciones;
    }

    public Pago otrasdeducciones(BigDecimal otrasdeducciones) {
        this.setOtrasdeducciones(otrasdeducciones);
        return this;
    }

    public void setOtrasdeducciones(BigDecimal otrasdeducciones) {
        this.otrasdeducciones = otrasdeducciones;
    }

    public BigDecimal getSocialSecurity() {
        return this.socialSecurity;
    }

    public Pago socialSecurity(BigDecimal socialSecurity) {
        this.setSocialSecurity(socialSecurity);
        return this;
    }

    public void setSocialSecurity(BigDecimal socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public BigDecimal getRenta() {
        return this.renta;
    }

    public Pago renta(BigDecimal renta) {
        this.setRenta(renta);
        return this;
    }

    public void setRenta(BigDecimal renta) {
        this.renta = renta;
    }

    public BigDecimal getPagoNeto() {
        return this.pagoNeto;
    }

    public Pago pagoNeto(BigDecimal pagoNeto) {
        this.setPagoNeto(pagoNeto);
        return this;
    }

    public void setPagoNeto(BigDecimal pagoNeto) {
        this.pagoNeto = pagoNeto;
    }

    public BigDecimal getAdicionalesnodeducibles() {
        return this.adicionalesnodeducibles;
    }

    public Pago adicionalesnodeducibles(BigDecimal adicionalesnodeducibles) {
        this.setAdicionalesnodeducibles(adicionalesnodeducibles);
        return this;
    }

    public void setAdicionalesnodeducibles(BigDecimal adicionalesnodeducibles) {
        this.adicionalesnodeducibles = adicionalesnodeducibles;
    }

    public String getType() {
        return this.type;
    }

    public Pago type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EstadoPago getStatus() {
        return this.status;
    }

    public Pago status(EstadoPago status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(EstadoPago status) {
        this.status = status;
    }

    public String getProcessID() {
        return this.processID;
    }

    public Pago processID(String processID) {
        this.setProcessID(processID);
        return this;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public Planilla getPlanilla() {
        return this.planilla;
    }

    public void setPlanilla(Planilla planilla) {
        this.planilla = planilla;
    }

    public Pago planilla(Planilla planilla) {
        this.setPlanilla(planilla);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pago)) {
            return false;
        }
        return id != null && id.equals(((Pago) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pago{" +
            "id=" + getId() +
            ", userID='" + getUserID() + "'" +
            ", company='" + getCompany() + "'" +
            ", sequenceBank=" + getSequenceBank() +
            ", salarioBruto=" + getSalarioBruto() +
            ", adicionales=" + getAdicionales() +
            ", deducciones=" + getDeducciones() +
            ", otrasdeducciones=" + getOtrasdeducciones() +
            ", socialSecurity=" + getSocialSecurity() +
            ", renta=" + getRenta() +
            ", pagoNeto=" + getPagoNeto() +
            ", adicionalesnodeducibles=" + getAdicionalesnodeducibles() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", processID='" + getProcessID() + "'" +
            "}";
    }
}
