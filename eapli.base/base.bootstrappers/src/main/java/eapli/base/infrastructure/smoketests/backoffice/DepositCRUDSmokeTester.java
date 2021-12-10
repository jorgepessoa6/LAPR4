/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Bruno Pereira
 */
public class DepositCRUDSmokeTester {

    private static final Logger LOGGER = LogManager.getLogger(DepositCRUDSmokeTester.class);

    private final DepositRepository repo = PersistenceContext.repositories().deposits();

    public void testDepositsCRUD() {
        // save
        repo.save(new Deposit(Designation.valueOf("1"), "a"));
        repo.save(new Deposit(Designation.valueOf("2"), "b"));
        LOGGER.info("»»» created deposits");

        // findAll
        final Iterable<Deposit> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all deposits");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # deposits = {}", n);

//        // ofIdentity
//        Designation a = Designation.valueOf("1");
//        Designation b = Designation.valueOf("2");
//        final Deposit d1 = repo.ofIdentity(a).orElseThrow(IllegalStateException::new);
//        final Deposit d2 = repo.ofIdentity(b).orElseThrow(IllegalStateException::new);
//        LOGGER.info("»»» found Deposit of identity");
//
//        // containsOfIdentity
//        final boolean hasId = repo.containsOfIdentity(d1.identity());
//        Invariants.ensure(hasId);
//        LOGGER.info("»»» contains deposit of identity");
//
//        // contains
//        final boolean has = repo.contains(d1);
//        Invariants.ensure(has);
//        LOGGER.info("»»» contains deposits");
//
//        // delete
//        repo.delete(d1);
//        LOGGER.info("»»» delete deposit");
//
//        // deleteOfIdentity
//        repo.deleteOfIdentity(d2.identity());
//        LOGGER.info("»»» delete deposit of identity");
//
//        // size
//        final long n2 = repo.size();
//        Invariants.ensure(n2 == n - 2);
//        LOGGER.info("»»» # deposits = {}", n2);
    }
}
