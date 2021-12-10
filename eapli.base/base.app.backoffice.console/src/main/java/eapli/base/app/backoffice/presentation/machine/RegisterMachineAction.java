package eapli.base.app.backoffice.presentation.machine;

import eapli.framework.actions.Action;

/**
 * @author Rui Mendes (1170385)
 */

public class RegisterMachineAction implements Action {
    @Override
    public boolean execute() {
        return new RegisterMachineUI().show();
    }
}
