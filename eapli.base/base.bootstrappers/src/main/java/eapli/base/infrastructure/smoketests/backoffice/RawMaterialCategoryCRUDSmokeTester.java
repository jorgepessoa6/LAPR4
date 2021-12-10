package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RawMaterialCategoryCRUDSmokeTester {

    private static final Logger LOGGER = LogManager.getLogger(RawMaterialCategoryCRUDSmokeTester.class);

    private final RawMaterialCategoryRepository repo = PersistenceContext.repositories().categories();

    public void testRawMaterialCategoryCRUD() {
        // save
        repo.save(new RawMaterialCategory(Designation.valueOf("C10"), "Vernizes"));
        repo.save(new RawMaterialCategory(Designation.valueOf("C11"), "Madeiras"));
        ;
        LOGGER.info("»»» created raw material categories");

        // findAll
        final Iterable<RawMaterialCategory> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all raw material categories");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # raw materials = {}", n);

/*        // ofIdentity
        Designation d1 = Designation.valueOf("RM1");
        Designation d2 = Designation.valueOf("RM2");
        final RawMaterialCategory c1 = repo.ofIdentity(d1).orElseThrow(IllegalStateException::new);
        final RawMaterialCategory c2 = repo.ofIdentity(d2).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found Raw Material of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(c1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains Raw Material Category of identity");

        // contains
        final boolean has = repo.contains(c1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains raw material categories");

        // delete
        repo.delete(c1);
        LOGGER.info("»»» delete raw material categories");

        // deleteOfIdentity
        repo.deleteOfIdentity(c2.identity());
        LOGGER.info("»»» delete raw material categories of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # products = {}", n2);*/
    }
}
