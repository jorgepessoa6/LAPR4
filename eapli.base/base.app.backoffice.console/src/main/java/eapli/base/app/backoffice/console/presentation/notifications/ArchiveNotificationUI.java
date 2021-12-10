package eapli.base.app.backoffice.console.presentation.notifications;

import eapli.base.notificationmanagement.application.ArchiveNotificationController;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import org.apache.commons.collections.IteratorUtils;

import java.util.ArrayList;
import java.util.List;

public class ArchiveNotificationUI extends AbstractUI {

    private final ArchiveNotificationController theController = new ArchiveNotificationController();

    @Override
    protected boolean doShow() {
        Iterable<Notification> notifications = this.theController.notifications();
        List<Notification> not = IteratorUtils.toList(notifications.iterator());
        boolean flag = true;
        List<Notification> notificationList = new ArrayList<>();
        while(flag){
            final SelectWidget<Notification> selector = new SelectWidget<>("Error Notifications: ", not, new NotificationsPrinter());
            selector.show();

            Notification notification = selector.selectedElement();
            if(notification != null) {
                notificationList.add(notification);
                not.remove(notification);
            }

            final String str = Console.readLine("Archive Another Notification? [Y/N]");
            if (str.equalsIgnoreCase("N")) {
                flag = false;
            }
        }
        this.theController.archiveNotifications(notificationList);

        return false;
    }

    @Override
    public String headline() {
        return "Archive Notifications";
    }
}
