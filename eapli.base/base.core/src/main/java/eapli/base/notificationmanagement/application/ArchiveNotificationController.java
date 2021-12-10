package eapli.base.notificationmanagement.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.domain.NotificationStatus;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.List;

public class ArchiveNotificationController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final NotificationRepository notificationRepository = PersistenceContext.repositories().notifications();

    public void archiveNotifications(final List<Notification> notificationList) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);

        for(Notification notification: notificationList){
            notification.setNotificationStatus(NotificationStatus.ARQUIVADA);
            notificationRepository.save(notification);
        }
    }

    public Iterable<Notification> notifications() {
        return this.notificationRepository.allNotificationsNonArchived();
    }
}
