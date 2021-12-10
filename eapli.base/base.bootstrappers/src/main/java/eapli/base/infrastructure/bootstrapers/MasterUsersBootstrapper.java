/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.base.infrastructure.bootstrapers;

import java.util.HashSet;
import java.util.Set;

import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * @author Paulo Gandra Sousa
 */
public class MasterUsersBootstrapper extends UsersBootstrapperBase implements Action {

    @Override
    public boolean execute() {
        registerAdmin("admin", TestDataConstants.PASSWORD1, "Jane", "Doe Admin",
                "jane.doe@email.local");
        registerProjectManager("ProjectManager", TestDataConstants.PASSWORD1, "Eusebio", "oGrande",
                "jane.doe@email.local");
        registerProductionManager("ProductionManager", TestDataConstants.PASSWORD1, "Felica", "Feliz",
                "jane.doe@email.local");
        registerFactoryFloorManager("GroundFloorManager", TestDataConstants.PASSWORD1, "Oracio", "Oraculo",
                "jane.doe@email.local");
        return true;
    }

    /**
     *
     */
    private void registerAdmin(final String username, final String password, final String firstName,
            final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.ADMIN);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerProjectManager(final String username, final String password, final String firstName,
            final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.GESTOR_PROJETO);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerProductionManager(final String username, final String password, final String firstName,
            final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.GESTOR_PRODUCAO);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerFactoryFloorManager(final String username, final String password, final String firstName,
            final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.GESTOR_CHAO_FABRICA);

        registerUser(username, password, firstName, lastName, email, roles);
    }
}
