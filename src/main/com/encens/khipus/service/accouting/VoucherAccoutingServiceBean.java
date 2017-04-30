package com.encens.khipus.service.accouting;

import com.encens.khipus.converter.BigDecimalConverter;
import com.encens.khipus.exception.finances.CompanyConfigurationNotFoundException;
import com.encens.khipus.framework.service.GenericServiceBean;
import com.encens.khipus.model.accounting.SaleType;
import com.encens.khipus.model.admin.ProductSaleType;
import com.encens.khipus.model.customers.ArticleOrder;
import com.encens.khipus.model.customers.CustomerOrder;
import com.encens.khipus.model.customers.VentaDirecta;
import com.encens.khipus.model.finances.*;
import com.encens.khipus.service.finances.FinancesPkGeneratorService;
import com.encens.khipus.service.fixedassets.CompanyConfigurationService;
import com.encens.khipus.util.*;
import com.encens.khipus.util.query.EntityQueryFactory;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.*;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

/**
 * @author
 * @version 2.4
 */
@Stateless
@Name("voucherAccoutingService")
@AutoCreate
public class VoucherAccoutingServiceBean extends GenericServiceBean implements VoucherAccoutingService {

    @In(value = "#{entityManager}")
    private EntityManager em;

    @In
    private FinancesPkGeneratorService financesPkGeneratorService;

    @In
    private CompanyConfigurationService companyConfigurationService;

    public void saveVoucher(Voucher voucher){

        voucher.setId(financesPkGeneratorService.newId_sf_tmpenc());

        if (voucher.getTransactionNumber() == null){
            voucher.setTransactionNumber(financesPkGeneratorService.getNextNoTransTmpenc());
        }

        if (voucher.getDocumentNumber() == null){
            voucher.setDocumentNumber(financesPkGeneratorService.getNextNoTransByDocumentType(voucher.getDocumentType()));
        }

        System.out.println("-------- VOUCHER ENCABEZADO -------");
        System.out.println("ID: " + voucher.getId());
        System.out.println("Fecha: " + voucher.getDate());
        System.out.println("NoTrans: " + voucher.getTransactionNumber());
        System.out.println("Tipo Doc: " + voucher.getDocumentType());
        System.out.println("Descripcion: " + voucher.getDescription());
        System.out.println("Glosa: " + voucher.getGloss());

        em.persist(voucher);
        em.flush();

        System.out.println("-------- VOUCHER DETAILS -------");
        for (VoucherDetail voucherDetail : voucher.getDetails()) {
            voucherDetail.setId(financesPkGeneratorService.newId_sf_tmpdet());
            voucherDetail.setTransactionNumber(voucher.getTransactionNumber());
            voucherDetail.setVoucher(voucher);

            System.out.println("CUENTA: " + voucherDetail.getAccount() + " NOTRANS:" + voucherDetail.getTransactionNumber() + " D:" + voucherDetail.getDebit() + " H:" + voucherDetail.getCredit());

            em.persist(voucherDetail);
            em.flush();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public void updateVoucher(Voucher voucher) {

        List<VoucherDetail> voucherDetailsDB = getVoucherDetailList(voucher);
        for (VoucherDetail voucherDetail : voucherDetailsDB) {
            em.merge(voucherDetail);
            em.remove(voucherDetail);
            em.flush();
        }

        for (VoucherDetail voucherDetail : voucher.getDetails()) {
            if(voucherDetail.getTransactionNumber() == null){
                voucherDetail.setId(financesPkGeneratorService.newId_sf_tmpdet());
                voucherDetail.setTransactionNumber(voucher.getTransactionNumber());
                voucherDetail.setVoucher(voucher);

                em.persist(voucherDetail);
                em.merge(voucher);
                em.flush();

            }else{
                em.merge(voucherDetail);
                em.merge(voucher);
                em.flush();
            }
        }

        em.merge(voucher);
        em.flush();
    }

    @Override
    public void approveVoucher(Voucher voucher){
        em.merge(voucher);
        em.flush();
    }

    @Override
    public void annulVoucher(Voucher voucher){
        em.merge(voucher);
        em.flush();
    }

    @Override
    public List<VoucherDetail> getVoucherDetailList(String transactionNumber){

        List<VoucherDetail> voucherDetails = new ArrayList<VoucherDetail>();

        try {
            voucherDetails = (List<VoucherDetail>) em.createQuery("select voucherDetail from VoucherDetail voucherDetail " +
                    " where voucherDetail.transactionNumber = :transactionNumber ")
                    .setParameter("transactionNumber", transactionNumber)
                    .getResultList();
            /*voucherDetails = (List<VoucherDetail>) em.createNativeQuery("select * from sf_tmpdet where no_trans = :transactionNumber")
                    .setParameter("transactionNumber", transactionNumber).getResultList();*/
        }catch (NoResultException e){
            return null;
        }
        return voucherDetails;
    }

    @Override
    public List<VoucherDetail> getVoucherDetailList(Voucher voucher){

        List<VoucherDetail> voucherDetails = new ArrayList<VoucherDetail>();

        try {
            voucherDetails = (List<VoucherDetail>) em.createQuery("select voucherDetail from VoucherDetail voucherDetail " +
                    " where voucherDetail.voucher = :voucher ")
                    .setParameter("voucher", voucher)
                    .getResultList();
            /*voucherDetails = (List<VoucherDetail>) em.createNativeQuery("select * from sf_tmpdet where no_trans = :transactionNumber")
                    .setParameter("transactionNumber", transactionNumber).getResultList();*/
        }catch (NoResultException e){
            return null;
        }
        return voucherDetails;
    }

    @Override
    public Voucher getVoucher(String transactionNumber) {

        Voucher voucher = null;
        try {
            voucher = (Voucher) em.createQuery("select voucher from Voucher voucher " +
                    " where voucher.transactionNumber = :transactionNumber ")
                    .setParameter("transactionNumber", transactionNumber)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return voucher;
    }

    public Voucher getVoucher(Long id) {

        Voucher voucher = null;
        try {
            voucher = (Voucher) em.createQuery("select voucher from Voucher voucher " +
                    " where voucher.id = :id ")
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return voucher;
    }

    public Double getBalance(Date startDate, String cashAccountCode){

        List<VoucherDetail> voucherDetailList = new ArrayList<VoucherDetail>();

        try {
            voucherDetailList = em.createQuery("select voucherDetail from VoucherDetail voucherDetail " +
                    " left join voucherDetail.voucher voucher " +
                    " where voucher.date < :startdate " +
                    " and voucherDetail.account = :cashAccountCode " +
                    " and voucher.state <> 'ANL' ")
                    .setParameter("startdate", startDate)
                    .setParameter("cashAccountCode", cashAccountCode)
                    .getResultList();
        }catch (NoResultException e){
            return null;
        }

        Double balance = new Double("0.00");
        Double balanceD = new Double("0.00");
        Double balanceC = new Double("0.00");

        for (VoucherDetail voucherDetail : voucherDetailList){

            balanceD = balanceD + voucherDetail.getDebit().doubleValue();
            balanceC = balanceC + voucherDetail.getCredit().doubleValue();
        }

        balance = (balanceD - balanceC);

        return balance;
    }

    public Double getBalanceProvider(Date startDate, String cashAccountCode, String providerCode){

        List<VoucherDetail> voucherDetailList = new ArrayList<VoucherDetail>();

        try {
            voucherDetailList = em.createQuery("select voucherDetail from VoucherDetail voucherDetail " +
                    " left join voucherDetail.voucher voucher " +
                    " where voucher.date < :startdate " +
                    " and voucherDetail.account = :cashAccountCode " +
                    " and voucher.providerCode = :providerCode " +
                    " and voucher.state <> 'ANL'")
                    .setParameter("startdate", startDate)
                    .setParameter("cashAccountCode", cashAccountCode)
                    .setParameter("providerCode", providerCode)
                    .getResultList();
        }catch (NoResultException e){
            return null;
        }

        Double balance = new Double("0.00");
        Double balanceD = new Double("0.00");
        Double balanceC = new Double("0.00");

        System.out.println("-------- GET BALANCE ---------");
        for (VoucherDetail voucherDetail : voucherDetailList){
            /*System.out.println("Voucher: " + voucherDetail.getVoucher().getDocumentType() + " - " + voucherDetail.getVoucher().getDocumentNumber());
            System.out.println("VoucherDetail: " + voucherDetail.getCashAccount().getFullName() + " - " + voucherDetail.getDebit() + " - " + voucherDetail.getCredit());
            System.out.println("--");*/

            balanceD = balanceD + voucherDetail.getDebit().doubleValue();
            balanceC = balanceC + voucherDetail.getCredit().doubleValue();
        }

        balance = (balanceD - balanceC);
        /*System.out.println("TOTAL BALANCE: " + balance);*/

        return balance;
    }

    public Double getTotalProfits(String startDate, String endDate){

        List<Object[]> datas = new ArrayList<Object[]>();

        String ejbql =  " SELECT " +
                " rootCashAccount.accountCode AS accountCode, " +
                " rootCashAccount.description AS description, " +
                " SUM(voucherDetail.debit) AS debit, " +
                " SUM(voucherDetail.credit) AS credit" +
                " FROM VoucherDetail voucherDetail " +
                " LEFT JOIN voucherDetail.voucher voucher " +
                " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                " WHERE cashAccount.accountType = 'I' " +
                " AND voucher.state <> 'ANL' " +
                " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                " GROUP BY rootCashAccount.accountCode, rootCashAccount.description ";

        Double totalProfits = new Double(0);
        try{

            datas = em.createQuery(ejbql).getResultList();

            Double totalDebit  = new Double(0);
            Double totalCredit = new Double(0);

            for(Object[] obj: datas){

                totalDebit  = totalDebit  + ((BigDecimal)obj[2]).doubleValue();
                totalCredit = totalCredit + ((BigDecimal)obj[3]).doubleValue();

            }

            totalProfits = totalCredit - totalDebit;

        }catch (NoResultException e){
            return null;
        }

        return totalProfits;
    }

    public Double getTotalLosses(String startDate, String endDate){

        List<Object[]> datas = new ArrayList<Object[]>();

        String ejbql =  " SELECT " +
                " rootCashAccount.accountCode AS accountCode, " +
                " rootCashAccount.description AS description, " +
                " SUM(voucherDetail.debit) AS debit, " +
                " SUM(voucherDetail.credit) AS credit" +
                " FROM VoucherDetail voucherDetail " +
                " LEFT JOIN voucherDetail.voucher voucher " +
                " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                " WHERE cashAccount.accountType = 'E' " +
                " AND voucher.state <> 'ANL' " +
                " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                " GROUP BY rootCashAccount.accountCode, rootCashAccount.description ";

        Double totalLosses = new Double(0);
        try{

            datas = em.createQuery(ejbql).getResultList();

            Double totalDebit  = new Double(0);
            Double totalCredit = new Double(0);

            for(Object[] obj: datas){

                totalDebit  = totalDebit  + ((BigDecimal)obj[2]).doubleValue();
                totalCredit = totalCredit + ((BigDecimal)obj[3]).doubleValue();

            }

            totalLosses = totalDebit - totalCredit;

        }catch (NoResultException e){
            return null;
        }

        return totalLosses;
    }

    public Double getTotalAssets(String startDate, String endDate){

        List<Object[]> datas = new ArrayList<Object[]>();

        String ejbql =  " SELECT " +
                " rootCashAccount.accountCode AS accountCode, " +
                " rootCashAccount.description AS description, " +
                " SUM(voucherDetail.debit) AS debit, " +
                " SUM(voucherDetail.credit) AS credit" +
                " FROM VoucherDetail voucherDetail " +
                " LEFT JOIN voucherDetail.voucher voucher " +
                " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                " WHERE cashAccount.accountType = 'A' " +
                " AND voucher.state <> 'ANL' " +
                " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                " GROUP BY rootCashAccount.accountCode, rootCashAccount.description ";

        Double totalAssets = new Double(0);
        try{

            datas = em.createQuery(ejbql).getResultList();

            Double totalDebit  = new Double(0);
            Double totalCredit = new Double(0);

            for(Object[] obj: datas){

                totalDebit  = totalDebit  + ((BigDecimal)obj[2]).doubleValue();
                totalCredit = totalCredit + ((BigDecimal)obj[3]).doubleValue();

            }

            totalAssets = totalDebit - totalCredit;

        }catch (NoResultException e){
            return null;
        }

        return totalAssets;
    }

    public Double getTotalLiabilities(String startDate, String endDate){

        List<Object[]> datas = new ArrayList<Object[]>();

        String ejbql =  " SELECT " +
                " rootCashAccount.accountCode AS accountCode, " +
                " rootCashAccount.description AS description, " +
                " SUM(voucherDetail.debit) AS debit, " +
                " SUM(voucherDetail.credit) AS credit" +
                " FROM VoucherDetail voucherDetail " +
                " LEFT JOIN voucherDetail.voucher voucher " +
                " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                " WHERE cashAccount.accountType = 'P' " +
                " AND voucher.state <> 'ANL' " +
                " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                " GROUP BY rootCashAccount.accountCode, rootCashAccount.description ";

        Double totalLiabilities = new Double(0);
        try{

            datas = em.createQuery(ejbql).getResultList();

            Double totalDebit  = new Double(0);
            Double totalCredit = new Double(0);

            for(Object[] obj: datas){

                totalDebit  = totalDebit  + ((BigDecimal)obj[2]).doubleValue();
                totalCredit = totalCredit + ((BigDecimal)obj[3]).doubleValue();

            }

            totalLiabilities = totalCredit - totalDebit;

        }catch (NoResultException e){
            return null;
        }

        return totalLiabilities;
    }

    public Double getTotalCapital(String startDate, String endDate){

        List<Object[]> datas = new ArrayList<Object[]>();

        String ejbql =  " SELECT " +
                " rootCashAccount.accountCode AS accountCode, " +
                " rootCashAccount.description AS description, " +
                " SUM(voucherDetail.debit) AS debit, " +
                " SUM(voucherDetail.credit) AS credit" +
                " FROM VoucherDetail voucherDetail " +
                " LEFT JOIN voucherDetail.voucher voucher " +
                " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                " WHERE cashAccount.accountType = 'C' " +
                " AND voucher.state <> 'ANL' " +
                " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                " GROUP BY rootCashAccount.accountCode, rootCashAccount.description ";

        Double totalCapital = new Double(0);
        try{

            datas = em.createQuery(ejbql).getResultList();

            Double totalDebit  = new Double(0);
            Double totalCredit = new Double(0);

            for(Object[] obj: datas){

                totalDebit  = totalDebit  + ((BigDecimal)obj[2]).doubleValue();
                totalCredit = totalCredit + ((BigDecimal)obj[3]).doubleValue();

            }

            totalCapital = totalCredit - totalDebit;

        }catch (NoResultException e){
            return null;
        }

        return totalCapital;
    }

    public BigDecimal totalCapital(String startDate, String endDate, String rootCashAccount){

        List<Object[]> datas = new ArrayList<Object[]>();

        BigDecimal totalCapital = new BigDecimal(0);
        BigDecimal totalDebit   = new BigDecimal(0);
        BigDecimal totalCredit  = new BigDecimal(0);

        try {
            datas = em.createQuery(
                            " SELECT " +
                            " rootCashAccount.accountCode as accountCode, " +
                            " rootCashAccount.description as description, " +
                            " SUM(voucherDetail.debit) AS debit, " +
                            " SUM(voucherDetail.credit) AS credit" +
                            " FROM VoucherDetail voucherDetail " +
                            " LEFT JOIN voucherDetail.voucher voucher " +
                            " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                            " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                            " WHERE cashAccount.accountType = 'C' " +
                            " AND voucher.state <> 'ANL' " +
                            " AND rootCashAccount.accountCode = '"+rootCashAccount+"' " +
                            " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                            " GROUP BY rootCashAccount.accountCode, rootCashAccount.description "
            ).getResultList();

            for(Object[] obj: datas){
                totalDebit  = BigDecimalUtil.sum(totalDebit, ((BigDecimal)obj[2]), 2);
                totalCredit = BigDecimalUtil.sum(totalCredit, ((BigDecimal)obj[3]), 2);
            }
            totalCapital = BigDecimalUtil.subtract(totalCredit, totalDebit, 2);
        }catch (NoResultException e){}
        return totalCapital;
    }

    public BigDecimal perdidasExcedentesPeriodo(String startDate, String endDate){
        List<Object[]> datas = new ArrayList<Object[]>();

        BigDecimal total        = new BigDecimal(0);
        BigDecimal totalDebit   = new BigDecimal(0);
        BigDecimal totalCredit  = new BigDecimal(0);

        try {
            datas = em.createQuery(
                    " SELECT " +
                            " voucher.documentType, " +
                            " SUM(voucherDetail.debit) AS debit, " +
                            " SUM(voucherDetail.credit) AS credit" +
                            " FROM VoucherDetail voucherDetail " +
                            " LEFT JOIN voucherDetail.voucher voucher " +
                            " WHERE voucher.documentType = 'SS' " +
                            " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                            " GROUP BY voucher.documentType "
            ).getResultList();

            for(Object[] obj: datas){
                totalDebit  = BigDecimalUtil.sum(totalDebit, ((BigDecimal)obj[1]), 2);
                totalCredit = BigDecimalUtil.sum(totalCredit, ((BigDecimal)obj[2]), 2);
            }
            total = BigDecimalUtil.subtract(totalCredit, totalDebit, 2);
        }catch (NoResultException e){}
        return total;

    }

    public HashMap<String, BigDecimal> stateBalanceReport(String start, String end){

        List<Object[]> datas = new ArrayList<Object[]>();
        HashMap<String, BigDecimal> totalResultStateBalance = new HashMap<String, BigDecimal>();

        String ejbql =  " SELECT " +
                        "        voucherDetail.account as account, " +          //0
                        "        cashAccount.description as description, " +    //1
                        "        SUM(voucherDetail.debit) as totalDebit, " +    //2
                        "        SUM(voucherDetail.credit) as totalCredit," +   //3
                        "        cashAccount.accountType as type " +            //4
                        "  FROM  VoucherDetail voucherDetail " +
                        "  LEFT  JOIN voucherDetail.voucher voucher " +
                        "  LEFT  JOIN voucherDetail.cashAccount cashAccount " +
                        "  WHERE voucher.date between '"+start+"' and '"+end+"' " +
                        "  AND   voucher.state <> 'ANL' " +
                        "  AND   voucher.documentType <> 'SS' " +
                        "  group by voucherDetail.account, cashAccount.description ";

        BigDecimal debit     = new BigDecimal(0);
        BigDecimal credit    = new BigDecimal(0);
        String accountType   = "";

        BigDecimal debitBalance     = new BigDecimal(0);
        BigDecimal creditBalance    = new BigDecimal(0);
        BigDecimal activeBalance    = new BigDecimal(0);
        BigDecimal passiveBalance   = new BigDecimal(0);
        BigDecimal egressBalance    = new BigDecimal(0);
        BigDecimal incomeBalance    = new BigDecimal(0);

        BigDecimal totalActiveBalance  = new BigDecimal(0);
        BigDecimal totalPassiveBalance = new BigDecimal(0);
        BigDecimal totalEgressBalance  = new BigDecimal(0);
        BigDecimal totalIncomeBalance  = new BigDecimal(0);


        try{
            datas = em.createQuery(ejbql).getResultList();

            for(Object[] data: datas){
                debit  = (BigDecimal)data[2];
                credit = (BigDecimal)data[3];
                accountType = (String)data[4];

                /** For Debit Balance **/
                if ( debit.doubleValue() > credit.doubleValue() )
                    debitBalance = BigDecimalUtil.subtract(debit, credit, 2);
                else
                    debitBalance = BigDecimal.ZERO ;

                //$F{totalCredit}.doubleValue() > $F{totalDebit}.doubleValue() ? $F{totalCredit}.subtract($F{totalDebit}) : (new BigDecimal("0.00"))
                /** For Credit Balance **/
                if ( credit.doubleValue() > debit.doubleValue() )
                    creditBalance = BigDecimalUtil.subtract(credit, debit, 2);
                else
                    creditBalance = BigDecimal.ZERO;

                //($V{debitBalance}.doubleValue() > $V{creditBalance}.doubleValue()) && ( $F{type}.equals("A") || $F{type}.equals("P") || $F{type}.equals("C") ) ? $V{debitBalance} : (new BigDecimal("0.00"))
                /** For Active Balance **/
                if ( (debitBalance.doubleValue() > creditBalance.doubleValue()) && (accountType.equals("A") || accountType.equals("P") || accountType.equals("C")) )
                    activeBalance = debitBalance;
                else
                    activeBalance = BigDecimal.ZERO;

                //($V{creditBalance}.doubleValue() > $V{debitBalance}.doubleValue()) && ( $F{type}.equals("A") || $F{type}.equals("P") || $F{type}.equals("C") ) ? $V{creditBalance} : (new BigDecimal("0.00"))
                /** For Passive Balance **/
                if ( (creditBalance.doubleValue() > debitBalance.doubleValue()) && (accountType.equals("A") || accountType.equals("P") || accountType.equals("C")) )
                    passiveBalance = creditBalance;
                else
                    passiveBalance = BigDecimal.ZERO;

                //($V{debitBalance}.doubleValue() > $V{creditBalance}.doubleValue()) && ( $F{type}.equals("E") || $F{type}.equals("I") ) ? $V{debitBalance} : (new BigDecimal("0.00"))
                /** For Egress Balance **/
                if ( (debitBalance.doubleValue() > creditBalance.doubleValue()) && (accountType.equals("E") || accountType.equals("I")) )
                    egressBalance = debitBalance;
                else
                    egressBalance = BigDecimal.ZERO;

                //($V{creditBalance}.doubleValue() > $V{debitBalance}.doubleValue()) && ( $F{type}.equals("I") || $F{type}.equals("E") ) ? $V{creditBalance} : (new BigDecimal("0.00"))
                /** For Income Balance **/
                if ( (creditBalance.doubleValue() > debitBalance.doubleValue()) && (accountType.equals("E") || accountType.equals("I")) )
                    incomeBalance = creditBalance;
                else
                    incomeBalance = BigDecimal.ZERO;

                totalActiveBalance  = BigDecimalUtil.sum(totalActiveBalance, activeBalance, 2);
                totalPassiveBalance = BigDecimalUtil.sum(totalPassiveBalance, passiveBalance, 2);
                totalEgressBalance  = BigDecimalUtil.sum(totalEgressBalance, egressBalance, 2);
                totalIncomeBalance  = BigDecimalUtil.sum(totalIncomeBalance, incomeBalance, 2);
            }

            totalResultStateBalance.put("totalActiveBalance", totalActiveBalance);
            totalResultStateBalance.put("totalPassiveBalance", totalPassiveBalance);
            totalResultStateBalance.put("totalEgressBalance", totalEgressBalance);
            totalResultStateBalance.put("totalIncomeBalance", totalIncomeBalance);

        }catch (NoResultException e){ return null; }
        return  totalResultStateBalance;
    }

    /**  **/
    public BigDecimal calculateLossesNiv3(String startDate, String endDate, String accountLevel3){

        List<Object[]> datas = new ArrayList<Object[]>();

        BigDecimal totalResult = new BigDecimal(0);
        BigDecimal totalDebit   = new BigDecimal(0);
        BigDecimal totalCredit  = new BigDecimal(0);

        try {
            /*datas = em.createQuery(
                    " SELECT " +
                            " rootCashAccount.accountCode as accountCode, " +
                            " rootCashAccount.description as description, " +
                            " SUM(voucherDetail.debit) AS debit, " +
                            " SUM(voucherDetail.credit) AS credit" +
                            " FROM VoucherDetail voucherDetail " +
                            " LEFT JOIN voucherDetail.voucher voucher " +
                            " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                            " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                            " WHERE cashAccount.accountType = 'C' " +
                            " AND voucher.state <> 'ANL' " +
                            " AND rootCashAccount.accountCode = '"+accountLevel3+"' " +
                            " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                            " GROUP BY rootCashAccount.accountCode, rootCashAccount.description "
            ).getResultList();*/

            datas = em.createQuery(
                            " SELECT " +
                            " cashAccountLeve3.accountCode as accountCode, " +
                            " cashAccountLeve3.description as description, " +
                            " SUM(voucherDetail.debit) AS debit, " +
                            " SUM(voucherDetail.credit) AS credit" +
                            " FROM VoucherDetail voucherDetail " +
                            " LEFT JOIN voucherDetail.voucher voucher " +
                            " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                            " LEFT JOIN voucherDetail.cashAccount.cashAccountLeve3 cashAccountLeve3 " +
                            " WHERE cashAccount.accountType = 'E' " +
                            " AND voucher.state <> 'ANL' " +
                            " AND cashAccountLeve3.accountCode = '"+accountLevel3+"' " +
                            " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                            " GROUP BY cashAccountLeve3.accountCode, cashAccountLeve3.description "
            ).getResultList();

            for(Object[] obj: datas){
                totalDebit  = BigDecimalUtil.sum(totalDebit, ((BigDecimal)obj[2]), 2);
                totalCredit = BigDecimalUtil.sum(totalCredit, ((BigDecimal)obj[3]), 2);
            }
            totalResult = BigDecimalUtil.subtract(totalDebit, totalCredit, 2);
        }catch (NoResultException e){}
        return totalResult;
    }

    public BigDecimal calculateLossesNiv2(String startDate, String endDate, String rootCashAccount){

        List<Object[]> datas = new ArrayList<Object[]>();

        BigDecimal totalResult = new BigDecimal(0);
        BigDecimal totalDebit   = new BigDecimal(0);
        BigDecimal totalCredit  = new BigDecimal(0);

        try {
            datas = em.createQuery(
                    " SELECT " +
                            " rootCashAccount.accountCode as accountCode, " +
                            " rootCashAccount.description as description, " +
                            " SUM(voucherDetail.debit) AS debit, " +
                            " SUM(voucherDetail.credit) AS credit" +
                            " FROM VoucherDetail voucherDetail " +
                            " LEFT JOIN voucherDetail.voucher voucher " +
                            " LEFT JOIN voucherDetail.cashAccount cashAccount" +
                            " LEFT JOIN voucherDetail.cashAccount.rootCashAccount rootCashAccount " +
                            " WHERE cashAccount.accountType = 'E' " +
                            " AND voucher.state <> 'ANL' " +
                            " AND rootCashAccount.accountCode = '"+rootCashAccount+"' " +
                            " AND voucher.date between '"+startDate+"' and '"+endDate+"' " +
                            " GROUP BY rootCashAccount.accountCode, rootCashAccount.description "
            ).getResultList();

            for(Object[] obj: datas){
                totalDebit  = BigDecimalUtil.sum(totalDebit, ((BigDecimal)obj[2]), 2);
                totalCredit = BigDecimalUtil.sum(totalCredit, ((BigDecimal)obj[3]), 2);
            }
            totalResult = BigDecimalUtil.subtract(totalDebit, totalCredit, 2);
        }catch (NoResultException e){}
        return totalResult;
    }

    public void createCostOfCashSales(Date startDate, Date endDate, ProductSaleType productSaleType) throws CompanyConfigurationNotFoundException {

        List<VentaDirecta> sales = null;
        BigDecimal totalCost = new BigDecimal(0.0);
        CompanyConfiguration companyConfiguration = companyConfigurationService.findCompanyConfiguration();
        Voucher voucher = VoucherBuilder.newGeneralVoucher(null, "Costo de ventas al contado " + productSaleType + ". Del " + DateUtils.format(startDate, "dd/MM/yyyy") + " al " + DateUtils.format(endDate, "dd/MM/yyyy"));
        voucher.setDocumentType(Constants.CV_VOUCHER_DOCTYPE);

        /** DAIRY_PRODUCT **/
        CashAccount ctaCost = companyConfiguration.getCtaCostPT();
        CashAccount ctaAlm  = companyConfiguration.getCtaAlmPT();
        Long userId = new Long(6); // Todo: cambiar el uso de id (user mflorero)

        if (ProductSaleType.VETERINARY_PRODUCT.equals(productSaleType)){
            ctaCost = companyConfiguration.getCtaCostPV();
            ctaAlm  = companyConfiguration.getCtaAlmPV();
            userId  = new Long(5); // Todo: cambiar el uso de id (user cisc)
        }

        try {
            sales = em.createNamedQuery("VentaDirecta.findByDatesForCosts")
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (NoResultException e) {}

        if (sales.size() > 0) {
            for (VentaDirecta sale : sales) {
                for (ArticleOrder articleOrder : sale.getArticulosPedidos())
                    totalCost = BigDecimalUtil.sum(totalCost, BigDecimalUtil.multiply(articleOrder.getCu(), BigDecimalUtil.toBigDecimal(articleOrder.getAmount())));
                sale.setCvFlag(true);
            }

            voucher.addVoucherDetail(VoucherDetailBuilder.newDebitVoucherDetail(null, null,
                    ctaCost,
                    totalCost,
                    FinancesCurrencyType.P, null));

            voucher.addVoucherDetail(VoucherDetailBuilder.newCreditVoucherDetail(null, null,
                    ctaAlm,
                    totalCost,
                    FinancesCurrencyType.P, null));

            voucher.setDate(endDate);
            voucher.setTransactionNumber(financesPkGeneratorService.getNextNoTransTmpenc());
            saveVoucher(voucher);
        }
    }

    public void createCostOfSalesCredit(Date startDate, Date endDate) throws CompanyConfigurationNotFoundException{

        List<CustomerOrder> sales = null;
        BigDecimal totalCost = new BigDecimal(0.0);
        CompanyConfiguration companyConfiguration = companyConfigurationService.findCompanyConfiguration();
        Voucher voucher = VoucherBuilder.newGeneralVoucher(null, "Costo de ventas a credito. Del " + DateUtils.format(startDate, "dd/MM/yyyy") + " al " + DateUtils.format(endDate, "dd/MM/yyyy"));
        voucher.setDocumentType(Constants.CV_VOUCHER_DOCTYPE);

        try {
            sales = em.createNamedQuery("CustomerOrder.findByDatesForCosts")
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (NoResultException e) {}

        if (sales.size() > 0) {
            for (CustomerOrder sale : sales) {
                for (ArticleOrder articleOrder : sale.getArticulosPedidos())
                    totalCost = BigDecimalUtil.sum(totalCost, BigDecimalUtil.multiply(articleOrder.getCu(), BigDecimalUtil.toBigDecimal(articleOrder.getAmount())));
                sale.setCvFlag(true);
            }

            voucher.addVoucherDetail(VoucherDetailBuilder.newDebitVoucherDetail(null, null,
                    companyConfiguration.getCtaCostPT(),
                    totalCost,
                    FinancesCurrencyType.P, null));

            voucher.addVoucherDetail(VoucherDetailBuilder.newCreditVoucherDetail(null, null,
                    companyConfiguration.getCtaAlmPT(),
                    totalCost,
                    FinancesCurrencyType.P, null));

            voucher.setDate(endDate);
            voucher.setTransactionNumber(financesPkGeneratorService.getNextNoTransTmpenc());
            saveVoucher(voucher);
        }
    }

}