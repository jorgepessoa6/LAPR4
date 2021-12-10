package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RawMaterialCRUDSmokeTester {

    private static final Logger LOGGER = LogManager.getLogger(RawMaterialCRUDSmokeTester.class);

    private final RawMaterialRepository repo = PersistenceContext.repositories().rawMaterials();

    public void testRawMaterialsCRUD() {
/*
        // save
        repo.save(new RawMaterial(Designation.valueOf("RM1"), "Verniz de Madeira", new RawMaterialCategory(Designation.valueOf("C1"), "Vernizes"), null));
                                            //new DataSheet("RM3-DataSheet", "C:\\Users\\migue\\OneDrive\\Ambiente de Trabalho\\ISEP\\2º ANO\\2º SEMESTRE\\EAPLI\\RM3-DataSheet.pdf")));
        repo.save(new RawMaterial(Designation.valueOf("RM2"), "Tronco de Carvalho", new RawMaterialCategory(Designation.valueOf("C2"), "Madeiras"),null));
                                             //new DataSheet("RM3-DataSheet", "C:\\Users\\migue\\OneDrive\\Ambiente de Trabalho\\ISEP\\2º ANO\\2º SEMESTRE\\EAPLI\\RM3-DataSheet.pdf")));
        ;
        LOGGER.info("»»» created raw materials");

        // findAll
        final Iterable<RawMaterial> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all raw materials");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # raw materials = {}", n);
*/

/*        // ofIdentity
        Designation d1 = Designation.valueOf("RM1");
        Designation d2 = Designation.valueOf("RM2");
        final RawMaterial rm1 = repo.ofIdentity(d1).orElseThrow(IllegalStateException::new);
        final RawMaterial rm2 = repo.ofIdentity(d2).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found Raw Material of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(rm1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains Raw Material of identity");

        // contains
        final boolean has = repo.contains(rm1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains raw materials");

        // delete
        repo.delete(rm1);
        LOGGER.info("»»» delete raw materials");

        // deleteOfIdentity
        repo.deleteOfIdentity(rm2.identity());
        LOGGER.info("»»» delete raw materials of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # products = {}", n2);*/
    }
}
