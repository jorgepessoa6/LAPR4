package eapli.base.app.backoffice.console.presentation.factoryfloor;

import eapli.base.infrastructure.bootstrapers.demo.DepositsBootstrapper;
import eapli.framework.actions.Action;

/**
 *
 * @author Pedro Cardoso
 */

public class ExportFactoryFloorAction  implements Action {

    @Override
    public boolean execute() {
        return new ExportFactoryFloorUI().show();
    }
}
