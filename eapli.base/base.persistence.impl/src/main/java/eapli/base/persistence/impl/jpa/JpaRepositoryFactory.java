package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
        return new JpaClientUserRepository(autoTx);
    }

    @Override
    public JpaClientUserRepository clientUsers() {
        return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
        return new JpaSignupRequestRepository(autoTx);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
    }

	@Override
	public RawMaterialCategoryRepository categories() {
		return new JpaRawMaterialCategoryRepository();
	}

	@Override
	public RawMaterialRepository rawMaterials() {
		return new JpaRawMaterialRepository();
	}

    @Override
    public ProductionLineRepository productionLines() {
        return new JpaProductionLineRepository();
    }


    @Override
	public TransactionalContext newTransactionalContext() {
		return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}


    @Override
    public ProductRepository products() {
        return new JpaProductRepository();
    }


    @Override
    public DepositRepository deposits() {
        return new JpaDepositRepository();
    }

    @Override
    public ProductionOrderRepository productionOrders() {
        return new JpaProductionOrderRepository();
    }

    @Override
    public MessageRepository messages() {
        return new JpaMessageRepository();
    }

    @Override
    public NotificationRepository notifications() {
        return new JpaNotificationRepository();
    }

}