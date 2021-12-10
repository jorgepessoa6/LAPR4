/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.productionlines;

import eapli.base.infrastructure.bootstrapers.demo.DepositsBootstrapper;
import eapli.base.infrastructure.bootstrapers.demo.ProductionLinesBootstrapper;
import eapli.framework.actions.Action;

/**
 *
 * @author Pedro Cardoso
 */
public class BootstrapProdcutionLineAction implements Action {
    
    @Override
    public boolean execute() {
        return new ProductionLinesBootstrapper().execute();
    }
}
