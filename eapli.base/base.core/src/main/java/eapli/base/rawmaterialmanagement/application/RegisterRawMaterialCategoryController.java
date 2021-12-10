package eapli.base.rawmaterialmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;


/**
 * @author Miguel Fortes (1171611)
 */
@UseCaseController
public class RegisterRawMaterialCategoryController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialCategoryRepository repository = PersistenceContext.repositories().categories();

    public RawMaterialCategory registerRawMaterialCategory(final String code, final String description) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_PRODUCAO,BaseRoles.GESTOR_PROJETO);
        final RawMaterialCategory newRawMaterialCategory = new RawMaterialCategory(Designation.valueOf(code), description);
        return this.repository.save(newRawMaterialCategory);
    }



}
