package eapli.base.app.backoffice.console.presentation.notifications;


import eapli.base.notificationmanagement.domain.Notification;
import eapli.framework.visitor.Visitor;

public class NotificationsPrinter implements Visitor<Notification> {

    @Override
    public void visit(final Notification visitee) {
        System.out.printf("%-50s%-30s%-30s%-30s",visitee.getMessage().getDesc(), visitee.getError().getDescription(), visitee.getError().getErrorType(), visitee.getProductionLine().identity());
    }
}
