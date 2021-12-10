package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.application.RegisterRawMaterialController;
import eapli.base.rawmaterialmanagement.domain.DataSheet;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Designation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RawMaterialBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(RawMaterialBootstrapper.class);
    private RawMaterialCategoryRepository repo = PersistenceContext.repositories().categories();

    @Override
    public boolean execute() {

        repo.save(new RawMaterialCategory(Designation.valueOf("CTESTE"), "Test"));
        final RawMaterialCategory c1 = repo.ofIdentity(Designation.valueOf("CTESTE")).orElseThrow(IllegalStateException::new);

        register("RM1", "Verniz de Madeira", c1, null);
/*        register("RM2", "Tronco de Carvalho", c1, "sgdjdg");
        register("RM3", "Barra de aço", c1, "jkakf");
        register("RM4", "Tecido de Seda", c1, "fgwkfgfru");
        register("RM5", "Tubos de Plástico", c1, "ffuewgfougf");
        register("RM6", "WD-40", c1, "fwrgebh");*/

        return true;
    }

    private void register(final String internalCode, final String description, final RawMaterialCategory category, final DataSheet datasheet) {
        final RegisterRawMaterialController controller = new RegisterRawMaterialController();
        try {
            controller.registerRawMaterial(internalCode, description, category, datasheet);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", internalCode);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}

