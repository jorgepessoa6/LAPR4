package eapli.base.messagemanagement.repositories;

import eapli.base.messagemanagement.domain.Message;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Calendar;

public interface MessageRepository extends DomainRepository<Calendar, Message> {

    Iterable<Message> allMessagesByTime(Calendar d1, Calendar d2);

    Iterable<Message> allMessagesToProcess();

    Iterable<Message> allMessagesToProcessByProductionLine(ProductionLine productionLine);
    Iterable<Message> allMessagesToProcessByProductionLineByTime(ProductionLine productionLine, Calendar d1, Calendar d2);
}
