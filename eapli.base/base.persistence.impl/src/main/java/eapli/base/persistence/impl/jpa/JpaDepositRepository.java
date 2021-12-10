/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.TypedQuery;

/**
 *
 * @author Bruno Pereira
 */
public class JpaDepositRepository extends BasepaRepositoryBase<Deposit, Long, Designation>
        implements DepositRepository {

    JpaDepositRepository() {
        super("cod");
    }

    @Override
    public Deposit depositByDesignation(Designation designation) {
        String s = designation.toString();
        final TypedQuery<Deposit> query = entityManager().createQuery("SELECT m FROM Deposit m WHERE NAME = : s", Deposit.class);
        query.setParameter("s", s);
        return query.getSingleResult();
    }
}
