package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.application.RegisterMachineController;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Designation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Rui Mendes (1170385)
 */

public class MachineBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(MachineBootstrapper.class);
    private ProductionLineRepository repo= PersistenceContext.repositories().productionLines();
    @Override
    public boolean execute() {
        repo.save(new ProductionLine(Designation.valueOf("7f")));
        final ProductionLine pl1 = repo.ofIdentity(Designation.valueOf("7f")).orElseThrow(IllegalStateException::new);
        //final ProductionLine pl2 = repo.ofIdentity(Designation.valueOf("2f")).orElseThrow(IllegalStateException::new);

        register("1", "000AA1", "Machine 1", "Bosh","k103","10.8.0.82", pl1);
        //register("2", "000AA2", "Machine 2", "Bish", "k107","10", pl2);


        return true;
    }

    private void register(final String internalCode, final String seriesNumber,
                          final String description, final String brand, String model,String network, ProductionLine pl) {
        final RegisterMachineController controller = new RegisterMachineController();
        try {

            controller.registerMachine(internalCode, seriesNumber, description, brand, model, network, pl);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", internalCode);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}
