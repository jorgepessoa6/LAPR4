package eapli.base.persistence.impl.jpa;


import eapli.base.machinemanagement.domain.Machine;
import eapli.base.messagemanagement.domain.Message;
import eapli.base.messagemanagement.domain.MessageStatus;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.domain.ProductionLineStatus;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.TypedQuery;

/**
 * @author Jorge Pessoa (1180761)
 */

public class JpaProductionLineRepository extends BasepaRepositoryBase<ProductionLine, Long, Designation> implements ProductionLineRepository {

    JpaProductionLineRepository() {
        super("productionLineId");
    }

    @Override
    public Iterable<Machine> allMachinesNoConfigFile() {
        final TypedQuery<Machine> query = entityManager().createQuery(
                "SELECT m FROM Machine m WHERE CONFIG IS NULL",
                Machine.class);

        return query.getResultList();
    }

//    @Override
//    public Iterable<Machine> allMachinesStated() {
//        final TypedQuery<Machine> query = entityManager().createQuery(
//                "SELECT m FROM Machine m WHERE m.machState IS NOT NULL",
//                Machine.class);
//
//        return query.getResultList();
//    }



    @Override
    public Machine machineByAddress(Designation ide) {
        String s = ide.toString();
        final TypedQuery<Machine> query = entityManager().createQuery("SELECT m FROM Machine m WHERE NAME = : s", Machine.class);
        query.setParameter("s", s);
        return query.getSingleResult();
    }
    @Override
    public Iterable<Machine> machineByAddress() {
        final TypedQuery<Machine> query = entityManager().createQuery("SELECT m FROM Machine m where m.networkAddress is not null", Machine.class);
        return query.getResultList();
    }

    @Override
    public Machine machineByAddress(String ide) {
        final TypedQuery<Machine> query = entityManager().createQuery("SELECT m FROM Machine m WHERE m.networkAddress = : s", Machine.class);
        query.setParameter("s", ide);
        return query.getSingleResult();
    }

    @Override
    public Iterable<ProductionLine> nonActiveForProcess() {
        final TypedQuery<ProductionLine> query = entityManager().createQuery(
                "SELECT pl FROM ProductionLine pl WHERE pl.productionLineStatus = :s",
                ProductionLine.class);
        query.setParameter("s", ProductionLineStatus.NON_ACTIVE);
        return query.getResultList();
    }

    @Override
    public ProductionLine productionLineByDesig(Designation id) {
        String s = id.toString();
        final TypedQuery<ProductionLine> query = entityManager().createQuery("SELECT m FROM ProductionLine m WHERE NAME = : s", ProductionLine.class);
        query.setParameter("s", s);
        return query.getSingleResult();
    }
}