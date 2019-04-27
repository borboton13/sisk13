package com.encens.khipus.service.production;

import com.encens.khipus.model.production.Production;
import com.encens.khipus.model.production.ProductionProduct;
import com.encens.khipus.model.production.Supply;
import com.encens.khipus.model.production.SupplyType;

import javax.ejb.Local;
import java.util.List;

/**
 * Product service interface
 *
 * @author
 * @version $Id: ProductService.java 2008-9-11 13:50:25 $
 */
@Local
public interface ProductionService {

    List<Supply> getSupplyList(Production production, SupplyType type);
    void createProduction(Production production, List<Supply> ingredientSupplyList, List<Supply> materialSupplyList);
    void updateProduction(Production production, List<Supply> ingredientSupplyList, List<Supply> materialSupplyList);
    void assignProduct(Production production, ProductionProduct product);
    void removeProductionProduct(ProductionProduct product);
}
