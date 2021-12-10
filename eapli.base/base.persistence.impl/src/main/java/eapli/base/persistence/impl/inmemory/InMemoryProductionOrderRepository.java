/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.Status;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bruno Pereira
 */
public class InMemoryProductionOrderRepository extends InMemoryDomainRepository<ProductionOrder, Designation>
        implements ProductionOrderRepository {
    static {
        InMemoryInitializer.init();
    }
    @Override
    public Iterable<ProductionOrder> allProductionOrdersByStatus(Status status) {
        final Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        //return match("e.status = :status", params);
        return null;
    }

    @Override
    public Iterable<ProductionOrder> allproductionOrderByOrderID(String s) {
      //  return match(ProductionOrder::isOrderInProductionOrder(s));
        return null;
    }

    @Override
    public Iterable<ProductionOrder> allproductionOrderByTimeFrame(Calendar d1, Calendar d2) {
        return null;
    }

    @Override
    public ProductionOrder productionOrderByDesignation(Designation id) {
        return null;
    }
}
