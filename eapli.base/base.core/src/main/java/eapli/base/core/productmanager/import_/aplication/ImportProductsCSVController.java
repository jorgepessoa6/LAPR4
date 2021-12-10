/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.import_.aplication;

import eapli.base.core.productmanager.import_.aplication.ProductImportService;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import java.io.IOException;

/**
 *
 * @author Pedro Cardoso
 */
public class ImportProductsCSVController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductImportService importSvc = new ProductImportService();

    public void importCSV(String filename) throws IOException {
         authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO,BaseRoles.POWER_USER);
        
        importSvc.importCSV(filename);
    }
}
