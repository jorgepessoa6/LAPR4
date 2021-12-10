//package eapli.base.persistence.impl.inmemory;
//
//import eapli.base.machinemanagement.domain.Machine;
//import eapli.base.machinemanagement.repositories.MachineRepository;
//import eapli.framework.general.domain.model.Designation;
//import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
//
//public class InMemoryMachineRepository extends InMemoryDomainRepository<Designation, Machine> implements MachineRepository {
//
//    static {
//        InMemoryInitializer.init();
//    }
//    @Override
//    public Iterable<Machine> allMachinesNoConfigFile() {
//        return match(Machine::isConfigFileEmpty);
//    }
//}
