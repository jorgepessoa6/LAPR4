package eapli.base.app.backoffice.console.presentation.factoryfloor;

import eapli.framework.actions.Action;

public class ExportXMLAction implements Action {
    @Override
    public boolean execute() {
        return new ExportXMLUI().show();
    }
}
