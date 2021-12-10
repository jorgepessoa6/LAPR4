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
 * @author Bruno Pereira
 */
public class DepositManagementSmokeTester implements Action{
    
    private static final Logger LOGGER = LogManager.getLogger(DepositManagementSmokeTester.class);
    final DepositCRUDSmokeTester crudTester = new DepositCRUDSmokeTester();
   
    @Override
    public boolean execute() {
        testDepositsCRUD();

        return true;
    }
     private void testDepositsCRUD() {
        crudTester.testDepositsCRUD();
    }
    
}
