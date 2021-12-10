package eapli.base.app.backoffice.presentation.machine;

import eapli.framework.actions.Action;

public class ImportConfigTXTFileAction implements Action {
    @Override
    public boolean execute() {
        return new ImportConfigTXTFileUI().show();
    }
}
