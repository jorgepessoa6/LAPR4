package eapli.base.productionlinemanagement.domain;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * @author Jorge Pessoa (1180761)
 */

public class ListProductionLineService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionLineRepository productionLineRepositoryRepository = PersistenceContext.repositories().productionLines();

    public Iterable<ProductionLine> allProductionLines() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_PROJETO, BaseRoles.GESTOR_CHAO_FABRICA);
        return this.productionLineRepositoryRepository.findAll();
    }
}
