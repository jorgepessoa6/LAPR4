package eapli.base.productionordermanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.Status;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Pedro Cardoso
 */
public class ConsultProductionOrdersOfOrderService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionOrderRepository repo = PersistenceContext.repositories().productionOrders();

    public Iterable<ProductionOrder> productionOrderByOrderID(String  s) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);

        return this.repo.allproductionOrderByOrderID(s);
    }

}
