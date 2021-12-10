package eapli.base.persistence.impl.inmemory;

import eapli.base.notificationmanagement.domain.Error;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryNotificationRepository extends InMemoryDomainRepository<Notification, Error> implements NotificationRepository {
    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<Notification> allNotificationsNonArchived() {
        return null;
    }
    
    @Override
    public Iterable<Notification> allNotificationsArchived() {
        return null;
    }

    @Override
    public Iterable<Notification> allNotificationsUntreatedByError(ErrorType errortype) {
        return null;
    }
    @Override
   public Iterable<Notification> allNotificationsUntreatedByProductionLine(ProductionLine productionLine){
        return null;
    }
   @Override
    public Iterable<Notification> allNotificationsUntreatedByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType){
        return null;
    }
    
    @Override
    public Iterable<Notification> allNotificationsArchivedByError(ErrorType errortype) {
        return null;
    }
    @Override
   public Iterable<Notification> allNotificationsArchivedByProductionLine(ProductionLine productionLine){
        return null;
    }
   @Override
    public Iterable<Notification> allNotificationsArchivedByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType){
        return null;
    }
}
