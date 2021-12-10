/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.Designation;


/**
 *
 * @author Pedro Cardoso
 */
@UseCaseController
public class RegisterProductController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    public Product registerProduct(String codFabrica,String codComercial, String descBreve,String descCompleta,String unit,String cat) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO,BaseRoles.POWER_USER,BaseRoles.GESTOR_PROJETO );

        final Product newProduct = new Product(Designation.valueOf(codFabrica),codComercial,
                descBreve,descCompleta,unit,cat);

        return this.productRepository.save(newProduct);
    }
}
