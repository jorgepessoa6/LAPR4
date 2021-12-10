/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.depositmanagement.repositories;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.domain.repositories.DomainRepository;

/**
 *
 * @author Bruno Pereira
 */
public interface DepositRepository extends DomainRepository<Designation, Deposit>{
    /**
    *empty
    */

    Deposit depositByDesignation(Designation designation);
}
