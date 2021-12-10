/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 *
 * @author Pedro Cardoso
 */
public class InMemoryProductRepository extends InMemoryDomainRepository<Product, Designation>
        implements ProductRepository {

    static {
        InMemoryInitializer.init();
    }
    @Override
    public Iterable<Product> allProductsNoProductionRecord() {
        return match(Product::isProductionRecordEmpty);
    }

    @Override
    public Product productByDesignation(Designation designation) {
        return null;
    }

}
