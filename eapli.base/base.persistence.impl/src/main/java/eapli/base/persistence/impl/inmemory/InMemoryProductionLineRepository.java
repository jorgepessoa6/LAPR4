package eapli.base.persistence.impl.inmemory;

import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * @author Jorge Pessoa (1180761)
 */

public class InMemoryProductionLineRepository extends InMemoryDomainRepository<ProductionLine, Designation> implements ProductionLineRepository {

    static {
        InMemoryInitializer.init();
    }


    @Override
    public Iterable<Machine> allMachinesNoConfigFile() {
        return null;
    }

    @Override
    public Machine machineByAddress(Designation id) {
        return null;
    }

    @Override
    public Iterable<Machine> machineByAddress() {
        return null;
    }

    @Override
    public Machine machineByAddress(String ide) {
        return null;
    }

    @Override
    public Iterable<ProductionLine> nonActiveForProcess() {
        return null;
    }

    @Override
    public ProductionLine productionLineByDesig(Designation id) {
        return null;
    }
}
