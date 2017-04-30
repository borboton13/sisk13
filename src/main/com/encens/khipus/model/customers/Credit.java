package com.encens.khipus.model.customers;

import com.encens.khipus.model.BaseModel;
import com.encens.khipus.model.CompanyListener;
import com.encens.khipus.model.admin.Company;
import com.encens.khipus.model.contacts.Entity;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity for credits
 *
 * @author:
 */

@NamedQueries(
        {
                //@NamedQuery(name = "Credit.findByEntity", query = "select c from Credit c where  c.entity =:entity")
        }
)
@TableGenerator(schema = com.encens.khipus.util.Constants.KHIPUS_SCHEMA, name = "Credit.tableGenerator",
        table = com.encens.khipus.util.Constants.SEQUENCE_TABLE_NAME,
        pkColumnName = com.encens.khipus.util.Constants.SEQUENCE_TABLE_PK_COLUMN_NAME,
        valueColumnName = com.encens.khipus.util.Constants.SEQUENCE_TABLE_VALUE_COLUMN_NAME,
        pkColumnValue = "credito",
        allocationSize = com.encens.khipus.util.Constants.SEQUENCE_ALLOCATION_SIZE)

@javax.persistence.Entity
@Filter(name = com.encens.khipus.util.Constants.COMPANY_FILTER_NAME)
@EntityListeners(CompanyListener.class)
@Table(schema = com.encens.khipus.util.Constants.KHIPUS_SCHEMA, name = "credito")
public class Credit implements BaseModel {

    @Id
    @Column(name = "idcredito", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Credit.tableGenerator")
    private Long id;

    @Column(name = "estado", nullable = true, length = 3)
    @Enumerated(EnumType.STRING)
    private CreditState state;

    @Column(name = "codigo", nullable = false, length = 25)
    @Length(max = 25)
    private String code;

    @Column(name = "importe", precision = 13, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "anual", nullable = false)
    private Integer annualRate;

    @Column(name = "plazo", nullable = false)
    private Integer term;

    @Column(name = "amortiza", nullable = false)
    private Integer amortization;

    @Column(name = "fechaconcesion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date grantDate;

    @Column(name = "fechapago", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date firstPayment;

    @Column(name = "cuota", precision = 13, scale = 2, nullable = false)
    private BigDecimal quota;

    @Column(name = "saldo", precision = 13, scale = 2, nullable = false)
    private BigDecimal capitalBalance;

    @Column(name = "fechacreacion", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(name = "entregado", nullable = false)
    @Type(type = com.encens.khipus.model.usertype.IntegerBooleanUserType.NAME)
    private Boolean delivered = Boolean.FALSE;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idcompania", nullable = false, updatable = false, insertable = true)
    @NotNull
    private Company company;

    /*@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "identidad", nullable = true)
    private Entity entity;*/

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "idsocio", nullable = false)
    private Partner partner;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /*public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }*/

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Integer getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(Integer annualRate) {
        this.annualRate = annualRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getAmortization() {
        return amortization;
    }

    public void setAmortization(Integer amortization) {
        this.amortization = amortization;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    public Date getFirstPayment() {
        return firstPayment;
    }

    public void setFirstPayment(Date firstPayment) {
        this.firstPayment = firstPayment;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public CreditState getState() {
        return state;
    }

    public void setState(CreditState state) {
        this.state = state;
    }

    public BigDecimal getCapitalBalance() {
        return capitalBalance;
    }

    public void setCapitalBalance(BigDecimal capitalBalance) {
        this.capitalBalance = capitalBalance;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }
}