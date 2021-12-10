/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.presentation.machine;

import eapli.base.infrastructure.bootstrapers.demo.MachineBootstrapper;
import eapli.base.infrastructure.bootstrapers.demo.RawMaterialBootstrapper;
import eapli.framework.actions.Action;

/**
 *
 * @author Pedro Cardoso
 */
public class BootstrapMachineAction implements Action {
    
    @Override
    public boolean execute() {
        return new MachineBootstrapper().execute();
    }
}
