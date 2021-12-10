package eapli.base.factoryfloor.application;

import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ExportXMLController {
    private final ExportXMLService svc = new ExportXMLService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public void export(String xmlPath, String xslPath, String extension)  {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        try {
            svc.xmlToHtml(xmlPath, xslPath, extension);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
