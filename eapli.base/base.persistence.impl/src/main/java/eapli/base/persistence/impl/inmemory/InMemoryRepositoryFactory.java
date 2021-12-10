package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.bootstrapers.BaseBootstrapper;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.InMemoryUserRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public ClientUserRepository clientUsers(final TransactionalContext tx) {

        return new InMemoryClientUserRepository();
    }

    @Override
    public ClientUserRepository clientUsers() {
        return clientUsers(null);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return signupRequests(null);
    }

	@Override
	public RawMaterialCategoryRepository categories() {
		return new InMemoryRawMaterialCategoryRepository();
	}

	@Override
	public RawMaterialRepository rawMaterials() {
		return new InMemoryRawMaterialRepository();
	}

    @Override
    public ProductionLineRepository productionLines() {
        return new InMemoryProductionLineRepository();
    }

    @Override
	public SignupRequestRepository signupRequests(final TransactionalContext tx) {
		return new InMemorySignupRequestRepository();
	}


    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

    @Override
    public ProductRepository products() {
        return new InMemoryProductRepository();
    }
    
    @Override
    public DepositRepository deposits() {
        return new InMemoryDepositRepository();
    }

    @Override
    public ProductionOrderRepository productionOrders() {
        return new InMemoryProductionOrderRepository();
    }

    @Override
    public MessageRepository messages() {
        return new InMemoryMessageRepository();
    }

    @Override
    public NotificationRepository notifications() {
        return new InMemoryNotificationRepository();
    }
}
