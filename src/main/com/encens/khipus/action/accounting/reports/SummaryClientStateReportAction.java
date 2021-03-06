package com.encens.khipus.action.accounting.reports;

import com.encens.khipus.action.accounting.VoucherUpdateAction;
import com.encens.khipus.action.reports.GenericReportAction;
import com.encens.khipus.action.reports.PageFormat;
import com.encens.khipus.action.reports.PageOrientation;
import com.encens.khipus.exception.finances.CompanyConfigurationNotFoundException;
import com.encens.khipus.model.finances.CashAccount;
import com.encens.khipus.model.finances.CompanyConfiguration;
import com.encens.khipus.model.finances.Provider;
import com.encens.khipus.service.accouting.VoucherAccoutingService;
import com.encens.khipus.service.finances.VoucherService;
import com.encens.khipus.service.fixedassets.CompanyConfigurationService;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Encens S.R.L.
 * This class implements the valued warehouse residue report action
 *
 * @author
 * @version 2.3
 */

@Name("summaryClientStateReportAction")
@Scope(ScopeType.PAGE)
public class SummaryClientStateReportAction extends GenericReportAction {

    private Date startDate;
    private Date endDate;

    private CashAccount cashAccount;
    private Provider provider;

    @In
    private CompanyConfigurationService companyConfigurationService;
    @In(create = true)
    VoucherUpdateAction voucherUpdateAction;
    @In
    private VoucherService voucherService;
    @In
    private VoucherAccoutingService voucherAccoutingService;


    @Create
    public void init() {
        restrictions = new String[]{
                //"voucherDetail.account=#{majorAccountingReportAction.cashAccount.accountCode}"
        };
        //sortProperty = "date";
    }

    @Override
    protected String getEjbql() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String start = df.format(startDate);
        String end   = df.format(endDate);

        String ejbql = "";

        ejbql = " SELECT " +
                "        client.territoriotrabajo.nombre as group, " +
                "        client.id, " +
                "        client.nitNumber, " +
                "        client.name, " +
                "        client.lastName, " +
                "        client.maidenName, " +
                "        SUM(voucherDetail.debit) as debit, " +
                "        SUM(voucherDetail.credit) as credit " +
                "  FROM  Voucher voucher " +
                "  JOIN voucher.voucherDetailList voucherDetail " +
                "  JOIN voucherDetail.client client " +
                "  WHERE voucher.date <= '" + end + "' " +
                "  AND   voucherDetail.account = '" + cashAccount.getAccountCode() + "'" +
                "  AND   voucher.state <> 'ANL' " +
                "  group by client.id, client.nitNumber, client.name, client.lastName, client.maidenName " +
                "  order by client.territoriotrabajo.nombre, client.name";

        return ejbql;
    }

    public void generateReport() {

        CompanyConfiguration companyConfiguration = null;
        try {
            companyConfiguration = companyConfigurationService.findCompanyConfiguration();
        } catch (CompanyConfigurationNotFoundException e) {e.printStackTrace();}

        String cashAccountName = this.cashAccount.getFullName();

        HashMap<String, Object> reportParameters = new HashMap<String, Object>();
        reportParameters.put("documentTitle", messages.get("SummaryClientState.report.title"));
        reportParameters.put("companyName", companyConfiguration.getCompanyName());
        reportParameters.put("systemName", companyConfiguration.getSystemName());
        reportParameters.put("locationName", companyConfiguration.getLocationName());
        reportParameters.put("startDate", startDate);
        reportParameters.put("endDate", endDate);
        reportParameters.put("cashAccount", cashAccountName);
        reportParameters.put("cashAccountCode", cashAccount.getAccountCode());

        super.generateReport(
                "summaryClientStateReport",
                "/accounting/reports/summaryClientStateReport.jrxml",
                PageFormat.LETTER,
                PageOrientation.PORTRAIT,
                messages.get("menu.finances.accounting.summaryClientState"),
                reportParameters);


    }

    public void clearAccount() {
        setCashAccount(null);
    }

    public void clearProvider() {
        setProvider(null);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CashAccount getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(CashAccount cashAccount) {
        this.cashAccount = cashAccount;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
