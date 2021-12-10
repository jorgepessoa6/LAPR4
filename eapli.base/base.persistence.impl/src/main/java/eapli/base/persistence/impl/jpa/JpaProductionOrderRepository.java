/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.Order;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.ProductionOrderStatus;
import eapli.base.productionordermanagement.domain.Status;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Calendar;

/**
 * @author Bruno Pereira
 */
public class JpaProductionOrderRepository extends BasepaRepositoryBase<ProductionOrder, Long, Designation>
        implements ProductionOrderRepository {

    JpaProductionOrderRepository() {
        super("codeOrder");
    }

    @Override
    public Iterable<ProductionOrder> allProductionOrdersByStatus(Status status) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT po FROM ProductionOrder po WHERE po.status = :status",
                ProductionOrder.class);
        ProductionOrderStatus pos = new ProductionOrderStatus(status);
        query.setParameter("status", pos);
        return query.getResultList();
    }

    @Override
    public Iterable<ProductionOrder> allproductionOrderByOrderID(String s) {
        Order o = new Order(s);
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT po FROM ProductionOrder po inner join po.encomenda p WHERE p.orderId = :s",
                ProductionOrder.class);
        query.setParameter("s", s);
        return query.getResultList();
    }

    @Override
    public Iterable<ProductionOrder> allproductionOrderByTimeFrame(Calendar d1, Calendar d2) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT po FROM ProductionOrder po WHERE po.startExecution BETWEEN :d1 AND :d2",
                ProductionOrder.class);
        query.setParameter("d1", d1, TemporalType.DATE);
        query.setParameter("d2", d2, TemporalType.DATE);
        return query.getResultList();
    }

    @Override
    public ProductionOrder productionOrderByDesignation(Designation id) {
        String s = id.toString();
        final TypedQuery<ProductionOrder> query = entityManager().createQuery("SELECT m FROM ProductionOrder m WHERE NAME = : s", ProductionOrder.class);
        query.setParameter("s", s);
        return query.getSingleResult();
    }


}
