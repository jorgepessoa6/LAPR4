package eapli.base.persistence.impl.inmemory;

import eapli.base.messagemanagement.domain.Message;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Calendar;

public class InMemoryMessageRepository extends InMemoryDomainRepository<Message, Calendar> implements MessageRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<Message> allMessagesByTime(Calendar d1, Calendar d2) {
        return null;
    }

    @Override
    public Iterable<Message> allMessagesToProcess() {
        return null;
    }

    @Override
    public Iterable<Message> allMessagesToProcessByProductionLine(ProductionLine productionLine) {
        return null;
    }

    @Override
    public Iterable<Message> allMessagesToProcessByProductionLineByTime(ProductionLine productionLine, Calendar d1, Calendar d2) {
        return null;
    }
}
