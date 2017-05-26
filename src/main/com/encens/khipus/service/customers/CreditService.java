package com.encens.khipus.service.customers;

import com.encens.khipus.model.contacts.Entity;
import com.encens.khipus.model.customers.Credit;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

/**
 * Credit service interface
 *
 * @author
 */

@Local
public interface CreditService {

    Credit findByEntity(Entity entity);

    BigDecimal getActualCreditBalance(Credit credit);
    BigDecimal getTotalPaidCapital(Credit credit);
    List<Credit> getAllCredits();
}
