/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.core.productmanager.repositories.ProductRepository;

/**
 *
 * @author Pedro Cardoso
 */
public class ListProductsNoProductionRecordService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository produtoRepository = PersistenceContext.repositories().products();


    public Iterable<Product> productsNoProductionRecord() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);

        return this.produtoRepository.allProductsNoProductionRecord();
    }
}
