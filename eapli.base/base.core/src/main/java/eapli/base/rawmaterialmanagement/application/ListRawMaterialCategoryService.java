package eapli.base.rawmaterialmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;


/**
 * @author Miguel Fortes (1171611)
 */
public class ListRawMaterialCategoryService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialCategoryRepository rawMaterialCategoryRepository = PersistenceContext.repositories().categories();

    public Iterable<RawMaterialCategory> allRawMaterialCategories() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_PRODUCAO);
        return this.rawMaterialCategoryRepository.findAll();
    }

}
