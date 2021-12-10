/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.application;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.ProductionOrderStatus;
import eapli.base.productionordermanagement.domain.Status;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Bruno Pereira
 */
public class ListProductionOrdersByStatusController {
    private final ListProductionOrdersByStatusService svc = new ListProductionOrdersByStatusService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public Iterable<ProductionOrder> listarOrdensDeProducaoPorEstado(Status status) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        return this.svc.productionOrdersByStatus(status);
    }
}
