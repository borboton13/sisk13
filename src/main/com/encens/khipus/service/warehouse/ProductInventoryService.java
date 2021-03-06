package com.encens.khipus.service.warehouse;

import com.encens.khipus.framework.service.GenericService;
import com.encens.khipus.model.customers.CustomerOrder;
import com.encens.khipus.model.customers.VentaDirecta;
import com.encens.khipus.model.warehouse.InitialInventory;
import com.encens.khipus.model.warehouse.InventoryPeriod;
import com.encens.khipus.model.warehouse.ProductInventory;
import com.encens.khipus.model.warehouse.ProductInventoryRecordType;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author
 * @version 3.0
 */
@Local
public interface ProductInventoryService extends GenericService {

    ProductInventory findProductInventoryByCode(String productItemCode);

    public void inputProducts(String productItemCode, Double quantity);

    public void outputProducts(VentaDirecta directSales);

    public void outputProducts(CustomerOrder customerOrder);

    public void outputProducts(List<CustomerOrder> customerOrders);

    public void updateProductInventory(String productItemCode, Double quantity, ProductInventoryRecordType typeRecord, String description);

    public List<InitialInventory> findInitialInventory(String warehouseCode, String year);

    public BigDecimal findUnitCostbyCode(String productItemCode, String year);

    List<InventoryPeriod> findInitialInventoryAll(String warehouseCode, String year);
}
