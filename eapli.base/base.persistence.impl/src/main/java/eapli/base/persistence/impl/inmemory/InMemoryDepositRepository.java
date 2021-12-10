/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 *
 * @author Bruno Pereira
 */
public class InMemoryDepositRepository extends InMemoryDomainRepository<Deposit, Designation>
        implements DepositRepository {
    
    static {
        InMemoryInitializer.init();
    }

    @Override
    public Deposit depositByDesignation(Designation designation) {
        return null;
    }
}
