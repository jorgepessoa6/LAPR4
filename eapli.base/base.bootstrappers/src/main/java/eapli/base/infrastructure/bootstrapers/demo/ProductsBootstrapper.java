/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.core.productmanager.application.RegisterProductController;
import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author Pedro Cardoso
 */
public class ProductsBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(ProductsBootstrapper.class);
    
    @Override
    public boolean execute() {
        register("1f","1c","bola","bola borracha","UN","TT-CC");
        register("2f","2c","papel","papel","UN","TT-CC");
        register("3f","3c","bota","bota militar","UN","TT-CC");
        register("4f","4c","camisa","camisa premium","UN","TT-CC");
        register("5f","5c","fe","fe e vontade","UN","TT-CC");
        register("6f","6c","cadeira","cadeira madeira","UN","TT-CC");
        
        return true;
    }
     private void register(final String codFactory, final String codComercial, final String descBrief,
                             final String descComplete, final String unit, final String cat) {
        final RegisterProductController controller = new RegisterProductController();
        try {
            controller.registerProduct(codFactory, codComercial,descBrief, descComplete,unit,cat);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", codFactory);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
    
}
