package eapli.base.notificationmanagement.repositories;

import eapli.base.machinemanagement.domain.Machine;
import eapli.base.notificationmanagement.domain.Error;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;

/**
 * @author Jorge Pessoa (1180761)
 */

public interface NotificationRepository extends DomainRepository<Error, Notification> {

    Iterable<Notification> allNotificationsNonArchived();
    Iterable<Notification> allNotificationsArchived();
    
    Iterable<Notification> allNotificationsUntreatedByError(ErrorType errorType);
    Iterable<Notification> allNotificationsUntreatedByProductionLine(ProductionLine productionLine);
    Iterable<Notification> allNotificationsUntreatedByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType);
    
    Iterable<Notification> allNotificationsArchivedByError(ErrorType errorType);
    Iterable<Notification> allNotificationsArchivedByProductionLine(ProductionLine productionLine);
    Iterable<Notification> allNotificationsArchivedByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType);
}


