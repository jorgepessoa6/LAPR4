package eapli.base.notificationmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;


public class ConsultUntreatedNotificationsController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final NotificationRepository notificationRepository = PersistenceContext.repositories().notifications();

    public Iterable<Notification> consultUntreatedNotificationsByError(ErrorType errorType) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);
       return notificationRepository.allNotificationsUntreatedByError(errorType);

    }

    public Iterable<Notification> consultUntreatedNotificationsByProductionLine(ProductionLine productionLine){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.GESTOR_PROJETO);
        return notificationRepository.allNotificationsUntreatedByProductionLine(productionLine);
    }

    public Iterable<Notification> consultUntreatedNotificationsByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);
        return notificationRepository.allNotificationsUntreatedByProductionLineAndErrorType(productionLine, errorType);
    }

    public Iterable<Notification> consultEveryUntreatedNotifications(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);
        return notificationRepository.allNotificationsNonArchived();
    }

    public Iterable<Notification> notifications() {
        return this.notificationRepository.allNotificationsNonArchived();
    }
}
