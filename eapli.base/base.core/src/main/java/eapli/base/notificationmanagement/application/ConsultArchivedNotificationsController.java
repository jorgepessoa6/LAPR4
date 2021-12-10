/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.notificationmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;


/**
 *
 * @author Bruno Pereira
 */
public class ConsultArchivedNotificationsController {
     private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final NotificationRepository notificationRepository = PersistenceContext.repositories().notifications();

    public Iterable<Notification> consultArchivedNotificationsByError(ErrorType errorType) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);
       return notificationRepository.allNotificationsArchivedByError(errorType);

    }

    public Iterable<Notification> consultArchivedNotificationsByProductionLine(ProductionLine productionLine){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.GESTOR_PROJETO);
        return notificationRepository.allNotificationsArchivedByProductionLine(productionLine);
    }

    public Iterable<Notification> consultArchivedNotificationsByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);
        return notificationRepository.allNotificationsArchivedByProductionLineAndErrorType(productionLine, errorType);
    }

    public Iterable<Notification> consultAllArchivedNotifications(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);
        return notificationRepository.allNotificationsArchived();
    }

    public Iterable<Notification> notifications() {
        return this.notificationRepository.allNotificationsArchived();
    }
}
