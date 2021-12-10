package eapli.base.app.backoffice.presentation.machine;

import eapli.framework.actions.Action;

public class SendConfigRequestAction implements Action {

    @Override
    public boolean execute() {
        return new SendConfigRequestUI().show();
    }


}
