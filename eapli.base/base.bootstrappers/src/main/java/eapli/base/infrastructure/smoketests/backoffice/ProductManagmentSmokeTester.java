/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Pedro Cardoso
 */
public class ProductManagmentSmokeTester  implements Action {
    private static final Logger LOGGER = LogManager.getLogger(ProductManagmentSmokeTester.class);
    final ProductCRUDSmokeTester crudTester = new ProductCRUDSmokeTester();
   
    @Override
    public boolean execute() {
        testProductsCRUD();

        return true;
    }
     private void testProductsCRUD() {
        crudTester.testProductsCRUD();
    }
    
}
