package com.encens.khipus.action.cashbox;

import com.encens.khipus.action.dashboard.DashboardObjectAction;
import com.encens.khipus.dashboard.component.factory.ComponentFactory;
import com.encens.khipus.dashboard.component.totalizer.SumTotalizer;
import com.encens.khipus.dashboard.module.cashbox.IncomeByInvoiceByBranch;
import com.encens.khipus.dashboard.module.cashbox.IncomeByInvoiceByBranchInstanceFactory;
import com.encens.khipus.dashboard.module.cashbox.IncomeByInvoiceByBranchSqlQuery;
import com.encens.khipus.util.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.Map;

/**
 * @author
 * @version 2.7
 */

@Name("incomeByInvoiceByBranchAction")
@Scope(ScopeType.PAGE)
public class IncomeByInvoiceByBranchAction extends DashboardObjectAction<IncomeByInvoiceByBranch> {
    @In
    private IncomeByInvoiceExtendedAction incomeByInvoiceExtendedAction;

    public Map<String, Number> getTotals() {
        return ((SumTotalizer<IncomeByInvoiceByBranch>) factory.getTotalizer()).getTotals();
    }

    @Override
    protected void initializeFactory() {
        factory = new ComponentFactory<IncomeByInvoiceByBranch, SumTotalizer<IncomeByInvoiceByBranch>>(
                new IncomeByInvoiceByBranchSqlQuery(),
                new IncomeByInvoiceByBranchInstanceFactory(),
                new SumTotalizer<IncomeByInvoiceByBranch>()
        );
    }

    @Override
    protected void setFilters() {
        Integer executorUnitCode = null;
        if (null != incomeByInvoiceExtendedAction.getExecutorUnit()) {
            executorUnitCode = incomeByInvoiceExtendedAction.getExecutorUnit().getId();
        }

        Integer startDateAsInteger = null;
        if (null != incomeByInvoiceExtendedAction.getStartDate()) {
            startDateAsInteger = DateUtils.dateToInteger(incomeByInvoiceExtendedAction.getStartDate());
        }

        Integer endDateAsInteger = null;
        if (null != incomeByInvoiceExtendedAction.getEndDate()) {
            endDateAsInteger = DateUtils.dateToInteger(incomeByInvoiceExtendedAction.getEndDate());
        }

        ((IncomeByInvoiceByBranchSqlQuery) factory.getSqlQuery()).setExecutorUnitCode(executorUnitCode);
        ((IncomeByInvoiceByBranchSqlQuery) factory.getSqlQuery()).setStartDate(startDateAsInteger);
        ((IncomeByInvoiceByBranchSqlQuery) factory.getSqlQuery()).setEndDate(endDateAsInteger);
    }
}
