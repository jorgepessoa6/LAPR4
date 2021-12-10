package eapli.base.productionlinemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListProductionLineService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();

    public Iterable<ProductionLine> allRawMaterialCategories() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA);
        return this.productionLineRepository.findAll();
    }
}
