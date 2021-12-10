package eapli.base.app.backoffice.console.presentation.notifications;

import eapli.framework.actions.Action;

public class ArchiveNotificationAction implements Action {
    @Override
    public boolean execute() {
        return new ArchiveNotificationUI().show();
    }
}
