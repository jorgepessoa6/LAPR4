package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.framework.actions.Action;

public class RegisterProductionOrderAction implements Action {
    @Override
    public boolean execute() {
        return new RegisterProductionOrderUI().show();
    }
}
