package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.rawmaterialmanagement.application.RegisterRawMaterialCategoryController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RawMaterialCategoryBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(RawMaterialCategoryBootstrapper.class);


    @Override
    public boolean execute() {
        register("C1", "Vernizes");
        register("C2", "Madeiras");
        register("C3", "Metais");
        register("C4", "Tecidos");
        register("C5", "Plásticos");
        register("C6", "Lubrificantes");

        return true;
    }

    private void register(final String code, final String description) {
        final RegisterRawMaterialCategoryController controller = new RegisterRawMaterialCategoryController();
        try {
            controller.registerRawMaterialCategory(code, description);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", code);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}
