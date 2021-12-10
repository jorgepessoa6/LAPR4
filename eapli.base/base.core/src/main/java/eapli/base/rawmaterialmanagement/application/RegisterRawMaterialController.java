package eapli.base.rawmaterialmanagement.application;


import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.DataSheet;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.application.UseCaseController;


/**
 * @author Miguel Fortes (1171611)
 */
@UseCaseController
public class RegisterRawMaterialController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ListRawMaterialCategoryService svc = new ListRawMaterialCategoryService();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();

    public RawMaterial registerRawMaterial(final String internalCode, final String description, final RawMaterialCategory category, final DataSheet datasheet) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_PRODUCAO,BaseRoles.GESTOR_PROJETO);

        final RawMaterial newRawMaterial = new RawMaterial(Designation.valueOf(internalCode), description, category, datasheet);

        return this.rawMaterialRepository.save(newRawMaterial);
    }

    public Iterable<RawMaterialCategory> rawMaterialCategories() {
        return this.svc.allRawMaterialCategories();
    }

}
