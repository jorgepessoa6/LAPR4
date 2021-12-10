package eapli.base.productionlinemanagement.repositories;

import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;

/**
 * @author Jorge Pessoa (1180761)
 */

public interface ProductionLineRepository extends DomainRepository<Designation, ProductionLine> {

    Iterable<Machine> allMachinesNoConfigFile();

    Machine machineByAddress(Designation id);

    Iterable<Machine> machineByAddress();
     Machine machineByAddress(String ide);

     Iterable<ProductionLine> nonActiveForProcess();

     ProductionLine productionLineByDesig(Designation id);
}


