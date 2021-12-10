package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Jorge Pessoa (1180761)
 */

public class ProductionLineCRUDSmokeTester {

    private static final Logger LOGGER = LogManager.getLogger(ProductionLineCRUDSmokeTester.class);

    private final ProductionLineRepository repo = PersistenceContext.repositories().productionLines();

    public void testProductionLinesCRUD() {
        // save
/*
        repo.save(new ProductionLine(Designation.valueOf("1f")));
        repo.save(new ProductionLine(Designation.valueOf("2f")));
        LOGGER.info("»»» created production lines");

        // findAll
        final Iterable<ProductionLine> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all production lines");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # production lines = {}", n);
*/

//        // ofIdentity
//        final ProductionLine p1 = repo.ofIdentity(Designation.valueOf("1f")).orElseThrow(IllegalStateException::new);
//        final ProductionLine p2 = repo.ofIdentity(Designation.valueOf("1f")).orElseThrow(IllegalStateException::new);
//        LOGGER.info("»»» found production lines of identity");
//
//        // containsOfIdentity
//        final boolean hasId = repo.containsOfIdentity(p1.identity());
//        Invariants.ensure(hasId);
//        LOGGER.info("»»» contains production lines of identity");
//
//        // contains
//        final boolean has = repo.contains(p1);
//        Invariants.ensure(has);
//        LOGGER.info("»»» contains production lines");
//
//        // delete
//        repo.delete(p1);
//        LOGGER.info("»»» delete production lines");
//
//        // deleteOfIdentity
//        repo.deleteOfIdentity(p2.identity());
//        LOGGER.info("»»» delete production lines of identity");
//
//        // size
//        final long n2 = repo.size();
//        Invariants.ensure(n2 == n - 2);
//        LOGGER.info("»»» # production lines = {}", n2);
    }
}
