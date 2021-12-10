package eapli.base.factoryfloor.application;


import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.LinkedHashMap;

/**
 * @author Pedro Cardoso
 */

public class ExportFactoryFloorController {
    private final ExportFactoryFloorService svc = new ExportFactoryFloorService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public void exportElements(LinkedHashMap<String, String> elements, String path) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        try {
            this.svc.export(elements, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
