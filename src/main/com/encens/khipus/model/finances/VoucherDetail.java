package com.encens.khipus.model.finances;

import com.encens.khipus.model.BaseModel;
import com.encens.khipus.model.UpperCaseStringListener;
import com.encens.khipus.model.customers.Account;
import com.encens.khipus.model.customers.Client;
import com.encens.khipus.model.customers.Credit;
import com.encens.khipus.model.customers.Partner;
import com.encens.khipus.model.purchases.PurchaseDocument;
import com.encens.khipus.model.warehouse.ProductItem;
import com.encens.khipus.util.Constants;
import com.encens.khipus.util.MessageUtils;
import org.hibernate.validator.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity for VoucherDetail...
 *
 * @author
 * @version 1.4
 */
@TableGenerator(schema = com.encens.khipus.util.Constants.KHIPUS_SCHEMA, name = "VoucherDetail.tableGenerator",
        table = com.encens.khipus.util.Constants.SEQUENCE_TABLE_NAME,
        pkColumnName = com.encens.khipus.util.Constants.SEQUENCE_TABLE_PK_COLUMN_NAME,
        valueColumnName = com.encens.khipus.util.Constants.SEQUENCE_TABLE_VALUE_COLUMN_NAME,
        pkColumnValue = "sf_tmpdet",
        initialValue = 1,
        allocationSize = 1)
@Entity
@EntityListeners(UpperCaseStringListener.class)
@Table(name = "sf_tmpdet", schema = Constants.FINANCES_SCHEMA)
public class VoucherDetail implements BaseModel {

    /*@GeneratedValue(strategy = GenerationType.TABLE, generator = "VoucherDetail.tableGenerator")*/
    @Id
    @Column(name = "ID_TMPDET", nullable = true)
    private Long id;

    //Fake ID, just to avoid duplicated in the EntityManager
    @Column(name = "TIMEMILLIS", insertable = false, updatable = true)
    private String idTime;

    @Column(name = "NO_TRANS", nullable = true, insertable = true, updatable = true, length = 10)
    @Length(max = 10)
    private String transactionNumber;

    @Column(name = "NO_CIA", updatable = false, length = 2)
    @Length(max = 2)
    private String companyNumber = "01";

    @Column(name = "COD_UNI", updatable = true)
    private String businessUnitCode;

    @Column(name = "COD_CC", updatable = true)
    private String costCenterCode;

    @Column(name = "CUENTA", updatable = false)
    @Length(max = 31)
    private String account;

    @Column(name = "DEBE", precision = 16, scale = 2, updatable = true)
    private BigDecimal debit;

    @Column(name = "HABER", precision = 16, scale = 2, updatable = true)
    private BigDecimal credit;

    @Column(name = "DEBEME", precision = 16, scale = 2, updatable = true)
    private BigDecimal debitMe;

    @Column(name = "HABERME", precision = 16, scale = 2, updatable = true)
    private BigDecimal creditMe;

    @Column(name = "MONEDA", updatable = false)
    @Enumerated(EnumType.STRING)
    private FinancesCurrencyType currency = FinancesCurrencyType.P;

    @Column(name = "TC", precision = 10, scale = 6, updatable = true)
    private BigDecimal exchangeAmount;

    @Column(name = "GLOSA", updatable = true, length = 1000)
    @Length(max = 1000)
    private String gloss;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tmpenc", nullable = false)
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            //@JoinColumn(name = "NO_CIA", referencedColumnName = "NO_CIA", nullable = false, updatable = false, insertable = false),
            @JoinColumn(name = "CUENTA", referencedColumnName = "CUENTA", nullable = false, updatable = false, insertable = false)
    })
    private CashAccount cashAccount;


    @Transient
    private String payableAccountFullName;

    @Transient
    private String clientFullName;

    @Transient
    private String fullCashAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPERSONACLIENTE", referencedColumnName = "IDPERSONACLIENTE")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "NO_CIA", updatable = false, insertable = false),
            @JoinColumn(name = "COD_PROV", updatable = false, insertable = false)
    })
    private Provider provider;

    @Column(name = "COD_PROV", length = 6)
    @Length(max = 6)
    private String providerCode;

    @Column(name = "CANT_ART", nullable = true)
    private BigDecimal quantityArt;

    @Column(name = "COD_ART", length = 6)
    @Length(max = 6)
    private String productItemCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "NO_CIA", updatable = false, insertable = false),
            @JoinColumn(name = "COD_ART", updatable = false, insertable = false)
    })
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "IDCUENTA", referencedColumnName = "IDCUENTA")
    private Account partnerAccount;

    @ManyToOne
    @JoinColumn(name = "IDSOCIO", referencedColumnName = "IDSOCIO")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "IDCREDITO", referencedColumnName = "IDCREDITO")
    private Credit creditPartner;


    @ManyToOne
    @JoinColumn(name = "IDDOCUMENTOCOMPRA", referencedColumnName = "IDDOCUMENTOCOMPRA")
    private PurchaseDocument purchaseDocument;

    public VoucherDetail(String businessUnitCode, String costCenterCode, String account,
                         BigDecimal debit, BigDecimal credit, FinancesCurrencyType currency, BigDecimal exchangeAmount) {
        this.businessUnitCode = businessUnitCode;
        this.costCenterCode = costCenterCode;
        this.account = account;
        this.debit = debit;
        this.credit = credit;
        this.currency = currency;
        this.exchangeAmount = exchangeAmount;
    }

    public VoucherDetail(String businessUnitCode, String costCenterCode, String account,
                         BigDecimal debit, BigDecimal credit, FinancesCurrencyType currency, BigDecimal exchangeAmount, String productItemCode, BigDecimal quantityArt) {
        this.businessUnitCode = businessUnitCode;
        this.costCenterCode = costCenterCode;
        this.account = account;
        this.debit = debit;
        this.credit = credit;
        this.currency = currency;
        this.exchangeAmount = exchangeAmount;
        this.productItemCode = productItemCode;
        this.setQuantityArt(quantityArt);
    }

    public VoucherDetail(String account, BigDecimal debit, BigDecimal credit, FinancesCurrencyType currency, BigDecimal exchangeAmount, String productItemCode, BigDecimal quantityArt) {
        this.account = account;
        this.debit = debit;
        this.credit = credit;
        this.currency = currency;
        this.exchangeAmount = exchangeAmount;
        this.productItemCode = productItemCode;
        this.setQuantityArt(quantityArt);
    }

    public VoucherDetail() {
    }

    @PrePersist
    private void defineFakeIdentifier() {
        idTime = UUID.randomUUID().toString();
    }

    public String getIdTime() {
        return idTime;
    }

    public void setIdTime(String idTime) {
        this.idTime = idTime;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public FinancesCurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(FinancesCurrencyType currency) {
        this.currency = currency;
    }

    public BigDecimal getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(BigDecimal exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public CashAccount getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(CashAccount cashAccount) {
        this.cashAccount = cashAccount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public Client getClient() {
        System.out.println("Get Cliente: " + client);
        return client;
    }

    public void setClient(Client client) {
        System.out.println("Set Cliente: " + client);
        this.client = client;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getAuxiliaryClient(){
        String result = "";

        if (client != null)
            result = client.getName();

        return  result;
    }

    public String getFullCashAccount(){

        fullCashAccount = this.cashAccount.getFullName();

        if (client != null)
            fullCashAccount = fullCashAccount + " (" + client.getFullName() + ")";
        if (provider != null)
            fullCashAccount = fullCashAccount + " (" +  provider.getFullName() + ")";
        if (productItem != null) {
            fullCashAccount = fullCashAccount + " (" + productItem.getFullName() + ")";

            if (getQuantityArt() != null) {
                String q = getQuantityArt().doubleValue() > 0 ? getQuantityArt().toString() : " ";
                fullCashAccount = fullCashAccount + " (" + q + ")";
            }
        }

        if (partnerAccount != null)
            fullCashAccount = fullCashAccount + " (" +  partnerAccount.getAccountNumber() + " " + partnerAccount.getPartner().getFirstAndMaidenName() + ")";
        if (purchaseDocument != null)
            fullCashAccount = fullCashAccount + " (F." + purchaseDocument.getNumber() + ")";
        if (creditPartner != null)
            fullCashAccount = fullCashAccount + " (" + creditPartner.getPartner().getFullName() + ")";
        if (partner != null)
            fullCashAccount = fullCashAccount + " (" + partner.getFullName() + ")";



        System.out.println("=======> getDebit(): " + getDebit());
        System.out.println("=======> getCredit(): " + getCredit());
        System.out.println("=======> getDebitMe(): " + getDebitMe());
        System.out.println("=======> getCreditMe(): " + getCreditMe());


        if (cashAccount.getCurrency().equals(FinancesCurrencyType.D)){
            if (getDebitMe().doubleValue() > 0)
                fullCashAccount = fullCashAccount + " (" + getDebitMe() + " " + MessageUtils.getMessage(cashAccount.getCurrency().getSymbolResourceKey()) + ")";
            if (getCreditMe().doubleValue() > 0)
                fullCashAccount = fullCashAccount + " (" + getCreditMe() + " " + MessageUtils.getMessage(cashAccount.getCurrency().getSymbolResourceKey()) + ")";
        }

        return fullCashAccount;
    }

    public String getPayableAccountFullName() {
        if (payableAccountFullName == null && getAccount() != null) {
            payableAccountFullName = getCashAccount().getFullName();
        }
        return payableAccountFullName;
    }

    public void setPayableAccountFullName(String payableAccountFullName) {
        this.payableAccountFullName = payableAccountFullName;
    }

    public String getClientFullName() {
        if (clientFullName == null && getClient() != null) {
            clientFullName = getClient().getFullName();
        }
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public String getProductItemCode() {
        return productItemCode;
    }

    public void setProductItemCode(String productItemCode) {
        this.productItemCode = productItemCode;
    }

    public Account getPartnerAccount() {
        return partnerAccount;
    }

    public void setPartnerAccount(Account partnerAccount) {
        this.partnerAccount = partnerAccount;
    }

    public PurchaseDocument getPurchaseDocument() {
        return purchaseDocument;
    }

    public void setPurchaseDocument(PurchaseDocument purchaseDocument) {
        this.purchaseDocument = purchaseDocument;
    }

    public Credit getCreditPartner() {
        return creditPartner;
    }

    public void setCreditPartner(Credit creditPartner) {
        this.creditPartner = creditPartner;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public BigDecimal getDebitMe() {
        return debitMe;
    }

    public void setDebitMe(BigDecimal debitMe) {
        this.debitMe = debitMe;
    }

    public BigDecimal getCreditMe() {
        return creditMe;
    }

    public void setCreditMe(BigDecimal creditMe) {
        this.creditMe = creditMe;
    }

    public BigDecimal getQuantityArt() {
        return quantityArt;
    }

    public void setQuantityArt(BigDecimal quantityArt) {
        this.quantityArt = quantityArt;
    }
}
