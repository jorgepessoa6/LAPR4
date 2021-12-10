package eapli.base.app.backoffice.console.presentation;

import eapli.base.infrastructure.bootstrapers.demo.*;
import eapli.framework.actions.Action;

public class BootstrapAllAction implements Action {


    @Override
    public boolean execute() {
        new ProductsBootstrapper().execute();
        new RawMaterialBootstrapper().execute();
        new RawMaterialCategoryBootstrapper().execute();
        new MachineBootstrapper().execute();
        new DepositsBootstrapper().execute();
        new ProductionLinesBootstrapper().execute();
        return true;
    }


}
