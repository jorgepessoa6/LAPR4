/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.general.domain.model.Designation;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pedro Cardoso
 */
 class JpaProductRepository extends BasepaRepositoryBase<Product, Long, Designation>
        implements ProductRepository {

    JpaProductRepository() {
        super("codeFactory");
    }

    @Override
    public Iterable<Product> allProductsNoProductionRecord() {
          final TypedQuery<Product> query = entityManager().createQuery(
                "SELECT p FROM Product p WHERE PRECORD_PK IS NULL",
                Product.class);
        return query.getResultList();
    }

    @Override
    public Product productByDesignation(Designation designation) {
        String s = designation.toString();
        final TypedQuery<Product> query = entityManager().createQuery("SELECT m FROM Product m WHERE NAME = : s", Product.class);
        query.setParameter("s", s);
        return query.getSingleResult();
    }


}
