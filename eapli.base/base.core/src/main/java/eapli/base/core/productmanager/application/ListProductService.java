package eapli.base.core.productmanager.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListProductService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository productsRepository = PersistenceContext.repositories().products();

    public Iterable<Product> allProducts() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_PROJETO, BaseRoles.GESTOR_PRODUCAO);
        return this.productsRepository.findAll();
    }
}
