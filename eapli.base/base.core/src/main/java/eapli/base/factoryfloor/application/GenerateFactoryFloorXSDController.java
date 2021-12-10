/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloor.application;

import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Bruno Pereira
 */
public class GenerateFactoryFloorXSDController {
    private final GenerateFactoryFloorXSDService svc = new GenerateFactoryFloorXSDService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public void exportXSD(String name) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        try {
            this.svc.export(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
