package eapli.base.persistence.impl.jpa;

import eapli.base.messagemanagement.domain.Message;
import eapli.base.messagemanagement.domain.MessageStatus;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.domain.NotificationStatus;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Calendar;

public class JpaMessageRepository extends BasepaRepositoryBase<Message, Long, Calendar> implements MessageRepository {

    JpaMessageRepository() {
        super("messageId");
    }


    public Iterable<Message> allMessagesByTime(Calendar d1, Calendar d2) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT mes FROM Message mes WHERE mes.messageDateCreation BETWEEN :d1 AND :d2",
                Message.class);
        query.setParameter("d1", d1, TemporalType.DATE);
        query.setParameter("d2", d2, TemporalType.DATE);
        return query.getResultList();
    }

    @Override
    public Iterable<Message> allMessagesToProcess() {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT mess FROM Message mess WHERE mess.messageState = :s",
                Message.class);
        query.setParameter("s", MessageStatus.POR_PROCESSAR);
        return query.getResultList();
    }

    @Override
    public Iterable<Message> allMessagesToProcessByProductionLine(ProductionLine productionLine) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT mess FROM Message mess WHERE mess.messageState = :s AND mess.mach.productionLine = :pl",
                Message.class);
        query.setParameter("s", MessageStatus.POR_PROCESSAR);
        query.setParameter("pl", productionLine);
        return query.getResultList();
    }

    @Override
    public Iterable<Message> allMessagesToProcessByProductionLineByTime(ProductionLine productionLine, Calendar d1, Calendar d2) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT mess FROM Message mess WHERE mess.messageState = :s AND mess.mach.productionLine = :pl AND mess.messageDateCreation BETWEEN :d1 AND :d2",
                Message.class);
        query.setParameter("s", MessageStatus.POR_PROCESSAR);
        query.setParameter("pl", productionLine);
        query.setParameter("d1", d1, TemporalType.DATE);
        query.setParameter("d2", d2, TemporalType.DATE);
        return query.getResultList();
    }
}
