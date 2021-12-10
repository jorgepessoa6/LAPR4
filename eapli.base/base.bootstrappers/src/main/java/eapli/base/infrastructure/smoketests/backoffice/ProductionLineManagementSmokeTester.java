package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Jorge Pessoa (1180761)
 */

public class ProductionLineManagementSmokeTester implements Action {

    private static final Logger LOGGER = LogManager.getLogger(ProductionLineManagementSmokeTester.class);
    final ProductionLineCRUDSmokeTester crudTester = new ProductionLineCRUDSmokeTester();

    @Override
    public boolean execute() {
        testProductionLinesCRUD();

        return true;
    }
    private void testProductionLinesCRUD() {
        crudTester.testProductionLinesCRUD();
    }
}
