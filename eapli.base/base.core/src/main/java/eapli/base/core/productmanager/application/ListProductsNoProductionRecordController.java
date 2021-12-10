/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Pedro Cardoso
 */

public class ListProductsNoProductionRecordController {
    private final ListProductsNoProductionRecordService svc = new ListProductsNoProductionRecordService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public Iterable<Product> listarProdutosSemFichaProducao() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        return this.svc.productsNoProductionRecord();
    }
}
