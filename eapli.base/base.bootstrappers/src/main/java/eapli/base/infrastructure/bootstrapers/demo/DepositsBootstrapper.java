/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.depositmanagement.application.RegisterDepositController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Bruno Pereira
 */
public class DepositsBootstrapper implements Action{
  private static final Logger LOGGER = LogManager.getLogger(DepositsBootstrapper.class);
    
    @Override
    public boolean execute() {
        register("1","a");
        register("2","b");
        register("3","c");
        register("4","d");
        register("5","e");
        register("6","f");
        
        return true;
    }
     private void register(final String cod, final String description) {
        final RegisterDepositController controller = new RegisterDepositController();
        try {
            controller.registerDeposit(cod, description);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", cod);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
    
    
}
