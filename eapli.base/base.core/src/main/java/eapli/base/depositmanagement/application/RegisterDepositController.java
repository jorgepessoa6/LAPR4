/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.depositmanagement.application;

import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.depositmanagement.repositories.DepositRepository;
/**
 *
 * @author Bruno Pereira
 */
public class RegisterDepositController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final DepositRepository depositRepository = PersistenceContext.repositories().deposits();
    
    public Deposit registerDeposit(String cod,String description) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.GESTOR_CHAO_FABRICA ,BaseRoles.GESTOR_PROJETO);

        final Deposit newDeposit = new Deposit(Designation.valueOf(cod), description);

        return this.depositRepository.save(newDeposit);
    }
}
