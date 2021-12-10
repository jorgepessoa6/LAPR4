//package eapli.base.persistence.impl.jpa;
//
//import eapli.base.machinemanagement.domain.Machine;
//import eapli.base.machinemanagement.repositories.MachineRepository;
//import eapli.framework.general.domain.model.Designation;
//
//import javax.persistence.TypedQuery;

//    class JpaMachineRepository extends BasepaRepositoryBase<Machine, Long, Designation> implements MachineRepository {
//
//        JpaMachineRepository() {
//            super(internalCode);
//        }
//
//        @Override
//        public Iterable<Machine> allMachinesNoConfigFile() {
//            final TypedQuery<Machine> query = entityManager().createQuery(
//                    "SELECT m FROM Machine m WHERE PRECORD_PK IS NULL",
//                    Machine.class);
//            return query.getResultList();
//        }
//
//
//
//    }
//}
