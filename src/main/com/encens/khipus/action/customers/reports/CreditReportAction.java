package com.encens.khipus.action.customers.reports;

import com.encens.khipus.action.reports.GenericReportAction;
import com.encens.khipus.model.admin.User;
import com.encens.khipus.model.customers.Credit;
import com.encens.khipus.util.BigDecimalUtil;
import com.encens.khipus.util.DateUtils;
import com.encens.khipus.util.JSFUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Encens S.R.L.
 * This class implements the purchaseOrder report action
 *
 * @author
 * @version 3.0
 */
@Name("creditReportAction")
@Scope(ScopeType.PAGE)
public class CreditReportAction extends GenericReportAction {

    @In
    private User currentUser;

    @Create
    public void init() {
        restrictions = new String[]{};
    }


    protected String getEjbql() {
        return "";
    }

    public void generateReport(Credit credit) {

        log.debug("generating Payment Plan......................................id: " + credit.getCode());
        BigDecimal quotas =  BigDecimalUtil.divide(credit.getAmount(), credit.getQuota(), 0);
        System.out.println(".....Numero de cuotas: " + quotas);
        BigDecimal saldoCapital = credit.getAmount();

        Date fechaUltimoPago = credit.getGrantDate();
        Date fechaPago       = credit.getFirstPayment();

        Collection<PaymentPlanData> beanCollection = new ArrayList();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

        for(int q=1; q<=quotas.intValue(); q++){

            String fecha = sdf.format(fechaPago);
            Long dias = DateUtils.daysBetween(fechaUltimoPago, fechaPago) - 1;
            BigDecimal var_interes = BigDecimalUtil.divide(BigDecimalUtil.toBigDecimal(credit.getAnnualRate()), BigDecimalUtil.toBigDecimal(100), 6);
            BigDecimal var_tiempo = BigDecimalUtil.divide(BigDecimalUtil.toBigDecimal(dias.toString()), BigDecimalUtil.toBigDecimal(360), 6);
            BigDecimal interes = BigDecimalUtil.multiply(saldoCapital, var_interes, 6);
            interes = BigDecimalUtil.multiply(interes, var_tiempo, 6);
            BigDecimal totalCuota = BigDecimalUtil.sum(credit.getQuota(), interes, 6);

            System.out.println(q + "\t\t" + sdf.format(fechaUltimoPago) + "\t\t" + fecha + "\t\t" + dias + "\t\t" + credit.getQuota() + "\t\t" + interes + "\t\t" + totalCuota + "\t\t" + saldoCapital);
            PaymentPlanData paymentPlanData = new PaymentPlanData(q, saldoCapital, credit.getQuota(), interes, totalCuota, fecha);
            beanCollection.add(paymentPlanData);

            saldoCapital = BigDecimalUtil.subtract(saldoCapital, credit.getQuota(), 6);
            fechaUltimoPago = fechaPago;

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaPago);
            //cal.add(Calendar.DATE, 60);
            cal.add(Calendar.MONTH, credit.getAmortization()/30);
            fechaPago = cal.getTime();
        }
        /** **/

        String partnerName = credit.getPartner().getName();
        String partnerCode = credit.getPartner().getNumber();
        String term = credit.getTerm().toString() + " MESES";
        String amortiza = "C/ " + credit.getAmortization().toString() + " DIAS";

        DecimalFormat df = new DecimalFormat("#,###.00");
        String amount = df.format(credit.getAmount());
        String interest = credit.getAnnualRate().toString() + " %";
        String grantDate = sdf.format(credit.getGrantDate());

        HashMap parameters = new HashMap();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reportTitle", "PLAN DE PAGOS");
        paramMap.put("partnerName", partnerName);
        paramMap.put("partnercode", partnerCode);
        paramMap.put("term", term);
        paramMap.put("amortiza", amortiza);
        paramMap.put("amount", amount);
        paramMap.put("interest", interest);
        paramMap.put("grantDate", grantDate);
        parameters.putAll(paramMap);

        try{
            File jasper = new File(JSFUtil.getRealPath("/customers/reports/paymentPlan.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, new JRBeanCollectionDataSource(beanCollection));
            exportarPDF(jasperPrint);

        }catch (Exception e){}

        /** **/
        /*try{
        Collection beanCollection = new ArrayList<String>();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanCollection);
        JasperPrint jasperPrint = JasperFillManager.fillReport("D:/paymentPlan.jasper", new HashMap(), beanCollectionDataSource);

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; file-name=plan_pagos.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();

        }catch (Exception e){}*/
            /** *************************** **/

        /*Collection<String> collection = new ArrayList<String>();

        TypedReportData typedReportData;
        String templatePath = "/customers/reports/paymentPlan.jrxml";
        String fileName = "payment_plan";

        Map params = new HashMap();
        params.putAll(getCommonDocumentParamsInfo());

        String query = "select idcredito from credito";

        typedReportData = getReport(
                fileName
                , templatePath
                , query
                , params
                , "RECEPCION_DE_PEDIDOS"
        );
        JasperPrint jasperPrint = typedReportData.getJasperPrint();

        try {
            typedReportData.setJasperPrint(jasperPrint);
            GenerationReportData generationReportData = new GenerationReportData(typedReportData);
            generationReportData.exportReport();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public void exportarPDF(JasperPrint jasperPrint) throws IOException, JRException {

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=REPORT.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }


    public class PaymentPlanData{

        private Integer nro;
        private BigDecimal capitalBalance;
        private BigDecimal quota;
        private BigDecimal interest;
        private BigDecimal totalQuota;
        private String paymentDate;


        public PaymentPlanData(Integer nro, BigDecimal capitalBalance, BigDecimal quota, BigDecimal interest, BigDecimal totalQuota, String paymentDate){
            this.setNro(nro);
            this.setCapitalBalance(capitalBalance);
            this.setQuota(quota);
            this.setInterest(interest);
            this.setTotalQuota(totalQuota);
            this.setPaymentDate(paymentDate);
        }


        public Integer getNro() {
            return nro;
        }

        public void setNro(Integer nro) {
            this.nro = nro;
        }

        public BigDecimal getCapitalBalance() {
            return capitalBalance;
        }

        public void setCapitalBalance(BigDecimal capitalBalance) {
            this.capitalBalance = capitalBalance;
        }

        public BigDecimal getQuota() {
            return quota;
        }

        public void setQuota(BigDecimal quota) {
            this.quota = quota;
        }

        public BigDecimal getInterest() {
            return interest;
        }

        public void setInterest(BigDecimal interest) {
            this.interest = interest;
        }

        public BigDecimal getTotalQuota() {
            return totalQuota;
        }

        public void setTotalQuota(BigDecimal totalQuota) {
            this.totalQuota = totalQuota;
        }

        public String getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(String paymentDate) {
            this.paymentDate = paymentDate;
        }
    }


}