package eapli.base.persistence.impl.jpa;

import eapli.base.notificationmanagement.domain.Error;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.domain.NotificationStatus;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import javax.persistence.TypedQuery;

public class JpaNotificationRepository extends BasepaRepositoryBase<Notification, Long, Error>
        implements NotificationRepository {

    JpaNotificationRepository() {
        super("error");
    }

    @Override
    public Iterable<Notification> allNotificationsNonArchived() {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s",
                Notification.class);
        query.setParameter("s", NotificationStatus.POR_TRATAR);
        return query.getResultList();
    }
    
    @Override
    public Iterable<Notification> allNotificationsArchived() {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s",
                Notification.class);
        query.setParameter("s", NotificationStatus.ARQUIVADA);
        return query.getResultList();
    }
    
    @Override
    public Iterable<Notification> allNotificationsUntreatedByError(ErrorType errortype) {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s and noti.error.errorType = :e",
                Notification.class);
        query.setParameter("s", NotificationStatus.POR_TRATAR  );
        query.setParameter("e",errortype);
        return query.getResultList();
    }

    @Override
    public Iterable<Notification> allNotificationsUntreatedByProductionLine(ProductionLine productionLine) {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s and noti.productionLine = :pl",
                Notification.class);
        query.setParameter("s", NotificationStatus.POR_TRATAR  );
        query.setParameter("pl",productionLine);
        return query.getResultList();
    }

    @Override
    public Iterable<Notification> allNotificationsUntreatedByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType) {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s and noti.productionLine = :pl and noti.error.errorType=:e",
                Notification.class);
        query.setParameter("s", NotificationStatus.POR_TRATAR  );
        query.setParameter("pl",productionLine);
        query.setParameter("e", errorType);
        return query.getResultList();
    }

    @Override
    public Iterable<Notification> allNotificationsArchivedByError(ErrorType errortype) {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s and noti.error.errorType = :e",
                Notification.class);
        query.setParameter("s", NotificationStatus.ARQUIVADA  );
        query.setParameter("e",errortype);
        return query.getResultList();
    }

    @Override
    public Iterable<Notification> allNotificationsArchivedByProductionLine(ProductionLine productionLine) {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s and noti.productionLine = :pl",
                Notification.class);
        query.setParameter("s", NotificationStatus.ARQUIVADA  );
        query.setParameter("pl",productionLine);
        return query.getResultList();
    }

    @Override
    public Iterable<Notification> allNotificationsArchivedByProductionLineAndErrorType(ProductionLine productionLine, ErrorType errorType) {
        final TypedQuery<Notification> query = entityManager().createQuery(
                "SELECT noti FROM Notification noti WHERE noti.notificationStatus = :s and noti.productionLine = :pl and noti.error.errorType=:e",
                Notification.class);
        query.setParameter("s", NotificationStatus.ARQUIVADA  );
        query.setParameter("pl",productionLine);
        query.setParameter("e", errorType);
        return query.getResultList();
    }
}
