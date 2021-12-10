/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.repositories;

import eapli.base.core.productmanager.domain.Product;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.domain.repositories.DomainRepository;

/**
 *
 * @author Pedro Cardoso
 */
public interface ProductRepository extends DomainRepository<Designation, Product> {

    /**
     * returns the active dish types
     *
     * @return An iterable for DishType.
     */
    Iterable<Product> allProductsNoProductionRecord();

    Product productByDesignation(Designation designation);
}

