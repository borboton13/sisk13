package com.encens.khipus.action.warehouse;

import com.encens.khipus.framework.action.QueryDataModel;
import com.encens.khipus.model.purchases.PurchaseOrderDetail;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @version 2.2
 */

@Name("warehousePurchaseOrderDetailDataModel")
@Scope(ScopeType.PAGE)
public class WarehousePurchaseOrderDetailDataModel extends QueryDataModel<Long, PurchaseOrderDetail> {
    private static final String[] RESTRICTIONS = {"warehousePurchaseOrderDetail.purchaseOrder = #{warehousePurchaseOrder}"};

    @Create
    public void init() {
        sortProperty = "warehousePurchaseOrderDetail.detailNumber";
    }

    @Override
    public String getEjbql() {
        return "select warehousePurchaseOrderDetail from PurchaseOrderDetail warehousePurchaseOrderDetail";
    }

    @Override
    public List<String> getRestrictions() {
        return Arrays.asList(RESTRICTIONS);
    }
}
