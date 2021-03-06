package com.encens.khipus.action.production;

import com.encens.khipus.framework.action.QueryDataModel;
import com.encens.khipus.model.production.ProductionPlan;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Name("productionPlanDataModel")
@Scope(ScopeType.PAGE)
public class ProductionPlanDataModel extends QueryDataModel<Long, ProductionPlan> {

    private Date startDate;
    private Date endDate;

    private static final String[] RESTRICTIONS = {"" +
            "productionPlan.date >= #{productionPlanDataModel.startDate}",
            "productionPlan.date <= #{productionPlanDataModel.endDate}"};

    @Create
    public void init() {
        sortProperty = "productionPlan.date";
        sortAsc = false;
    }

    @Override
    public String getEjbql() {
        return "select productionPlan from ProductionPlan productionPlan";
    }

    @Override
    public List<String> getRestrictions() {
        return Arrays.asList(RESTRICTIONS);
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
}