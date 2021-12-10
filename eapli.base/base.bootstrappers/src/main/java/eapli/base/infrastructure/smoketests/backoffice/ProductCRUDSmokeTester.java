/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Pedro Cardoso
 */
public class ProductCRUDSmokeTester {
    private static final Logger LOGGER = LogManager.getLogger(ProductCRUDSmokeTester.class);

    private final ProductRepository repo = PersistenceContext.repositories().products();

    public void testProductsCRUD() {
        // save
        repo.save(new Product(Designation.valueOf("7f"), "7c", "bola", "bola borracha", "UN", "TT_CC"));
        repo.save(new Product(Designation.valueOf("8f"), "8c", "papel", "papel premium", "UN", "TT-CN"));
        ;
        LOGGER.info("»»» created products");

        // findAll
        final Iterable<Product> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all products");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # products = {}", n);

/*        // ofIdentity
        final Product p1 = repo.ofIdentity(Designation.valueOf("7f")).orElseThrow(IllegalStateException::new);
        final Product p2 = repo.ofIdentity(Designation.valueOf("8f")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found Product of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(p1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains Produto of identity");

        // contains
        final boolean has = repo.contains(p1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains products");

        // delete
        repo.delete(p1);
        LOGGER.info("»»» delete products");

        // deleteOfIdentity
        repo.deleteOfIdentity(p2.identity());
        LOGGER.info("»»» delete products of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # products = {}", n2);*/
    }
}
