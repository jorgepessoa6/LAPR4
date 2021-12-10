package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RawMaterialManagementSmokeTester implements Action {

    private static final Logger LOGGER = LogManager.getLogger(RawMaterialManagementSmokeTester.class);
    final RawMaterialCRUDSmokeTester rawMaterialCRUDSmokeTester = new RawMaterialCRUDSmokeTester();
    final RawMaterialCategoryCRUDSmokeTester rawMaterialCategoryCRUDSmokeTester = new RawMaterialCategoryCRUDSmokeTester();

    @Override
    public boolean execute() {
        testRawMaterialsCRUD();
        testRawMaterialCategoryCRUD();


        return true;
    }

    private void testRawMaterialsCRUD() {
        rawMaterialCRUDSmokeTester.testRawMaterialsCRUD();
    }

    private void testRawMaterialCategoryCRUD() {
        rawMaterialCategoryCRUDSmokeTester.testRawMaterialCategoryCRUD();
    }
}
