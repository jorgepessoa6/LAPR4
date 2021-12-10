/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Pedro Cardoso
 */
public class ListRawMaterialService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();

    public Iterable<RawMaterial> allRawMaterialCategories() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_PRODUCAO);
        return this.rawMaterialRepository.findAll();
    }
}
