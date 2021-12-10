package eapli.base.productionordermanagement.application;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Pedro Cardoso
 */
public class ConsultProductionOrdersOfOrderController {
    private final ConsultProductionOrdersOfOrderService svc = new ConsultProductionOrdersOfOrderService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public Iterable<ProductionOrder> consult(String s) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        return this.svc.productionOrderByOrderID(s);
    }
}
