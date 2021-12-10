package eapli.base.app.backoffice.console.presentation.notifications;

import eapli.framework.actions.Action;

import javax.swing.*;

public class ConsultUntreatedNotificationsAction implements Action {
    @Override
    public boolean execute() {
        return new ConsultUntreatedNotificationsUI().show();
    }
}
