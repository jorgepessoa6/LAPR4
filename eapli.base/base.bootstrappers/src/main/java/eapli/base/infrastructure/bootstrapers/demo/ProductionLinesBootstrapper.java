package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.productionlinemanagement.application.RegisterProductionLineController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Jorge Pessoa (1180761)
 */

public class ProductionLinesBootstrapper implements Action{

    private static final Logger LOGGER = LogManager.getLogger(ProductionLinesBootstrapper.class);

    @Override
    public boolean execute() {
        register("1f");
        register("2f");
        register("3f");
        register("4f");
        register("5f");
        register("6f");

        return true;
    }

    private void register(final String productionLineId) {
        final RegisterProductionLineController controller = new RegisterProductionLineController();
        try {
            controller.registerProductionLine(productionLineId);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", productionLineId);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}
