package eapli.base.productionordermanagment.import_.application;

import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Pedro Cardoso
 */
public class ImportProductionOrderCsvController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ImportProdutionOrderService svc = new ImportProdutionOrderService();
    public void importCSV(String filename,String divider) throws IOException, ParseException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO,BaseRoles.POWER_USER);

        svc.importCSV(filename,divider);
    }
}
