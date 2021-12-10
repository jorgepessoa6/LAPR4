/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.repositories;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.ProductionOrderStatus;
import eapli.base.productionordermanagement.domain.Status;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;

import java.util.Calendar;

/**
 *
 * @author Bruno Pereira
 */
public interface ProductionOrderRepository extends DomainRepository<Designation, ProductionOrder>{

    Iterable<ProductionOrder> allProductionOrdersByStatus(Status status);

    Iterable<ProductionOrder> allproductionOrderByOrderID(String s);

    Iterable<ProductionOrder> allproductionOrderByTimeFrame(Calendar d1, Calendar d2);

    ProductionOrder productionOrderByDesignation(Designation id);


}
