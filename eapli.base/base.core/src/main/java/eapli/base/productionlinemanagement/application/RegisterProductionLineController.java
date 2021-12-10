package eapli.base.productionlinemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * @author Jorge Pessoa (1180761)
 */

public class RegisterProductionLineController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionLineRepository newProductionLineRepository = PersistenceContext.repositories().productionLines();

    public ProductionLine registerProductionLine(final String productionLineId) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA,BaseRoles.GESTOR_PROJETO);

        final ProductionLine newProductionLine = new ProductionLine(Designation.valueOf(productionLineId));

        return this.newProductionLineRepository.save(newProductionLine);
    }
}
